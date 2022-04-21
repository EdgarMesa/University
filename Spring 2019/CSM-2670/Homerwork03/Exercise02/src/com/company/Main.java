package com.company;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Random;


public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        PrintWriter err = new PrintWriter(System.err, true);

        // Scanner and random objects
        Scanner console = new Scanner(System.in);
        Random ran = new Random();

        int scorecomputer = 0;
        int scoreplayer = 0;
        String line = "";

        File score = new File("score.txt");

        /* if does not exist set score to 0*/
        if(!score.exists()){
            scorecomputer = 0;
            scoreplayer = 0;
        }
            else{

                Scanner input = new Scanner(new File("score.txt"));

            /* if file empty set score to 0*/
            if(!input.hasNextLine())
                {
                    scorecomputer = 0;
                    scoreplayer = 0;
                }

                    /*go through the file*/
                    else
                    {
                      while(input.hasNextLine())
                      {
                          line = input.nextLine();

                          /* strop when get to the line where the score is*/
                          if(line.contains("The final score is: Computer:"))
                          {
                              /* skip the String and get the total score stored for both players*/
                              Scanner input2 = new Scanner(line);
                              while(!input2.hasNextInt()) input2.next();
                              scorecomputer = input2.nextInt();
                              while(!input2.hasNextInt()) input2.next();
                              scoreplayer = input2.nextInt();

                          }

                      }
                    }
            }

        rules();
        // Where the computer´s and player´s choices will be stored
        String playerplay,stop;
        String computerplay;


        System.out.println("Please enter a move\n" +
                "Rock = R\nPaper = P\nScissors = S\nLizard = L\nSpock = SPO");

        stop = "yes";

        String c = "Computer:";
        String y = "You:";
        // keep playing until the player answers no
        while(!stop.equalsIgnoreCase("NO")){

            // Final answer from the player
            playerplay  = finalAnswer(console);
            // Final answer from the player
            computerplay = random(ran);

            // Different combinations
            if(playerplay.equals(computerplay)){
                System.out.println("It´s a tie!");}
            if(playerplay.equals("S")  && computerplay.equals("P")){
                System.out.println("Scissor cuts paper. You win!!");scoreplayer++;}
            if(playerplay.equals("S")  && computerplay.equals("L")){
                System.out.println("Scissor decapitates lizard. You win!!");scoreplayer++;}
            if(playerplay.equals("S")  && computerplay.equals("SPO")){
                System.out.println("Spock smashes scissor. You lose!!");scorecomputer++;}
            if(playerplay.equals("S")  && computerplay.equals("R")){
                System.out.println("Rock crushes scissor. You lose!!");scorecomputer++;}
            if(playerplay.equals("P")  && computerplay.equals("SPO")){
                System.out.println("Paper disproves spock. You win!!");scoreplayer++;}
            if(playerplay.equals("P")  && computerplay.equals("R")){
                System.out.println("Paper covers rock. You win!!");scoreplayer++;}
            if(playerplay.equals("P")  && computerplay.equals("S")){
                System.out.println("Scissor cuts paper. You lose!!");scorecomputer++;}
            if(playerplay.equals("P")  && computerplay.equals("L")){
                System.out.println("Lizard eats paper. You lose!!");scorecomputer++;}
            if(playerplay.equals("R")  && computerplay.equals("L")){
                System.out.println("Rock crushes scissor. You win!!");scoreplayer++;}
            if(playerplay.equals("R")  && computerplay.equals("S")){
                System.out.println("Rock crushes lizard. You win!!");scoreplayer++;}
            if(playerplay.equals("R")  && computerplay.equals("SPO")){
                System.out.println("Spock vaporizes rock. You lose!!");scorecomputer++;}
            if(playerplay.equals("R")  && computerplay.equals("P")){
                System.out.println("Paper covers rock. You lose!!");scorecomputer++;}
            if(playerplay.equals("SPO")  && computerplay.equals("S")){
                System.out.println("Spock smashes scissor. You win!!");scoreplayer++;}
            if(playerplay.equals("SPO")  && computerplay.equals("R")){
                System.out.println("Spock vaporizes rock. You win!!");scoreplayer++;}
            if(playerplay.equals("SPO")  && computerplay.equals("P")){
                System.out.println("Paper disproves spock. You lose!!");scorecomputer++;}
            if(playerplay.equals("SPO")  && computerplay.equals("L")){
                System.out.println("Lizard poisons spock. You lose!!");scorecomputer++;}
            if(playerplay.equals("L")  && computerplay.equals("SPO")){
                System.out.println("Lizard poisons spock. You win!!");scoreplayer++;}
            if(playerplay.equals("L")  && computerplay.equals("P")){
                System.out.println("Lizard eats paper. You win!!");scoreplayer++;}
            if(playerplay.equals("L")  && computerplay.equals("R")){
                System.out.println("Rock crushes lizard. You lose!!");scorecomputer++;}
            if(playerplay.equals("L")  && computerplay.equals("S")){
                System.out.println("Scissor decapitates lizard. You lose!!");scorecomputer++;}

            System.out.printf("The current score is: %-9s %-3d %-4s %-3d\n",c,scorecomputer,y,scoreplayer);
            System.out.println();

            stop = keepPlaying(console);}

        System.out.printf("The final score is: %-9s %-3d %-4s %-3d",c,scorecomputer,y,scoreplayer);
        PrintStream out = new PrintStream(new File("score.txt"));
        out.printf("The final score is: %-9s %-3d %-4s %-3d",c,scorecomputer,y,scoreplayer);

    }


    // Method to ask the payer if wants to play another game or not
    public static String keepPlaying(Scanner console){
        System.out.print("Do you want to play another game?\n" +
                "Yes = yes\nNo = no\nEnter your answer: ");
        String answer = console.next();
        System.out.println();

        // If the answer is not a yes or a no, keep asking
        while(!answer.equalsIgnoreCase("NO") && !answer.equalsIgnoreCase("YES")){
            System.out.println("The answer introduced was not either a yes or a no; try again");
            System.out.print("Do you want to play another game?\n" +
                    "Yes = yes\nNo = no\\nEnter your answer: ");
            answer = console.next();}
        return answer;}

    public static void rules(){

        // Format table explaining all the possibilities for each pair of options
        System.out.print("\nHey, let's play Rock-Paper-Scissors-Lizard-Spock!\n" +
                "here you have the winning rules, Enjoy!\n");
        System.out.println();
        String s = "Scissors";
        String p = "Paper";
        String r = "Rock";
        String spo = "Spock";
        String l = "Lizard";
        String W = "WIN";
        String L = "LOSE";
        String D = "Draw";
        System.out.println("                                    Opponent");
        System.out.print("                  _________________________________________________");
        System.out.println();
        System.out.printf("        _________|%-9s %-9s %-9s %-9s %-9s|\n",l,spo,r,p,s);
        System.out.printf("       |%-9s %-9s %-9s %-9s %-9s %-9s|\n",s,W,L,L,W,D);
        System.out.printf("       |%-9s %-9s %-9s %-9s %-9s %-9s|\n",p,L,W,W,D,L);
        System.out.printf("Player |%-9s %-9s %-9s %-9s %-9s %-9s|\n",r,W,L,D,L,W);
        System.out.printf("       |%-9s %-9s %-9s %-9s %-9s %-9s|\n",spo,L,D,W,L,W);
        System.out.printf("       |%-9s %-9s %-9s %-9s %-9s %-9s|\n",l,D,W,L,W,L);
        System.out.println("       ------------------------------------------------------------");
    }

    // Method to make sure that the data introduces is a String
    public static String finalAnswer(Scanner console){
        PrintWriter out = new PrintWriter(System.out, true);
        PrintWriter err = new PrintWriter(System.err, true);
        System.out.print("Enter your move: ");
        String answer = console.next();
        answer = answer.toUpperCase();

        //while the answer does not correspond to one of the moves, keep asking again
        while(!answer.equals("R") && !answer.equals("P") && !answer.equals("S") && !answer.equals("L") && !answer.equals("SPO")){
            err.println("The information entered does not correspond to any of the moves; try again.");
            System.out.print("Enter your move: ");
            answer = console.next();
            answer = answer.toUpperCase();}
    return answer;}

    public static String random(Random ran){
        int computernumer = ran.nextInt(5)+1;
        String computerplay = "";

        // translating the computer´s choice into the different moves
        if(computernumer == 1){
            computerplay = "R";}
        if(computernumer == 2){
            computerplay = "P";}
        if(computernumer == 3){
            computerplay = "S";}
        if(computernumer == 4){
            computerplay = "L";}
        if(computernumer == 5){
            computerplay = "SPO";}

        return computerplay;}
}
