/*
    This BuildTable class will take a file as a parameter and will clean it of unimportant data like comments.
    this will allow us to use those clean lines to easily parse them (send it to the parse object) and also will calculate
    the offset table that will be used to add the final offset into the instructions

 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

// The start of an LC-3 Symbol Table Generator
public class BuildTable
{


    // For when things go wrong
    private static class AssemblyException extends Exception{private AssemblyException(String m){super(m);}}
    private static String file;
    private static String instructions = "";
    private static Set<String> labels ;
    private Map<String, Integer> table;
    public static String Symboltablename;
    public static  LinkedList<Integer> addresses = new LinkedList<>();



    // Exit codes
    private static final int BAD_ARGS=1, BAD_FILE=2,
            BAD_ORIG = 3, BAD_LABEL = 4, SAVE_ERROR = 5;

    public static void main(String[] args)
    {

    }

    public BuildTable(String file)

    {
        this.file = file;

        // Read in asm file
        final List<String> lines;
        try
        {
            lines = Files.readAllLines(Paths.get(file));
        } catch (IOException ex)
        {
            System.err.printf("Unable to read %s!\n", file);
            System.exit(BAD_FILE);
            return;
        }

        // Find ORIG
        final int orig;
        try
        {
            orig = findOrig(lines);
        } catch (AssemblyException ex)
        {
            System.err.println(ex.getMessage());
            System.exit(BAD_ORIG);
            return;
        }

        // Build Table
        try
        {
            table = buildTable(orig, lines);
        } catch (AssemblyException ex)
        {
            System.err.println(ex.getMessage());
            System.exit(BAD_LABEL);
            return;
        }

        // Save table (right now it just prints the table)
        saveTable(table, file);


    }

//    public Integer getvalue(String key){return table.get(key);}

    // Prints simple usage information
    private static void printUsage()
    {
        System.out.println("BuildTable asmFile");
        System.out.println("Saves the table for the given asm file");
    }

    public Set<String> getLabels(){return labels;}

    public String getInstructions(){return instructions;}
    // Saves the given table to a file named x.sym where x is the basename of asmFilename
    private static void saveTable(Map<String, Integer> table, String asmFilename)
    {

        int DashIndex = asmFilename.lastIndexOf("\\"); //does not matter that the path given does not have a dash. Lastindex will return -1. So -1 +1 = 0.


        if(DashIndex == asmFilename.length()-1){DashIndex = asmFilename.substring(0,DashIndex).lastIndexOf("\\");} //if the dash is the last character, get the second last and ignore the last one

        int DotIndex = asmFilename.indexOf(".");

        //maxlength of the label for padding
        int maxlenght = "Symbol Table".length();
        for(String key : table.keySet())
        {
            if(key.length() > maxlenght){maxlenght = key.length();}
        }

        //create the knew filename based on the input filename
        String Filename = asmFilename.substring(DashIndex+1,DotIndex);
        BufferedWriter TableFile ;
        try
        {

            Symboltablename = Filename;
            TableFile = new BufferedWriter(new FileWriter(Filename+".sym"));

            //writes the headers of the file
            String intro = String.format("%s%s\n",fixedLengthString("Symbol Name",maxlenght),"Page Address");
            TableFile.write(intro);

            String dashes = String.format("%s%s\n\n",fixedLengthString("-----------",maxlenght),"------------");

            TableFile.write(dashes);

            //for each label writes its address
            for(String key : table.keySet())
            {

                String line = String.format("%s0X%x\n",fixedLengthString(key.toUpperCase(),maxlenght),table.get(key));
                TableFile.write(line);

            }

            //saves al the labels for future references
            labels = table.keySet();
            TableFile.close();

        }catch (IOException ex)
        {
            System.err.println(ex.getMessage());
            System.exit(BAD_FILE);
        }

    }

    //helper function to create the table padded based on largest key
    public static String fixedLengthString(String string, int length)
    {
        int offset = length-string.length();
        int padding = string.length() + offset;
        int sum = padding+offset;


        return String.format("%1$s%2$" + sum+"s" , string," ");
    }

    // Finds the orig of the given asm program
    private static int findOrig(List<String> lines) throws AssemblyException
    {
        // Scan each line until .ORIG is found
        for(String rawLine : lines)
        {
            // Does the line start with .ORIG
            final String cleanedLine = cleanLine(rawLine).toUpperCase();
            if(cleanedLine.startsWith(".ORIG"))
            {
                // Try to convert the next token to a location
                try
                {
                    // Get the rest of the line
                    String location = cleanedLine.substring(5).trim();

                    // If the value is in Hex add the leading 0 so we can decode it
                    if(location.startsWith("X")) location = "0" + location;

                    Integer ori = Integer.decode(location);
                    if(ori < 0 || ori > 65534){System.err.println("ORIG. must be greater or equal to X3000 and lower than xFFFF");System.exit(BAD_ORIG);}
                    // ToDo: Check that location is valid i.e. in LC-3 range


                    return Integer.decode(location);
                }
                catch (NumberFormatException ex)
                {
                    throw new AssemblyException(String.format("Bad orig %s!\n", cleanedLine.substring(5)));
                }
            }
        }

        throw new AssemblyException("No orig found!");
    }

    // Builds Table from the given asm file and starting location
    private static Map<String, Integer> buildTable(int orig, List<String> lines) throws AssemblyException
    {
        // Start at the orig with an empty table
        final Map<String, Integer> table = new HashMap<>(lines.size());
        int LC = orig;

        // For each line
        for(String rawLine : lines)
        {
            // clean and tokenize the line
            final String cleanedLine = cleanLine(rawLine);
            final Scanner lineScanner = new Scanner(cleanedLine);

            // If the line is empty then go to the next line
            if(!lineScanner.hasNext()) continue;


            // Otherwise get the token and check if it is a label
            final String token = lineScanner.next();
            if(isLabel(token))
            {
                // Check if the label has already be used.
                if(table.containsKey(token))
                {
                    throw new AssemblyException(String.format("Label %s used at %04x and %04x!",
                            token, table.get(token), LC));
                }


                // Add label to the table
                table.put(token, LC);
            }

            instructions = instructions + String.format("%s\n",cleanedLine);
            addresses.add(LC);


            // Advance the LC
            LC += findOffset(cleanedLine);
            if(LC > 65534){System.err.println("Range out of Bounds. Make sure the PC not greater than xFFFE");System.exit(BAD_FILE);}

        }

        return table;
    }

    // Finds the amount that the LC should be offset for the given line of assembly
    private static int findOffset(String line)
    {

        // Get the first token of the line
        final Scanner lineScanner = new Scanner(line);
        if(!lineScanner.hasNext()) return 0;
        String token = lineScanner.next().toUpperCase();


        //if it has a label in front, skip and get the opcode.
        if(isLabel(token))
        {
            if(!lineScanner.hasNext()) return 0;
            token = lineScanner.next().toUpperCase();
        }

        switch (token)
        {
            case ".ORIG":
            case ".END": return 0;
            case ".BLKW":
                // TODO: check for bad sizes and missing token

                if(!lineScanner.hasNext()) {System.err.println("No argument specify for "+token);System.exit(BAD_ARGS);}
                String [] arr = isHexorDecimal(lineScanner.next()); //this already check for hex and decimal and if it is negative
                int decimal;

                if(arr[0].equals("16")){decimal = Integer.decode("0X"+arr[1]);} //if decimal convert to integer

                else{decimal = Integer.parseInt(arr[1]);}
                if(decimal == 0){System.err.println("You are asking for a .BLNW of size 0");}

                if(decimal < 0 || decimal > 65534){System.err.println("size must be greater than 0 "+token);System.exit(BAD_ARGS);} //check for the size

                return decimal;
            case ".STRINGZ":


                if(!lineScanner.hasNext()) {System.err.println("No argument specify for "+token);System.exit(BAD_ARGS);}
                String string = lineScanner.nextLine().trim();

                if(string.length() == 2){System.err.println("Empty string");}
                if(string.indexOf("\"") != 0 || string.substring(1).indexOf("\"") == string.length()-1) //check if it a string or not
                {
                    System.err.println("Not a string. Argument of "+token);System.exit(BAD_ARGS);
                }

                // TODO: check for missing token and that it is really a string
                // Next token should have form "..." but the quotes do not count for the size
                // and we need to have space for the null. Thus we subtract 1 from the length.
                return string.length() - 1;
            default: return 1; // Other ops have size 1 (blank lines have already been skipped
        }
    }


    //cleans the number and return the base iff is an hex value or a decimal
    private static String[] isHexorDecimal(String num)
    {
        //String array to save arr[0] = the base the number input is. arr[1] clean number
        String [] arr = new String[2];

        if(num.equals("0"))
        {
            arr[0] = Integer.toString(10);
            arr[1] = num;
            return arr;
        }

        //if negatives, get rid of the sign
        if(num.substring(0,1).equals("-")){FormatError(num);}



        //check if it is an Hex number
        if(Character.toString(num.charAt(0)).matches("x|X|[0X]|[0x]"))
        {
            //if starts with just an X or x, get rid of the X and check if the rest numbers are correct
            if(Character.toString(num.charAt(0)).equalsIgnoreCase("x")  && !num.substring(1).matches(".*[g-zG-Z]+.*")) {
                arr[0] = Integer.toString(16);
                arr[1] = num.substring(1);
                return arr;
            }

            //if starts with just an 0X or 0x, get rid of the 0X and check if the rest numbers are correct
            else if(num.substring(0,2).equalsIgnoreCase("0x") )
            {

                arr[0] = Integer.toString(16);
                arr[1] = num.substring(2);
                return arr;
            }

            //if starts with anything else, throws error
            else{FormatError(num);}

        }


        //check if the decimal number has any other thing that is not a digit
        else if(!num.matches("[0-9]+"))
        {
            FormatError(num);
        }
        arr[0] = Integer.toString(10);
        arr[1] = num;
        return arr;

    }


    //throws a format error
    private static void FormatError(String number)
    {System.err.println("No Correct format specified for "+number+ "\n" +
            "Hex numbers must start with x,X,0X or 0x and can contain [0-9] [a-f] or [A-F]\n" +
            "and decimals must contain [0-9]");System.exit(BAD_ARGS);}


    private static String cleanLine(String line)
    {
        // Remove any comments and leading or trailing whitespace.
        final int index = line.indexOf(';');
        if(index == -1) return line.trim();
        return line.substring(0, index).trim();
    }

    // Array of all of our OPs
    private static final String[] ops = {"ADD", "AND",
            "BR", "JMP", "JSR", "JSRR", "LD", "LDR", "LDI",
            "LEA", "NOT", "RET", "RTI", "ST", "STR", "STI",
            "TRAP", "SUB", "MUL"};

    private static boolean isLabel(String token)
    {
        // Labels cannot be blank
        if(token == null || token.isEmpty()) return false;

        // Labels must start with letter or underscore and
        // contain only letters, digits, and underscores.
        token = token.toUpperCase();
        if(!token.matches("^[_A-Z]\\w*$")) return false;

        // Labels can not be OPs
        if(Arrays.asList(ops).contains(token)) return false;

        // Check for branches with CCs
        return !token.matches("^BRN?Z?P?$");
    }
}