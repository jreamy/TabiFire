
import java.util.ArrayList;


public class TABLive extends TAB
{
    private ArrayList<Note> _notes;
    private double _initialTime;
    private double _deltaT;
    private double _sameEvent;
    
    public TABLive(TabSettings settings)
    {
        super(settings);
        _notes = new ArrayList<>();
        _sameEvent = 30;
    }
    
    public void addNote(Note note)
    {
        _notes.add(note);
        
        // Either reinitialize, or add
        if (_notes.size() < 10)
        {
            buildTAB();
        }
        else
        {
            addNoteToTAB(note);
        }
    }
    
    public double getDeltaT()
    {
        return _deltaT;
    }
    
    private void buildTAB()
    {
        // Set the initial time
        _initialTime = _notes.get(0).getTime();
        
        // Set the interval of one beat
        calcDeltaT();
        
        // Empty the actual TAB
        super.removeAll();
        
        // Add all of the notes to the tab
        for (Note n : _notes)
        {
            addNoteToTAB(n);
        }
    }
    
    private void addNoteToTAB(Note note)
    {
        int numOfBlanks = 1;
        double t1;
        double t2;
        
        if (_notes.indexOf(note) != 0)
        {
            Note lastNote = _notes.get(_notes.indexOf(note) - 1);
            
            // Calculate number of rests
            t1 = note.getTime();
            t2 = lastNote.getTime();
            
            // If it's not the same event as the last one
            if (Math.abs(t1 - t2) > _sameEvent)
            {
                // Get the number of blank lines to add
                numOfBlanks = (int) Math.round((t1 - t2) / _deltaT);
            }
            else
            {
                numOfBlanks = 0;
            }
        }
        
        // Insert the rests + 1 line
        for (int i = 0; i < numOfBlanks; i++)
        {
            super.addLine(emptyLine());
        }

        // Insert the note into the final line
        super.addNote(note.getStringNumber(), 
                super.getTABLength() - 1, note.getFrequency());
    }
    
    private void calcDeltaT()
    {   
        // For initial iteration
        ArrayList<Double> minDeltaT = new ArrayList<>();
        double minDelta;
        double delta;
        int i = 0;
        int j = 1;
        
        // For secondary iteration
        double total = 0;
        int divisor = 0;
        
        minDelta = 100000000;
        
        while (j < _notes.size())
        {
            // Get the difference
            delta = _notes.get(j).getTime() - _notes.get(i).getTime();
            
            // Update the minimum
            if (delta < minDelta && delta > _sameEvent)
                minDelta = delta;
            
            // If the difference is great enough
            if (delta > _sameEvent)
            {
                // Add the difference
                minDeltaT.add(delta);
                
                // Catch i up
                i = j;
            }
            
            // Increase j
            j++;
        }
        
        // Add all numbers in the right range to a total
        for (Double d : minDeltaT)
        {
            if (Math.round(d / minDelta) == 1)
            {
                total += d;
                divisor++;
            }
        }
        
        // Average them
        if (divisor > 0)
            total /= divisor;
        
        // Set the minimum time gap
        _deltaT = total;
    }
    
    private int[] emptyLine()
    {
        int[] emptyLine = new int[super.getNumberOfStrings()];
        
        for (int i = 0; i < emptyLine.length; i++)
        {
            emptyLine[i] = -1;
        }
        
        return emptyLine;
    }
}
