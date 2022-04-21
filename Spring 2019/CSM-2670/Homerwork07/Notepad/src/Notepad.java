import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.swing.filechooser.FileSystemView;

/**
 * GUI programs that represents a simple Notepad clone. It supports 4 main buttom where you can clear the text buffer, upload
 * the text from another textFile, save the actual text in another file text and exit the program. In addition, the fond size
 * the background and and the foreground can be changed
 */

public class Notepad extends JFrame {
    Map<String,JButton> MapButtoms = new HashMap<>();


    public Notepad(double screenw, double screenh)
    {
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setBounds((int)screenw/2-500/2,(int)screenh/2-500/2,500,500); //centered on the creen
        super.setLayout(new BorderLayout());

        //different panels representing the top,middle and bottom
        JPanel upper = new JPanel();
        upper.setLayout(new FlowLayout());
        JPanel down = new JPanel();
        down.setLayout(new BorderLayout());

        //Components for the change of background
        JPanel Backgrounds = new JPanel();
        Backgrounds.setLayout(new FlowLayout());

        JPanel SIZES = new JPanel();
        SIZES.setLayout(new FlowLayout());

        String[] ListNameButtoms = {"New", "Save","Load","Quit","Foreground","Background"};

        //Loop to create all the bottoms. New, save, open, and quit will be place on the top.
        //Foreground and Background on the bottom
        for(int i = 0;i < 6;i++)
        {
            if(i < 4)
            {
                JButton button = new JButton(ListNameButtoms[i]);
                MapButtoms.put(ListNameButtoms[i],button);
                upper.add(button);
            }

            else
                {
                    JButton button = new JButton(ListNameButtoms[i]);
                    MapButtoms.put(ListNameButtoms[i], button);
                    Backgrounds.add(button);
                }
        }

        super.add(upper,BorderLayout.NORTH); //Down
        down.add(Backgrounds,BorderLayout.NORTH);

        //Components for the size
        JLabel size = new JLabel("Size");
        JTextField fontsize = new JTextField(3);
        SIZES.add(size);
        SIZES.add(fontsize);

        down.add(SIZES,BorderLayout.SOUTH); //South
        super.add(down,BorderLayout.SOUTH);


        //text area and ScrollPane
        JTextArea texta = new JTextArea();

        //First Thread to change the size of the text as soon as a number its introduce in the Textfield.
        SizeThread sizing = new SizeThread(fontsize,texta);
        sizing.start();
        JScrollPane scrollp = new JScrollPane(texta);


        super.add(scrollp,BorderLayout.CENTER); //Center


        //Change the Foreground Color
        ForeGround(texta);

        //Change the Background Color
        BackGround(texta);

        //Clear the textarea
        MapButtoms.get("New").addActionListener((e) ->
        {
            texta.setText(null);
            texta.setBackground(Color.white);
            texta.setForeground(Color.black);
            fontsize.setText(null);
        });

        //Saves the file in a text file in the current directory
        SaveText(texta);


        //Uploads the text from another text file
        Upload(texta);

        //Exits the program
        MapButtoms.get("Quit").addActionListener((e) ->
        {
            System.exit(0);
        });



    }

    /**
     * Uploads the text from another text file by displaying a file chooser
     * @param text = JTextarea
     */
    public void Upload(JTextArea text)
    {


        MapButtoms.get("Load").addActionListener((e) ->
        {
            //create a file that uses the given FileSystemView
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            int returnValue = jfc.showOpenDialog(null);

            //if it was approved and is a text file, saved it
            if (returnValue == JFileChooser.APPROVE_OPTION && jfc.getSelectedFile().getAbsolutePath().endsWith("txt")) {
                File selected = jfc.getSelectedFile();
                try
                {
                    Scanner input = new Scanner(new FileReader(selected));
                    String fulltext = "";

                    //For each line of the file, copy it in the text area
                    while(input.hasNextLine())
                    {
                        String line = input.nextLine();
                        fulltext+= line+"\n";
                    }

                    text.setText(fulltext);

                }catch (FileNotFoundException ex){System.err.println(ex.getMessage());}

            }
        });

    }

    /**
     * saves the text in a text File inside the currently directory
     * @param text
     */
    public void SaveText(JTextArea text)
    {
        //getting the right bottom from the list
        MapButtoms.get("Save").addActionListener((e) ->
        {
            try
            {
                PrintStream out = new PrintStream(new File("Save.txt"));
                Scanner input = new Scanner(text.getText());

                //For each line, copy it in the textfile
                while(input.hasNextLine())
                {
                    out.println(input.nextLine());
                }

            }catch (FileNotFoundException ex){System.err.println(ex.getMessage());}
        });


    }

    /**
     * Changes the color of the foregroundby displaying a Color chooser.
     * @param texta = JTextarea
     */
    public void ForeGround(JTextArea texta)
    {

        //getting the right bottom from the list
        MapButtoms.get("Foreground").addActionListener((e) ->
        {
            Color initialForeground = texta.getForeground();
            Color foreground = JColorChooser.showDialog(null, "Foreground Color", initialForeground);
            if (foreground != null) {texta.setForeground(foreground);}
        });
    }

    /**
     * Changes the color of the background by displaying a Color chooser.
     * @param texta = JTextarea
     */
    public void BackGround(JTextArea texta)
    {

        //getting the right bottom from the list
        MapButtoms.get("Background").addActionListener((e) ->
        {
            Color initialBackground = texta.getBackground();
            Color background = JColorChooser.showDialog(null, "Background Color", initialBackground);
            if (background != null) {texta.setBackground(background);}
        });
    }



    public static void main(String[] args) {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        java.awt.EventQueue.invokeLater(() ->
    {
        new Notepad(width,height).setVisible(true);

    });


    }
}
