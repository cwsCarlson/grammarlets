import javax.swing.*; //JOptionPane, JPanel

/**
 * A dialog. It will either welcome the user or provide them with information about their answers.
 * 
 * @author Carlson Sharpless
 * @version 0.02 4/12/2018
 */
public class DialogWindow extends JPanel
{
    AnswerKey key;
    String[] reasons;
    
    /**
     * Builds the correct DialogWindow and imports key variables.
     */
    public DialogWindow(String type, AnswerKey ak)
    {
       key = ak; //import key and list of reasoning
       reasons = AnswerPanel.getReasons();
       
       if(type.equals("welcome")) //if program was opened, write the welcome message
          JOptionPane.showMessageDialog(null, "Welcome to this tutor!\n\nClick and drag to cover text that is incorrect.\nAlways start from the left.\nCorrect the sentence and select the issue with the original version.\n\nMake sure to cover the whole word.\nIf you want to correct a punctuation mark, cover the words on both sides.", "Welcome", JOptionPane.INFORMATION_MESSAGE);
       
       if(type.equals("done")) //if last answer was submitted, write feedback and exit message
          JOptionPane.showMessageDialog(null, getResults(true), "Results", JOptionPane.INFORMATION_MESSAGE);
          
       if(type.equals("results")) //if other answer was submitted, write feedback
          JOptionPane.showMessageDialog(null, getResults(false), "Results", JOptionPane.INFORMATION_MESSAGE);   
    }
    
    /**
     * Builds feedback for the last question based on user inputs.
     * This informs the user of the correct answers for the problem, if they missed any.
     * If last is true, an exit message is added to the end.
     */
    public String getResults(boolean last)
    {
        String results = ""; //create a blank string to add the results to.
        String[][] answers = key.getAnswers(); //get the answer strings and "correct" values
        boolean[][] correct = key.getResultsList();
        for(int i = 0; i < answers.length; i++)
        {
            if(correct[i][0] == false || correct[i][1] == false) //if a problem was ignored or incorrectly fixed, give the correct answer.
            {
                results += "The phrase \"" + answers[i][0] + "\" should have been replaced with \"" + answers[i][1] + "\".\n";
                if(correct[i][2] == false) //if the reasoning was wrong, note that, too.
                    results += "This fixes a(n) " + reasons[Integer.parseInt(answers[i][2]) - 1] + " error.\n";
            }
            else if(correct[i][2] == false) //if only the reasoning was wrong, give the correct reasoning.
            {
                results += "The phrase \"" + answers[i][1] + "\" fixes a(n) " + reasons[Integer.parseInt(answers[i][2]) - 1] + " error.\n";
            }
        } 
        
        if(results.equals("")) //if no errors found, use this message.
        {
            if(last == false)
               results = "Everything is correct.\nGet ready for the next question!";
            else
               results = "Everything is correct.";
        }
        
        if(last) //if the last question, add this to the end.
        {
            results += "\n\nThat's all for now. Have a nice day!";
        }
        return results;
    }
}