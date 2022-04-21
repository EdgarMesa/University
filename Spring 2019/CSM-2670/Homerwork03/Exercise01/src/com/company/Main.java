package com.company;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        PrintWriter err = new PrintWriter(System.err,true);

        /* current directory path*/
        String currentDir = System.getProperty("user.dir");

        /* get the files of the current directory*/
        File fi = new File(currentDir);


        /* listing all the file and search for Fortune.txt*/
        File[] listing = fi.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.equals("Fortunes.txt");}
        });

        /* if no files called Fortune.txt, exit */
        if(listing.length == 0)
        {
            err.println("Any Fortune.txt file found in such directory");
            System.exit(-1);
        }

        /* getting the file´s path and reading it through scanner*/
        String path = listing[0].getName();
        Scanner input = new Scanner(new File(path));
        input.useDelimiter("%");

        /* if no command line given, normal fortune method*/
        if(args.length == 0)
        {
            fortune(input,path);
            return;
        }

        String debug = " ";
        int [] numList = new int[args.length];
        int index = 0;

        /* going through all the arguments searching for debug or and integer*/
        for (String s : args)
        {
            if (s.equalsIgnoreCase("debug"))
            {
                debug = s;
            }

            try
            {
                numList[index] = Integer.valueOf(s);
                index++;
            }
            catch (NumberFormatException ex){}

        }

        /* number + debug = fortuneDebugInt method*/
        if(numList[0] > 0 && debug.equalsIgnoreCase("debug"))

        {
            fortuneDebugInt(input,path,numList[0]);

        }
        /* neither a number not debug in the arguments*/
        else if(numList[0] <= 0 && !debug.equalsIgnoreCase("debug"))
        {
            fortune(input,path);

        }
        /* just debug debug = fortuneInt method*/
        else if(numList[0] > 0)
        {
            fortuneInt(input,path,numList[0]);
        }

        /* just a number = fortuneDebug method*/
        else if(debug.equalsIgnoreCase("debug"))
        {
            fortuneDebug(input, path);
        }

    }

    public static void fortune(Scanner input, String file)throws FileNotFoundException{

        /* creating random object for the random fortune and new Scanner to go through the fortunes file again
        * and pick the correct one*/
        Scanner select = new Scanner(new File(file));
        select.useDelimiter("%");
        Random ran = new Random();
        int random;
        String fortune;

        /* skip fortune´s introduction*/
        input.next();

        /* while loop to count how many fortunes there are*/
        int count = 0;
        while(input.hasNext())
        {
            input.next();
            count++; /*total number of fortunes*/
        }

        /* skip the intro of the second scanner*/
        select.next();

        /*random fortune between 1 and the max number of fortunes*/
        random = ran.nextInt(count)+1;

        /* for loop to skip all the fortunes until the desired one*/
        for(int i = 1; i < random; i++)
        {
            select.next();
        }
        fortune = select.next(); /* desired fortune*/

         int max = max(fortune);

        /* lines stored in arrays*/
        String[] arr = array(fortune);

        /* Print a panther with the fortune randomly picked*/
        panthersay(arr, max);
    }
    public static void fortuneDebug(Scanner input, String file)throws FileNotFoundException{

        /* creating random object for the random fortune and new Scanner to go through the fortunes file again
         * and pick the correct one*/
        Scanner select = new Scanner(new File(file));
        select.useDelimiter("%");
        Random ran = new Random();
        int random;
        String fortune;

        /* skip fortune´s introduction*/
        input.next();

        /* while loop to count how many fortunes there are*/
        int count = 0;
        while(input.hasNext())
        {
            input.next();
            count++; /*total number of fortunes*/
        }

        /* skip the intro of the second scanner*/
        select.next();

        /*random fortune between 1 and the max number of fortunes*/
        random = ran.nextInt(count)+1;

        /* for loop to skip all the fortunes until the desired one*/
        for(int i = 1; i < random; i++)
        {
            select.next();
        }
        fortune = select.next(); /* desired fortune*/

        int max = max(fortune);
         /* Print a panther with the fortune picked  */

        /* lines stored in arrays*/
        String [] arr = array(fortune);

        panthersay(arr,max);

        /* print fortune number randomly picked and total # of fortunes in the file*/
        System.out.printf("The fortune randomly picked was #%-5d \n",random);
        System.out.printf("The total number of fortunes is  %-5d \n",count);

    }

    public static void fortuneInt(Scanner input, String file,int number)throws FileNotFoundException{
        PrintWriter err = new PrintWriter(System.err,true);

        /* new Scanner to go through the fortunes file again and pick the correct one*/
        Scanner select = new Scanner(new File(file));
        select.useDelimiter("%");
        String fortune;

        /* skip fortune´s introduction*/
        input.next();

        /* while loop to count how many fortunes there are*/
        int count = 0;
        while(input.hasNext())
        {
            input.next();
            count++; /*total number of fortunes*/
        }

        /* if the desired fortune is out of range, error message and exit*/
        if(number <= 0 || number > count )
        {
            err.println("Number selected out of range");
            System.exit(-1);
        }

        /* skip the intro of the second scanner*/
        select.next();


        /* for loop to skip all the fortunes until the desired one*/
        for(int i = 1; i < number; i++)
        {
            select.next();
        }
        fortune = select.next(); /* desired fortune*/

        int max = max(fortune);

        /* lines stored in arrays*/
        String [] arr = array(fortune);

        /* Print a panther with the fortune picked*/
        panthersay(arr,max);
    }


        public static void fortuneDebugInt(Scanner input, String file,int number)throws FileNotFoundException{
            PrintWriter err = new PrintWriter(System.err,true);

            /* new Scanner to go through the fortunes file again and pick the correct one*/
            Scanner select = new Scanner(new File(file));
            select.useDelimiter("%");
            String fortune;

            /* skip fortune´s introduction*/
            input.next();

            /* while loop to count how many fortunes there are*/
            int count = 0;
            while(input.hasNext())
            {
                input.next();
                count++; /*total number of fortunes*/
            }

            /* if the desired fortune is out of range, error message and exit*/
            if(number <= 0 || number > count )
            {
                err.println("Number selected out of range");
                System.exit(-1);
            }

            /* skip the intro of the second scanner*/
            select.next();


            /* for loop to skip all the fortunes until the desired one*/
            for(int i = 1; i < number; i++)
            {
                select.next();
            }
            fortune = select.next(); /* desired fortune*/

            int max = max(fortune);

            /* lines stored in arrays*/
            String [] arr = array(fortune);

            /* Print a panther with the fortune picked*/
            panthersay(arr,max);

            /* print fortune´s number picked picked and total # of fortunes in the file*/
            System.out.printf("The fortune randomly picked was #%-5d \n",number);
            System.out.printf("The total number of fortunes is  %-5d \n",count);

        }





    public static int max(String fortune){

        String[] arrcompleted = array(fortune);

        /*get the greatest length*/
        int max = 0;
        for(int i = 0; i < arrcompleted.length; i++)
        {
            if(arrcompleted[i].length() > max)
            {
                max = arrcompleted[i].length();
            }
        }
        max+=2;

        return max;
    }
        /*returns the desired fortune in an array*/
    public static String[] array(String fortune){
        Scanner line = new Scanner(fortune);
        int contador = 0;

        /*counts the lines of the fortune selected*/
        while(line.hasNextLine())
        {
            line.nextLine();
            contador++;
        }
        /*store every line to check for the max length*/
        String[] arr = new String[contador];

        /*store all the lines of the fortune in a an array*/
        Scanner line2 = new Scanner(fortune);

        /*store the single values (lines) in the array*/
        int index = 0;
        while(line2.hasNextLine())
        {
            arr[index] = line2.nextLine();
            index++;
        }
        return arr;
    }
    public static void panthersay(String[] array, int max){

        /* UPPER LINE */
        for(int i = 0; i <= max;i++ )
        {
            System.out.print("-");
        }
        System.out.println();

        /* TEXT */
        String dash = "|";
        for(int q = 0; q < array.length;q++ )
        {
            System.out.print(dash + array[q]);

            /* to get all the dashes at the same position (end)
            we subtract the greatest length to the length of each line */
            int deff = max - array[q].length();
            System.out.printf("%" + deff +"s",dash);

            System.out.println();

        }

        /* LOWER LINE */
        for(int t = 0; t <= max;t++ )
        {
            System.out.print("-");
        }
        System.out.println();
        System.out.println("                         \\");
        System.out.println("                          \\");
        System.out.println("                           \\");
        System.out.println("                            \\");
        System.out.println("                             \\");
        System.out.println("                              \\");
        System.out.println("                               \\");
        System.out.println("                             (O_/ __ \\_O) ");
        System.out.println("                             / (o)__(o) \\");
        System.out.println("                            (__.--\\/--.__)");
        System.out.println("                           ====(__/\\__)====");
        System.out.println("                                 `__´");
        System.out.println("                                 _||_");
        System.out.println("                               /'....'\\");
        System.out.println("                              | :    : |");
        System.out.println("                              ||:    :||");
        System.out.println("                              ||:    :||");
        System.out.println("                              ||:    :||");
        System.out.println("                              ||:    :||");
        System.out.println("                              ||:    :||");
        System.out.println("                              ||:    :||");
        System.out.println("                              ||:    :||");
        System.out.println("                              || `..´ ||");
        System.out.println("                             (  | || |  )");
        System.out.println("                              \\ | || | /");
        System.out.println("                                | || |");
        System.out.println("                                | || |");
        System.out.println("                                | || |");
        System.out.println("                                | || |");
        System.out.println("                                | || |");
        System.out.println("                            __,-´ || ´-,__");
        System.out.println("                           (___,--'`--,___)\n");


















    }
}
