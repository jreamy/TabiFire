// Imports
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

/**
 * The RecorderSettings Panel which holds the tuning of
 * each string, the time signature, the name of the tuning,
 * the buttons to add or remove strings, and the button to 
 * save the settings.
 * @author jackreamy
 */
public class RecorderSettings extends JPanel
{
    // ArrayList to hold the RecString objects
    private final ArrayList<RecString> _stringData;
    
    // The panel to hold the strings
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
    
    // Buttons to increase or decrease the number of stings
    private final JButton _newString;
    private final JButton _delString;
    private final JPanel _strNumPanel;
    
    // The TabSettings object
    public TabSettings _currentSettings;
    private ArrayList<TabSettings> _allSettings;
    
    // Settings Panel
    private final JButton _saveSettings;
    private final JPanel _settings;
    
    public RecorderSettings() 
    {        
        // =============================================== //
        //              Initialize the Strings             //
        // =============================================== //
        
        // Initialize the ArrayList or Record Strings
        _stringData = new ArrayList<>();
        
        // Initialize the String Panel
        _stringPanel = new JPanel();
        _stringPanel.setLayout(new BoxLayout(_stringPanel, BoxLayout.Y_AXIS));
        
        // =============================================== //
        //              Initialize Presets                 //
        // =============================================== //

        // Set up all the saved string types
        loadPresets();
        
        // Left button
        _presetLeft = new JButton(" < ");
        _presetLeft.setFont(new Font("Consolas", 0, 12));
        _presetLeft.addActionListener(new PresetLeft());
        
        // Right button
        _presetRight = new JButton(" > ");
        _presetRight.setFont(new Font("Consolas", 0, 12));
        _presetRight.addActionListener(new PresetRight());
        
        // Display Area
        _presetDisplay = new JTextArea(1, 8);
        
        // Add all the things to the preset panel
        _presetPanel = new JPanel();
        _presetPanel.add(_presetLeft);
        _presetPanel.add(_presetDisplay);
        _presetPanel.add(_presetRight);
        
        // =============================================== //
        //         Time Signature Settings Panel           //
        // =============================================== //

        // Initialize the Time Signature Box
        _timSigPanel = new TimeSignatureBox();
        
        // =============================================== //
        //         Add and Remove String Buttons           //
        // =============================================== //
        
        // New String Button
        _newString = new JButton(" + ");
        _newString.setFont(new Font("Consolas", 0, 12));
        _newString.addActionListener(new NewStringButton());
        
        // Delete String Button
        _delString = new JButton(" - ");
        _delString.setFont(new Font("Consolas", 0, 12));
        _delString.addActionListener(new DelStringButton());
        
        // String Num Panel
        _strNumPanel = new JPanel();
        _strNumPanel.add(_newString);
        _strNumPanel.add(_delString);
        
        // =============================================== //
        //                    Save Panel                   //
        // =============================================== //
        
        // Save Button
        _saveSettings = new JButton(" Save Settings ");
        _saveSettings.addActionListener(new SaveSettings());
        
        // Add the panels to settings
        _settings = new JPanel();
        _settings.add(_timSigPanel);
        _settings.add(_saveSettings);
        _settings.add(_strNumPanel);
        
        // =============================================== //
        //           Format the RecorderSettings           //
        // =============================================== //
        
        // Set the layout
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // Add the panels
        this.add(_presetPanel);
        this.add(_settings);
        this.add(_stringPanel);
        
        // =============================================== //
        //             Load the default tuning             //
        // =============================================== //
        
        // Load Preset
        loadPreset(_presetLocation);
    }
    
    /**
     * Returns the TabSettings object.
     * @return the TabSettings object.
     */
    public TabSettings getTabSettings()
    {
        // An array with length equal to the number of
        // RecString objects.
        double[] frequencies = new double[_stringData.size()];
        
        // Iterate through the RecString obejcts
        for (int i = 0; i < _stringData.size(); i ++)
        {
            // Get the frequency of each string
            frequencies[i] = _stringData.get(i).getFrequency();
        }
        
        // Get the time Signature from the
        // TimeSignatureBox object.
        int[] timeSig = _timSigPanel.getTimeSignature();
        
        // Return the corresponding TabSettings object.
        return new TabSettings(frequencies, timeSig);
    }
    
    /**
     * Deletes the last RecString object.
     */
    public void delString()
    {
        // Find what number it is in the ArrayList
        int location = _stringData.size() - 1;
        
        // Remove the object from the pane
        _stringPanel.remove(location);
        
        // Delete the object
        _stringData.remove(location);
        
        // Refresh the panel
        _stringPanel.revalidate();
        _stringPanel.repaint();
    }
    
    /**
     * Add a new RecString object with a given frequency.
     * @param frequency the frequency of the new string.
     */
    public void newString(double frequency)
    {
        // The location of the new string in the ArrayList
        int location = _stringData.size();
        
        // Add a new string to the ArrayList
        _stringData.add(new RecString(frequency, this));
        
        // Add the new string to the panel
        _stringPanel.add(_stringData.get(location), location);
        
        // Refresh the panel
        _stringPanel.revalidate();
        _stringPanel.repaint();
    }
    
    /**
     * Add a new string with a default offset 
     * of 5 frets from the previous string
     */
    public void newString()
    {
        // Get the frequency of the last string
        double freq = _stringData.get(_stringData.size() - 1).getFrequency();
        
        // Offset the frequency by 5 frets
        freq = TAB.closestNote(freq, 5);
        
        // Make a new string with this offset frequency
        newString(freq);
    }
    
    public void setString(int stringNumber, double frequency)
    {
        System.out.println(stringNumber + " : " + frequency);
        if (stringNumber >= 0 && stringNumber < _stringData.size())
        {
            _stringData.get(stringNumber).setFrequency(frequency);
        }
    }
    
    /**
     * Loads the saved settings from file.
     */
    public void loadPresets()
    {
        // Initialize the ArrayList of all the TabSettings objects
        _allSettings = new ArrayList<>();
        
        // Local variables for parsing the file
        String name;
        int numOfStrings;
        double[] baseNotes;
        
        // Make a new file object using the settings file
        File infile = new File("TabSettings.txt");
        
        try
        {
            // Open the settings file
            Scanner in = new Scanner(infile);
            
            // Get the location (the first data point in the file)
            _presetLocation = in.nextInt();
            
            // Clear the \n character
            in.nextLine();
            
            // Iterate through the data in the file
            while (in.hasNext())
            {
                // Get the settings name
                name = in.nextLine();
                
                // Add 1 to the number of custom settings
                // if it's another custom setting
                if (name.contains("Custom"))
                    _numOfCustom++;
                
                // Get the number of strings
                numOfStrings = in.nextInt();
                
                // Initialize a new array of doubles for the frequencies
                baseNotes = new double[numOfStrings];
                
                // Iterate through the frequencies
                // Adding them to the array
                for (int i = 0; i < numOfStrings; i++)
                {
                    baseNotes[i] = in.nextDouble();
                }
                
                // Construct a new TabSettings objects and 
                // Add it to the TabSettings ArrayList
                _allSettings.add(new TabSettings(baseNotes, name));
                
                // Clear the \n character
                in.nextLine();
            }
            
            // Close the file
            in.close();
        }
        catch(final FileNotFoundException f)
        {
            // If the file didn't work, add E Standard
            _allSettings.add(new TabSettings());
        }
    }
    
    /**
     * Saves the TabSettings to file.
     */
    public void savePresets() 
    {
        try
        {
            // Open a new file writing object
            PrintWriter writer = new PrintWriter("TabSettings.txt");
            
            // Print the current preset location
            writer.println(Integer.toString(_presetLocation));
            
            // Iterate through the settings
            for (TabSettings t : _allSettings)
            {
                // Write each to the file
                writer.print(t.getPrintable());
            }
            
            // Close the file
            writer.close();
        }
        catch (final FileNotFoundException f)
        {
            // If there was an error, tell the user
            JOptionPane.showMessageDialog(null, "Error saving file.");
        }
        
        // Reloads the Presets
        loadPresets();
        
        // Reloads the 'name' panel of the tuning
        this.rename();
    }
    
    /**
     * Loads a specific preset into the window.
     * @param num the preset location in the ArrayList.
     */
    public void loadPreset(int num)
    {
        // make sure the location is within the data
        if (num >= 0 && num < _allSettings.size())
        {
            // Gets the number of Strings 
            int reps = _stringData.size();
            
            // Iterate though the strings
            for (int i = 0; i < reps; i++)
            {
                // Delete the strings
                delString();
            }
            
            // Get the input preset
            _currentSettings = _allSettings.get(num);
            
            // Iterate through the strings
            for (int i = 0; i < _currentSettings.getNumberOfStrings(); i++)
            {
                // Add the strings with their frequencies
                newString(_currentSettings.getBaseNotes()[i]);
            }
            
            // Update the name
            rename();
        }
    }
    
    /**
     * Checks to see if the current tuning is a preset,
     * and names it if it is.
     * @return if it is a new custom tuning.
     */
    public boolean rename()
    {
        // Booleans for iteration and return
        boolean isCustom = true;
        boolean userNamed = true;
        
        // Gets the current name in the display
        String name = _presetDisplay.getText();
        
        // Int for the while loop
        int i = 0;
        
        // Gets the current settings from the panel
        _currentSettings = this.getTabSettings();
        _currentSettings.setName(name);
        
        // Iterates through the preset settings
        while (isCustom && i < _allSettings.size())
        {            
            // Check if the current settings are the same 
            // as the preset settings
            if (_currentSettings.sameAs(_allSettings.get(i)))
            {
                // Update current settings
                _currentSettings = _allSettings.get(i);
                
                // Break out of loop
                isCustom = false;
            }
            
            // If the name is the same as the preset
            if (_currentSettings.getName().equals(_allSettings.get(i).getName()))
            {
                // The user did not name the setting
                userNamed = false;
            }
            
            // Increment the loop counter
            i++;
        }
        
        // If the user didn't name it, and it is a custom tuning,
        // Give it a custom number
        if (!userNamed && isCustom)
        {
            _currentSettings.setName("Custom " 
                    + Integer.toString(_numOfCustom + 1));
        }
        
        // Update the name panel
        _presetDisplay.setText(_currentSettings.getName());
        
        // Return whether it was a new custom tuning or not
        return isCustom;
    }
    
    /**
     * The left button to pan through the presets.
     */
    private class PresetLeft implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            // If the location is greater than 0
            if (_presetLocation > 0)
            {
                // Decrease the location
                _presetLocation--;
                
                // Load the new preset
                loadPreset(_presetLocation);
                
                // Update the tuning name
                rename();
            }
        }
    }
    
    /**
     * The right button to pan through the presets.
     */
    private class PresetRight implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            // If the location is less than the last in the ArrayList
            if (_presetLocation < _allSettings.size() - 1)
            {
                // Increase the location
                _presetLocation++;
                
                // Load the new preset
                loadPreset(_presetLocation);
                
                // Update the tuning name
                rename();
            }
        }
    }
    
    /**
     * When clicked, adds a string
     */
    private class NewStringButton implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            // Adds a string
            newString();
            
            // Updates the tuning name
            rename();
        }
    }
    
    /**
     * When clicked deletes a string
     */
    private class DelStringButton implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            // Deletes a string if there is more than 1
            if (_stringData.size() > 1)
                delString();
            
            // Updates the tuning name
            rename();
        }
    }
    
    /**
     * Button to call the save presets method.
     */
    private class SaveSettings implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            // Updates the name, and if it is a cutom name
            // Add the name to the settings ArrayList
            if (rename())
                _allSettings.add(getTabSettings());
            
            // Saves the presets
            savePresets();
        }
    }
}
