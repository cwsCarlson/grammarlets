import greenfoot.*;  //Actor, Color, Greenfoot, GreenfootImage, MouseInfo, World
import java.io.IOException;

/**
 * A blank slate for words, braces, buttons, and strikes.
 * 
 * @author Carlson Sharpless
 * @version 0.02 4/12/2018
 */
public class Canvas extends World
{
    static Actor[][] fullText;
    static Strike[] strikes;
    Actor[] corrections, reasons, currentWords;
    AnswerKey key;
    Brace brace;
    Button undoButton, submitButton;
    String selected;
    int strikeNo = 0;
    int charsPerLine = 55;
    boolean notShown = true;
    String[] reasonList = {"(Contradiction)", "(Homophone)", "(Punctuation)", "(Tense)",
    "(Punctuation)", "(Word Choice)", "(Possessive)", "(Pronoun-Antecedent)",
    "(Run-On)", "(Fragment)", "(Subject-Verb)", "(Punctuation)", "(Other)"};

    /**
     * Creates the canvas, answer key, arrays, and actors.
     */
    public Canvas() throws IOException
    {    
        super(600, 400, 1); //create a new world with 600x400 cells with a cell size of 1x1 pixels
        key = new AnswerKey(); //create the answer key
        fullText = new Actor[8][25]; //create the arrays that hold the text and correction objects
        strikes = new Strike[10];
        corrections = new Actor[10];
        reasons = new Actor[10];
        
        writeText(key.nextQuestion());
        undoButton = new Button("undo"); //Create the undo and submit buttons.
        addObject(undoButton, 561, 340);
        submitButton= new Button("idk");
        addObject(submitButton, 540, 380);
    }
    
    /**
     * Checks for button presses, mouse drags, and world updates every act cycle.
     */
    public void act()
    {
        if(undoButton.returnGreyedStatus() == false && Greenfoot.mouseClicked(undoButton)) //If undo available and clicked, run undo command
            undoCorrection();
        if(Greenfoot.mouseClicked(submitButton)) //if Submit/I Don't Know is clicked, run submit command
            submitAnswer();
        if(Greenfoot.mouseDragged(null) && brace == null) //if the mouse is dragged, start drawing the selection brace
        {
            MouseInfo mouseInfo = Greenfoot.getMouseInfo();
            int x = mouseInfo.getX();
            int y = mouseInfo.getY();
            brace = new Brace(x, calculateBracePos(y), this);
            addObject(brace, x, calculateBracePos(y));
        }
        else if(Greenfoot.mouseDragEnded(null) && brace != null && brace.getWordsAbove() != null) //if the mouse is released, get selected text
        {
            currentWords = brace.getWordsAbove();
            selected = "";
            for(int i = 0; i < currentWords.length && currentWords[i] != null; i++)
            {
                selected += ((Word) currentWords[i]).getText();
                if(i < currentWords.length - 1)
                    selected += " ";
            }
            AnswerWindow.createWindow(selected, this);
            removeObject(brace);
            brace = null;
        }
        if(corrections[0] != null && undoButton.returnGreyedStatus()) //if there's a correction, change the buttons
        {
            undoButton.swapGreyStatus();
            submitButton.swapType();
        }
        if(notShown == true) //if the dialog hasn't been shown yet, show it 
        {
            DialogWindow welcome = new DialogWindow("welcome", key);
            notShown = false;
        }
    }
    
    /**
     * Prints allText on the screen.
     */
    public void writeText(String allText)
    {
        String[] text = allText.split(" "); //Each word is separated by a space
        int line = 0;
        int currentChars = -1;
        int linePlace = 0;
        int xPos = 15;
        for(int i = 0; i < text.length; i++)
        {
            currentChars = currentChars + 1 + text[i].length(); //count number of chars in the line
            if(currentChars > charsPerLine) //If too high, move current word to next line and start over
            {
                line++;
                currentChars = text[i].length();
                linePlace = 0;
                xPos = 15;
            }
            fullText[line][linePlace] = new Word(text[i]);
            GreenfootImage image = new GreenfootImage(text[i], 24, Color.BLACK, Color.WHITE); //create the Word's image, which is the text
            fullText[line][linePlace].setImage(image);
            addObject(fullText[line][linePlace], xPos + (image.getWidth() / 2), getLinePos(line)); //place word and move on
            xPos = xPos + 10 + image.getWidth();
            linePlace++;
        }
    }
    
    /**
     * Calculates the y-position of a new brace given the yPos of the cursor.
     */
    public int calculateBracePos(int yPos)
    {
        for(int i = 0; i < fullText.length; i++) //check all lines
        {
            if(yPos > getLinePos(i) && yPos < getLinePos(i + 1)) //if below this line and above the next
                return getLinePos(i) + 20;
        }
        return 10; //if brace (somehow) isn't on the screen
    }
    
    /**
     * Calculates the y-position of a given line of text.
     */
    public int getLinePos(int line)
    {
        return 50 + (50 * line); //each line is 50 apart
    }
    
    /**
     * Returns the words in a line based on the yPos of a brace.
     */
    public Actor[] getWordsInLine(int yPos)
    {
        int line = (yPos - 70) / 50; //brace is 70 below line start
        return fullText[line];
    }
    
    /**
     * Places a new strikethrough over toCover, adds the correction in replace, and adds the abbreviation for reason.
     */
    public void placeCorrection(String toCover, String replace, int reason)
    {
        int initialX = currentWords[0].getX() - ((currentWords[0].getImage()).getWidth() / 2); //get position of word start
        int finalX = currentWords[currentWords.length - 1].getX() + ((currentWords[currentWords.length - 1].getImage()).getWidth() / 2); //get pos of word end
        strikes[strikeNo] = new Strike(initialX, finalX, key.checkSelection(selected)); //create a new strikethrough
        addObject(strikes[strikeNo], (initialX + finalX) / 2, currentWords[0].getY());

        corrections[strikeNo] = new Word(replace); //create new correction
        GreenfootImage image = new GreenfootImage(replace, 12, getTextColor(key.checkCorrection(replace)), Color.WHITE); //create text
        corrections[strikeNo].setImage(image); //set text 
        addObject(corrections[strikeNo],(initialX + finalX) / 2, currentWords[0].getY() - 12); //add correction
        
        reasons[strikeNo] = new Word(reasonList[reason - 1]); //create new reason
        image = new GreenfootImage(reasonList[reason - 1], 12, getTextColor(key.checkReason(reason, selected)), Color.WHITE);
        reasons[strikeNo].setImage(image); //add reason text
        addObject(reasons[strikeNo],(initialX + finalX) / 2, currentWords[0].getY() - 24); //add reason
        
        strikeNo++; //increase strike number
    }
    
    /**
     * Passes the shade (green or red) used based on the variable.
     */
    public Color getTextColor(boolean correct)
    {
        if(correct) //use green shade
            return new Color(0, 176, 80);
        else //use red shade
            return new Color(255, 0, 0);
    }
    
    /**
     * Removes the last correction.
     */
    public void undoCorrection()
    {
        strikeNo--; //go back to the last correction
        removeObject(corrections[strikeNo]); //remove everything that the last correction added...
        removeObject(reasons[strikeNo]);
        removeObject(strikes[strikeNo]);
        corrections[strikeNo] = null; //...and make them null for the replacement
        reasons[strikeNo] = null;
        strikes[strikeNo] = null;
        if(strikeNo == 0) //if this was the first, reverse the button changes.
        {
           undoButton.swapGreyStatus();
           submitButton.swapType();
        }
    }
    
    /**
     * Clears the canvas and creates a DialogWindow displaying the results.
     */
    public void submitAnswer()
    {
        removeObjects(getObjects(null)); //remove all text (and buttons)
        fullText = new Actor[8][25]; //reset all variables and arrays
        strikes = new Strike[10];
        corrections = new Actor[10];
        reasons = new Actor[10];
        strikeNo = 0;
        undoButton.swapGreyStatus(); //re-add the buttons
        addObject(undoButton, 561, 340);
        submitButton= new Button("idk");
        addObject(submitButton, 540, 380);
        if(key.checkNext()) //if this is the last question, use the "final" dialog and close the window
        {
           DialogWindow dialog = new DialogWindow("done", key);
           System.exit(0);
        }
        else //Otherwise, just use a "results" dialog.
        {   
           DialogWindow dialog = new DialogWindow("results", key);
           writeText(key.nextQuestion());
        }
    }
}