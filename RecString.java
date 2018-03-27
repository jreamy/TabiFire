
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class RecString extends JPanel
{
    private double _frequency;
    
    private final JButton _leftButton;
    private final JButton _rightButton;
    
    private final JTextArea _freqDisplay;
    private final JTextArea _noteDisplay;
    
    public RecString(double frequency)
    {
        // Left Button
        _leftButton = new JButton(" < ");
        _leftButton.addActionListener(new LeftButton());
        
        // Right Button
        _rightButton = new JButton(" > ");
        _rightButton.addActionListener(new RightButton());
        
        // Frequency
        _frequency = frequency;
        _freqDisplay = new JTextArea(1, 4);
        
        // Note Name
        _noteDisplay = new JTextArea(1, 4);
        _noteDisplay.setEditable(false);
        
        // Add the components
        this.add(_leftButton);
        this.add(_noteDisplay);
        this.add(_rightButton);
        this.add(_freqDisplay);
                
        // Display the frequency
        showFrequency();
    }
    
    public RecString()
    {
        this(110.0);
    }
    
    public double getFrequency()
    {
        return _frequency;
    }
    
    public void showFrequency()
    {
        // Show the frequency
        _freqDisplay.setText(String.format("%.2f", _frequency));
        
        // Show the note
        _noteDisplay.setText("    " + TAB.frequencyToNote(_frequency));
    }
    
    public void frequencyButtons(int offset)
    {
        try
        {
            _frequency = TAB.closestNote(Double.parseDouble(_freqDisplay.getText()), offset);
        }
        catch (final IllegalArgumentException E)
        {
            _frequency = TAB.closestNote(_frequency, offset);
        }
    }
    
    private class LeftButton implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            frequencyButtons(-1);
            showFrequency();
        }
    }
    
    private class RightButton implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            frequencyButtons(1);
            showFrequency();
        }
    }
}
