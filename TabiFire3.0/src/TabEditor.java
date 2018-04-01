// Imports
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * The TabEditor Panel loads a given tab and displays it for editing.
 * @author jackreamy
 */
public class TabEditor extends JPanel implements TabDisplay
{
    // Instance variables
    private int _tabPosition = 0;
    private int _tabDist = 20;
    public final static int _defaultDist = 32;
    private TAB _tab;
    
    // TAB display things
    private final JTextArea[] _tabLines;
    private String _tabTuning;
    private String _tabBlank;
    private String _tabDivider;
    private String _tabEmpty;
    private String[] _tabDisplay;
    private final JPanel _tabPanel;
    
    // TAB Editing Buttons
    private final JButton _editUpdate;
    private final JButton _editRight;
    private final JButton _editFarRight;
    private final JButton _editLeft;
    private final JButton _editFarLeft;
    private final JButton _editPlus;
    private final JButton _editMinus;
    private final JPanel _editPanel;
    
    // Copy to clipboard
    private final JButton _copyButton;
    private final JPanel _copyPanel;
    private final JTextArea _copyText;
    private final JTextArea _copyNumber;
    
    /**
     * The panel constructor.
     */
    public TabEditor()
    {
        // =============================================== //
        //                   TAB display                   //
        // =============================================== //
        
        // Set up the panel for the tab
        _tabPanel = new JPanel();
        _tabPanel.setLayout(new GridBagLayout());
        
        // Set up the tab columns
        _tabLines = new JTextArea[5 + _defaultDist * 2];
        
        // Add each text column to the tab panel
        for (int i = 0; i < _tabLines.length; i++)
        {
            // Create the new text area
            _tabLines[i] = new JTextArea(6, 1);
            
            // Set the font
            _tabLines[i].setFont(new Font("Consolas", 0, 14));
            
            // Add the text area to the panel
            _tabPanel.add(_tabLines[i]);
        }
        
        // Initializes the array list of strings which will 
        // hold the column text of the tabline
        _tabDisplay = new String[_defaultDist];
        
        // =============================================== //
        //                  Editing Tools                  //
        // =============================================== //
        
        // Right button
        _editRight = new JButton(" >> ");
        _editRight.addActionListener(new EditButtonRight());
        
        // Far Right button
        _editFarRight = new JButton(" >>> ");
        _editFarRight.addActionListener(new EditButtonFarRight());
        
        // Left button
        _editLeft = new JButton(" << ");
        _editLeft.addActionListener(new EditButtonLeft());
        
        // Far Left Button
        _editFarLeft = new JButton(" <<< ");
        _editFarLeft.addActionListener(new EditButtonFarLeft());
        
        // Update Button
        _editUpdate = new JButton(" Update ");
        _editUpdate.addActionListener(new EditButtonUpdate());
        
        // Plus Button
        _editPlus = new JButton (" + ");
        _editPlus.addActionListener(new EditButtonPlus());
        
        // Minus Button
        _editMinus = new JButton (" - ");
        _editMinus.addActionListener(new EditButtonMinus());
        
        // Initialize the Edit panel
        _editPanel = new JPanel();
        _editPanel.setLayout(new GridBagLayout());
        
        // Add things to the edit Panel
        _editPanel.add(_editFarLeft);
        _editPanel.add(_editLeft);
        _editPanel.add(_editMinus);
        _editPanel.add(_editUpdate);
        _editPanel.add(_editPlus);
        _editPanel.add(_editRight);
        _editPanel.add(_editFarRight);
        
        // =============================================== //
        //                  Copying Tools                  //
        // =============================================== //
        
        // Copy Button
        _copyButton = new JButton(" Copy to Clipboard ");
        _copyButton.addActionListener(new CopyButton());
        
        // Copy Text Field
        _copyText = new JTextArea(1, 6);
        _copyText.setEditable(false);
        _copyText.setText("Measures per line : ");
        
        // The number text field
        _copyNumber = new JTextArea(1, 3);
        _copyNumber.setText("2");
        
        // Set up the copy panel
        _copyPanel = new JPanel();
        _copyPanel.setLayout(new FlowLayout());
        
        // Add the copy things to the copy panel
        _copyPanel.add(_copyButton);
        _copyPanel.add(_copyText);
        _copyPanel.add(_copyNumber);
        
        // =============================================== //
        //                  Construction                   //
        // =============================================== //
        
        // Set the layout of the TabEditor panel
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // Add the other panels to the TabEditor Panel
        this.add(_tabPanel);
        this.add(_editPanel);
        this.add(_copyPanel);
        
        // Set the panel visible
        this.setVisible(true);
    }
    
    /**
     * Loads the input tab into the TabEditor panel / display.
     * @param tab the input tab to edit.
     */
    public void setTAB(TAB tab)
    {
        // Get the TAB to display
        _tab = tab;
                
        // Zero the position
        _tabPosition = 0;
        
        // Update the distribution
        updateDist();
        
        // Get the tuning
        double tuning[] = _tab.getBaseNotes();
        
        // Set the line Strings
        _tabTuning = TAB.frequencyToNote(tuning[0]);
        _tabBlank = "-";
        _tabDivider = "|";
        _tabEmpty = " ";
        
        // Iterate through the base notes setting default tab lines
        for (int i = 1; i < tuning.length; i++)
        {
            _tabTuning += "\n" + TAB.frequencyToNote(tuning[i]);
            _tabBlank += "\n-";
            _tabDivider += "\n|";
            _tabEmpty += "\n ";
        }
        
        // First few lines
        _tabLines[0].setText(_tabEmpty);
        _tabLines[1].setText(_tabTuning);
        _tabLines[2].setText(_tabDivider);
        _tabLines[3].setText(_tabBlank);        
        
        // Set the first few lines uneditable
        for(int i = 0; i < 4; i++)
        {
            _tabLines[i].setEditable(false);
        }
        
        // All the blank lines (every other) not editable
        for (int i = 5; i < _tabLines.length; i += 2)
        {
            _tabLines[i].setText(_tabBlank);
            _tabLines[i].setEditable(false);
        }
        
        // Set the last line of the tab to a series of '|'
        _tabLines[4 + 2 * _defaultDist].setText(_tabDivider);
        
        // Update the display
        displayTAB();
    }
    
    /**
     * Updated the display of the current tab held in _tab.
     * This one was really messy and I got it working by the
     * grace of God.
     */
    public void displayTAB()
    {
        // Update the distribution
        updateDist();

        // Steps per metre
        int multiplier = 4 * _tab.getTimeSignature()[0];
        
        int col = 0;
        int cols = 0;
        int pos = _tabPosition;
        boolean wasColumn = true;
                
        for(int i = 0; i < _tabDist; i ++)
        {
            col = 4 + 2 * i;
            // Increase the offset
            if ((_tabPosition + i - cols) % multiplier == 0 && !wasColumn)
            {   
                _tabDisplay[i] = _tabDivider;
                _tabLines[col].setEditable(false);
                wasColumn = true;
                cols++;
            }
            else
            {
                try
                {
                    _tabDisplay[i] = TAB.lineToString(_tab.getTABColumn(pos));
                }
                catch (final IndexOutOfBoundsException e)
                {
                    _tabDisplay[i] = _tabBlank;
                }
                _tabLines[col].setEditable(true);
                wasColumn = false;
                pos++;
            }
                        
            _tabLines[col].setText(_tabDisplay[i]);
        }
    }
    
    /**
     * Given the column number in the display, calculates the 
     * position in the actual tab.
     * @param column the column number in the display
     * @return the column number in the tab
     */
    public int calcPosition(int column)
    {
        int multiplier = 4 * _tab.getTimeSignature()[0];
        int pos = 0;
        
        pos -= column / multiplier;
        pos += _tabPosition + column;
        return pos;
    }
    
    public int[][] getChangedColumns()
    {
        // Initialize an ArrayList of int arrays
        ArrayList<int[]> columns = new ArrayList<>();
        
        // Steps per metre
        int multiplier = 4 * _tab.getTimeSignature()[0];
        
        int col = 0;
        int pos = _tabPosition;
        
        // Iterate through the columns
        for(int i = 0; i < _tabDist; i ++)
        {
            // Calculates column number in the display
            col = 4 + 2 * i;
            
            // If the position is not an added '|' character
            if ((_tabPosition + i) % multiplier != 0 || col == 4)
            {   
                // If the display line is not what it 'supposes' to be
                if (!_tabDisplay[i].equals(_tabLines[col].getText()))
                {
                    // Add an array to the ArrayList containing:
                    //  { position in display, position in tab }
                    columns.add(new int[] {i, pos});
                }
                
                // Increase the position in the tab
                pos++;
            }
        }
        
        // Returns an array made from the ArrayList
        return columns.toArray(new int[0][0]);
    }
    
    /**
     * Updates the tab data given changes in the display.
     */
    public void updateTAB()
    {        
        // The line to hold which things to update
        int[] line;
        
        // Iterate though the lines to change
        for (int[] i : getChangedColumns())
        {
            // Get array corresponding to the changed column 
            line = TAB.stringToLine(_tabLines[4 + 2 * i[0]].getText());
            
            // Check that the line is the right length
            if (line.length == _tab.getNumberOfStrings())
            {
                // Set the line in the tab
                _tab.setLine(line, i[1]);
            }
        }
        
        // Update Display
        displayTAB();
    }
    
    /**
     * Updates the _tabDist given the length of the array.
     * The _tabDist is used initially if the tab is less than a given length.
     */
    public void updateDist()
    {
        // Check if the tab is too short to display the entirety
        if (_tab.getTABLength() < _defaultDist)
        {
            // If it is, set the 'dist' to the tab length
            _tabDist = _tab.getTABLength();
        }
        else
        {
            // Otherwise use the default 'dist'
            _tabDist = _defaultDist;
        }
    }
    
    /**
     * The left button moves the tab 1 left.
     */
    private class EditButtonLeft implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            // Update Tab
            updateTAB();

            if(_tabPosition > 0)
            {
                // Increment the location
                _tabPosition--;
                
                // Update Display
                displayTAB();
            }
        }
    }
    
    /**
     * The far left button moves the tab all the way to the beginning.
     */
    public class EditButtonFarLeft implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            // Update Tab
            updateTAB();
            
            // Set the location to the end of the tab
            _tabPosition = 0;
            
            // Update Display
            displayTAB();
        }
    }
    
    /**
     * The right button moves the tab 1 right.
     */
    private class EditButtonRight implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            // Update Tab
            updateTAB();
            
            if(_tabPosition <= _tab.getTABLength() - _tabDist)
            {
                // Increment the location
                _tabPosition++;
                
                // Update Display
                displayTAB();
            }
        }
    }
    
    /**
     * The far right button moves the tab all the way to the end.
     */
    private class EditButtonFarRight implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            // Update Tab
            updateTAB();
            
            // Set the location to the end of the tab
            _tabPosition = _tab.getTABLength() - _tabDist + 1;
            
            // Update Display
            displayTAB();
        }
    }
    
    /**
     * The plus button adds a new measure.
     */
    private class EditButtonPlus implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            // Get the length of a staff
            int nums[] = _tab.getTimeSignature();
            
            // Add a blank staff
            do
            {
                _tab.addLine(TAB.stringToLine(_tabBlank));
            } while (_tab.getTABLength() % (nums[0] * 4) != 0);
        }
    }
    
    /**
     * The minus button removes the last measure
     */
    private class EditButtonMinus implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            // Get the length of a staff
            int nums[] = _tab.getTimeSignature();
            
            // Delete a blank staff if able
            if (nums[0] * 4 < _tab.getTABLength())
                for (int i = 0; i < nums[0] * 4; i++)
                    _tab.removeLine();
            
            // Update Tab
            updateTAB();
            
            // Reset the location if needed
            if(_tabPosition >= _tab.getTABLength() - _tabDist)
            {
                _tabPosition = _tab.getTABLength() - _tabDist + 1;
            }
            
            // Reset the display
            displayTAB();
        }
    }
    
    /**
     * The update button updates the tab data given what's in the display
     */
    private class EditButtonUpdate implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            // Update Tab
            updateTAB();
        }
    }
    
    /**
     * The copy button copies the tab to the clipboard
     */
    private class CopyButton implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            try
            {
                TAB.copyTAB(_tab, Integer.parseInt(_copyNumber.getText()));
            }
            catch (final InputMismatchException f)
            {
                TAB.copyTAB(_tab);
                _copyNumber.setText("2");
            }
        }
    }
}
