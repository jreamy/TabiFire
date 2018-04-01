// Imports
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * The TabRecorder is the display panel for an incoming tab
 * during the record mode.
 * @author jackreamy
 */
public class TabRecorder extends JPanel implements TabDisplay
{
    // The tab being recorded
    private TAB _tab;
    
    // The tab display text and panel
    private JTextArea[] _displayText;
    private final JPanel _displayPanel;
    
    // Positioning variable
    private final int _defaultDist = TabEditor._defaultDist;
    
    // Helpful Strings to have
    private String _tabTuning;
    private String _tabBlank;
    private String _tabDivider;
    private String _tabEmpty;
    
    /**
     * The constructor
     */
    public TabRecorder()
    {
        // Set up the text panel
        _displayPanel = new JPanel();
        _displayPanel.setLayout(new BoxLayout(_displayPanel, BoxLayout.Y_AXIS));
        
        // Add the text panel to the recorder
        this.add(_displayPanel);
    }
    
    /**
     * Calls the setup function given an input TabSettings object
     * @param settings the input settings
     */
    public void setTAB(TabSettings settings)
    {
        // Send in a new TAB
        setTAB(new TAB(settings));
    }
    
    /**
     * Initializes the display, given a tab.
     * @param tab the input tab
     */
    public void setTAB(TAB tab)
    {   
        // Get the TAB to display
        _tab = tab;
        
        // Set the number of text lines (rows)
        _displayText = new JTextArea[_tab.getNumberOfStrings()];
        
        // Clear the text storage
        _displayPanel.removeAll();
        
        // Initialize the line TextArea(s)
        for (int i = 0; i < _tab.getNumberOfStrings(); i++)
        {
            JTextArea line = new JTextArea(1, 40);
            line.setEditable(false);
            line.setFont(new Font("Consolas", 0, 14));
            line.setText(_tab.getTABLineFromEnd(i, 32));
            _displayText[i] = line;
            _displayPanel.add(line);
        }
    }
    
    /**
     * Adds a new line to the tab and updates the display
     * @param inLine the line to add
     */
    public void newLine(int[] inLine)
    {
        // If the line to add has the right length
        if (inLine.length == _tab.getNumberOfStrings())
        {
            // Add the line to the tab
            _tab.addLine(inLine);
            
            // Iterate through the lines
            for (int i = 0; i < _tab.getNumberOfStrings(); i++)
            {
                // Update the text of the display line
                _displayText[i].setText(_tab.getTABLineFromEnd(_tab.getTABLength() - i - 1, 32));
            }
        }
    }
}
