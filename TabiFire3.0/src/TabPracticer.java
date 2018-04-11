
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jackreamy
 */
public class TabPracticer extends JPanel implements TabDisplay
{
    // The Tab to practice
    private TAB _loadedTAB;
    private TAB _recordTAB;
    
    private JTextArea[] _displayText;
    private final JPanel _displayPanel;
    
    
    public TabPracticer()
    {
        _displayPanel = new JPanel();
        _displayPanel.setLayout(new BoxLayout(_displayPanel, BoxLayout.Y_AXIS));
        
        this.add(_displayPanel);
    }
    
    
    @Override
    public void setTAB(TAB tab) 
    {
        _loadedTAB = tab;
        
        _displayText = new JTextArea[_loadedTAB.getNumberOfStrings()];
        
        // Initialize the line TextArea(s)
        for (int i = 0; i < _loadedTAB.getNumberOfStrings(); i++)
        {
            JTextArea line = new JTextArea(1, 40);
            line.setEditable(false);
            line.setFont(new Font("Consolas", 0, 14));
            line.setText(_recordTAB.getTABLineFromEnd(i, 32));
            _displayText[i] = line;
            _displayPanel.add(line);
        }
    }

    @Override
    public void displayTAB() 
    {
        
    }
    
    @Override
    public TAB getTAB()
    {
        return _recordTAB;
    }
    
}
