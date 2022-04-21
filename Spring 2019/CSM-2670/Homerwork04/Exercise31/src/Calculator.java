
import java.util.*;
import java.io.*;

/**Rational numbers should be stored in reduced form with any negative sign stored only on the numerator (i.e. the denominator must be positive).
        Your toString method should omit denominators of 1. You will need to include methods for add, subtract, multiply, and divide.
        Use your class to build a simple text-based calculator for Rational Numbers. Your calculator should accept commands to give help
        (i.e. list valid commands and give usage information), clear the current accumulator, set the accumulator to a new value,
        negate the accumulator, add, subtract, multiply or divide, the accumulator by a given rational number.
        The accumulator should start at 0 and be printed after each command.*/

public class Calculator {

    public static void main(String[] args) {

        PrintStream out = new PrintStream(System.out);
        PrintStream err = new PrintStream(System.err);
        Accumulator acc = new Accumulator();

        Scanner console = new Scanner(System.in);

        intro(out);
        Boolean yesno = IntroYesNo(console,out);
        if(yesno){instructions(out);}


        Boolean loop = true;

        while(loop) {



            String [] values;
            values = RightOperator(console,out,err, "Type your operation: ");

            if (values[0].equalsIgnoreCase("+") || values[0].equalsIgnoreCase("-")
                    || values[0].equalsIgnoreCase("/") || values[0].equalsIgnoreCase("*")) {
                Operators(values, acc);
            }

            if (values[0].equalsIgnoreCase("set")) {
                Set(values, acc);
            }
            if (values[0].equalsIgnoreCase("neg") || values[0].equalsIgnoreCase("clear")) {
                NegandClear(values, acc);
            }

            out.println("Total accumulator: " + acc.ToString());
        }
    }

    /** Welcome message*/
    static public void intro(PrintStream out){

        out.println("\nWELCOME TO THIS AMAZING TEXT-BASED CALCULATOR OF RATIONAL NUMBERS!\n\n" +
                "We strongly suggest to read the instructions of this calculator.\n" +
                "Otherwise, be ready to be riddle by a bunch of Exceptions and errors.\n\n" +
                "For the instructions type \"help\". Otherwise type anything and press enter.");
    }

    /** gets the answer of the user to if he/she want to see the instructions
     *
     * @param console = scanner to read the answer
     * @param out = PrinterStream
     * @return A Boolean value depending on the answer of the user
     */
    static public boolean IntroYesNo(Scanner console, PrintStream out){

        String yesono = console.nextLine();

        if(yesono.equalsIgnoreCase("help")){return true;}

        else
            {
            out.println("\nAre you completely sure that you do not want to take a look to the instructions?\n" +
                    "I will give you one more chance.\n" +
                    "For the instructions type \"help\". Otherwise type anything and press enter.");

            yesono = console.nextLine();

            if(yesono.equalsIgnoreCase("help")){return true;}
            else{return false;}
        }
    }

    /** Cheeks for a correct operation prompted by the user.
     * @param console = Scanner
     * @param out = PrintStream
     * @param promt = Sentence to ask
     * @return a an array of strings containing the correct operators, numerator and denominator
     */
    static public String[] RightOperator(Scanner console,PrintStream out,PrintStream err, String promt) {

        String[] array = new String[3];
        out.println(promt);
        out.print("Accumulator ");

        String answer = console.nextLine();

        Scanner input = new Scanner(answer);


        Boolean nocorrect = true;
        String operation = input.next();

        // while not correct operator, keep asking for a correct one
        while (nocorrect) {
            // cheeks for a correct operator and if there are more or less tokens than expected
            if ((!operation.equals("+") && !operation.equals("-") && !operation.equals("*") && !operation.equals("set") && !operation.equals("neg")
                    && !operation.equals("clear") && !operation.equals("/")) || !WrongSyntax(answer, 2, 0)) {
                err.println("Wrong syntax.");
                out.println("Try again. ");
                out.println(promt);
                out.print("Accumulator ");
                answer = console.nextLine();
                input = new Scanner(answer);
                operation = input.next();
            } else {
                nocorrect = false;
            }
        }

        // if is clear or neg and nothing else. Other way error
        if ((operation.equals("clear") || operation.equals("neg")) && WrongSyntax(answer, 1, 0))
        {
            array[0] = operation;
            return array;
        }

        // if is set and have two values. Other way error
        if(operation.equalsIgnoreCase("set") && WrongSyntax(answer, 2, 1))
        {
            String num = input.next();

            try {Double.valueOf(num);}

            catch (NumberFormatException ex)
            {
                err.println("Second value not a number. Wrong syntax\n" +
                        "Please check the instructions");
                System.exit(-1);
            }

            array[0] = operation;
            array[1] = num;

            return array;
        }



        if ((operation.equals("+") || operation.equals("-") || operation.equals("*") || operation.equals("/")) && WrongSyntax(answer, 2, 1))
        {
            // in case that we want a division we will have a slash (ex.Accumulator / 45)
            if(operation.equals("/"))
            {
                input = new Scanner(input.next());
                String num = input.next();

                // no dash, return the num and set the deno at 1
                try {
                    Integer.valueOf(num);
                } catch (NumberFormatException ex) {
                    err.println("The numerator is not a number. Wrong syntax\n" +
                            "Please check the instructions");
                    System.exit(-1);
                }

                array[0] = operation;
                array[1] = num;
                array[2] = "1";
                return array;

            }

            //cheeking for the numerator and denominator
            input = new Scanner(input.next());

            input.useDelimiter("/");
            String num = input.next();

            // if it has a dash, separate the num and the deno
            if(answer.contains("/")) {

                // check if they are numbers or not
                try {
                    Integer.valueOf(num);
                } catch (NumberFormatException ex) {
                    err.println("The numerator is not a number. Wrong syntax\n" +
                            "Please check the instructions");
                    System.exit(-1);
                }

                if (input.hasNext()) {
                    String deno = input.next();

                    try {
                        Integer.valueOf(deno);
                    } catch (NumberFormatException ex) {
                        err.println("The denominator is not a number. Wrong syntax\n" +
                                "Please check the instructions");
                        System.exit(-1);

                    }

                    array[0] = operation;
                    array[1] = num;
                    array[2] = deno;

                    return array;
                } else {
                    throw new IllegalArgumentException("Wrong Syntax.\nPlease check the instructions");
                }

            }
            // no dash, return the num and set the deno at 1
            try {
                Integer.valueOf(num);
            } catch (NumberFormatException ex) {
                err.println("The numerator is not a number. Wrong syntax\n" +
                        "Please check the instructions");
                System.exit(-1);
            }

            array[0] = operation;
            array[1] = num;
            array[2] = "1";
            return array;

        }else {throw new IllegalArgumentException("Wrong Syntax.\nPlease check the instructions");}

    }

    /** counts how many tokens the user prompted

     * @param sentence = the users operation
     * @return true iff the are exactly 2 tokens (one for the operation, the other for the rational number)
     */
    static public Boolean WrongSyntax(String sentence, int number,int limit){
        Scanner input = new Scanner(sentence);
        int count = 0;
        while(input.hasNext())
        {
            count++;
            input.next();
        }

    if (count > number || count == limit){return false;}

    return true;
    }

    /** sum, subtraction, multiplication or division
     * @param arr = array of String
     * @param acc = Accumulator
     * @return an accumulator after the operation
     */
    static public Accumulator Operators(String[] arr, Accumulator acc){

        RationalNumber rational = new RationalNumber(Integer.valueOf(arr[1]),Integer.valueOf(arr[2]));
        String operator = arr[0];

        if(operator.equals("+"))
        {
            acc.add(rational);
        }
        if(operator.equals("-"))
        {
            acc.sub(rational);
        }
        if(operator.equals("/"))
        {
            acc.div(rational);
        }
        if(operator.equals("*"))
        {
            acc.mult(rational);
        }

        return acc;
    }

    /** Set the acc to a desired value
     *
     * @param arr = array of Strings
     * @param acc = accumulator
     * @return the accumulator set to a desired value
     */
    static public Accumulator Set(String[] arr, Accumulator acc)
    {
        Double num = Double.valueOf(arr[1]);
        acc.set(num);
        return acc;
    }

    /** if operator is ned and clear
     * @param arr = array String
     * @param acc = accumulator
     * @return if "clear" set the acc = 0
     *         if "neg" negate the acc
     */
    static public Accumulator NegandClear(String[] arr, Accumulator acc)
    {
        String operator = arr[0];

        if(operator.equalsIgnoreCase("clear")){acc.clear();}
        if(operator.equalsIgnoreCase("neg")){acc.negate();}

        return acc;
    }


    /** displays the instructions*/
    static public void instructions(PrintStream out){
        out.println("__________________________________________________________________________");
        out.println("|                              INSTRUCTIONS                               |");

        out.println("|                                                                         |");
        out.println("|                                                                         |");

        out.println("|These are all the valid commands that you will be able to do.            |");

        out.println("|                                                                         |");
        out.println("|                                                                         |");

        out.print("|1) set = set the accumulator to a desired value.                         |\n" +
                "|2) clear = sets the accumulator to 0.                                    |\n" +
                "|3) neg = negates the current accumulator.                                |\n" +
                "|4) \"+\" = add a rational number to the accumulator.                       |\n" +
                "|5) \"-\" = subtract a rational number from the accumulator.                |\n" +
                "|6) \"*\" = multiplies the accumulator for a rational number.               |\n" +
                "|7) \"/\" = divides the accumulator for a rational number.                  |\n");

        out.println("|                                                                         |");
        out.println("|                                                                         |");

        out.print("|The accumulator will start at 0.                                         |\n" +
                "|the accumulator will be printed after each operation.                    |\n" +
                "|It is very important to follow the proper syntax                         |\n" +
                "|to not get any FormatExceptions.                                         |\n" +
                "|the syntax fot this calculator is:                                       |\n" +
                "|operation + space + numerator + / denominator.                           |\n" +
                "|Or operation + space + number for a non rational number                  |\n");

        out.println("|                                                                         |");
        out.println("|                                                                         |");

        out.print("|For example, if we would want to add one third to the accumulator        |\n" +
                "|will be translated as:                                                   |\n" +
                "|\"+ 1/3\"                                                                  |\n");
        out.println("|                                                                         |");
        out.println("|                                                                         |");

        out.println("|To negate or clear the accumulator                                       |\n" +
                "|just type the command of the operation. To set a certain value           |\n" +
                "|to the accumulator type set + a space + a number                         |\n" +
                "|For example, if you want to set it to 10. It wil be translated as:       |\n" +
                "|\"set 10\"                                                                 |");

        out.println("|                                                                         |");
        out.println("|                                                                         |");

        out.print("|Any other information after the denominator will be ignored              |\n" +
                "|an FormatException will be displayed                                     |\n" +
                "|if the format of the operation does not follow the syntax.               |\n");
        out.println("|                                                                         |");
        out.println("|                                                                         |");
        out.println("Enjoy!                                                                    |");
        out.println("__________________________________________________________________________");

    }

}
