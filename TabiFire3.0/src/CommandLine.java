
import java.util.Scanner;


public class CommandLine extends Hardware
{
    private TabiFire _host;
    private final Scanner in = new Scanner(System.in);
    
    public CommandLine()
    {
        System.out.println("Opened Command Prompting");
    }
    
    @Override
    public void link(TabiFire host)
    {
        _host = host;
    }
    
    @Override
    public boolean connect(String comPort)
    {
        System.out.println("Connected as " + comPort);
        
        return true;
    }
    
    @Override
    public String serialRead()
    {
        String incoming;
        try
        {
            System.out.print("\nSend: ");
            
            {
                if (in.hasNextInt())
                {
                    _host.send(in.nextInt());
                    in.nextLine();
                }
                else if (in.hasNextDouble())
                {
                    _host.send(in.nextDouble());
                    in.nextLine();
                }
                else
                {
                    incoming = in.nextLine();
                    if (incoming.length() == 1)
                    {
                        _host.send(incoming.charAt(0));
                    }
                    else if (incoming.length() > 1)
                    {
                        _host.send(incoming);
                    }
                }
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        
        return "";
    }
    
    public void send(char c)
    {
        System.out.print("\nGot : " + Character.toString(c) + "\nSend: ");
        if (c == 'i')
            super.connected = true;
    }
    
    public void send(int i)
    {
        System.out.print("\nGot : " + Integer.toString(i) + "\nSend: ");
    }
    
    public void send(double d)
    {
        System.out.print("\nGot : " + Double.toString(d) + "\nSend: ");
    }
}
