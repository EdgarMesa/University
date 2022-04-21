
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class ButtonFrame extends JFrame
{

    private final List<JButton> myButtons = new ArrayList<>(5);
    
    public ButtonFrame()
    {
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setSize(800, 800);
        super.setLayout(new FlowLayout());
        for(int i = 0; i < 5; i++)
        {
            JButton temp = new JButton("Press me");
            super.add(temp);
            myButtons.add(temp);
            temp.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    System.out.println("You pressed a button!");
                    myButtons.get(2).setText("Hi");
                    ((JButton) e.getSource()).setText("Bye");
                }
            });
        }
        
    }

    public static void main(String[] args)
    {
        java.awt.EventQueue.invokeLater(() ->
        {
            new ButtonFrame().setVisible(true);
        });
    }
    
}
