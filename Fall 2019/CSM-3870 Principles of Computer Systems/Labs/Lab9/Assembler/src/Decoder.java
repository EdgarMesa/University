/*
This Decoder object will return a string of 1s and 0s as the instruction decoded and ready to
convert into bytes.
 */


import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Decoder {

    // Exit codes
    private static final int BAD_ARGS=1, BAD_FILE=2,
            BAD_ORIG = 3, BAD_LABEL = 4, SAVE_ERROR = 5, BAD_INSTRUCTION = 6;

    private String instruction;
    private String instructionDecoded;
    private Set<String> labels;
    private BuildTable table;
    private LinkedList<Integer> addresses;
    private int index ;
    public String finalinstruction;
    public String operand;





    public static void main(String[] args)
    {

    }

    Decoder(String instructions, Set<String> labels, BuildTable table, int index)
    {
        this.instruction = instructions;
        this.labels = labels;
        this.table = table;
        this.addresses = table.addresses;
        this.index = index;
        decoder(instruction);

    }

    private void decoder(String line)
    {
        //split in words
        Scanner token = new Scanner(line);
        //comas or spaces as parameters
        token.useDelimiter("(\\p{javaWhitespace}|,)+");

        //create a link list because each instruction has a fixed amount of arguments need (check if missing one very quick)
        ArrayList<String> tokens = new ArrayList<>();
        while(token.hasNext()){tokens.add(token.next().trim());}
        String label ="";

        String t = tokens.get(0);

        //separates the label from the instruction
        if(labels.contains(t)){label = t;tokens.remove(t);}


        //check that the instruction is a valid one
        String instruction = tokens.get(0);
        operand = tokens.get(0);
        if(!notLabel(instruction)){System.err.println("Not a valid instruction "+instruction);System.exit(BAD_INSTRUCTION);}
        tokens.remove(instruction);

        instruction = instruction.toUpperCase();


        //check for the right amount of arguments
        ArgumentError(instruction,tokens);


        //decomposes the instruction and convert it into bites
        Decomposition(instruction,tokens);

        int len = instructionDecoded.length();
        int i = 0;
        int j= 16;

        //divide he long strings in 16bits
        while(len != 0)
        {

            int decimal = Integer.parseInt(instructionDecoded.substring(i,j),2);
            //substring for the .BLWK and .STRINGZ

            String hexStr = SignedExtender(Integer.toString(decimal,16),false,'0',4);

            //sign extend the hex values to make sure we write 4 bytes at the time
            finalinstruction = finalinstruction + hexStr;
            len -= 16;
            i +=16;
            j +=16;

        }
        finalinstruction = finalinstruction.substring(4);


    }

    //decomposes the instruction and convert it into bites

    private void Decomposition(String instruction,ArrayList<String> tokens)
    {
        String opcode = "";
        //convert the orig to a 16bit binary
        if(instruction.equals(".ORIG"))
        {
            String number = tokens.get(0);
            String binary = BaseConverter(number,2);
            instructionDecoded  = SignedExtender(binary,false,'0',16);
        }
        //those operands have in common that take DR, SR! and SR2 as arguments
        else if((instruction.equals("ADD") || instruction.equals("AND") || instruction.equals("SUB") ||
                instruction.equals("MUL")) && Arrays.asList(registers).contains(tokens.get(2).toUpperCase()))
        {
            if (!Arrays.asList(registers).contains(tokens.get(0)) || !Arrays.asList(registers).contains(tokens.get(1))) {
                ArgumentErrorMessage();
            }
            String DR = RegistertoBinary(tokens.get(0).substring(1),tokens);
            String SR1 =  RegistertoBinary(tokens.get(1).substring(1),tokens);
            String SR2 =  RegistertoBinary(tokens.get(2).substring(1),tokens);


            String twobites = "00";
            if(instruction.equals("ADD") || instruction.equals("SUB")){opcode = "0001";}
            if(instruction.equals("AND")){opcode = "0101";}
            if(instruction.equals("MUL")){opcode = "1101";}
            if(instruction.equals("SUB")){twobites = "10";}


            instructionDecoded = String.format("%s%s%s0%s%s",opcode,DR,SR1,twobites,SR2);

        }

        //in common that take as arguments DR, SR1 and imm5
        else if(instruction.equals("ADD") || instruction.equals("AND"))
        {

            String address;

            if (!Arrays.asList(registers).contains(tokens.get(0)) || !Arrays.asList(registers).contains(tokens.get(1))) {
                ArgumentErrorMessage();
            }

            //get the registers
            String DR = RegistertoBinary(tokens.get(0).substring(1),tokens);
            String SR1 =  RegistertoBinary(tokens.get(1).substring(1),tokens);

            //imm5
            String imm5 = tokens.get(2);
            int limit = Integer.parseInt(BaseConverter(imm5,10));
            String[] data = NumberCleaner(imm5);
            if(data[0].equals("16")){address = Hexadecimal(SignedExtender(data[1],false,'0',2),5);}
            else{address =  Decimal(data[1],5);}

            OffsetOutofBounds(limit,5);

            if(instruction.equals("ADD")){opcode = "0001";}
            if(instruction.equals("AND")){opcode = "0101";}

            instructionDecoded = String.format("%s%s%s1%s",opcode,DR,SR1,address);

        }

        //both take BaseR as argument
        else if(instruction.equals("JMP") || instruction.equals("JSRR"))
        {
            if (!Arrays.asList(registers).contains(tokens.get(0)))
            {
                ArgumentErrorMessage();
            }

            //get the registers
            String Base = RegistertoBinary(tokens.get(0).substring(1),tokens);


            if(instruction.equals("JMP")){opcode = "1100";}
            if(instruction.equals("JSRR")){opcode = "0100";}

            instructionDecoded = String.format("%s000%s000000",opcode,Base);

        }

        //Flags and just a 9PCoffset as arguments
        else if(instruction.matches("^BRN?Z?P?$"))
        {

            //get the flags
            String flag = Flags(instruction);

            String token = tokens.get(0);
            String address = "";
            //if it is in the labels, calculate the offset
            if (labels.contains(token)) {

                //calculates the offset from the Offset function and converts it into a 9 bits binary
                address = Decimal(Offset(token,9),9);
            }


            //calculate the PCoffset9
            else
                {
                    address = DecodeDecimalHexOffsets(token,9);
                }

            instructionDecoded = String.format("0000%s%s",flag,address);

        }

        //JSR uses just an 11PCoffset
        else if(instruction.equals("JSR"))
        {
            String address = "";
            String token = tokens.get(0);
            if (labels.contains(token)) {

                //calculates the offset from the Offset function and converts it into a 9 bits binary
                address = Decimal(Offset(token,11),11);
            }
           else {address =  DecodeDecimalHexOffsets(token,11);}
           instructionDecoded = String.format("01001%s",address);

        }

        //in common that take as arguments DR and 9PCoffset
        else if(instruction.equals("LD") || instruction.equals("LDI") || instruction.equals("LEA") || instruction.equals("ST") || instruction.equals("STI"))
        {
            String address;

            //check if it a registers valid argument
            if (!Arrays.asList(registers).contains(tokens.get(0)))
            {
                ArgumentErrorMessage();
            }
            String token = tokens.get(1);

            if (labels.contains(token)) {

                //calculates the offset from the Offset function and converts it into a 9 bits binary
                address = Decimal(Offset(token,9),9);
            }
            else
                {
                String[] data = NumberCleaner(token);
                if(data[0].equalsIgnoreCase("16")){address = DecodeDecimalHexOffsets(token,9);}

                else {address = Decimal(token,9);}
            }

            String DR = RegistertoBinary(tokens.get(0).substring(1),tokens);

            if(instruction.equals("LD")){opcode = "0010";}
            if(instruction.equals("LDI")){opcode = "1010";}
            if(instruction.equals("LEA")){opcode = "1110";}
            if(instruction.equals("ST")){opcode = "0011";}
            if(instruction.equals("STI")){opcode = "1011";}

            instructionDecoded = String.format("%s%s%s",opcode,DR,address);

        }

        //they both take SR, Base register and a 6PCoffset as arguments
        else if(instruction.equals("LDR") || instruction.equals("STR"))
        {
            String address = "";

            //check if it a registers valid argument
            if (!Arrays.asList(registers).contains(tokens.get(0)) || !Arrays.asList(registers).contains(tokens.get(1)) )
            {
                ArgumentErrorMessage();
            }
            String token = tokens.get(2);
            int limit = Integer.parseInt(BaseConverter(token,10));
            String[] data = NumberCleaner(token);
            if(data[0].equals("16")){address = Hexadecimal(SignedExtender(data[1],false,'0',2),6);}
            else{address = Decimal(data[1],6);}


            OffsetOutofBounds(limit,6);


            String SR1 = RegistertoBinary(tokens.get(0).substring(1),tokens);
            String Base = RegistertoBinary(tokens.get(1).substring(1),tokens);

            if(instruction.equals("LDR")){opcode = "0110";}
            if(instruction.equals("STR")){opcode = "0111";}
            instructionDecoded = String.format("%s%s%s%s",opcode,SR1,Base,address);
        }

        //takes DR and SR1 as arguments
        else if(instruction.equals("NOT"))
        {
            //check if it a registers valid argument
            if (!Arrays.asList(registers).contains(tokens.get(0)) || !Arrays.asList(registers).contains(tokens.get(1)) )
            {
                ArgumentErrorMessage();
            }

            String DR = RegistertoBinary(tokens.get(0).substring(1),tokens);
            String SR1 = RegistertoBinary(tokens.get(1).substring(1),tokens);
            instructionDecoded = String.format("1001%s%s111111",DR,SR1);

        }
        //No arguments but they just differ on three bits
        else if(instruction.equals("RET") || instruction.equals("RTI"))
        {
            String threeones = "111";
            if(instruction.equals("RET")){opcode = "1100";}
            if(instruction.equals("RTI")){opcode = "1000";threeones = "000";}

            instructionDecoded = String.format("%s000%s000000",opcode,threeones);
        }
        //Trap just take the trap vector name or the hex representation of the cevtor
        else if(instruction.equals("TRAP"))
        {
            String TrapVector = tokens.get(0).toUpperCase();
            String vectordecoded = "";

            //if the assembly name is used
            if(Arrays.asList(trapVectors).contains(TrapVector))
            {
                //decode the vector to its binary
                vectordecoded = DecodeAssemblerName(TrapVector);
            }

            //if it is an hex value, convert to to binary
            else if(NumberCleaner(TrapVector)[0].equals("16"))
            {
                vectordecoded = DecodeTrapVector(TrapVector);
            }
            else{ArgumentErrorMessage();}

            instructionDecoded = String.format("11110000%s",vectordecoded);

        }

        //fill instruction takes a 16 bit instruction and puts it in memory
        else if(instruction.equals(".FILL"))
        {
            String number = tokens.get(0).toUpperCase();

            number = Fill(number);

            instructionDecoded = String.format("%s",number);


        }

        //for each character we will fill a location in memory
        else if(instruction.equals(".STRINGZ"))
         {
            StringBuilder sentence = new StringBuilder();

            //reconstruct the string again
            for(String word : tokens)
            {
                sentence.append(word+" ");
            }

            //deletes the " "
            sentence.deleteCharAt(0);
            int len = sentence.length()-1;
            sentence.deleteCharAt(len-1);

             //insert null at the end


             //per each character, get the binary and sign extend it
            for(int i = 0; i < sentence.length()-1; i++)
            {
                int ascii = sentence.toString().charAt(i);

                String extended = SignedExtender(BaseConverter(Integer.toString(ascii),2),false,'0',16);

                instructionDecoded = instructionDecoded+ extended.trim();
            }

             //get rid of the first null character and add one at the end
            instructionDecoded = instructionDecoded.substring(4)+"0000000000000000";


         }

        //we send n lines of 16 bits of 0s
        else if(instruction.equals(".BLKW"))
        {
            String number = tokens.get(0);

            //clear the number. data[0] = base. data[1] clean number
            String[] data = NumberCleaner(number);
            int decimal;
            //get the decimal
            if(data[0].equals("16")){decimal = Integer.parseInt(BaseConverter(number,10));}

            else{decimal = Integer.parseInt(data[1]);}

            //creating n memory spaces
            for(int i = 0; i < decimal; i++)
            {
                instructionDecoded = instructionDecoded+"0000000000000000";
            }

            //delete first null character
            instructionDecoded = instructionDecoded.substring(4);

        }

        else if(instruction.equals(".END")){instructionDecoded = "0000000000000000";}

        else{System.err.println("Missing .END opcode");}

    }

    //function that makes sure that is an hex value, converts it to binary and signs extended to 16bits
    private String Fill(String number)
    {
        String[] data = NumberCleaner(number);

        if(data[0].equalsIgnoreCase("16"))
        {

            int limit = Integer.parseInt(BaseConverter(number,10));
            //range of 16 bits twos complement
            if(limit > 65535){System.err.println("Out of bound, .FILL instruction must be 16bits long");System.exit(BAD_ARGS);}

            //cleans the number without Xs
            number = SignedExtender(BaseConverter(number,2),false,'0',16);
        }
        else if(data[0].equals("10"))
        {
            number = Decimal(number,16);
        }
        else{ArgumentErrorMessage();}
        return number;
    }

    //if they give is the assembler name, we return its
    private String DecodeAssemblerName(String vector)
    {
        switch (vector)
        {
            case"GETC":
                return "00100000";
            case"OUT":
                return "00100001";
            case"PUTS":
                return "00100010";
            case"IN":
                return "00100011";
            case"PUTSP":
                return "00100100";
                default:
                return "00100101";
        }

    }
    private String DecodeTrapVector(String vector)
    {
        if(vector.equalsIgnoreCase("X25")||vector.equalsIgnoreCase("0X25")){return "00100101";}
        else if(vector.equalsIgnoreCase("X24")||vector.equalsIgnoreCase("0X24")){return "00100100";}
        else if(vector.equalsIgnoreCase("X23")||vector.equalsIgnoreCase("0X23")){return "00100011";}
        else if(vector.equalsIgnoreCase("X22")||vector.equalsIgnoreCase("022")){return "00100010";}
        else if(vector.equalsIgnoreCase("X21")||vector.equalsIgnoreCase("0X21")){return "00100001";}
        else if(vector.equalsIgnoreCase("X20")||vector.equalsIgnoreCase("0X20")){return "00100000";}
        else{ArgumentErrorMessage();return "";
        }

    }


    //if the offset given is a decimal number or a hex
    private String DecodeDecimalHexOffsets(String number,int PCofsset)
    {

        String offset = number;
        String address;

        //calculate the range for the twos complement
        String limit = BaseConverter(offset, 10);

        OffsetOutofBounds(Integer.parseInt(limit),PCofsset);

        String[] data = NumberCleaner(offset);

        //if decimal number convert to  binary with the desired sign extension
        if (data[0].equals("10")) {address = Decimal(data[1], PCofsset);}

        //if hex number convert to binary with the desired sign extension
        else {address = Hexadecimal(data[1], PCofsset);}


        return address;
    }

    //takes the address of the label calculates the offset depending of the current PC
    private String Offset(String label,int offsetPC)
    {
        try
        {

            Scanner reader = new Scanner(new File(table.Symboltablename+".sym"));
            String hex = "";

            while(reader.hasNext())
            {
                String word = reader.next();

                //scan until match with the label. Then extract the hex value
                if(word.trim().equals(label.toUpperCase()))
                {
                     hex = reader.next().substring(2);
                }
            }

            //gets the address of the label from the
            int addressLabel = Integer.parseInt(hex,16);

            //gets the address of the current LC
            int PC = addresses.get(index);


            //subtract the address of the label from the PC+1 to get the real offset
            int offset = addressLabel-(PC+1);

            //make sure it is a valid offset
            OffsetOutofBounds(offset,offsetPC);
            return Integer.toString(offset);


        }catch (FileNotFoundException ex){System.err.println("File" + table.Symboltablename +"not found");System.exit(BAD_FILE);}
        return "";

    }
    //check for the different offsets boundaries
    private void OffsetOutofBounds(int offset,int offsetPC)
    {
        if(offsetPC == 9)
        {
            if(offset < -256 || offset > 255){System.err.println("Range must be between -256 and 255 for the offset of instruction: " + instruction);System.exit(BAD_LABEL);}
        }

        if(offsetPC == 11)
        {
            if(offset < -1024 || offset > 1023){System.err.println("Range must be between -1024 and 1023 for the offset of instruction: " + instruction);System.exit(BAD_LABEL);}
        }

        if(offsetPC == 6)
        {
            if(offset < -32 || offset > 31){System.err.println("Range must be between -32 and 31 for the offset of instruction: " + instruction);System.exit(BAD_LABEL);}
        }

        if(offsetPC == 5)
        {
            if(offset < -16 || offset > 15){System.err.println("Range must be between -16 and 15 for the offset of instruction: " + instruction);System.exit(BAD_LABEL);}
        }

    }



    //will get the Flags of the BR operand and turn them into binary
    private String Flags(String token)
    {
        if(token.length() == 2 || token.length() == 5){return "111";}
        StringBuilder bi = new StringBuilder();
        String nzp = token.substring(2);

        if(nzp.contains("P")){bi.insert(0,'1');}
        else {bi.insert(0,'0');}
        if(nzp.contains("Z")){bi.insert(0,'1');}
        else {bi.insert(0,'0');}
        if(nzp.contains("N")){bi.insert(0,'1');}
        else {bi.insert(0,'0');}
        
        return bi.toString();
    }

    private static String Decimal(String n,int offset)
    {

        String result;


        //if the number is negative
        if(n.charAt(0) == '-')
        {
            //eliminate the negative sign
            n = n.substring(1);

            //take the twos complement
            String binary = TwosComplement(BaseConverter(n,2));

            //binary is equal to the twos complement of the decimal sign extended with 1 because it is negative
            result = SignedExtender(binary,false,'1',offset);

        }

        //if it is positive
        else
        {

            //binary is equal to the decimal base 2 sign extended with 0 because it is a positive number
            result = BaseConverter(n,2);

            //sign extension with 0 to get the right amount bits
            // because in this case we are not going to have a 1 in front that will automatically gives us the right amount of hex values
             result = SignedExtender(result,false,'0',offset);

        }

        return result;

    }

    private static String Hexadecimal(String n,int offset)
    {

        String result;


        //if the number is positive
        if(Character.toString(n.charAt(0)).matches("0|1|2|3|4|5|6|7"))
        {
            //binary value equal to the hex number to base 2 and sign extended with 0
            result = SignedExtender(BaseConverter("X"+n,2),false,'0',offset);


        }
        //if the number is negative
        else
        {

            //binary number equal to the hex number to the base 2
            //binary value equal to the hex number to base 2 and sign extended with 1
            result = SignedExtender(BaseConverter("X"+n,2),false,'1',offset);


        }

        return result;

    }

    private static String Not(String n)
    {
        StringBuilder number = new StringBuilder(n);

        //we change every one for every 0 and biseversa
        for(int i = 0;i < number.length(); i++)
        {

            if(number.charAt(i) == '1'){number.setCharAt(i,'0');}
            else{number.setCharAt(i,'1');}
        }
        return number.toString();
    }

    private static String TwosComplement(String n)
    {
        n = Not(n);
        StringBuilder number = new StringBuilder(n);
        int last = number.length()-1;
        //if last digit is a 0 just turn it to 1
        if(number.charAt(last) == '0'){number.setCharAt(last,'1');}

        //otherwise set all the consecutive 1 to 0 until reach a 0
        else
        {
            while (number.charAt(last) =='1')
            {
                number.setCharAt(last,'0');
                last--;
            }

            //set that first 0 to 1
            number.setCharAt(last,'1');

        }
        return number.toString();
    }




    private String RegistertoBinary(String register,ArrayList<String> tokens)
    {
        String R = BaseConverter(register,2);
        R = SignedExtender(R,false,'0',3);

        return R;
    }

    private static String BaseConverter(String num,int DestinationBase)
    {

        String [] a;
        a = NumberCleaner(num);


        BigInteger big = new BigInteger(a[1],Integer.parseInt(a[0]));
        //changes the base of a number
        String converted = big.toString(DestinationBase);

        return converted;
    }





    private static String[] NumberCleaner(String num)
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
        if(num.substring(0,1).equals("-")){num = num.substring(1);}

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
            else{FormatError();}

        }


        //checks if it is a binary number and get rid of the b check if the rest of the numbers are 1s and 0s
        else if(num.substring(0,1).equalsIgnoreCase("b") && num.substring(1).matches("[01]+"))
        {
            arr[0] = Integer.toString(2);
            arr[1] = num.substring(1);
            return arr;
        }

        //check if the decimal number has any other thing that is not a digit
        else if(!num.matches("[0-9]+"))
        {
            FormatError();
        }
        arr[0] = Integer.toString(10);
        arr[1] = num;
        return arr;

    }

    //throws a format error
    private static Exception FormatError(){throw new NumberFormatException("No Correct format specified.\n" +
            "Hex numbers must start with x,X,0X or 0x and can contain [0-9] [a-f] or [A-F]\n" +
            "and decimals must contain [0-9]");}


    //sign extends a binary number. if FirstDigit is true will repeat the last digit, if false will repeat the char that we specify
    private static String SignedExtender(String n,boolean FirstDigit,char rep,int numberofbits)
    {
        StringBuilder number = new StringBuilder(n);

        //get the first letter to repeat if FirstDigit is true
        char first = number.charAt(0);


        //if true we repeat the last digit until the len is a multiple of 4
        if(FirstDigit)
        {
            while(number.length() != numberofbits)
            {

                number.insert(0,first);
            }
        }

        //if FirstDigit false, we repeat the desired character
        else
        {
            while(number.length() != numberofbits)
            {

                number.insert(0,rep);
            }
        }


        return number.toString();

    }

    //will make sure that per each instruction the right amount of arguments are given. So it will not have to check every time if there is missing one
    private void ArgumentError(String instruction, ArrayList<String> tokens)
    {


        if((instruction.equals("ADD") || instruction.equals("AND") || instruction.equals("SUB") || instruction.equals("LDR")
                || instruction.equals("STR") || instruction.equals("MUL")) && tokens.size() != 3){ArgumentErrorMessage();}

        if((instruction.equals("LD") || instruction.equals("LDI") || instruction.equals("LEA") || instruction.equals("NOT")
                | instruction.equals("ST")| instruction.equals("STI")) && tokens.size() != 2){ArgumentErrorMessage();}


        if((instruction.equals("JMP") || instruction.equals("JSR") || instruction.equals(".FILL") || instruction.equals("JSRR") || instruction.equals("TRAP")
                || instruction.equals(".BLKW")) && tokens.size() != 1){ArgumentErrorMessage();}

        if((instruction.equals("RET") || instruction.equals("RTI")|| instruction.equals(".END"))
                && tokens.size() != 0){ArgumentErrorMessage();}

        if(instruction.matches("^BRN?Z?P?$") && tokens.size() != 1){ArgumentErrorMessage();}




    }

    private static boolean notLabel(String ins)
    {
        // Labels can not be OPs
        if(Arrays.asList(ops).contains(ins.toUpperCase())) return true;

        if(ins.toUpperCase().matches("^BRN?Z?P?$"))return true;



        // Check for branches with CCs
        return false;
    }

    private void ArgumentErrorMessage()
    {
        System.err.printf("Missing argument for the instruction %s", instruction);
        System.exit(BAD_ARGS);
    }

    // Array of all of our OPs
    private static final String[] ops = {"ADD", "AND",
            "BR", "JMP", "JSR", "JSRR", "LD", "LDR", "LDI",
            "LEA", "NOT", "RET", "RTI", "ST", "STR", "STI",
            "TRAP", "SUB", "MUL",".ORIG",".STRINGZ",".BLKW",".END",".FILL"};

    // Array of TRAP VECTOR
    private static final String[] trapVectors = {"GETC", "OUT",
            "PUTS", "IN", "PUTSP", "HALT"};


    // Array of all registers
    private static final String[] registers = {"R0","R1", "R2",
            "R3", "R4", "R5", "R6", "R7","r0","r1", "r2",
            "r3", "r4", "r5", "r6", "r7"};

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
