

/**
 * Undeveloped interface of hardware and TabiFire app,
 * it channels the input data and sends it to the app
 * @author jackreamy
 */
public class Hardware 
{
    
    TabiFire _host;
    
    public Hardware()
    {
        // Do nothing
    }
    
    public void link(TabiFire host)
    {
        _host = host;
    }
    
    public boolean connect(String comPort)
    {
        boolean success = false;
        
        // Try and connect
        
        return success;
    }
    
    public void send(char c)
    {
        
    }
    
    public void send(int i)
    {
        
    }
    
    public void send(double d)
    {
        
    }
}
