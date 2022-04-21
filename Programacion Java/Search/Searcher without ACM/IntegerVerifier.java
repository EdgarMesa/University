
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

// An InputVerifier to ensure that text of a text component is an integer in
// the range [start, end].
public class IntegerVerifier extends InputVerifier
{
    private final Integer start, end;

    public IntegerVerifier(Integer start, Integer end)
    {
        if(start != null && end != null && start > end)
            throw new IllegalArgumentException("start must be <= end");
        
        this.start = start;
        this.end = end;
    }

    public IntegerVerifier(Integer start)
    {
        this(start, null);
    }

    public IntegerVerifier()
    {
        this(null, null);
    }
    
    @Override
    public boolean verify(JComponent input)
    {
        if(!(input instanceof JTextComponent))
            throw new IllegalArgumentException("input must be a JTextComponent");
        
        int value;
        try
        {
            value = Integer.valueOf(((JTextComponent) input).getText());
        }
        catch(NumberFormatException e)
        {
            return false;
        }

        if(start != null && value < start) return false;
        return end == null || value <= end;
    }

    @Override
    public boolean shouldYieldFocus(JComponent input)
    {
        if(verify(input)) return true;
        
        String message = "Input must be an integer";
        if (start != null) message += " at least " + start;
        if (end != null)   message += " and at most " + end;
        message += ".";
        JOptionPane.showMessageDialog(input, message, "Invalid input", JOptionPane.WARNING_MESSAGE);
        return false;
    }
}
