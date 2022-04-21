
import java.util.IllegalFormatException;

public class LC3Decoder {

    public static void main(String[] args) {

        //must be an argument
        if(args.length !=1)
        {
            throw new IllegalArgumentException("\nYou must specify a number of 16-bit\n" +
                    ">>Hex numbers must start with x,X,0X or 0x and can contain [0-9] [a-f] or [A-F]\n" +
                    ">>Binary numbers must start with a B or b and just contain 1s and 0s\n");
        }

        String number = args[0];

        //get the base of the number input and the numerical part
        String[] info = NumberCleaner(number);
        String base = info[0];
        String cleanNumber = info[1];

        //check if its a 16-bit number
        NumberBoundaries(cleanNumber,base);
        String FullBinary;

        //In case of a binary number, sign extent it with 0
        if(base.equals("2"))
        {
            FullBinary = SignedExtender(cleanNumber);
        }

        //In case of and hex value, turn it to binary and then sign extend it
        else
            {
                cleanNumber = BaseConverter(cleanNumber,16,2);
                FullBinary = SignedExtender(cleanNumber);
            }

        String a = Instruction(FullBinary);
        System.out.println("Instruction   "+a);

    }

    private static String BaseConverter(String number,int source,int destination)
    {

        //changes the base of a number
        String converted = Integer.toString(Integer.parseInt(number,source),destination);

        return converted;
    }
    //Returns the Destination register
    private static String Destination(String n)
    {
        n =  n.substring(4,7);
        n = BaseConverter(n,2,10);
        return "R"+n;
    }
    //Returns the Source1 register
    private static String Source1(String n)
    {
        n =  n.substring(7,10);
        n = BaseConverter(n,2,10);
        return "R"+n;
    }
    //Returns the Source2 register
    private static String Source2(String n)
    {
        n =  n.substring(13,16);
        n = BaseConverter(n,2,10);
        return "R"+n;
    }
    //Returns the 9-bit offset in decimal
    private static String PCoffset9(String n) {return "#"+BaseConverter(n.substring(7,16),2,10);}

    //Returns the 11-bit offset in decimal
    private static String PCoffset11(String n) {return "#"+BaseConverter(n.substring(5,16),2,10);}

    //Returns the 6-bit offset in decimal
    private static String offset6(String n) {return "#"+BaseConverter(n.substring(10,16),2,10);}

    //Returns the 5-bit immediate decimal
    private static String imm5(String n) {return "#"+BaseConverter(n.substring(11,16),2,10);}
    private static String trapvect8(String n)
    {
        n =  n.substring(8,16);
        String vector = BaseConverter(n,2,16);
        return "x"+vector;
    }
    //Returns the register used as a base
    private static String base(String n) {
        n =  n.substring(7,10);
        n = BaseConverter(n,2,10);
        return "R"+n;
    }

    //Returns the conditions for the branch
    private static String Condition(String n)
    {
        n =  n.substring(4,7);

        String[] arr = new String[3];
        if(n.charAt(0) == '1'){arr[0] = Character.toString('n');}
        if(n.charAt(1) == '1'){arr[1] = Character.toString('z');}
        if(n.charAt(2) == '1'){arr[2] = Character.toString('p');}

        StringBuilder nzp = new StringBuilder();
        for(String i : arr)
        {
            if(i != null)
            {
                nzp.append(i);
            }
        }
        return nzp.toString();
    }



    //Instruction decoder
    private static String Instruction(String bi)
    {
        String firstFour = bi.substring(0,4);

        System.out.println();
        //ADD indirect
        if(firstFour.equals("0001") && bi.charAt(10) == '0')
        {
            return String.format("ADD ,%s ,%s ,%s ",Destination(bi),Source1(bi),Source2(bi));
        }

        //ADD direct
        else if(firstFour.equals("0001"))
        {
            return String.format("ADD ,%s ,%s ,%s ",Destination(bi),Source1(bi),imm5(bi));
        }

        //AND indirect
        else if(firstFour.equals("0101") && bi.charAt(10) == '0')
        {
            return String.format("AND ,%s ,%s ,%s ",Destination(bi),Source1(bi),Source2(bi));
        }

        //AND direct
        else if(firstFour.equals("0101"))
        {
            return String.format("AND ,%s ,%s ,%s ",Destination(bi),Source1(bi),imm5(bi));
        }

        //BR
        else if(firstFour.equals("0000"))
        {
            return String.format("BR%s ,%s ",Condition(bi),PCoffset9(bi));
        }

        //JMP
        else if(firstFour.equals("1100"))
        {
            return String.format("JMP ,%s ",base(bi));
        }

        //JSR
        else if(firstFour.equals("0100") && bi.charAt(10) == '1')
        {
            return String.format("JSR ,%s ", PCoffset11(bi));
        }

        //JSRR
        else if(firstFour.equals("0100"))
        {
            return String.format("JSRR ,%s ",base(bi));
        }

        //LD
        else if(firstFour.equals("0010"))
        {
            return String.format("LD ,%s ,%s ",Destination(bi),PCoffset9(bi));
        }

        //LDI
        else if(firstFour.equals("1010"))
        {
            return String.format("LDI ,%s ,%s ",Destination(bi),PCoffset9(bi));
        }

        //LDR
        else if(firstFour.equals("0110"))
        {
            return String.format("LDR ,%s ,%s ,%s ",Destination(bi),base(bi),offset6(bi));
        }

        //LEA
        else if(firstFour.equals("1110"))
        {
            return String.format("LEA ,%s ,%s ",Destination(bi),PCoffset9(bi));
        }

        //NOT
        else if(firstFour.equals("1001"))
        {
            return String.format("NOT ,%s ,%s ",Destination(bi),Source1(bi));
        }

        //RET
        else if(firstFour.equals("1100"))
        {
            return String.format("RET");
        }

        //RTI
        else if(firstFour.equals("1000"))
        {
            return String.format("RTI");
        }

        //ST
        else if(firstFour.equals("0100"))
        {
            return String.format("ST ,%s ,%s ",Destination(bi),PCoffset9(bi));
        }

        //STI
        else if(firstFour.equals("0100"))
        {
            return String.format("STI ,%s ,%s ",Destination(bi),PCoffset9(bi));
        }

        //STR
        else if(firstFour.equals("0111"))
        {
            return String.format("STR ,%s ,%s ,%s ",Destination(bi),base(bi),offset6(bi));
        }

        //TRAP
        else if(firstFour.equals("1111"))
        {
            return String.format("TRAP ,%s ",trapvect8(bi));
        }

        else
            {
                throw new IllegalCallerException("This does not corresponds to an instruction!");
            }

    }

    private static void NumberBoundaries(String n,String base)
    {

        //if it is an hex number, check if it is longer than 4 values
        if(base.equals("16"))
        {
            if(n.length() > 4)
            {
                throw new IndexOutOfBoundsException("It must be a 16-bit number");
            }
        }

        //if it is a binary number, check if it is longer than 16 values
        else
            {
                if(n.length() > 16)
                {
                    throw new IndexOutOfBoundsException("It must be a 16-bit number");
                }
            }
    }


    private static String[] NumberCleaner(String num)
    {
        //String array to save arr[0] = the base the number input is. arr[1] clean number
        String [] arr = new String[2];


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

            //checks if it is a binary number and get rid of the b check if the rest of the numbers are 1s and 0s
            else if(num.substring(0,1).equalsIgnoreCase("b") && num.substring(1).matches("[01]+"))
            {
                arr[0] = Integer.toString(2);
                arr[1] = num.substring(1);
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

        //if not a binary or and hex value, throws an Exception
        else
        {
            FormatError();
        }

        return arr;


    }

    //throws a format error
    private static Exception FormatError(){throw new NumberFormatException("No Correct format specified.\n" +
            "Hex numbers must start with x,X,0X or 0x and can contain [0-9] [a-f] or [A-F]\n" +
            "Binary numbers must start with a B or b and just contain 1s and 0s\n");}

    private static String SignedExtender(String n)
    {
        StringBuilder number = new StringBuilder(n);


        //add 0s until get to 16-bits
        while(number.length() !=16)
        {

            number.insert(0,0);
        }

        return number.toString();

    }
}
