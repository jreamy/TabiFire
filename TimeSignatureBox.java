
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class TimeSignatureBox extends JPanel
{
    public static final int[][] TIME_SIGNATURES = new int[][]{
        new int[] {4, 4},
        new int[] {3, 4},
        new int[] {2, 4},
        new int[] {6, 8},
        new int[] {5, 4}
    };
    
    private final JButton _upButton;
    private final JButton _downButton;
    private final JTextArea _currentTS;
    
    private int _timeSig;
    
    public TimeSignatureBox()
    {
        _upButton = new JButton(" < ");
        _upButton.addActionListener(new UpButton());
        
        _downButton = new JButton(" > ");
        _downButton.addActionListener(new DownButton());
        
        _currentTS = new JTextArea(1, 3);
        _currentTS.setEditable(false);
        
        this.add(_upButton);
        this.add(_currentTS);
        this.add(_downButton);
        
        
        updateDisplay();
    }
    
    public int[] getTimeSignature()
    {
        return TIME_SIGNATURES[_timeSig];
    }
    
    public void updateDisplay()
    {
        _currentTS.setText(TIME_SIGNATURES[_timeSig][0] 
            + " / " + TIME_SIGNATURES[_timeSig][1]);
    }
    
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
