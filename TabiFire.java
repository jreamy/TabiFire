
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class TabiFire extends JFrame
{
    // State Labels
    public static final char INIT = 'i';
    public static final char REC  = 'r';
    public static final char RECD = 'R';
    public static final char EDT  = 'e';
    public static final char EDIT = 'E';
    public static final char TUNE = 't';
    public static final char QUIT = 'q';
    
    // State variable
    private char _state;
    
    // The tabs themselves
    TAB _recTAB;
    TAB _edtTAB;
    
    // Header Panel
    private final JButton _menuConnect;
    private final JButton _menuRecord;
    private final JButton _menuEdit;
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
        
        this.setLayout(new FlowLayout());

        this.add(_headerPanel);
        this.add(_recorder);
        this.add(_recorderSettings);
        this.add(_editor);
        this.add(_tuner);
        this.add(_filePanel);
        
        this.setState(INIT);
        
        this.setVisible(true);
    }
    
    public void setState(char state)
    {
        switch(state)
        {
            case INIT:
                hideAll();
                showConnector();
                break;
            case REC:
                hideAll();
                showRecorderSettings();
                break;
            case RECD:
                hideAll();
                showRecorder();
                _recorder.setTAB(_recTAB);
                break;
            case EDT:
                hideAll();
                showEditOpener();
                break;
            case EDIT:
                hideAll(false);
                showEditor();
                break;
            case TUNE:
                hideAll();
                showTuner();
                break;
            case QUIT:
                hideAll();
                break;
        }

        // Update the state
        _state = state;
    }
    
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
    
    public void hideAll()
    {
        hideAll(true);
    }
    
    public void showConnector()
    {   
        // Show the things we need
        _filePanel.setVisible(true);
        _fileButton.setText(" Connect ");
        
        // Set instructions
        _instructions.setText(" Enter the name of the COM port to connect with: ");
        _instructions.setVisible(true);
    }
    
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
    
    public void showTuner()
    {
        // Show the things we need
        _tuner.setVisible(true);
    }
    
    private class FileButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            switch(_state)
            {
                case INIT:
                    break;
                case REC:
                    _recTAB = new TAB(_recorderSettings.getTabSettings());
                    try
                    {
                        _recTAB.readTAB("BabyTab.txt");
                    }
                    catch (final IOException E){}
                    setState(RECD);
                    break;
                case RECD:
                    break;
                case EDT:
                    try
                    {
                        _edtTAB = new TAB(_fileText.getText());
                        _editor.setTAB(_edtTAB);
                        setState(EDIT);
                    }
                    catch(final IOException E)
                    {
                        JOptionPane.showMessageDialog(null, "Could not locate file.");
                        setState(EDT);
                    }
                    break;
                case EDIT:
                    try
                    {
                        _edtTAB.saveTAB(_fileText.getText());
                    }
                    catch(final IOException E)
                    {
                        JOptionPane.showMessageDialog(null, "Error saving file.");
                    }
                    break;
                case TUNE:
                    break;
                case QUIT:
                    break;
            }
        }
    }
    
    private class MenuConnectButton implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            setState(INIT);
        }
    }
    
    private class MenuRecordButton implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            setState(REC);
        }
    }
    
    private class MenuEditButton implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            setState(EDT);
        }
    }
    
    private class MenuTuneButton implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            setState(TUNE);
        }
    }
    
    private class MenuQuitButton implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            setState(QUIT);
        }
    }
    
    private class WindowCloser extends WindowAdapter
    {  
        @Override
        public void windowClosing(final WindowEvent event)
        {  
            System.exit(0);
        }
    }
}
