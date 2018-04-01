// Imports
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * The TimeSignatureBox allows the user to select one of the
 * given time signatures
 * @author jackreamy
 */
public class TimeSignatureBox extends JPanel
{
    // The allowed time signatures
    public static final int[][] TIME_SIGNATURES = new int[][]{
        new int[] {4, 4},
        new int[] {3, 4},
        new int[] {2, 4},
        new int[] {6, 8},
        new int[] {5, 4}
    };
    
    // The Buttons and display
    private final JButton _upButton;
    private final JButton _downButton;
    private final JTextArea _currentTS;
    
    // An int which holds which preset time signature is 
    // being used
    private int _timeSig;
    
    /**
     * The TimeSignatureBox constructor
     */
    public TimeSignatureBox()
    {
        // Up Button
        _upButton = new JButton(" < ");
        _upButton.addActionListener(new UpButton());
        
        // Down Button
        _downButton = new JButton(" > ");
        _downButton.addActionListener(new DownButton());
        
        // Current Time Signature Display
        _currentTS = new JTextArea(1, 3);
        _currentTS.setEditable(false);
        
        // Adds the things ^^^ to the TimeSignatureBox panel
        this.add(_upButton);
        this.add(_currentTS);
        this.add(_downButton);
        
        // Updates the display
        updateDisplay();
    }
    
    /**
     * Returns the selected time signature.
     * @return the selected time signature.
     */
    public int[] getTimeSignature()
    {
        return TIME_SIGNATURES[_timeSig];
    }
    
    /**
     * Updates the display
     */
    public void updateDisplay()
    {
        _currentTS.setText(TIME_SIGNATURES[_timeSig][0] 
            + " / " + TIME_SIGNATURES[_timeSig][1]);
    }
    
    /**
     * The up button 'listener'
     */
    private class UpButton implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            if (_timeSig > 0)
            {
                _timeSig--;
                updateDisplay();
            }
        }
    }
    
    /**
     * The down button 'listener'
     */
    private class DownButton implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            if (_timeSig < TIME_SIGNATURES.length - 1)
            {
                _timeSig++;
                updateDisplay();
            }
        }
    }
}
