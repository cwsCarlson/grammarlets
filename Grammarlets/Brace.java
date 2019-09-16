import greenfoot.*;  //Actor, GreenfootImage, MouseInfo

/**
 * A depiction of the words selected by the user.
 * 
 * @author Carlson Sharpless
 * @version 0.02 4/12/2018
 */
public class Brace extends Actor
{
    static GreenfootImage[] images; //array of braces at various sizes
    int initialX;
    int yPos;
    int currentX; //X position of the mouse cursor
    int size;
    Canvas canvas; //the world, passed to get access to the line's word list
    
    /**
     * Prepares images, establishes the initial position, and passes the world.
     */
    public Brace(int xInit, int yInit, Canvas c)
    {
        initializeImages(); //set up the image array
        initialX = xInit; //establish variables
        yPos = yInit;
        canvas = c;
    }
    
    /**
     * Follows the cursor's current X position and adjust the image accordingly.
     */
    public void act() 
    {
        MouseInfo mouseInfo = Greenfoot.getMouseInfo(); //Get information on the mouse
        currentX = mouseInfo.getX(); //Determine new X location
        size = currentX - initialX; //Determine new size
        
        this.setLocation(initialX + (size / 2), yPos); //Set new location and image
        this.setImage(images[Math.abs(size)]);
    }    
    
    /**
     * Sets up the image array, which holds different braces of different sizes.
     */
    public static void initializeImages()
    {
        if(images == null)
        {
            GreenfootImage baseImage = new GreenfootImage("brace.png"); //the base image used in scaling
            images = new GreenfootImage[601]; //601 images for all lengths between 1 and 600 
            for(int i = 1; i < images.length; i++) //each new image is scaled to length i.
            {
                images[i] = new GreenfootImage(baseImage);
                images[i].scale(i, 20);
            }
        }
    }
    
    /**
     * Creates an array of the words above the brace.
     */
    public Actor[] getWordsAbove()
    {
        if(initialX > currentX) //if brace was dragged left, flip initial and current so words will be detected
        {
            int temp = currentX;
            currentX = initialX;
            initialX = temp;
        }
        if(canvas != null && canvas.getWordsInLine(yPos) != null)
        {
            Actor[] wordList = canvas.getWordsInLine(yPos); //get the words in the line above
            Actor[] words = new Actor[getWordCount(wordList)]; //create an empty array to store included words in.
            int place = 0;
            for(int i = 0; i < wordList.length; i++)
            {
                if (wordList[i] != null)
                {
                    int wordSize = (wordList[i].getImage()).getWidth(); //get the word's width to find its initial and final x-coords
                    if (wordList[i].getX() - (wordSize / 2) >= initialX && wordList[i].getX() + (wordSize / 2) <= currentX)
                    {
                        words[place] = wordList[i];
                        place++;
                    }
                }
            }
            return words;
        }
        return null;
    }
    
    /**
     * Gets the number of words above the brace to properly size the words array in getWordsAbove().
     */
    public int getWordCount(Actor[] wordList)
    {
        int numWords = 0;
        for(int i = 0; i < wordList.length; i++)
        {
            if(wordList[i] != null)
            {
                int wordSize = (wordList[i].getImage()).getWidth();
                if (wordList[i].getX() - (wordSize / 2) >= initialX && wordList[i].getX() + (wordSize / 2) <= currentX)
                    numWords++;
            }
        }
        return numWords;
    }
}