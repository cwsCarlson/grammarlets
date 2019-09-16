import java.awt.Dimension;
import javax.swing.*; //BorderFactory, Box, BoxLayout, JComboBox, JFormattedTextField, JPanel, JTextArea, TOP_ALIGNMENT
import javax.swing.text.DefaultFormatter;

/**
 * The panel containing the content for AnswerWindow objects.
 * 
 * @author Carlson Sharpless
 * @version 0.02 4/12/2018
 */
public class AnswerPanel extends JPanel
{
    JFormattedTextField textField;
    JComboBox ruleChooser;
    static String[] rules = {"Contradictory Language", "Homophone", "Incorrect Punctuation",
    "Incorrect Tense", "Missing Punctuation", "Poor Word Choice", "Possessive", 
    "Pronoun-Antecedent", "Run-On Sentence", "Sentence Fragment", "Subject-Verb Agreement",
    "Unnecessary Punctuation", "Other"};
    
    /**
     * Builds the AnswerPanel and components, placing initialText into the field.
     */
    public AnswerPanel(String initialText)
    {
       DefaultFormatter format = new DefaultFormatter(); //create field format
       format.setOverwriteMode(false); //ensure field text is not overwritten (w/o Backspace or Delete)
       textField = new JFormattedTextField(format); //create field
       textField.setValue(initialText); //add initial text to field
       
       ruleChooser = new JComboBox(); //create the rule chooser and add rules
       for (int i = 0; i < rules.length; i++)
            ruleChooser.addItem(rules[i]);
       ruleChooser.setSelectedIndex(0);
       
       JPanel unitGroup = new JPanel() //create panel for text field
       { public Dimension getPreferredSize() { return new Dimension(500, 20);} };
       unitGroup.setLayout(new BoxLayout(unitGroup, BoxLayout.PAGE_AXIS));
       unitGroup.setBorder(BorderFactory.createEmptyBorder(0,0,0,5));
       unitGroup.add(textField);
       
       JPanel chooserPanel = new JPanel(); //create panel for chooser
       chooserPanel.setLayout(new BoxLayout(chooserPanel, BoxLayout.PAGE_AXIS));
       chooserPanel.add(ruleChooser);
       chooserPanel.add(Box.createHorizontalStrut(100));
        
       setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS)); //set window layout
       setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
       add(unitGroup); //add text and chooser panels and align
       add(chooserPanel);
       unitGroup.setAlignmentY(TOP_ALIGNMENT);
       chooserPanel.setAlignmentY(TOP_ALIGNMENT);
    }
    
    /**
     * Returns the number of the selected rule.
     * Used by AnswerKey to check if the selected rule is correct.
     */
    public int getRuleSelected()
    {
        String selectedRule = (ruleChooser.getSelectedItem()).toString(); 
        int rulePos = 0;
        for(int i = 0; i < rules.length; i++)
        {
            if(rules[i] == selectedRule)
            {
                return rulePos + 1;
            }
            rulePos++;
        }
        return 0;
    }
    
    /**
     * Returns the user's answer in the text field.
     */
    public String getUserAnswer()
    {
        return textField.getText();
    }
    
    /**
     * Returns the list of reasons for the results dialog.
     * (The list in Canvas has shortened names.)
     */
    public static String[] getReasons()
    {
        return rules;
    }
}