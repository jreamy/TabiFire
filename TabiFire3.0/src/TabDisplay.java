
/**
 * This is an interface, it requires the objects that 'implement' 
 * it to define:
 *  - a setTAB function
 * 
 * @author jackreamy
 */
public interface TabDisplay
{
    public void setTAB(TAB tab);
    
    public void displayTAB();
    
    public TAB getTAB();
}
