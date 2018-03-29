
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class RecorderSettings extends JPanel
{
    // Note Editor
    private final ArrayList<RecString> _stringData;
    private final JPanel _stringPanel;
    
    // Preset / Saved settings
    private final JButton _presetLeft;
    private final JButton _presetRight;
    private final JTextArea _presetDisplay;
    private final JPanel _presetPanel;
    private int _presetLocation = 0;
    private int _numOfCustom = 0;
    
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
    private final JButton _saveSettings;
    private final JPanel _settings;
    
    public RecorderSettings() 
    {
        // Set up all the saved string types
        loadPresets();
                
        // Initialize the arraylist
        _stringData = new ArrayList<>();
        
        // Initialize the String Panel
        _stringPanel = new JPanel();
        _stringPanel.setLayout(new BoxLayout(_stringPanel, BoxLayout.Y_AXIS));
        
        // Initialize the Presets
        _presetLeft = new JButton(" < ");
        _presetLeft.setFont(new Font("Consolas", 0, 12));
        _presetLeft.addActionListener(new PresetLeft());
        
        _presetRight = new JButton(" > ");
        _presetRight.setFont(new Font("Consolas", 0, 12));
        _presetRight.addActionListener(new PresetRight());
        
        _presetDisplay = new JTextArea(1, 8);
        
        _presetPanel = new JPanel();
        _presetPanel.add(_presetLeft);
        _presetPanel.add(_presetDisplay);
        _presetPanel.add(_presetRight);
        
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
        
        // Save Button
        _saveSettings = new JButton(" Save Settings ");
        _saveSettings.addActionListener(new SaveSettings());
        
        // Add the panels to settings
        _settings = new JPanel();
        _settings.add(_timSigPanel);
        _settings.add(_saveSettings);
        _settings.add(_strNumPanel);
        
        // Set the layout
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // Add the panels
        this.add(_presetPanel);
        this.add(_settings);
        this.add(_stringPanel);
        
        // Load Preset
        loadPreset(0);
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
        _stringData.add(new RecString(frequency, this));
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
        
        String name;
        int numOfStrings;
        double[] baseNotes;
        
        // Add other settings
        File infile = new File("TabSettings.txt");
        
        try
        {
            Scanner in = new Scanner(infile);
            
            _presetLocation = in.nextInt();
            
            in.nextLine();
            
            while (in.hasNext())
            {
                name = in.nextLine();
                
                if (name.contains("Custom"))
                    _numOfCustom++;
                                
                numOfStrings = in.nextInt();
                
                baseNotes = new double[numOfStrings];
                
                for (int i = 0; i < numOfStrings; i++)
                {
                    baseNotes[i] = in.nextDouble();
                }
                
                _allSettings.add(new TabSettings(baseNotes, name));
                
                in.nextLine();
            }
            
            in.close();
        }
        catch(final FileNotFoundException f)
        {
            // Add the default settings
            _allSettings.add(new TabSettings());
        }
    }
    
    public void savePresets() 
    {
        try
        {
            PrintWriter writer = new PrintWriter("TabSettings.txt");
            
            writer.println(Integer.toString(_presetLocation));
            
            for (TabSettings t : _allSettings)
            {
                writer.print(t.getPrintable());
            }
            
            writer.close();
        }
        catch (final FileNotFoundException f)
        {
            JOptionPane.showMessageDialog(null, "Error saving file.");
        }
        
        loadPresets();
        
        this.rename();
    }
    
    public void loadPreset(int num)
    {
        if (num >= 0 && num < _allSettings.size())
        {
            int reps = _stringData.size();
            for (int i = 0; i < reps; i++)
            {
                delString();
            }
            
            _currentSettings = _allSettings.get(num);
            
            for (int i = 0; i < _currentSettings.getNumberOfStrings(); i++)
            {
                newString(_currentSettings.getBaseNotes()[i]);
            }
            
            rename();
        }
    }
    
    public boolean rename()
    {
        boolean keepGoing = true;
        boolean userNamed = true;
        String name = _presetDisplay.getText();
        int i = 0;
        
        _currentSettings = this.getTabSettings();
        _currentSettings.setName(name);
        
        while (keepGoing && i < _allSettings.size())
        {            
            if (_currentSettings.sameAs(_allSettings.get(i)))
            {
                _currentSettings = _allSettings.get(i);
                keepGoing = false;
            }
            
            if (_currentSettings.getName().equals(_allSettings.get(i).getName()))
            {
                userNamed = false;
            }
            
            i++;
        }
             
        if (!userNamed && keepGoing)
        {
            _currentSettings.setName("Custom " + Integer.toString(_numOfCustom + 1));
        }
                
        _presetDisplay.setText(_currentSettings.getName());
        
        return keepGoing;
    }
    
    private class PresetLeft implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            if (_presetLocation > 0)
            {
                _presetLocation--;
                loadPreset(_presetLocation);
                rename();
            }
        }
    }
    
    private class PresetRight implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            if (_presetLocation < _allSettings.size() - 1)
            {
                _presetLocation++;
                loadPreset(_presetLocation);
                rename();
            }
        }
    }
    
    private class NewStringButton implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            newString();
            rename();
        }
    }
    
    private class DelStringButton implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            if (_stringData.size() > 1)
                delString();
            rename();
        }
    }
    
    private class SaveSettings implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            if (rename())
                _allSettings.add(getTabSettings());
            savePresets();
        }
    }
}
