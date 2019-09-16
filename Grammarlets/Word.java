import greenfoot.Actor;

/**
 * A word in the canvas. Used for both passage and correction text.
 * This class is essentially a placeholder that displays text imposed by the Canvas.
 * It also passes text that has been selected by Braces.
 * 
 * @author Carlson Sharpless
 * @version 0.02 4/12/2018
 */
public class Word extends Actor
{
    String wordText;
    
    /**
     * Passes text into the Word object.
     */
    public Word(String text)
    {
        wordText = text;
    }

    /**
     * Passes the text back to the Canvas to be used in AnswerWindows.
     */
    public String getText()
    {
        return wordText;
    }
}