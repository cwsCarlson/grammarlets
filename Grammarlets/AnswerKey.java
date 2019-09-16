import java.io.*; //BufferedReader, InputStream

/**
 * An arrays containing the questions, as well as some arrays containing the answers of the current questions.
 * 
 * @author Carlson Sharpless
 * @version 0.02 4/12/2018
 */
public class AnswerKey  
{
    String[] questionSets;
    String[][] answers;
    boolean[][] currentResults;
    int currentQ;

    /**
     * Passes and splits the question list.
     */
    public AnswerKey() throws IOException
    {
       InputStream is = getClass().getClassLoader().getResourceAsStream("QuestionList.txt"); //create objects used to read text file
       BufferedReader r = new BufferedReader(new InputStreamReader(is));
       
       currentQ = 0;
       String fullKey = r.readLine(); //since entire key is on one line, this copies entire key
       questionSets = fullKey.split("::"); //splits key at the "new question" symbol
    }
    
    /**
     * Fills the answerSets array with the answers for the next question and returns the passage text.
     */
    public String nextQuestion()
    {
        currentQ++; //move to next question block (question 0 is skipped to avoid a compression error)
        String[] components = (questionSets[currentQ]).split("%%"); //split answers from passage...
        String[] answerSets = components[1].split("##"); //...and split the answer sets
        answers = new String[answerSets.length][3]; //create and fill the array with answers
        for(int i = 0; i < answerSets.length; i++)
            answers[i] = answerSets[i].split("&&");
        currentResults = new boolean[answerSets.length][3]; //recreate currentResults to adjust size and reset all values to false
        return components[0];
    }
    
    /**
     * Checks to see if this question is the last one.
     * Used by Canvas to determine the next dialog.
     */
    public boolean checkNext()
    {
        return (currentQ + 1 == questionSets.length);
    }
    
    /**
     * Checks if the selection is a phrase needing correction.
     */
    public boolean checkSelection(String selection)
    {
        for(int i = 0; i < answers.length; i++)
        {
            if(answers[i][0].equals(selection))
            {
                currentResults[i][0] = true;
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if the user's correction matches the answer key.
     */
    public boolean checkCorrection(String correction)
    {
        for(int i = 0; i < answers.length; i++)
        {
            if(answers[i][1].equals(correction))
            {
                currentResults[i][1] = true;
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if the reason chosen regarding selection is correct.
     */
    public boolean checkReason(int reason, String selection)
    {
        for(int i = 0; i < answers.length; i++)
        {
            if(answers[i][0].equals(selection)) //if answers[i] is the phrase in selection, check reasoning
            {
                if(answers[i][2].equals(Integer.toString(reason)))
                {
                    currentResults[i][2] = true;
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Returns the array containing the user's results
     */
    public boolean[][] getResultsList()
    {
        return currentResults;
    }
    
    /**
     * Returns the array of answers.
     */
    public String[][] getAnswers()
    {
        return answers;
    }
}