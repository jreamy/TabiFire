
import java.util.Arrays;


public class TabSettings 
{
    private double[] _baseNotes;
    private int[] _timeSignature;
    private String _settingsName;
        
    public TabSettings(double[] baseNotes, int[] timeSignature, String name)
    {
        _baseNotes = baseNotes;
        _timeSignature = timeSignature;
        _settingsName = name;
    }
    
    public TabSettings(double[] baseNotes, int[] timeSignature)
    {
        this(baseNotes, timeSignature, "Custom");
    }    
    
    public TabSettings(double[] baseNotes, String name)
    {
        this(baseNotes, new int[]{4, 4}, name);
    }
    
    public TabSettings(TAB tab)
    {
        this(tab.getBaseNotes(), tab.getTimeSignature());
    }
    
    public TabSettings(TabSettings settings)
    {
        this(settings.getBaseNotes(), settings.getTimeSignature());
    }
    
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
    
    public double[] getBaseNotes()
    {
        return _baseNotes;
    }
    
    public int[] getTimeSignature()
    {
        return _timeSignature;
    }
    
    public String getName()
    {
        return _settingsName;
    }
    
    public void setName(String newName)
    {
        _settingsName = newName;
    }
    
    public int getNumberOfStrings()
    {
        return _baseNotes.length;
    }
    
    public boolean sameAs(Object obj)
    {
        boolean isSame = false;
        
        if (obj instanceof TabSettings)
        {
            TabSettings t = (TabSettings) obj;
            
            if (_baseNotes.length == t.getBaseNotes().length)
            {
                isSame = true;
                
                for (int i = 0; i < _baseNotes.length; i++)
                {
                    isSame = isSame && (Math.abs(_baseNotes[i] - t.getBaseNotes()[i]) < .01);
                }
            }
        }
        
        return isSame;
    }
    
    public String getPrintable()
    {
        String ret = "";
        ret += this._settingsName + "\n";
        ret += this.getNumberOfStrings() + "\n";
        
        for (double freq : this.getBaseNotes())
        {
            ret += String.format("%.3f", freq) + "\n";
        }
        
        return ret;
    }
}
