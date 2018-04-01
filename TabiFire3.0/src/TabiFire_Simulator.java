

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
        // Sets up a new TabiFire display
        TabiFire tabitha = new TabiFire();
        tabitha.setTitle("TabiFire 3.0");
        
        // Sets up a new hardware object
        Hardware box = new Hardware();
        
        // Connect the arduino and the app
        box.link(tabitha);
        tabitha.link(box);
    }
}
