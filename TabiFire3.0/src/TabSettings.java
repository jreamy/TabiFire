


/**
 * The TabSettings object holding the base notes, 
 * time signature, and name of the settings
 * @author jackreamy
 */
public class TabSettings 
{
    // Instance variables
    private double[] _baseNotes;
    private int[] _timeSignature;
    private String _settingsName;
        
    /**
     * The default constructor
     * @param baseNotes the base notes
     * @param timeSignature the time signature
     * @param name the name
     */
    public TabSettings(double[] baseNotes, int[] timeSignature, String name)
    {
        _baseNotes = baseNotes;
        _timeSignature = timeSignature;
        _settingsName = name;
    }
    
    /**
     * Constructor that doesn't have a name
     * @param baseNotes the base notes
     * @param timeSignature the time signature
     */
    public TabSettings(double[] baseNotes, int[] timeSignature)
    {
        // Call the constructor sending it the name "Custom"
        this(baseNotes, timeSignature, "Custom");
    }
    
    /**
     * Constructor that doesn't have a time signature
     * @param baseNotes the base notes
     * @param name the name
     */
    public TabSettings(double[] baseNotes, String name)
    {
        // Call the constructor sending it the time signature { 4, 4 }
        this(baseNotes, new int[]{4, 4}, name);
    }
    
    /**
     * A constructor taking settings from a tab
     * @param tab the input tab
     */
    public TabSettings(TAB tab)
    {
        this(tab.getBaseNotes(), tab.getTimeSignature());
    }
    
    /**
     * Basically a copy function that doesn't preserve the name
     * @param settings the input settings
     */
    public TabSettings(TabSettings settings)
    {
        this(settings.getBaseNotes(), settings.getTimeSignature());
    }
    
    /**
     * A constructor that sets E Standard
     */
    public TabSettings()
    {
        this(new double[] 
                {
                    82.407,
                    110.000,
                    146.832,
                    195.998,
                    246.942,
                    329.628
                }, 
                new int[]
                {
                    4, 
                    4
                },
                "E Standard"
        );
    }
    
    /**
     * Returns the base notes of the settings.
     * @return the base notes of the settings.
     */
    public double[] getBaseNotes()
    {
        return _baseNotes;
    }
    
    /**
     * Returns the time signature of the settings.
     * @return the time signature of the settings.
     */
    public int[] getTimeSignature()
    {
        return _timeSignature;
    }
    
    /**
     * Returns the name of the settings.
     * @return the name of the settings.
     */
    public String getName()
    {
        return _settingsName;
    }
    
    /**
     * Sets the name of the settings.
     * @param newName the input name.
     */
    public void setName(String newName)
    {
        _settingsName = newName;
    }
    
    /**
     * Returns the number of strings in the settings.
     * @return the number of strings in the settings.
     */
    public int getNumberOfStrings()
    {
        return _baseNotes.length;
    }
    
    /**
     * Compares the TabSettings to the input object comparing
     * just length and base notes
     * @param obj the input object to check
     * @return whether the objects have the same base notes
     */
    public boolean sameAs(Object obj)
    {
        // Default not equal
        boolean isSame = false;
        
        // If the Object is a TabSettings object
        if (obj instanceof TabSettings)
        {
            // Cast the object
            TabSettings t = (TabSettings) obj;
            
            // Check that the length is the same
            if (_baseNotes.length == t.getBaseNotes().length)
            {
                isSame = true;
                
                // Iterate through the base frequencies, if they're
                // 'pretty close' then return true
                for (int i = 0; i < _baseNotes.length; i++)
                {
                    isSame = isSame && (Math.abs(_baseNotes[i] - t.getBaseNotes()[i]) < .01);
                }
            }
        }
        
        return isSame;
    }
    
    /**
     * Returns a pretty version of the TabSettings (for saving).
     * @return a pretty version of the TabSettings.
     */
    public String getPrintable()
    {
        String ret = "";
        
        // First line is settings name
        ret += this._settingsName + "\n";
        
        // Next line is number of strings
        ret += this.getNumberOfStrings() + "\n";
        
        // Add the frequencies one at a time
        for (double freq : this.getBaseNotes())
        {
            ret += String.format("%.3f", freq) + "\n";
        }
        
        return ret;
    }
}
