
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class RecorderSettings extends JPanel
{
    // Note Editor
    private final ArrayList<RecString> _stringData;
    private final JPanel _stringPanel;
    
    // Time Signature
    private final TimeSignatureBox _timSigPanel;
    
    // More or less Strings
    private final JButton _newString;
    private final JButton _delString;
    private final JPanel _strNumPanel;
    
    // TabSettings
    private TabSettings _currentSettings;
    private ArrayList<TabSettings> _allSettings;
    
    // Settings Panel
    private final JPanel _settings;
    
    public RecorderSettings() 
    {
        // Set up all the saved string types
        loadPresets();
        
        // Set the default tuning
        _currentSettings = new TabSettings(_allSettings.get(0));
        
        // Initialize the arraylist
        _stringData = new ArrayList<>();
        
        // Initialize the String Panel
        _stringPanel = new JPanel();
        
        for (int i = 0; i < _currentSettings.getNumberOfStrings(); i++)
        {
            newString(_currentSettings.getBaseNotes()[i]);
        }
        
        _stringPanel.setLayout(new BoxLayout(_stringPanel, BoxLayout.Y_AXIS));
        
        // Initialize the Time Signature Box
        _timSigPanel = new TimeSignatureBox();
        
        // The String num editor
        _newString = new JButton(" + ");
        _newString.setFont(new Font("Consolas", 0, 12));
        _newString.addActionListener(new NewStringButton());
        
        _delString = new JButton(" - ");
        _delString.setFont(new Font("Consolas", 0, 12));
        _delString.addActionListener(new DelStringButton());
        
        // String Num Panel
        _strNumPanel = new JPanel();
        _strNumPanel.add(_newString);
        _strNumPanel.add(_delString);
        
        // Add the panels to settings
        _settings = new JPanel();
        _settings.add(_timSigPanel);
        _settings.add(_strNumPanel);
        
        // Set the layout
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // Add the panels
        this.add(_settings);
        this.add(_stringPanel);
    }
    
    public TabSettings getTabSettings()
    {
        double[] frequencies = new double[_stringData.size()];
        
        for (int i = 0; i < _stringData.size(); i ++)
        {
            frequencies[i] = _stringData.get(i).getFrequency();
        }
        
        int[] timeSig = _timSigPanel.getTimeSignature();
        
        return new TabSettings(frequencies, timeSig);
    }
    
    public void delString()
    {
        // Find where it is
        int location = _stringData.size() - 1;
        
        // Remove the object from the pane
        _stringPanel.remove(location);
        
        // Delete the object
        _stringData.remove(location);
        
        // Refresh the panel
        _stringPanel.revalidate();
        _stringPanel.repaint();
    }
    
    public void newString(double frequency)
    {
        int location = _stringData.size();
        _stringData.add(new RecString(frequency));
        _stringPanel.add(_stringData.get(location), location);
        _stringPanel.revalidate();
        _stringPanel.repaint();
    }
    
    public void newString()
    {
        // Get the frequency and offset it
        double freq = _stringData.get(_stringData.size() - 1).getFrequency();
        freq = TAB.closestNote(freq, 5);
        
        newString(freq);
    }
    
    public void loadPresets()
    {
        _allSettings = new ArrayList<>();
        
        // Add the default settings
        _allSettings.add(new TabSettings());
        
        // Add other settings
    }
    
    private class NewStringButton implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            {
                newString();
            }
        }
    }
    
    private class DelStringButton implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            {
                if (_stringData.size() > 1)
                    delString();
            }
        }
    }
}
