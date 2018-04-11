

/**
 * This is the file that actually runs
 * @author jackreamy
 */
public class TabiFire_Simulator 
{
    /**
     * Akin to the void loop() 
     * @param args 
     */
    public static void main(String args[])
    {
        int microDelay = 1;
        // Sets up a new TabiFire display
        TabiFire tabitha = new TabiFire();
        tabitha.setTitle("TabiFire 3.0");
        
        // Sets up a new hardware object
        Hardware box = new CommandLine();
        // Hardware box = new Hardware();
        
        // Connect the arduino and the app
        box.link(tabitha);
        tabitha.link(box);
        
        while (tabitha.running)
        {
            System.out.print("");
            if (tabitha.connected && box.connected)
            {
                System.out.print("");
                box.serialRead();
            }
        }
    }
}
