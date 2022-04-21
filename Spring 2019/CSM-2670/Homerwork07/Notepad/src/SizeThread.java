import javax.swing.*;
import java.awt.*;

public class SizeThread extends Thread {
    private JTextField t;
    private String s;
    private Font defaultfont;
    private int newfontsize;
    private JTextArea area;

    public SizeThread(JTextField text, JTextArea area){this.t = text;this.area = area;}


    @Override
    public void run() {

        while (true) {

            defaultfont = t.getFont();
            s = t.getText();

            try
            {
                newfontsize = Integer.valueOf(s);
                area.setFont(area.getFont().deriveFont((float)newfontsize));

            }
            catch (NumberFormatException ex)
            {
                area.setFont(defaultfont);
            }


            try {
                Thread.sleep(33);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }

    }
}
