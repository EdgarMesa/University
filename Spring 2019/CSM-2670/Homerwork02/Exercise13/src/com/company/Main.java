package com.company;
import java.util.*;


public class Main {

    public static void main(String[] args) {

        grade();


    }

    /* formula to calculate the scores needed in the final */
    public static double formula(double average, char lett) {
        if (lett == 'A') {
            double coeff = 90.001;
            double total = (coeff - average) / 0.3;
            return total;
        } else if (lett == 'B') {
            double coeff = 80.001;
            double total = (coeff - average) / 0.3;
            return total;
        } else if (lett == 'C') {
            double coeff = 70.001;
            double total = (coeff - average) / 0.3;
            return total;
        }
        return -1;
    }

    public static void grade() {
        /*questions and prompts for the average of the homework and exams*/
        Scanner console = new Scanner(System.in);
        System.out.print("What was your homework average? ");
        double homework = console.nextDouble();
        System.out.print("What was your exam average? ");
        double exam = console.nextDouble();
        if (homework < 0 || homework > 100 || exam < 0 || exam > 100) {
            throw new IllegalArgumentException("there averages introduced must be > 0 or < 100");
        }

        System.out.print("Are all these correct? " + "homework: " + homework
                + " exams: " + exam);
        /*question to make sure the data introduced is correct*/
        System.out.println();
        System.out.print("Type a yes or a no ");
        String answer = console.next();

        /* if the data no correct, error*/
        if (answer.equalsIgnoreCase("no")) {
            throw new IllegalArgumentException("Make sure to put the right data");
        } else if (answer.equalsIgnoreCase("yes")) {

                /* To be able to get a C or higher you have to get a C or higher in all of them. Otherwise you will obtain the
                grade of the lowest
                 */
            double avrg = (homework * 0.30) + (exam * 0.40);
            System.out.printf("Your average is: %-2.2f", avrg);
            System.out.println();
            /* if homework or exam lower than a C set the grade already for either a D or F*/
            if (homework <= 69 || exam <= 69) {
                if (homework < 55 || exam < 55) {
                    char letter = 'F';
                    System.out.print("you will have a letter: " + letter);
                    System.out.println();
                } else if (homework >= 55 && homework <= 69) {
                    String letter = "D";
                    System.out.print("you will have a letter: " + letter);
                    System.out.println();
                } else if (exam >= 55 && exam <= 69) {
                    char letter = 'D';
                    System.out.print("you will have a letter: " + letter);
                    System.out.println();
                }
                /*table with the scores needed for each letter*/
                System.out.println();
                System.out.print("To be able to make your final exam average count you " +
                        "have to get at least a C in the homework or exam average!");
            } else {
                String s = "Score needed for each grade:";
                System.out.printf("%-30s\n\n", s);
                /* if the score needed is higher than 100, not able to get it*/
                if (formula(avrg, 'A') > 100) {
                    String t = "A: Not able to get an A";
                    System.out.printf("%-30s\n", t);
                }else{
                        double total = formula(avrg, 'A');
                        System.out.printf("A: %2.2f\n", total);
                }
                if (formula(avrg, 'B') > 100) {
                    String t = "B: Not able to get an B";
                    System.out.printf("%-30s\n", t);
                } else {

                    double total = formula(avrg, 'B');
                    System.out.printf("B: %2.2f\n", total); }

                if (formula(avrg, 'C') < 100) {
                    double total = formula(avrg, 'C');
                    System.out.printf("C: %2.2f\n", total); }

                /* no matter what if the final score is below 70 either a D or and F*/
                String t = "D: If you get a D you will automatically get a D in the course";
                System.out.printf("%40s\n", t);
                String q = "F: If you get a F you will automatically get a F in the course";
                System.out.printf("%30s\n", q);
            }
        }
    }
}

