import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.*;


/**
 * GUI programs that displays the Hangman game program. The program draws the stick-man step by step,
 * drawing a part each time the users guesses the wrong letter. The letters will letter that have and not have been
 * guessed will be displayed. The guess is trying to guess from a persona worldlist.
 */
public class Hangman extends JFrame {

    private Drawpanel mypanel = new Drawpanel();
    public final static double width = 650;
    public final static double height = 500;


    public class Drawpanel extends JPanel {

        public Random ran = new Random();
        public Man man;
        public Letters alphabet;
        public GuessedWord guess;
        public int livesleft = 8;
        public ArrayList<String> wordlist = CreateArr();
        int choice = ran.nextInt(wordlist.size());
        public char Letterselected;
        public char startgamechar;


        public String wordtoguess = wordlist.get(choice);
        public ArrayList<Character> GUESSES = new ArrayList<>();
        ArrayList<Character> RightGuesses = new ArrayList<>();

        boolean aletter = true;
        boolean yes;
        boolean no;
        boolean already;
        public boolean win;
        public boolean lose;
        public boolean ignore = false;
        public boolean presentation = true;


        int order = 0;

        public Drawpanel() {
            setBackground(Color.WHITE);
            setForeground(Color.BLACK);


            guess = new GuessedWord(wordtoguess, width, height);
            man = new Man(width, height);
            alphabet = new Letters(width, height);

            /**
             * KeyListener to get the letter selected by the user
             */
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e)
                {
                    char c = Character.toLowerCase(e.getKeyChar());
                    startgamechar = c;

                    aletter = false;

                    //check if it is a letter by compare it with the alphabet
                    for(Character lett : alphabet.alpahabet)
                    {
                        if(lett == c)
                        {
                            aletter = true;
                        }
                    }

                    //just if it is a letter
                    if(aletter && !ignore && !presentation) {
                        Letterselected = c;
                    if (GUESSES.contains(c)) { //check if it was previously selected
                        already = true;
                    } else {
                        already = false;
                        if (wordtoguess.contains(Character.toString(c))) { //if the word to guess contains the letter. Righr guess
                            RightGuesses.add(c); //add to the list of right guesses
                            guess.mapletters.put(c, true); //make it visible
                            yes = true; //right guess message
                            no = false;
                        } else {
                            String[] hang = {"HorizontalPole", "VerticalPole", "Head", "Body", "Leftarm", "Rightarm", "Leftleg", "Rightleg"};
                            man.mapparts.put(hang[order], true); //the index will increase every wrong letter guess. Will add in order the parts of the hangman
                            livesleft--;
                            if(livesleft == 0){lose = true;} //no lives, we lost
                            no = true;
                            yes = false;
                            order++; //next index increases

                        }
                        GUESSES.add(c); //add to guesses
                        alphabet.mapletters.put(c, false); //marked it selected on the screen

                        //check if all the letter from the world to guess are displayed. If that is so, the user won
                        boolean allgood = true;
                        for(char letter : guess.mapletters.keySet())
                        {
                            if(!guess.mapletters.get(letter)){allgood = false;} //if any still not on the screen, we have letter left so no win
                        }
                        if(allgood){win = true;} //just if all true, win,

                    }
                }

                }
            });

        }

        public ArrayList<String> CreateArr() {
            Scanner input;
            ArrayList<String> array = new ArrayList<>();

            // get the default position of pacman from the img dictionary
            File directory = new File(".");
            File[] arr = directory.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.contains("WordList") && name.endsWith("txt");
                }
            });


            try {
                input = new Scanner(new FileReader(arr[0]));

                StringUtils noaccent = new StringUtils();

                while (input.hasNext()) {
                    String word = input.next();

                    word = noaccent.removeAccents(word);

                    array.add(word);
                }

            } catch (FileNotFoundException ex) {
                System.err.println("No file with the worldliest found");
                System.exit(0);
            }


            return array;

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();


            if(presentation)
            {

                String text = "WELCOME TO THE HANGMAN GAME!!";
                String text2 = "Please enter your guess by pressing a letter in the KeyBoard." ;
                String text3 = "Make sure to type just a letter." ;
                String text4 = "Please enter 'n' to start the game" ;

                g2d.setColor(Color.BLACK);


                g2d.setFont(new Font("Monospaced", Font.BOLD, 23));
                g2d.drawString(text, (int) width / 2 -200, 50);

                g2d.setFont(new Font("Monospaced", Font.BOLD, 17));
                g2d.drawString(text2, (int) width / 2 - 310, 220);
                g2d.drawString(text3, (int) width / 2 - 150, 240);
                g2d.drawString(text4, 270, 400);


                if(startgamechar == 'n'){presentation = false;}

            }

            else {

                //if it is not a letter message
                if (!aletter) {
                    g2d.setFont(new Font("Monospaced", Font.BOLD, 15));
                    g2d.setColor(Color.RED);
                    already = false;
                    yes = false;
                    no = false;
                    g2d.drawString("NOT A LETTER!!", 20, 20);
                }

                //if it was already guessed
                if (already) {

                    aletter = true;
                    yes = false;
                    no = false;
                    g2d.setFont(new Font("Monospaced", Font.BOLD, 15));
                    g2d.setColor(Color.RED);
                    g2d.drawString("ALREADY GUESSED!!", 20, 20);
                }
                //Right guess message
                if (yes) {
                    g2d.setColor(Color.BLACK);
                    g2d.drawString("YOU GUESSED A LETTER!!", 20, 40);
                }
                //Wrong guess message
                if (no) {
                    g2d.setColor(Color.RED);
                    g2d.drawString("WRONG LETTER GUESSED!!", 20, 40);
                }

                //Win message
                if (win) {
                    String text = "You won with " + livesleft + " guesses left!";
                    g2d.setFont(new Font("Monospaced", Font.BOLD, 22));
                    g2d.setColor(Color.BLACK);
                    int stringW = g2d.getFontMetrics().stringWidth(text);
                    lose = false;
                    ignore = true; //ignore any other keyevent

                    g2d.drawString(text, (int) width / 2 - stringW / 2, (int) height - 130);

                }

                //Lose message
                if (lose) {
                    String text = "You lost, the world to guess was " + wordtoguess;
                    g2d.setFont(new Font("Monospaced", Font.BOLD, 17));
                    g2d.setColor(Color.BLACK);
                    int stringW = g2d.getFontMetrics().stringWidth(text);
                    win = false;
                    ignore = true; //ignore anyother keyevent


                    g2d.drawString(text, (int) width / 2 - stringW / 2, (int) height - 130);

                    requestFocusInWindow();

                }

                g2d.setColor(Color.BLACK);
                man.draw(g2d);
                alphabet.draw(g2d);
                guess.draw(g2d);
            }

            repaint();
        }
    }


    public Hangman(double screenw, double screenh) {

        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setBounds((int) screenw / 2 - (int) width / 2, (int) screenh / 2 - (int) height / 2, (int) width, (int) height); //centered on the creen
        super.setLayout(new BorderLayout());
        super.add(mypanel, BorderLayout.CENTER);

        //Jpanel that will be set in the South
        JPanel down = new JPanel();
        down.setLayout(new BorderLayout());

        //Creates a new game
        JButton newbutton = new JButton("New");
        newbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {Reset();}
        });

        //Creating the JLabel and JTextfield that will hold the last letter pressed
        JLabel lastLetter = new JLabel("Last letter pressed: ");
        JTextField last = new JTextField(4);
        last.setSize(15,15);

        //Timer to change the textfield text for the letter selected
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                last.setText("     " + Character.toString(mypanel.Letterselected));
            }
        });
        timer.start();

        last.setFont(last.getFont().deriveFont(25f));

        //creating a secondary panel that will be placed inside the south panel in the West holding
        //the label and the textfield
        JPanel insideleft = new JPanel();
        insideleft.setLayout(new FlowLayout());

        insideleft.add(lastLetter);
        insideleft.add(last);

        //adding the secondary panel to the South panel
        down.add(insideleft,BorderLayout.WEST);
        down.add(newbutton,BorderLayout.EAST);


        super.add(down,BorderLayout.SOUTH);
        super.setResizable(false);

    }

    /**
     * Resets all the parameters of the game. From changing the word to guess and clear all the lists to set all the booleans values to default
     */
    public void Reset()
    {
        Random ran = new Random();

        mypanel.guess.Createmap();
        mypanel.man.Createmap();

        mypanel.alphabet.CreateMap();
        int choice = ran.nextInt(mypanel.wordlist.size());
        mypanel.wordtoguess = mypanel.wordlist.get(choice);
        mypanel.guess = new GuessedWord(mypanel.wordtoguess, width, height);

        mypanel.GUESSES.clear();
        mypanel.RightGuesses.clear();
        mypanel.aletter = true;
        mypanel.yes = false;
        mypanel.no = false;
        mypanel.already = false;
        mypanel.win = false;
        mypanel.lose = false;
        mypanel.order = 0;
        mypanel.livesleft = 8;
        mypanel.ignore = false;
        mypanel.Letterselected = '\u0000';


       mypanel.requestFocusInWindow();



    }


    public static void main(String[] args) {


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        Hangman hangman = new Hangman(width, height);

        java.awt.EventQueue.invokeLater(() ->
        {
            hangman.setVisible(true);
            hangman.mypanel.requestFocusInWindow();


        });

    }

}








