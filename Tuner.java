
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class Tuner extends JPanel
{
    private JTextArea _tunerText;
    private JButton _textLeft;
    private JButton _testRight;
    
    public Tuner()
    {
        // Set up the text field
        _tunerText = new JTextArea(1, 16);
        _tunerText.setFont(new Font("Consolas", 0, 18));
        
        // Set the layout and add the text
        setLayout(new FlowLayout());
        add(_tunerText);
        
        // Initialize the frequency
        setFrequency(90.0);
                
    }
    
    public void setFrequency(double frequency)
    {
        _tunerText.setText(
                "Current Frequency  : " 
                + String.format("%.2f", frequency)
            + "\nClosest Note ( " 
                + TAB.frequencyToNote(frequency)
                + ") : " 
                + String.format("%.2f", TAB.closestNote(frequency))
        );
    }
}