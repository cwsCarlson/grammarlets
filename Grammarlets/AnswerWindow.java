import java.awt.event.*; //ActionEvent, KeyEvent, WindowEvent
import javax.swing.*; //AbstractAction, Action, BorderFactory, BoxLayout, JFormattedTextField, JFrame, JPanel, JToolBar 

/**
 * A window with a text box, a reasoning selector, and two buttons.
 * 
 * @author Carlson Sharpless
 * @version 0.02 4/12/2018
 */
public class AnswerWindow extends JPanel
{
    static JFrame frame;
    private JPanel windowPanel; //panel for AnswerPanel and buttons
    private AnswerPanel mainPanel; //window's AnswerPanel
    String textSelected;
    Canvas canvas; //the world, called by the SubmitAction command to place strikes.
    
    /**
     * Creates the main AnswerWindow panel and passes instance variables. Invoked by the createWindow process.
     */
    public AnswerWindow(String select, Canvas c)
    {        
       textSelected = select; //passes variables
       canvas = c;
     
       mainPanel = new AnswerPanel(textSelected); //create the panel containing answering tools
       
       JButton cancel = new JButton(new CloseAction("Cancel", "Close this window.", new Integer(KeyEvent.VK_X))); //create the buttons and assign actions
       JButton submit = new JButton(new SubmitAction("Submit", "Submit your answer.", new Integer(KeyEvent.VK_ENTER)));

       windowPanel = new JPanel(); //create panel for the full window
       windowPanel.setLayout(new BoxLayout(windowPanel, BoxLayout.PAGE_AXIS));
       windowPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
       windowPanel.add(mainPanel); //add panel and buttons
       windowPanel.add(submit);
       windowPanel.add(cancel);
    }
    
    /**
     * Creates an instance of an AnswerWindow. 
     * This creates the frame and packs the window panel created by the constructor.
     */
    public static void createWindow(String select, Canvas c)
    {
        frame = new JFrame("Answer Input"); //create the window frame
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 300);
        
        AnswerWindow newContentPane = new AnswerWindow(select, c); //create content pane
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane.windowPanel);
        
        frame.pack(); //pack and display window
        frame.setVisible(true);
    }
    
    /**
     * The action invoked when the "Close" button is pressed.
     */
    public class CloseAction extends AbstractAction 
    {
        public CloseAction(String text, String desc, Integer mnemonic)
        {
            super(text); //pass variables
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }
        public void actionPerformed(ActionEvent e)
        {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)); //close the window
        } 
    }
    
    /**
     * The action invoked when the "Submit" button is pressed.
     */
    public class SubmitAction extends AbstractAction 
    {
        public SubmitAction(String text, String desc, Integer mnemonic) 
        {
            super(text); //pass variables
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }
        public void actionPerformed(ActionEvent e)
        {
            if(!(textSelected.equals(null) || textSelected.equals(""))) //If there is text selected, place a strike on the canvas
               canvas.placeCorrection(textSelected, mainPanel.getUserAnswer(), mainPanel.getRuleSelected());
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)); //close the window
        }
    }
}