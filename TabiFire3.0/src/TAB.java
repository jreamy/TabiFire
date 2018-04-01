// Imports
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * The tab itself.
 * @author jackreamy
 */
public class TAB 
{
    // ArrayList of interger arrays that holds the tab lines
    private ArrayList<int []> _tab;
    
    // Private data of the tab
    private double[] _baseNotes;
    private int _numOfStrings;
    private int[] _timeSignature = new int[2];
    
    /**
     * Empty constructor that loads E Standard in an empty tab.
     */
    public TAB()
    {
        // Calls the constructor
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
    
    /**
     * Constructor that takes just settings and loads an empty tab.
     * @param tabSettings the settings to use
     */
    public TAB(TabSettings tabSettings)
    {
        // Calls the constructor
        this(tabSettings.getBaseNotes(), tabSettings.getTimeSignature());
    }
    
    /**
     * The default constructor which takes the base notes
     * and time signature.
     * @param baseNotes the incoming base notes
     * @param timeSignature the incoming time signature
     */
    public TAB(double[] baseNotes, int[] timeSignature)
    {
        // Set the number of strings and time signature
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
     * @param filename the file to load.
     * @throws FileNotFoundException
     */
    public TAB(String filename) throws IOException
    {
        // Loads the tab in lieu of the constructor
        this.readTAB(filename);
    }
    
    /**
     * Basically a constructor, reconstructs the tab from
     * the file
     * @param filename the file to load.
     * @throws IOException 
     */
    public void readTAB(String filename) throws IOException
    {
        // Open the file
        File infile = new File(filename);
	Scanner in = new Scanner(infile);
        
        // Array for parsing data from the file
        int[] noteLine;
        
        // Initialize the tab to an empty ArrayList
        _tab = new ArrayList<>();
        
        // Get the time signature
        _timeSignature[0] = in.nextInt();
        _timeSignature[1] = in.nextInt();
        
        // Get the number of strings
        _numOfStrings = in.nextInt();
        
        // Set up the arrays
        _baseNotes = new double[_numOfStrings];
        noteLine = new int[_numOfStrings];
        
	// Load the Base note frequencies
        for (int i = 0; i < _numOfStrings; i++)
	{
            _baseNotes[i] = in.nextDouble();
	}
                
        // Iterate through the rest of the file
        while (in.hasNextInt())
        {
            // Add the notes from each line to the array
            for (int i = 0; i < _numOfStrings; i++)
            {
                noteLine[i] = in.nextInt();
            }
            
            // Add the array to the ArrayList
            this.addLine(noteLine);
        }
        
        // Close the file
        in.close();
    }
    
    /**
     * Saves the tab to file.
     * @param filename the filename to which to save the tab.
     * @throws FileNotFoundException 
     */
    public void saveTAB(String filename) throws FileNotFoundException
    {
        // Open a new file writer
        PrintWriter writer = new PrintWriter(filename);
        
        // Print the time signature
        writer.print(_timeSignature[0] + " ");
        writer.println(_timeSignature[1]);
        
        // Print the number of strings
        writer.println(_numOfStrings);
        
        // Print the base notes to file
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
                // If it's not negative, print an extra space
                if (line[i] >= 0)
                    writer.print(" ");
                
                // Print the fret (-1 if not played)
                writer.print(line[i]);
                
                // Print a space
                writer.print(" ");
            }
            
            // Print a newline char \n
            writer.println();
        }
        
        // Close the file
        writer.close();
    }
    
    /**
     * Add the line to the tab.
     * @param line the incoming line to add to the tab
     */
    public void addLine(int[] line)
    {
        // Array for copying the incoming line
        int[] noteLine = new int[line.length];
        
        // Copy the input 
        for (int i = 0; i < line.length; i++)
        {
            noteLine[i] = line[i];
        }
        
        // Add the input to the tab
        _tab.add(noteLine);
    }
    
    /**
     * Sets a specific line
     * @param line the line to set
     * @param location the location to set the line
     */
    public void setLine(int[] line, int location)
    {
        // Big check of:
        //  - if location is positive
        //  - if location is within the tab
        //  - if the incoming line has the correct length
        if (location >= 0 
                && location <= this.getTABLength()
                && line.length == this.getNumberOfStrings())
        {
            // Sets the line if it met all requirements
            _tab.set(location, line);
        }
    }
    
    /**
     * Removes the last line from the tab.
     */
    public void removeLine()
    {
        _tab.remove(_tab.size() - 1);
    }
    
    /**
     * Returns the time signature.
     * @return the time signature.
     */
    public int[] getTimeSignature()
    {
        return _timeSignature;
    }
    
    /**
     * Returns the number of strings.
     * @return the number of strings.
     */
    public int getNumberOfStrings()
    {
        return _baseNotes.length;
    }
    
    /**
     * Returns the array at the given line number (a column).
     * @param lineNumber the line number to return.
     * @return the array at the given line number (a column).
     */
    public int[] getTABColumn(int lineNumber)
    {
        return _tab.get(lineNumber);
    }
    
    /**
     * Returns the base note frequencies.
     * @return the base note frequencies.
     */
    public double[] getBaseNotes()
    {
        return _baseNotes;
    }
    
    /**
     * Returns the entire tab line (row) as a String.
     * @param lineNumber the string number to return.
     * @return the entire tab line (row) as a String.
     */
    public String getTABLine(int lineNumber)
    {
        // Call the getTABLine function and return the entire row
        return getTABLine(lineNumber, 0, this.getTABLength());
    }
    
    /**
     * Returns the 14 notes to the left and right of the location in the row.
     * @param lineNumber the string number to return.
     * @param location the location in the string.
     * @return the 14 notes to the left and right of the location in the row.
     */
    public String getTABLine(int lineNumber, int location)
    {
        int min = 0;
        int max = getTABLength();
        int dist = 14;
        
        // If the min is too far, set it closer to the location.
        if (location - dist > min)
            min = location - dist;
        
        // If the max is too far, set it closer to the location.
        if (location + dist < max)
            max = location + dist;
        
        // Call the getTABLine function and return it.
        return getTABLine(lineNumber, min, max);
    }
    
    /**
     * Returns a String of the row from the start to the end location.
     * @param lineNumber the string number.
     * @param start the starting location of the row.
     * @param end the end location of the row.
     * @return a String of the row from the start to the end location.
     */
    public String getTABLine(int lineNumber, int start, int end)
    {
        // The Strings for parsing the line.
        String line = "";
        String ret;
        
        // Iterate through the data from the start to end locations
        for (int i = start; i < end; i++)
        {
            // The value from the tab at the given location
            int n = _tab.get(i)[lineNumber];
            
            if (n >= 0)             // If it's posiitve add it 
                line += n + "-";
            else                    // If it's -1 add a '-'
                line += "--";
            
            // If the position is the end of a measure, add a '|'
            if (i % (_timeSignature[0] * 4) == (_timeSignature[0] * 4) - 1)
                line += " | -";
        }
        
        // Add the name of the base note, and the line data 
        ret = " " 
                + frequencyToNote(_baseNotes[lineNumber]) 
                + " | -"
                + line 
                + " |";
        
        // Reformat the end of the stirng if it ends in a break
        if (ret.substring(ret.length() - 7).equals("- | - |"))
            ret = ret.substring(0, ret.length() - 4);
        
        // Return the String
        return ret;
    }
    
    /**
     * Returns a certain amount of the tab line from the end.
     * @param lineNumber the string number
     * @param length how much of the tab line to return
     * @return 
     */
    public String getTABLineFromEnd(int lineNumber, int length)
    {
        // Set start bound
        int start = getTABLength() - length - 1;
        if (start < 0)
            start = 0;
        
        // Set the end bound
        int end = getTABLength() - 1;
        if (end < 0)
            end = 0;
                
        // Return the line
        return getTABLine(lineNumber, start, end);
    }
    
    /**
     * Returns a formatted tab with default of 3 measures per line.
     * @return a formatted tab with default of 3 measures per line.
     */
    public String getFormattedTAB()
    {
        return getFormattedTAB(3);
    }
    
    /**
     * Returns a formatted tab with an given number of measures per line.
     * @param measuresPerLine the number of measures per line.
     * @return a formatted tab with an given number of measures per line.
     */
    public String getFormattedTAB(int measuresPerLine)
    {
        // The return line
        String ret = "";
        int step = _timeSignature[0] * 4 * measuresPerLine;
        int loc = 0;
        
        // Iterate through the tab adding each line to the return string
        while (loc + step < getTABLength())
        {
            for (int i = getNumberOfStrings() - 1; i >= 0; i--)
            {
                ret += getTABLine(i, loc, loc + step);
                ret += "\n";
            }
            ret += "\n";
            loc += step;
        }
        
        // Add the remaining measure(s) to the lazt line
        for (int i = getNumberOfStrings() - 1; i >= 0; i--)
        {
            ret += getTABLine(i, loc, getTABLength());
            ret += "\n";
        }
        
        return ret;
    }
    
    /**
     * Returns the length of the tab.
     * @return the length of the tab.
     */
    public int getTABLength()
    {
        return _tab.size();
    }
    
    /**
     * Returns the fret of a given string based on the frequency.
     * @param frequency the frequency of the string
     * @param string the string number
     * @return the fret of a given string based on the frequency.
     */
    public int frequencyToFret(double frequency, int string)
    {
        double baseFrequency = _baseNotes[string];
        return  (int) Math.rint(12 * (Math.log(frequency) - Math.log(baseFrequency)) 
                / Math.log(2));
    }
    
    // ==================================================== //
    //                    Static methods                    //
    //             available to all other files             //
    // ==================================================== //
    
    /**
     * Returns the note name of a given frequency.
     * @param frequency the frequency to translate into a note.
     * @return the note name of a given frequency.
     */
    public static String frequencyToNote(double frequency)
    {
        // An Array of all the note names 
        String[] noteNames = new String[]{ 
                "A ", "A#", "B ", "C ", "C#", "D ", 
                "D#", "E ", "F ", "F#", "G ", "G#"
                };
        
        // Gets the position of the note relative to A
        int relToA = (int) Math.rint((Math.log(frequency) - Math.log(110))
                * 12 / Math.log(2)) - 12;
        
        // Returns the note name from the array
        return noteNames[((relToA % 12) + 12) % 12];
    }
    
    /**
     * Returns the closest frequency to the input frequency 
     * that is a note.
     * @param frequency the input frequency.
     * @return the closest frequency to the input frequency
     * that is a note.
     */
    public static double closestNote(double frequency)
    {
        return closestNote(frequency, 0);
    }
    
    /**
     * Returns the closest frequency to the input frequency
     * plus the given offset of frets (positive or negative).
     * @param frequency the input frequency.
     * @param offset the number of frets away from the note.
     * @return the closest frequency to the input frequency
     * plus the given offset of frets (positive or negative).
     */
    public static double closestNote(double frequency, int offset)
    {
        // Gets the number of 'frets' away from A (110.0 Hz)
        int noteStep = stepsToA(frequency) + offset;
        
        // Returns the frequency of that number of frets
        return 110.0 * Math.pow(2, noteStep / 12.0);
    }
    
    /**
     * Returns the number of frets away from concert A (110.0 Hz).
     * @param frequency the input frequency.
     * @return the number of frets away from concert A (110.0 Hz).
     */
    public static int stepsToA(double frequency)
    {
        // This is a fancy math function, but just manipulating:
        // 110.0 * 2 ^ (x / 12)
        return (int) Math.rint((Math.log(frequency) - Math.log(110))
                * 12 / Math.log(2));
    }
    
    /**
     * Takes a line (column) and returns a String.
     * @param line the input line.
     * @return a line (column) in String format.
     */
    public static String lineToString(int[] line)
    {
        // The return string
        String ret = "";

        // If the line isn't empty
        if (line != null)
        {
            // If the character is negative (-1) add a '-' 
            if (line[0] < 0)
            {
                ret = "-";
            }
            else
            {
                // Otherwise, add the integer 
                ret = Integer.toString(line[0]);
            }
            
            // Iterate and continue likewise, adding a newline character
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
    
    /**
     * Takes an input line in String format and returns the int[] array.
     * @param inLine the String to parse.
     * @return the int[] array corresponding to the input string.
     */
    public static int[] stringToLine(String inLine)
    {
        // Split the input String around the newline characters
        String[] notes = inLine.split("\n");
        
        // Initialize a return array with the proper length
        int[] retLine = new int[notes.length];
        
        // Variables for parsing data
        int loadInt;
        int r; // reverse index
        
        // Iterate through the items in the notes[] array
        for (int i = 0; i < notes.length; i++)
        {
            // Set the reverse index
            r = notes.length - i - 1;
            try
            {
               // If the line can be parsed to an int, add it to the array
               loadInt = Integer.parseInt(notes[i]);
               retLine[r] = loadInt;
            }
            catch (final NumberFormatException N)
            {
                // Otherwise, add a -1 ('-') to the array
                retLine[r] = -1;
            }
        }
        
        return retLine;
    }
    
    /**
     * Copies the input tab to the clipboard with the given number of lines.
     * @param tab the input tab
     * @param measuresPerLine the number of measures per line
     */
    public static void copyTAB(TAB tab, int measuresPerLine)
    {
        // Copy to clipboard the formatted tab
        stringToClipboard(tab.getFormattedTAB(measuresPerLine));
    }
    
    /**
     * Copies the input tab to the clipboard with the default 
     * of 2 measures per line.
     * @param tab the input tab
     */
    public static void copyTAB(TAB tab)
    {
        // Call the other copy method with input measuresPerLine = 2
        copyTAB(tab, 2);
    }
    
    /**
     * Copies input String str to the clipboard.
     * @param str the input String to copy to the clipboard.
     */
    public static void stringToClipboard(String str)
    {
        // Gets the system clipboard
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        
        // Makes a 'StringSelection' object out of the input string
        StringSelection sl = new StringSelection(str);
        
        // Copies the string to the clipboard
        clip.setContents(sl, null);
    }
}
