
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author jackreamy
 */
public class TAB {
    private ArrayList<int []> _tab;
    private double[] _baseNotes;
    private int _numOfStrings;
    private int[] _timeSignature = new int[2];
    
    // Standard tuning
    public TAB()
    {
        this(new double[] 
            {
                82.407,
                110.000,
                146.832,
                195.998,
                246.942,
                329.628
            }, new int[]{4, 4}
        );
    }
    
    public TAB(TabSettings tabSettings)
    {
        this(tabSettings.getBaseNotes(), tabSettings.getTimeSignature());
    }
    
    public TAB(double[] baseNotes, int[] timeSignature)
    {
        _numOfStrings = baseNotes.length;
        _timeSignature = timeSignature;

        // Copy the base notes
        _baseNotes = new double[_numOfStrings];
        for (int i = 0; i < baseNotes.length; i++)
        {
            _baseNotes[i] = baseNotes[i];
        }
        
        // Initialize the tab
        _tab = new ArrayList<>();
    }
    
    /**
     * The TAB constructor taking just the file name to read from
     * @param filename
     * @throws FileNotFoundException
     */
    public TAB(String filename) throws IOException
    {
        this.readTAB(filename);
    }
    
    public void readTAB(String filename) throws IOException
    {
        File infile = new File(filename);
	Scanner in = new Scanner(infile);
        int[] noteLine;
        
        // Initialize the tab
        _tab = new ArrayList<>();
        
        // Get the time signature
        _timeSignature[0] = in.nextInt();
        _timeSignature[1] = in.nextInt();
        
        // Get the number of strings
        _numOfStrings = in.nextInt();
        
        _baseNotes = new double[_numOfStrings];
        noteLine = new int[_numOfStrings];
        
	// Load the Base note frequencies
        for (int i = 0; i < _numOfStrings; i++)
	{
            _baseNotes[i] = in.nextDouble();
	}
                
        // Read the rest of the data
        // And set up the rest of the tab
        
        while (in.hasNextInt())
        {
            // 
            for (int i = 0; i < _numOfStrings; i++)
            {
                noteLine[i] = in.nextInt();
            }
            this.addLine(noteLine);
        }       
        in.close();
    }
    
    public void saveTAB(String filename) throws FileNotFoundException
    {
        PrintWriter writer = new PrintWriter(filename);
        
        writer.print(_timeSignature[0] + " ");
        writer.println(_timeSignature[1]);
        
        writer.println(_numOfStrings);
        
        for (int i = 0; i < _numOfStrings; i ++)
        {
            writer.println(_baseNotes[i]);
        }
        
        // Iterate through the lines
        for (int[] line : _tab)
        {
            // Iterate through the notes
            for (int i = 0; i < _numOfStrings; i++)
            {
                if (line[i] >= 0)
                {
                    writer.print(" ");
                }
                writer.print(line[i]);
                writer.print(" ");
            }
            writer.println();
        }
        writer.close();
    }
    
    public void addLine(int[] line)
    {
        int[] noteLine = new int[line.length];
        
        // Copy the input
        for (int i = 0; i < line.length; i++)
        {
            noteLine[i] = line[i];
        }
        
        // Add the input
        _tab.add(noteLine);
    }
    
    public void setLine(int[] line, int location)
    {
        if (location >= 0 
                && location <= this.getTABLength()
                && line.length == this.getNumberOfStrings())
        {
            _tab.set(location, line);
        }
    }
    
    public void removeLine()
    {
        _tab.remove(_tab.size() - 1);
    }
    
    public int[] getTimeSignature()
    {
        return _timeSignature;
    }
    
    public int getNumberOfStrings()
    {
        return _baseNotes.length;
    }
    
    public int[] getTABColumn(int lineNumber)
    {
        return _tab.get(lineNumber);
    }
    
    public double[] getBaseNotes()
    {
        return _baseNotes;
    }
    
    public String getTABLine(int lineNumber)
    {
        return getTABLine(lineNumber, 0, this.getTABLength());
    }
    
    public String getTABLine(int lineNumber, int location)
    {
        int min = 0;
        int max = getTABLength();
        int dist = 14;
        
        if (location - dist > min)
        {
            min = location - dist;
        }
        if (location + dist < max)
        {
            max = location + dist;
        }
        
        return getTABLine(lineNumber, min, max);
    }
    
    public String getTABLine(int lineNumber, int start, int end)
    {
        String line = "";
        for (int i = start; i < end; i++)
        {
            int n = _tab.get(i)[lineNumber];
            
            if (n >= 0)
                line += n + "-";
            else
                line += "--";
            if (i % (_timeSignature[0] * 4) == (_timeSignature[0] * 4) - 1)
                line += " | -";
        }
        
        return " " 
                + frequencyToNote(_baseNotes[lineNumber]) 
                + " | -"
                + line 
                + " |";
    }
    
    public String getTABLineFromEnd(int lineNumber, int length)
    {
        // Set the bounds
        int start = getTABLength() - length - 1;
        if (start < 0)
        {
            start = 0;
        }
        int end = getTABLength() - 1;
        if (end < 0)
        {
            end = 0;
        }
        
        // Get the line
        return getTABLine(lineNumber, start, end);
    }
    
    public int getTABLength()
    {
        return _tab.size();
    }
    
    public int frequencyToFret(double frequency, int string)
    {
        double baseFrequency = _baseNotes[string];
        return  (int) Math.rint(12 * (Math.log(frequency) - Math.log(baseFrequency)) 
                / Math.log(2));
    }
    
    public static String frequencyToNote(double frequency)
    {
        String[] noteNames = new String[]{ 
                "A ", "A#", "B ", "C ", "C#", "D ", 
                "D#", "E ", "F ", "F#", "G ", "G#"
                };
        int relToA = (int) Math.rint((Math.log(frequency) - Math.log(110))
                * 12 / Math.log(2)) - 12;
                
        return noteNames[((relToA % 12) + 12) % 12];
    }
    
    public static double closestNote(double frequency)
    {
        return closestNote(frequency, 0);
    }
    
    public static double closestNote(double frequency, int offset)
    {
        int offSet = stepsToA(frequency) + offset;
        
        return 110.0 * Math.pow(2, offSet / 12.0);
    }
    
    
    
    public static int stepsToA(double frequency)
    {
        return (int) Math.rint((Math.log(frequency) - Math.log(110))
                * 12 / Math.log(2));
    }
    
    public static String lineToString(int[] line)
    {
        String ret;
        if (line == null)
        {
            ret = "";
        }
        else
        {
            if (line[0] < 0)
            {
                ret = "-";
            }
            else
            {
                ret = Integer.toString(line[0]);
            }
            
            for (int i = 1; i < line.length; i++)
            {
                if (line[i] < 0)
                {
                    ret = "-\n" + ret;
                }
                else
                {
                    ret = Integer.toString(line[i]) + "\n" + ret;
                }
            }
        }
        
        return ret;
    }
    
    public static int[] stringToLine(String inLine)
    {
        String[] notes = inLine.split("\n");
        int[] retLine = new int[notes.length];
        int loadInt;
        int r;
        
        for (int i = 0; i < notes.length; i++)
        {
            r = notes.length - i - 1;
            try
            {
               loadInt = Integer.parseInt(notes[i]);
               retLine[r] = loadInt;
            }
            catch (final NumberFormatException N)
            {
                retLine[r] = -1;
            }
        }
        
        return retLine;
    }
            
}
