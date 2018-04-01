// Imports
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * The RecString or Recording String class allows users to
 * specify the frequency or note of a given string.
 * @author jackreamy
 */
public class RecString extends JPanel
{
    // The frequency of the note
    private double _frequency;
    
    // The left and right buttons
    private final JButton _leftButton;
    private final JButton _rightButton;
    
    // The frequency and note name display areas
    private final JTextArea _freqDisplay;
    private final JTextArea _noteDisplay;
    
    // The recorder setting that this is a part of 
    private RecorderSettings _partOf;
    
    /**
     * The object constructor.
     * @param frequency the initial frequency.
     * @param housing the RecorderSettings object that this
     * string is a part of.
     */
    public RecString(double frequency, RecorderSettings housing)
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
        
        // Add the components to the frame
        this.add(_leftButton);
        this.add(_noteDisplay);
        this.add(_rightButton);
        this.add(_freqDisplay);
                
        // Display the frequency
        showFrequency();
        
        // Set up the connection between this object and 
        // the RecorderSettings object it is a part of
        _partOf = housing;
    }
    
    /**
     * The object constructor that defaults to 110.0 Hz or an A note
     * @param housing the RecorderSetttings object that this
     * string is a part of
     */
    public RecString(RecorderSettings housing)
    {
        // Call the other consructor
        this(110.0, housing);
    }
    
    /**
     * Returns the frequency of the string.
     * @return the frequency of the string.
     */
    public double getFrequency()
    {
        // Return the string frequency
        return _frequency;
    }
    
    /**
     * Shows the frequency and note name of the frequency.
     */
    public void showFrequency()
    {
        // Show the frequency
        _freqDisplay.setText(String.format("%.3f", _frequency));
        
        // Show the note name
        _noteDisplay.setText(TAB.frequencyToNote(_frequency));
    }
    
    /**
     * The function that shifts the frequency up or down 
     * to the next note.
     * @param offset how many steps to shift up or down.
     */
    public void frequencyButtons(int offset)
    {
        // Try parsing the frequency from the input box,
        // otherwise use the last stable frequency
        try
        {
            _frequency = TAB.closestNote(Double.parseDouble(_freqDisplay.getText()), offset);
        }
        catch (final IllegalArgumentException E)
        {
            _frequency = TAB.closestNote(_frequency, offset);
        }
    }
    
    /**
     * The left button listener, which executes every time
     * the left button is pressed.
     */
    private class LeftButton implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            // Shift the frequency down one note
            frequencyButtons(-1);
            
            // Display the frequency
            showFrequency();
            
            // Update the name of the tuning in the 
            // RecorderSettings object
            _partOf.rename();
        }
    }
    
    /**
     * The right button listener, which executes every time
     * the right button is pressed.
     */
    private class RightButton implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            // Shift the frequency up one note
            frequencyButtons(1);
            
            // Display the frequency
            showFrequency();
            
            // Update the name of the tuning in the
            // RecorderSettings object
            _partOf.rename();
        }
    }
}
