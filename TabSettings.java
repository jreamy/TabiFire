
public class TabSettings 
{
    private double[] _baseNotes;
    private int[] _timeSignature;
    private String _settingsName;
    
    private static int _numOfCustom = 0;
    
    public TabSettings(double[] baseNotes, int[] timeSignature, String name)
    {
        _baseNotes = baseNotes;
        _timeSignature = timeSignature;
        _settingsName = name;
    }
    
    public TabSettings(double[] baseNotes, int[] timeSignature)
    {
        this(baseNotes, timeSignature, "Custom " + Integer.toString(_numOfCustom));
        _numOfCustom++;
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
                "Standard"
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
    
    public int getNumberOfStrings()
    {
        return _baseNotes.length;
    }
}
