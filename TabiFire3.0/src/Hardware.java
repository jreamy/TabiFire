
import arduino.*;
import com.fazecast.jSerialComm.SerialPort;
import java.util.Scanner;

/**
 * Undeveloped interface of hardware and TabiFire app,
 * it channels the input data and sends it to the app
 * @author jackreamy
 */
public class Hardware extends Arduino
{
    private TabiFire _host;
    public boolean connected = false;
    
    public Hardware()
    {
        this("COM4");
    }
    
    public Hardware(String comPort)
    {
        super(comPort);
        super.setBaudRate(115200);
    }
    
    public void link(TabiFire host)
    {
        _host = host;
    }
    
    public boolean connect(String comPort)
    {
        boolean success;
        
        // Try and connect        
        success = super.openConnection();
        
        return success;
    }
    
    @Override
    public String serialRead()
    {
        String incoming;
        super.getSerialPort().setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        Scanner in = new Scanner(super.getSerialPort().getInputStream());
        try
        {
            while (in.hasNext())
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
            System.out.println("error");
        }
        
        in.close();
        
        return "";
    }
    
    public void disconnect()
    {
        super.closeConnection();
    }
    
    public void send(char c)
    {
        super.serialWrite(Character.toString(c));
    }
    
    public void send(int i)
    {
        super.serialWrite(Integer.toString(i));
    }
    
    public void send(double d)
    {
        super.serialWrite(Double.toString(d));
    }
}
