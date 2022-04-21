/*
This Assembler takes one file argument. The file must be a LC3 asm file with its proper
instruction, the program will throw exception if the file document does not follow the format
for an LC3 document. ex) not having a .ORIG, illegal instruction or wring syntax format. This assembler
will output two files, an obj file and a sym file that can be fed into an LC3 simulator like PennSim.
 */


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

public class Assembler {

    // For when things go wrong
    private static class AssemblyException extends Exception{private AssemblyException(String m){super(m);}}

    // Exit codes
    private static final int BAD_ARGS=1, BAD_FILE=2,
            BAD_ORIG = 3, BAD_LABEL = 4, SAVE_ERROR = 5;

    private static Set<String> labels;

    private static FileOutputStream Fileobj;



    public static void main(String[] args) {

        // Check usage
        if(args.length != 1)
        {
            printUsage();
            System.exit(BAD_ARGS);
        }

        BuildTable table = new BuildTable(args[0]);
        String instructions = table.getInstructions(); //generate the table, labels and the file as string with all the important information
        labels = table.getLabels();


        Scanner rawline = new Scanner(instructions); //split in lines

        //index for the parallel Linkedlist of addresses and the instructions
        int index = 0;


        try {
            //OutputStream object to send the bytes to the obj file
            Fileobj = new FileOutputStream(new File(table.Symboltablename + ".obj"));


            //for each line send it to the decoder object tht will return a string of 1s and 0s (instruction)

            //for each instruction, decoded and write it to the obj.file
            while (rawline.hasNextLine()) {
                String in = rawline.nextLine().trim();
                Decoder decoder = new Decoder(in, labels, table, index++);

                if(decoder.operand.equalsIgnoreCase(".END")){continue;}
                //get the bytes for the string
                String binaryString = decoder.finalinstruction;

                int len = binaryString.length();
                int i = 0;
                int j = 4;


                while(len != 0)
                {

                    byte[] outputbytes = StringtoByte(binaryString.substring(i,j));
                    Fileobj.write(outputbytes);

                    i+=4;
                    j+=4;
                    len-=4;

                }

            }
            Fileobj.close();

        }catch (IOException ex){System.exit(BAD_FILE);}


    }


    // Prints simple usage information
    private static void printUsage()
    {
        System.out.println("BuildTable asmFile");
        System.out.println("Saves the table for the given asm file");
    }


    private static byte[] StringtoByte(String s)
    {
        byte[] result = new byte[2];
        result[0] =Byte(s.substring(0,2));
        result[1] = Byte(s.substring(2,4));

        return result;

    }

    private static byte Byte(String s)
    {

        int l = value(s.charAt(0));
        int r = value(s.charAt(1));

        return  (byte) (l *16 +r);
    }

    private static int value(char a)
    {
        if(a > 0 || a <= 9)
        {
            return a -'0';
        }

        else
            {
                return a -'A'+10;
            }

    }


    private static void ArgumentError(String s)
    {
        System.err.println("Missing or wrong argument for the instruction "+s);
        System.exit(BAD_ARGS);
    }

}
