package com.company;

import java.util.ArrayList;
import java.io.File;
import java.io.*;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        PrintWriter err = new PrintWriter(System.err, true);
        ArrayList<Double> li = new ArrayList<>(2);
        double finalYear,finalPercent;
        /* display the intro*/
        intro();

        /* current directory path*/
        String path = System.getProperty("user.dir");
        /* get the file*/
        File fi = new File(path);


        final String start = "yob";
        final String ext = ".txt";

        /* get all the files that start with yob and end with .txt*/
        File[] listing = fi.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return (name.endsWith(ext) && name.startsWith(start));}
        });

        /* In casa the directory is empty, exit*/
        if(listing.length == 0){
            err.println("No files found such directory");
            System.exit(-1);
        }

        /* Scanner to prompt the data*/
        Scanner con = new Scanner(System.in);

        /* make sures the answer is either an F or an M*/
        char tarjetg = rightInputGen(con, "What gender?\ntype F for female or M for Male: ");

        System.out.print("What is your name: ");
        String tarjetn = con.next();
        System.out.println();
        System.out.println("Year        Total Percentage");


        /*values for the max and min if statements*/
        double max = 0;
        double min = 100;
        double maxyear = 0;
        double minyear = 0;

        /* loop to iterate through the files*/
        String year,name;
        for (int i = 0; i < listing.length; i++) {
            name = listing[i].getName();
            year = (listing[i].getName().substring(3,7));


            /* one scanner to read the whole file */
            Scanner input = new Scanner(new File(name));

            /* another scanner to sum the population and calculate de popularity*/

            Scanner input2 = new Scanner(new File(name));
            int num = information(input, tarjetg,tarjetn);
            /* converting the year string on an integer */

            double yeard = Integer.valueOf(year);
            li = popularity(input2,num,yeard);

            /* final data per year*/
            finalPercent = li.get(0);
            finalYear = li.get(1);

            System.out.printf("%-5.0f  %15.6f %%\n",finalYear,finalPercent);
            System.out.println();


            /*get the highest and lowest percentage*/

            if(li.get(0) > max){
                max = li.get(0);
                maxyear = li.get(1);

            }else if(li.get(0) < min){
                min = li.get(0);
                minyear = li.get(1);
            }

        }   /* if max year == 0 no matches. Prompt and error message*/
        if(max ==0){
            err.println("No matches found.\n" +
                    "\nMake sure the name is spelt correctly and the gender correspond\n" +
                    "with the name.");
            System.exit(-1);
        }
        System.out.printf("%-4.0f was the year with the highest percent = %-3.6f%%\n",maxyear,max);
        System.out.printf("%-4.0f was the year with the lowest percent = %-3.6f%%\n",minyear,min);


    }

    public static int information(Scanner input, char tarjetg, String tarjetn){
        String line;
        /* while having text compute*/
        while(input.hasNextLine()) {

            /* break the text in ones*/
            line = input.nextLine();
            Scanner data = new Scanner(line);

            /* split each line where commas are*/
            data.useDelimiter(",");
            String name = data.next();
            String gender = data.next();
            int number;

            /* if the name and gender match, return the number of babies born with that name*/
            if (gender.charAt(0) == tarjetg && name.equalsIgnoreCase(tarjetn)) {
                number = data.nextInt();
                return number;
            } }
    return 0;}



    /* make sure the answer is either an F or a M */
    public static char rightInputGen(Scanner con, String message){
        PrintWriter err = new PrintWriter(System.err, true);

        System.out.print(message);
        char gender = con.next().charAt(0);
        gender = Character.toUpperCase(gender);

        while(gender != 'F' && gender != 'M'){
            err.println("That does not stand for neither F from Female nor M for Male.Try again!");
            System.out.print(message);
            gender = con.next().charAt(0);
            gender = Character.toUpperCase(gender);}

            return gender;}




    public static ArrayList<Double> popularity(Scanner input, int tarjetNumber, double year) {
        PrintWriter err = new PrintWriter(System.err, true);
        /* array to store both doubles (year and popularity*/
        ArrayList<Double> lis = new ArrayList<>(2);

        String line;
        double sum = 0;
        double number;
        /* if the tarjet numer is 0 = no math. Terminate the program*/
        if(tarjetNumber == 0) {
            err.println("No match");}

        while (input.hasNextLine()) {
            line = input.nextLine();
            Scanner data = new Scanner(line);
            data.useDelimiter(",");
            data.next();
            data.next();
            /* calculate the sum of the total population */
            number = data.nextDouble();
            sum += number;
        }
        /* if total = 100, the percent of the portion = portion * 100 divided by the total*/
        Double percentaje = (tarjetNumber * 100)/sum;
        lis.add(percentaje);
        lis.add(year);
       return lis;}



    public static void intro(){
        System.out.println("Welcome!\nThis program reads data about the changing popularity of baby names over the years.\n" +
                "Prompt a name to see the popularity change over the time.\n" +
                "It calculates the percentage of babies that were born that year over the total babies born\n" +
                "I will also prompt a format table with all the year and their percentages\n" +
                "and the year with the highest and lowest popularity. Enjoy!\n\n");}


}
