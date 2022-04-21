package com.company;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        grades();
    }

    public static void grades() {
        /*questions and prompts for the average of the homework, exams and final exam*/
        Scanner console = new Scanner(System.in);
        System.out.print("What was your homework average? ");
        double homework = console.nextDouble();
        System.out.print("What was your exam average? ");
        double exam = console.nextDouble();
        System.out.print("What was your final exam grade? ");
        double finalexam = console.nextDouble();
        /* if any of the averages < or > 100 error*/
        if (homework < 0 || homework > 100 || exam < 0 || exam > 100 || finalexam < 0 || finalexam > 100) {
            throw new IllegalArgumentException("there averages introduced must be > 0 or < 100");
        }
        System.out.print("Are all these correct? " + "homework: " + homework
                + " exams: " + exam + " final exam: " + finalexam);
        /*question to make sure the data introduced is correct*/
        System.out.println();
        System.out.print("Type a yes or a no ");
        String answer = console.next();
        /* if the data no correct, restart the program*/
        if (answer.equalsIgnoreCase("no")) {
            throw new IllegalArgumentException("Make sure to put the right data");
        }

        /* To be able to get a C or higher you have to get a C or higher in all of them. Otherwise you will obtain the
        grade of the lowest
         */
        double avrg = (homework * 0.30) + (exam * 0.40) + (finalexam * 0.30);
        System.out.print("Your average is: "+avrg);
        System.out.println();

        if(homework <=69 || exam <= 69|| finalexam <=69) {
             if (homework < 55 || exam < 55 || finalexam < 55) {
                char letter = 'F';
                System.out.print("you will have a letter: " + letter);
                System.out.println(); }
                else if (homework >= 55 && homework <= 69) {
                    String letter = "D";
                    System.out.print("you will have a letter: " + letter);
                    System.out.println();
                }else if (exam >= 55 && exam <= 69) {
                    char letter = 'D';
                    System.out.print("you will have a letter: " + letter);
                    System.out.println();
                }else if (finalexam >= 55 && finalexam <= 69) {
                    char letter = 'D';
                    System.out.print("you will have a letter: " + letter);
                    System.out.println();
                }
            }
            else{
            /*letter for the average*/
                if (avrg <= 100 && avrg >= 90) {
                    char letter = 'A';
                    System.out.print("you will have a letter: " + letter);
                    System.out.println();
                }
                else if (avrg <= 89 && avrg >= 80) {
                    char letter = 'B';
                    System.out.print("you will have a letter: " + letter);
                    System.out.println();
                }
                else if (avrg <= 79 && avrg >= 50) {
                    char letter = 'C';
                    System.out.print("you will have a letter: " + letter);
                    System.out.println(); }


        }
    }
}