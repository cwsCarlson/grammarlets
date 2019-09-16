import greenfoot.*;  //Actor, GreenfootImage

/**
 * Press it and things happen.
 * This class is used for both the Undo and Submit/I Don't Know buttons. 
 * 
 * @author Carlson Sharpless
 * @version 0.02 4/6/2018
 */
public class Button extends Actor
{
    GreenfootImage image;
    String currentType;
    boolean greyed;

    /**
     * Sets the image and type of the button based on the string provided.
     */
    public Button(String type)
    {
        currentType = type;
        if(currentType == "undo")
        {
            image = new GreenfootImage("undoButtonGreyed.png");
            greyed = true;
        }
        if(currentType == "idk")
           image = new GreenfootImage("idkButton.png");
        if(currentType == "submit")
           image = new GreenfootImage("submitButton.png");
        this.setImage(image);
    }

    /**
     * If "Don't Know" or "Submit", changes it to the other type to reflect whether there are answers.
     */
    public void swapType()
    {
        if(currentType == "idk")
        {
            currentType = "submit";
            image = new GreenfootImage("submitButton.png"); 
            this.setImage(image);
        }
        else if(currentType == "submit")
        {
            currentType = "idk";
            image = new GreenfootImage("idkButton.png"); 
            this.setImage(image);
        }        
    }
    
    /**
     * If an "Undo" button, changes whether the button is greyed out to indicate whether it is unavailable.
     */
    public void swapGreyStatus()
    {
        if(currentType == "undo" && greyed == false)
        {
            greyed = true;
            image = new GreenfootImage("undoButtonGreyed.png");
            this.setImage(image);
        }
        else if(currentType == "undo" && greyed == true)
        {
            greyed = false;
            image = new GreenfootImage("undoButton.png");
            this.setImage(image);
        }
    }
    
    /**
     * Returns whether the button is greyed.
     */
    public boolean returnGreyedStatus()
    {
        return greyed;
    }
}