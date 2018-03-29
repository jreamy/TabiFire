
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class TabRecorder extends JPanel
{
    private TAB _tab;
    
    private JTextArea[] _displayText;
    private final JPanel _displayPanel;
    
    // Positioning variables
    private final int _defaultDist = TabEditor._defaultDist;
    
    // Helpful Strings to have
    private String _tabTuning;
    private String _tabBlank;
    private String _tabDivider;
    private String _tabEmpty;
    
    public TabRecorder()
    {
        // Set up the text panel
        _displayPanel = new JPanel();
        _displayPanel.setLayout(new BoxLayout(_displayPanel, BoxLayout.Y_AXIS));
        
        this.add(_displayPanel);
        
    }
    
    public void setTAB(TabSettings settings)
    {
        // Send in a new TAB
        setTAB(new TAB(settings));
    }
    
    public void setTAB(TAB tab)
    {   
        // Get the TAB to display
        _tab = tab;
                
        _displayText = new JTextArea[_tab.getNumberOfStrings()];
        
        // Clear the text storage
        _displayPanel.removeAll();
        
        // Initialize the Line Text Areas
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
    
    public void newLine(int[] inLine)
    {
        if (inLine.length == _tab.getNumberOfStrings())
        {
            _tab.addLine(inLine);
            for (int i = 0; i < _tab.getNumberOfStrings(); i++)
            {
                _displayText[i].setText(_tab.getTABLineFromEnd(_tab.getTABLength() - i - 1, 32));
            }
        }
    }
}
