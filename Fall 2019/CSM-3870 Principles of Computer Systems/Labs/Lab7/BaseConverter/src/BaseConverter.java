import java.math.BigInteger;


public class BaseConverter {

    public static void main(String[] args) {

        if(args.length !=1)
        {
            throw new IllegalArgumentException("\nYou must specify a number of 64-bit\n" +
                    ">>Hex numbers must start with x,X,0X or 0x and can contain [0-9] [a-f] or [A-F]\n" +
                    ">>Binary numbers must start with a B or b and just contain 1s and 0s\n" +
                    ">>Decimals must contain [0-9]");
        }

        String number = args[0];
        String [] results = new String[2];
        String hex = "";
        String binary = "";
        BigInteger decimal;
        String [] info;
        info = NumberCleaner(number);

        String cleanNumber = info[1];

        //if the input is a binary
        if(info[0].equals("2"))
        {
            results = Binary(cleanNumber,results);
            binary = number;
            hex = "0x"+results[0];
            decimal = new BigInteger(results[1]);

            //check if it is greater or smaller than 64-bit
            NumberBoundaries(decimal);

        }
        //if the input is an hex value
        else if(info[0].equals("16"))
        {

            results = Hexadecimal(cleanNumber,results);
            hex = number;
            binary = results[0];
            decimal = new BigInteger(results[1]);
            binary = "B"+binary;
            //check if it is greater or smaller than 64-bit
            NumberBoundaries(decimal);

        }

        //if the input is a decimal
        else
            {
                results = Decimal(number,results);
                decimal = new BigInteger(number);
                binary = "B"+results[0];
                hex = "0x"+results[1];
                //check if it is greater or smaller than 64-bit
                NumberBoundaries(decimal);
            }

        System.out.println("BINARY:  " + binary.toUpperCase());
        System.out.println("HEX:  " + hex.toUpperCase());
        System.out.println("Decimal  " + decimal);


    }

    public static void NumberBoundaries(BigInteger number)
    {
        //Upper bound of a 64 bit = 2**63-1
        BigInteger UpperBound = new BigInteger("9223372036854775807");
        //Lower bound of a 64 bit = -2**63
        BigInteger LowerBound = new BigInteger("-9223372036854775808");

        if(number.compareTo(UpperBound) == 1 || number.compareTo(LowerBound) == -1)
        {
            throw new IndexOutOfBoundsException("the number must be => than 64 bits");
        }
    }

    public static String BaseConverter(String num,int DestinationBase)
    {

        String [] a;
        a = NumberCleaner(num);


        BigInteger big = new BigInteger(a[1],Integer.parseInt(a[0]));
        //changes the base of a number
        String converted = big.toString(DestinationBase);

        return converted;
    }

    private static String[] Decimal(String n,String[] results)
    {
        //[0] binary number , [1] hex number
        results[0] = "";
        results[1] = "";

        //if the number is negative
        if(n.charAt(0) == '-')
        {
            //eliminate the negative sign
            n = n.substring(1);

            //take the twos complement
            String binary = TwosComplement(BaseConverter(n,2));

            //binary is equal to the twos complement of the decimal sign extended with 1 because it is negative
            results[0] = SignedExtender(binary,false,'1');

            //hex number is equal the binary value of the decimal converted to base 16
            results[1] = BaseConverter("B"+results[0],16);
        }

        //if it is positive
        else
        {

            //binary is equal to the decimal base 2 sign extended with 0 because it is a positive number
            results[0] = BaseConverter(n,2);

            //sign extension with 0 to get the right amount bits
            // because in this case we are not going to have a 1 in front that will automatically gives us the right amount of hex values
            String signExtended = SignedExtender(BaseConverter(n,2),false,'0');

            //number of 4-bit that we have in the number
            int partition = signExtended.length()/4;

            //Convert the binary to hex per each partition of 4-bits.
            for(int i = 0; i < partition;i++)
            {
                int endIndex = 4+4*i;
                int begIndex = 4*i;

                results[1] = results[1]+BaseConverter("B" + signExtended.substring(begIndex,endIndex),16);

            }

        }

        return results;

    }

    private static String[] Hexadecimal(String n,String[] results)
    {
        //[0] binary number , [1] decimal number
        results[0] = "";
        results[1] = "";

        //if the number is positive
        if(Character.toString(n.charAt(0)).matches("1|2|3|4|5|6|7"))
        {
             //binary value equal to the hex number to base 2 and sign extended with 0
             results[0] = SignedExtender(BaseConverter("X"+n,2),false,'0');

             //deciaml is equal to the hex number to the base 10
             results[1] = BaseConverter("X"+n,10);
        }
        //if the number is negative
        else
            {
                //binary number equal to the hex number to the base 2
                // (no sign extended because it wil automatically do it because the first digit is 1 (negative hex value))
                results[0] = BaseConverter("X"+n,2);

                //decimal value equal to the complement of the binary form of the hex value + the negative sign
                String complement = TwosComplement(results[0]);
                results[1] = "-" +BaseConverter("B"+ complement,10);

            }

        return results;

    }

    private static String[] Binary(String n,String[] results)
    {
        //[0] hex number , [1] decimal number
        results[0] = "";
        results[1] = "";

        //if the number is positive
        if(n.charAt(0) == '1')
        {

            //hex value is equal to the signed extended binary number to the base 16
            results[0] = BaseConverter("B" + SignedExtender(n,true,' '),16);
            //to get the decimal we convert that number to its complement and we add a negative sign
            String complement = TwosComplement(n);
            results[1] = "-" +BaseConverter("B"+ complement,10);

        }

        //if the number is positive
        else
            {
                String SignExtended = SignedExtender(n,true,' ');
                //number of 4-bit that we have in the number
                int partition = SignExtended.length()/4;

                //Convert the binary to hex per each partition of 4-bits.
                for(int i = 0; i < partition;i++)
                {
                    int endIndex = 4+4*i;
                    int begIndex = 4*i;

                    results[0] = results[0]+BaseConverter("B" + SignExtended.substring(begIndex,endIndex),16);

                }

                //to get the decimal just convert the binary to the base 10
                results[1] = BaseConverter("B"+ n,10);
            }

        return results;

    }
    private static String SignedExtender(String n,boolean FirstDigit,char rep)
    {
        StringBuilder number = new StringBuilder(n);

        //get the first letter to repeat if FirstDigit is true
        char first = number.charAt(0);


        //if true we repeat the last digit until the len is a multiple of 4
        if(FirstDigit)
        {
            while(number.length() %4 != 0)
            {

                number.insert(0,first);
            }
        }

        //if FirstDigit false, we repeat the desired character
        else
            {
                while(number.length() %4 != 0)
                {

                    number.insert(0,rep);
                }
            }

        return number.toString();

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



    private static String[] NumberCleaner(String num)
    {
        //String array to save arr[0] = the base the number input is. arr[1] clean number
        String [] arr = new String[2];

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
            "Binary numbers must start with a B or b and just contain 1s and 0s\n" +
            "and decimals must contain [0-9]");}
}
