// Imports
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * The TabiFire is the application...
 * @author jackreamy
 */
public class TabiFire extends JFrame
{
    // State Labels
    public static final String commands = "irReEpPtwq";
    public static final char INIT = 'i';
    public static final char REC  = 'r';
    public static final char RECD = 'R';
    public static final char EDT  = 'e';
    public static final char EDIT = 'E';
    public static final char PRA  = 'p';
    public static final char PRAC = 'P';
    public static final char TUNE = 't';
    public static final char WAIT = 'w';
    public static final char QUIT = 'q';
    
    // Data Prompts
    public static final int START_DATA = -1;
    public static final int END_DATA = -2;
    public static final int QUIT_DATA = -3;
    
    // Data Buffer
    private ArrayList<Double> _frequencyData = new ArrayList<>();
    private ArrayList<Integer> _stringNumbers = new ArrayList<>();
    
    // COM Port names
    public static String[] PORTNAMES = new String[]
    { "COM3", "COM4", "COM5" };
    private int _portNameLocation = 0;
    
    // State variable
    private char _state;
    public boolean running = true;
    public boolean connected = false;
    
    // The tabs themselves for the recorder and editor
    TAB _recTAB = new TAB();
    TAB _edtTAB = new TAB();
    TAB _praTAB = new TAB();
    
    // Header Panel
    private final JButton _menuConnect;
    private final JButton _menuRecord;
    private final JButton _menuEdit;
    private final JButton _menuPractice;
    private final JButton _menuTune;
    private final JButton _menuQuit;
    private final JPanel _menuPanel;
    
    private final JTextArea _instructions;
    private final JPanel _headerPanel;
    
    // File Panel
    private final JButton _fileButton;
    private final JTextArea _fileText;
    private final JPanel _filePanel;
    
    // TAB Recorder
    private final TabRecorder _recorder;
    
    // Recorder Settings
    private final RecorderSettings _recorderSettings;
    
    // TAB Editor
    private final TabEditor _editor;
    
    // Tuner
    private final Tuner _tuner;
    
    // Hardware interface
    private Hardware _arduino;
    
    /**
     * The constructor.
     */
    TabiFire()
    {
        // Set up the frame
        final int FRAME_WIDTH = 600;
        final int FRAME_HEIGHT = 450;
        
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        
        this.addWindowListener(new WindowCloser());
        
        // =============================================== //
        //                   The Header                    //
        // =============================================== //
        
        // Menu Items
        _menuPanel = new JPanel();
        _menuPanel.setLayout(new FlowLayout());
        
        // Connect Button
        _menuConnect = new JButton(" Connect ");
        _menuConnect.addActionListener(new MenuConnectButton());
        
        // Record Button
        _menuRecord = new JButton(" Record ");
        _menuRecord.addActionListener(new MenuRecordButton());
        
        // Edit Button
        _menuEdit = new JButton(" Edit ");
        _menuEdit.addActionListener(new MenuEditButton());
        
        // Edit Button
        _menuPractice = new JButton(" Practice ");
        _menuPractice.addActionListener(new MenuPracticeButton());
        
        // Tune Button
        _menuTune = new JButton(" Tune ");
        _menuTune.addActionListener(new MenuTuneButton());
        
        // Quit Button
        _menuQuit = new JButton(" Quit ");
        _menuQuit.addActionListener(new MenuQuitButton());
        
        // Add things to the menu panel
        _menuPanel.add(_menuConnect);
        _menuPanel.add(_menuRecord);
        _menuPanel.add(_menuEdit);
        _menuPanel.add(_menuPractice);
        _menuPanel.add(_menuTune);
        _menuPanel.add(_menuQuit);
        
        // Instructions field
        _instructions = new JTextArea(1, 16);
        _instructions.setEditable(false);
        _instructions.setFont(new Font("Consolas", 0, 16));
        
        // Set up the header
        _headerPanel = new JPanel();
        _headerPanel.setLayout(new BoxLayout(_headerPanel, BoxLayout.Y_AXIS));
        
        _headerPanel.add(_menuPanel);
        _headerPanel.add(_instructions);
        
        // =============================================== //
        //                  The Recorder                   //
        // =============================================== //
        
        _recorder = new TabRecorder();
        _recorderSettings = new RecorderSettings();
        
        
        // =============================================== //
        //                   The Editor                    //
        // =============================================== //
        
        _editor = new TabEditor();
        
        
        // =============================================== //
        //                    The Tuner                    //
        // =============================================== //
        
        _tuner = new Tuner();
        
        
        // =============================================== //
        //                   File Button                   //
        // =============================================== //
        
        // File panel
        _filePanel = new JPanel();
        _filePanel.setLayout(new GridLayout(1, 2));
        
        // File Text Field
        _fileText = new JTextArea(1, 16);
        
        // File Button
        _fileButton = new JButton(" Save ");
        _fileButton.addActionListener(new FileButtonListener());
        
        // Make the file panel
        _filePanel.add(_fileText);
        _filePanel.add(_fileButton);
        
        // =============================================== //
        //                  Construction                   //
        // =============================================== //
        
        // Set the layout of the TabiFire
        this.setLayout(new FlowLayout());
        
        // Add things to the TabiFire panel
        this.add(_headerPanel);
        this.add(_recorder);
        this.add(_recorderSettings);
        this.add(_editor);
        this.add(_tuner);
        this.add(_filePanel);
        
        // Set the state
        this.setState(INIT);
        
        // Set it visible
        this.setVisible(true);
    }
    
    /**
     * Sets the state.
     * @param state the state to switch to.
     */
    public void setState(char state)
    {
        // Switch on the input state and display what needs
        // to be displayed
        hideAll();
        switch(state)
        {
            case INIT:
                showConnector();
                break;
            case REC:
                showRecorderSettings();
                break;
            case RECD:
                showRecorder();
                _recorder.setTAB(_recTAB);
                break;
            case EDT:
                showEditOpener();
                break;
            case EDIT:
                hideAll(false);
                showEditor();
                break;
            case PRA:
                showPracticeOpener();
                break;
            case PRAC:
                break;
            case TUNE:
                showTuner();
                break;
            case QUIT:
                break;
        }

        // Update the state
        _state = state;
    }
    
    // ===================================================== //
    //                   Display Functions                   //
    // ===================================================== //
    
    /**
     * Sets most things not visible
     * @param clearText whether the fileText should be cleared
     */
    public void hideAll(boolean clearText)
    {
        _headerPanel.setVisible(true);
        _instructions.setVisible(false);
        _recorder.setVisible(false);
        _recorderSettings.setVisible(false);
        _editor.setVisible(false);
        _tuner.setVisible(false);
        _filePanel.setVisible(false);
        
        if (clearText)
            _fileText.setText("");
    }
    
    /**
     * Calls the hideAll() method and clears the fileText
     */
    public void hideAll()
    {
        hideAll(true);
    }
    
    /**
     * Shows the connector features
     */
    public void showConnector()
    {   
        // Show the things we need
        _filePanel.setVisible(true);
        _fileButton.setText(" Connect ");
        if (!connected)
        {
            _fileText.setText(PORTNAMES[_portNameLocation]);
        }
        else
        {
            _fileText.setText("Connected");
        }
        
        // Set instructions
        _instructions.setText(" Enter the name of the COM port to connect with: ");
        _instructions.setVisible(true);
    }
    
    /**
     * Shows the Recorder Settings panel
     */
    public void showRecorderSettings()
    {
        // Show the things we need
        _recorderSettings.setVisible(true);
        _filePanel.setVisible(true);
        _fileButton.setText(" Ready ");
        
        // Set instructions
        _instructions.setText(" Adjust the instrument setup: ");
        _instructions.setVisible(true);
    }
    
    /**
     * Shows the Tab Recorder panel
     */
    public void showRecorder()
    {
        // Show the things we do need
        _recorder.setVisible(true);
        _filePanel.setVisible(true);
        _fileButton.setText(" Save ");
        
        // Set instructions
        _instructions.setText(" Press the Red Button to begin recording: ");
        _instructions.setVisible(true);
    }
    
    /**
     * Shows the "Open file" page
     */
    public void showEditOpener()
    {
        // Show the things we need
        _filePanel.setVisible(true);
        _fileButton.setText(" Open ");
        _fileText.setText("SampleTab.txt");
        
        // Set instructions
        _instructions.setText(" Enter the name of the file to edit: ");
        _instructions.setVisible(true);
    }
    
    /**
     * Shows the editor
     */
    public void showEditor()
    {
        // Show the things we need
        _filePanel.setVisible(true);
        _fileButton.setText(" Save ");
        _editor.setVisible(true);
        
        // Set instructions
        _instructions.setText(" Use the arrow buttons to navigate the TAB "
                + "\n Edit a note by clicking and entering the new note "
                + "\n Click the update button to change the note in the TAB "
                + "\n Click save to save the TAB to file ");
        _instructions.setVisible(true);
    }
    
    public void showPracticeOpener()
    {
        // Show the things we need
        _filePanel.setVisible(true);
        _fileButton.setText(" Open ");
        _fileText.setText("SampleTab.txt");
        
        // Set instructions
        _instructions.setText(" Enter the name of the TAB to practice: ");
        _instructions.setVisible(true);
    }
    
    /**
     * Shows the tuner
     */
    public void showTuner()
    {
        // Show the things we need
        _tuner.setVisible(true);
    }
    
    // ===================================================== //
    //                     Save and Load                     //
    // ===================================================== //
    
    /**
     * Tries to save the Tab to a file
     * @param tab the tab to save
     * @param filename the filename to save 
     * @return whether it successfully saved
     */
    public boolean trySaving(TAB tab, String filename)
    {
        // Default failed to save
        boolean success = false;
        
        // If the filename doesn't have .txt, add it to the filename
        if (!filename.contains(".txt"))
        {
            filename += ".txt";
        }
        
        try
        {
            // Saves the tab
            tab.saveTAB(filename);
            success = true;
        }
        catch(final IOException E)
        {
            // If it couldn't save, tell the user
            JOptionPane.showMessageDialog(null, "Error saving file.");
        }
        
        return success;
    }
    
    /**
     * Tries to load the tab from file
     * @param tabDisplay the display to set
     * @param tab the tab to load into
     * @param filename the file to load from
     * @return whether the file was successfully loaded
     */
    public boolean tryLoading(TabDisplay tabDisplay, TAB tab, String filename)
    {
        boolean success = false;
                
        if (!filename.contains(".txt"))
        {
            filename += ".txt";
        }
        
        try
        {
            // Read the tab from file
            tab.readTAB(filename);

            // Set the tab to the display
            tabDisplay.setTAB(tab);
            
            success = true;
        }
        catch(final IOException E)
        {
            // If it didn't work, let the user know
            JOptionPane.showMessageDialog(null, "Could not locate file.");
        }
        
        return success;
    }
    
    // ===================================================== //
    //                   Button Listeners                    //
    // ===================================================== //
    
    /**
     * The multipurpose file button with it's various purposes
     */
    private class FileButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            switch(_state)
            {
                case INIT:
                    // Try and connect
                    connected = _arduino.connect(_fileText.getText());
                    if (connected)
                    {
                        _fileText.setText("Connected");
                        respond();
                    }
                    else
                    {
                        _portNameLocation = (_portNameLocation + 1) % 3;
                        _fileText.setText(PORTNAMES[_portNameLocation]);
                    }
                    break;
                case REC:
                    _recTAB = new TAB(_recorderSettings.getTabSettings());
                    _recorderSettings.savePresets();
                    setState(RECD);
                    respond();
                    break;
                case RECD:
                    trySaving(_recTAB, _fileText.getText());
                    break;
                case EDT:
                    if (tryLoading(_editor, _edtTAB, _fileText.getText()))
                        setState(EDIT);
                    else
                        setState(EDT);
                    break;
                case EDIT:
                    trySaving(_edtTAB, _fileText.getText());
                    break;
                case TUNE:
                    break;
                case QUIT:
                    break;
            }
        }
    }
    
    /**
     * The menu connect button sets the INIT state
     */
    private class MenuConnectButton implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            setState(INIT);
        }
    }
    
    /**
     * The menu record button sets the REC state
     */
    private class MenuRecordButton implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            setState(REC);
        }
    }
    
    /**
     * The edit button sets the EDT state
     */
    private class MenuEditButton implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            setState(EDT);
        }
    }
    
    /**
     * The edit button sets the EDT state
     */
    private class MenuPracticeButton implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            setState(PRA);
        }
    }
    
    /**
     * The tune button sets the TUNE state
     */
    private class MenuTuneButton implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            setState(TUNE);
        }
    }
    
    /**
     * The quit button sets the QUIT state
     */
    private class MenuQuitButton implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            setState(QUIT);
        }
    }
    
    /**
     * The window closer allows you to close the window
     */
    private class WindowCloser extends WindowAdapter
    {  
        @Override
        public void windowClosing(final WindowEvent event)
        {  
            running = false;
            System.exit(0);
        }
    }
    
    // ===================================================== //
    //                  Data Communication                   //
    //                These are undeveloped                  //
    // ===================================================== //
    
    /**
     * This isn't developed yet, but it'll be used to communicate
     * between the Hardware object and the TabiFire
     * @param arduino the hardware object to connect with
     */
    public void link(Hardware arduino)
    {
        _arduino = arduino;
    }
    
    /**
     * Process the data that has already been taken 
     */
    public void processData()
    {
        switch(_state)
        {
            case TUNE:
                _tuner.setFrequency(_frequencyData.get(0));
                
                for (int i = 0; i < _stringNumbers.size(); i++)
                {
                    if (_stringNumbers.get(i) < _recTAB.getNumberOfStrings())
                    {
                        _recorderSettings.setString(_stringNumbers.get(i), _frequencyData.get(i));
                    }
                }
                
                break;
            case RECD:
                double[] line = new double[_recTAB.getNumberOfStrings()];
                
                for (int i = 0; i < line.length; i++)
                {
                    line[i] = -1.0;
                }
        
                for (int i = 0; i < _frequencyData.size(); i++)
                {
                    line[_stringNumbers.get(i)] = _frequencyData.get(i);
                }
                
                _recorder.newLine(line);
                
                break;
            default:
                break;
        }
    }
    
    /**
     * Sends back to the Arduino the appropriate response given the state
     */
    public void respond()
    {
        switch(_state)
        {
            case RECD:
                _arduino.send('r');
                break;
            case PRAC:
                _arduino.send('p');
                break;
            default:
                _arduino.send(_state);
                break;
        }
    }
    
    /**
     * Takes a character command
     * @param c a character command
     */
    public void send(char c)
    {
        if (commands.indexOf(c) > -1)
        {
            setState(c);
            respond();
        }
    }
    
    /**
     * Takes an integer command
     * @param i an integer command
     */
    public void send(int i)
    {
        switch(i)
        {
            case START_DATA:
                _frequencyData.clear();
                _stringNumbers.clear();
                break;
            case END_DATA:
                processData();
                break;
            case QUIT_DATA:
                break;
            default:
                if (i < _recTAB.getNumberOfStrings())
                {
                    _stringNumbers.add(i);
                }
                else
                {
                    send((double) i);
                }
                break;
        }
    }
    
    /**
     * Takes a double command
     * @param d a double command
     */
    public void send(double d)
    {
        _frequencyData.add(d);
    }
    
    public void send(String S)
    {
        System.out.println("String " + S);
    }
}
