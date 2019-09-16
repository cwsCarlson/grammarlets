import greenfoot.*;  //Actor, GreenfootImage

/**
 * A marker indicating what words have already been corrected.
 * 
 * @author Carlson Sharpless
 * @version 0.02 4/12/2018
 */
public class Strike extends Actor 
{
    GreenfootImage baseImage;
    
    /**
     * Selects and scales the strike image based on the size and whether it is correct.
     */
    public Strike(int xInit, int xEnd, boolean correct)
    {
        if(correct) //use green for correct, red for incorrect
            baseImage = new GreenfootImage("greenStrike.png");
        else
            baseImage = new GreenfootImage("redStrike.png");
        baseImage.scale(xEnd - xInit, 5); //scale the image to the proper length
        this.setImage(baseImage);
    }  
}