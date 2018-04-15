
public class Note implements Comparable
{
    private int _stringNumber;
    private double _frequency;
    private double _time;
    
    public Note()
    {
        this(0, 0, 0);
    }
    
    public Note(int stringNumber, double frequency, double time)
    {
        _stringNumber = stringNumber;
        _frequency = frequency;
        _time = time;
    }
    
    public void setStringNumber(int stringNumber)
    {
        _stringNumber = stringNumber;
    }
    
    public void setFrequency(double frequency)
    {
        _frequency = frequency;
    }
    
    public void setTime(double time)
    {
        _time = time;
    }
    
    public int getStringNumber()
    {
        return _stringNumber;
    }
    
    public double getFrequency()
    {
        return _frequency;
    }
    
    public double getTime()
    {
        return _time;
    }

    @Override
    public int compareTo(Object o) 
    {
        int ret = 0;
        
        if (o instanceof Note)
        {
            Note n = (Note) o;
            ret = ((Double) _time).compareTo(n.getTime());
        }
        
        return ret;
    }
}
