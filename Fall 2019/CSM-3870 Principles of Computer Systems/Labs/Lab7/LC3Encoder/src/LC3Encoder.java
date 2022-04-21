import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class LC3Encoder extends JFrame
{
    private static JTextArea text;
    private static boolean ready = true;

    public LC3Encoder(double screenw, double screenh) {
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setBounds((int) screenw / 2 - 500 / 2, (int) screenh / 2 - 500 / 2, 500, 500); //centered on the screen
        super.setLayout(new BorderLayout());

        JPanel center = new JPanel();
        JPanel north = new JPanel();
        JPanel south = new JPanel();


        //Button to initialize the process to create the machone intruction
        JButton generate = new JButton("GENERATE");

        //ComboBox for the instructions
        JLabel labelInstruction = new JLabel("Instruction");
        String[] ins = {"  ","ADD","ADD(Immediate)","AND","AND(Immediate)","BR","JMP","JSR","JSRR","LD","LDI","LDR","LEA","NOT","RET",
                "RTI","ST","STI","STR","TRAP"};
        JComboBox<String> boxIns = new JComboBox(ins);

        //ComboBox for the destination register
        JLabel labelDestRegister= new JLabel("DR");
        String[] registers = {"","R0","R1","R2","R3","R4","R5","R6","R7",};
        JComboBox<String> boxDestRegister = new JComboBox(registers);

        //ComboBox for the source1 register
        JLabel labelSource1Register = new JLabel("SR1");
        JComboBox<String> boxSource1Register = new JComboBox(registers);

        //ComboBox for the source2 register
        JLabel labelSource2Register = new JLabel("SR2");
        JComboBox<String> boxSource2Register = new JComboBox(registers);

        //JTextField for the PCoffset9
        JLabel labelPCoffset9 = new JLabel("    PCoffset9");
        JTextField TextPCoffset9 = new JTextField(3);



        //JTextField for the PCoffset11
        JLabel labelPCoffset11 = new JLabel("PCoffset11");
        JTextField TextPCoffset11 = new JTextField(4);

        //if(Integer.parseInt(TextPCoffset11.getText()) > 1023 || Integer.parseInt(TextPCoffset11.getText()) < -1024 ){}

        //JTextField for the offset5
        JLabel labeloffset6 = new JLabel("offset6");
        JTextField Textoffset6 = new JTextField(2);

        //JTextField for the imm5
        JLabel labelimm5 = new JLabel("imm5");
        JTextField Textimm5 = new JTextField(2);

        //if(Integer.parseInt(Textimm5.getText()) > 15 || Integer.parseInt(Textimm5.getText()) < -16){}

        //ComboBox for the base register
        JLabel labelbase = new JLabel("BASE");
        JComboBox<String> boxbase = new JComboBox(registers);

        //ComboBox for the Trap vectors register
        JLabel labelvector = new JLabel("TrapVectors");
        String[] vectors = {"","x20","x21","x22","x23","x24","x25"};
        JComboBox<String> boxvector = new JComboBox(vectors);

        //Radio Buttons for the n,z,p flags
        JLabel labelFlags = new JLabel("Flags");
        JRadioButton negative = new JRadioButton("N");
        JRadioButton zero = new JRadioButton("Z");
        JRadioButton postive = new JRadioButton("P");

        //Text are to output the result
        JLabel Result = new JLabel("Machine Code: ");
        text = new JTextArea(2,12);


        //adding Instructions components
        center.add(labelInstruction);
        center.add(boxIns);

        //adding DR components
        center.add(labelDestRegister);
        center.add(boxDestRegister);

        //adding SR1 components
        center.add(labelSource1Register);
        center.add(boxSource1Register);

        //adding SR2 components
        center.add(labelSource2Register);
        center.add(boxSource2Register);

        //adding PCoffset9 components
        center.add(labelPCoffset9);
        center.add(TextPCoffset9);

        //adding PCoffset11 components
        center.add(labelPCoffset11);
        center.add(TextPCoffset11);

        //adding offset6 components
        center.add(labeloffset6);
        center.add(Textoffset6);

        //adding imm5 components
        center.add(labelimm5);
        center.add(Textimm5);

        //adding base components
        center.add(labelbase);
        center.add(boxbase);

        //adding TRAP vectors components
        center.add(labelvector);
        center.add(boxvector);

        //adding Flag conditions components
        center.add(labelFlags);
        center.add(negative);
        center.add(zero);
        center.add(postive);

        //adding JTextArea components for the result
        south.add(Result);
        south.add(text);


        north.add(generate);

        add(north,BorderLayout.NORTH);
        add(south,BorderLayout.SOUTH);
        add(center,BorderLayout.CENTER);

        Component[] components = center.getComponents();

        EnableDissable(components,boxIns,TextPCoffset9,TextPCoffset11,Textimm5,Textoffset6,generate);

        generate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                String instruction = String.valueOf(boxIns.getSelectedItem());


                String des = String.valueOf(boxDestRegister.getSelectedItem());
                String sr1 = String.valueOf(boxSource1Register.getSelectedItem());
                String sr2 = String.valueOf(boxSource2Register.getSelectedItem());
                String imm5 = Textimm5.getText();
                String off9 =TextPCoffset9.getText();
                String off11 = TextPCoffset11.getText();
                String off6 = Textoffset6.getText();
                String base = String.valueOf(boxbase.getSelectedItem());
                String trap = String.valueOf(boxvector.getSelectedItem());

                checkForMissingOperands(components,boxDestRegister,Textoffset6,text);

                String Binary = "";
                String Hex ;

                if(ready)
                {

                    if(instruction.equals("ADD"))
                    {
                        Binary = String.format("0001%s%s000%s",RegisterEncoder(des),RegisterEncoder(sr1),RegisterEncoder(sr2));

                    }

                    else if(instruction.equals("ADD(Immediate)"))
                    {
                        Binary = String.format("0001%s%s1%s",RegisterEncoder(des),RegisterEncoder(sr1),Offsets(imm5,5));

                    }
                    if(instruction.equals("AND"))
                    {
                        Binary = String.format("0101%s%s000%s",RegisterEncoder(des),RegisterEncoder(sr1),RegisterEncoder(sr2));

                    }
                    else if(instruction.equals("AND(Immediate)"))
                    {
                        Binary = String.format("0101%s%s1%s",RegisterEncoder(des),RegisterEncoder(sr1),Offsets(imm5,5));

                    }
                    else if(instruction.equals("JMP"))
                    {
                        Binary = String.format("1100000%s000000",RegisterEncoder(base));

                    }
                    else if(instruction.equals("JSR"))
                    {
                        Binary = String.format("01001%s",Offsets(off11,11));

                    }
                    else if(instruction.equals("JSRR"))
                    {
                        Binary = String.format("0100000%s000000",RegisterEncoder(base));

                    }
                    else if(instruction.equals("LD"))
                    {
                        Binary = String.format("0010%s%s",RegisterEncoder(des),Offsets(off9,9));

                    }
                    else if(instruction.equals("LDI"))
                    {
                        Binary = String.format("1010%s%s",RegisterEncoder(des),Offsets(off9,9));

                    }
                    else if(instruction.equals("LDR"))
                    {
                        Binary = String.format("0110%s%s%s",RegisterEncoder(des),RegisterEncoder(base),Offsets(off6,6));
                    }
                    else if(instruction.equals("LEA"))
                    {
                        Binary = String.format("1110%s%s",RegisterEncoder(des),Offsets(off9,9));
                    }
                    else if(instruction.equals("NOT"))
                    {
                        Binary = String.format("1001%s%s111111",RegisterEncoder(des),RegisterEncoder(sr1));
                    }
                    else if(instruction.equals("RET"))
                    {
                        Binary = String.format("1100000111000000");
                    }
                    else if(instruction.equals("RTI"))
                    {
                        Binary = String.format("1000000000000000");
                    }
                    else if(instruction.equals("ST"))
                    {
                        Binary = String.format("0011%s%s",RegisterEncoder(sr1),Offsets(off9,9));
                    }
                    else if(instruction.equals("STI"))
                    {
                        Binary = String.format("1011%s%s",RegisterEncoder(sr1),Offsets(off9,9));
                    }
                    else if(instruction.equals("STR"))
                    {
                        Binary = String.format("0111%s%s%s",RegisterEncoder(sr1),RegisterEncoder(base),Offsets(off6,6));
                    }
                    else if(instruction.equals("TRAP"))
                    {
                        Binary = String.format("11110000%s",TrapVector(trap));
                    }

                    else if(instruction.equals("BR"))
                    {
                        boolean[] flags = new boolean[3];
                        flags[0] = negative.isSelected();
                        flags[1] = zero.isSelected();
                        flags[2] = postive.isSelected();

                        String nzp = Flags(flags);

                        Binary = String.format("0000%s%s",nzp,Offsets(off9,9));


                    }
                    Hex = "x"+Hex(Binary);
                    text.setText(Binary+"\n"+Hex);

                }


            }
        });

    }

    private static String Hex(String signExtended)

    {
        String result = "";
        int partition = signExtended.length()/4;

        //Convert the binary to hex per each partition of 4-bits.
        for(int i = 0; i < partition;i++)
        {
            int endIndex = 4+4*i;
            int begIndex = 4*i;

            result = result+BaseConverter("B" + signExtended.substring(begIndex,endIndex),16);

        }
        return result;
    }

    private static String TrapVector(String vector)
    {

        return SignedExtenderUnsigned(BaseConverter(vector,2),8);
    }

    private static void checkForMissingOperands(Component[] components, JComboBox boxDestRegister,JTextField Textoffset6,JTextArea text)
    {
        LinkedList<JComboBox> combos = new LinkedList<JComboBox>();
        LinkedList<JTextField> textfields = new LinkedList<JTextField>();

        for(Component c : components)
        {
            if(c.getClass() == boxDestRegister.getClass() && c.isEnabled() ){combos.add((JComboBox) c);}

            if(c.getClass() == Textoffset6.getClass()  && c.isEnabled() ){textfields.add((JTextField) c);}
        }

        for(JComboBox com : combos)
        {
            if(String.valueOf(com.getSelectedItem()).length() == 0){text.setText("MISSING OPERANDS");ready = false;return;}
        }

        for(JTextField t : textfields)
        {
            if(t.getText().length() == 0){text.setText("MISSING OPERANDS. for and offset of 0 type 0");ready = false;return;}

        }

        ready = true;

    }



    private static String SignedExtenderTwosComplement(String n,boolean FirstDigit,int limit,char rep)
    {
        StringBuilder number = new StringBuilder(n);

        //get the first letter to repeat if FirstDigit is true
        char first = number.charAt(0);


        //if true we repeat the last digit until the len is the desired
        if(FirstDigit)
        {
            while(number.length() != limit)
            {

                number.insert(0,first);
            }
        }

        //if FirstDigit false, we repeat the desired character
        else
        {
            while(number.length()  != limit)
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
    private static void FormatError(){text.setText("No Correct format specified.\nDecimals must contain [0-9]");}

    private static String Flags(boolean[] flags)
    {
        StringBuilder nzp = new StringBuilder();
        for(boolean i : flags)
        {
            if(i)
            {
                nzp.append(1);
            }
            else{nzp.append(0);}
        }
        return nzp.toString();
    }

    private static String SignedExtenderUnsigned(String n, int numberofBitstoExtend)
    {
        StringBuilder number = new StringBuilder(n);


        //if FirstDigit false, we repeat the desired character
        while(number.length() !=numberofBitstoExtend)
        {

            number.insert(0,0);
        }

        return number.toString();

    }

    private static String Offsets(String number,int offsetBit)
    {
        return  Decimal(number,offsetBit);
    }

    private static String RegisterEncoder(String register)
    {
        register = register.substring(1);

        if(register.equals("0")){return SignedExtenderUnsigned("0",3);}
        else
            {
                String binary = BaseConverter(register,2);
                return SignedExtenderUnsigned(binary,3);
            }

    }

    private static String Decimal(String n,int limit)
    {

        String results;

        //if the number is negative
        if(n.charAt(0) == '-')
        {
            //eliminate the negative sign
            n = n.substring(1);

            //take the twos complement
            String binary = TwosComplement(BaseConverter(n,2));

            //binary is equal to the twos complement of the decimal sign extended with 1 because it is negative
            results = SignedExtenderTwosComplement(binary,true,limit, ' ');

        }

        //if it is positive
        else
        {

            //binary is equal to the decimal base 2 sign extended with 0 because it is a positive number
            results = BaseConverter(n,2);
            results = SignedExtenderTwosComplement(results,false,limit, '0');

        }

        return results;

    }

    private static String BaseConverter(String number,int destination)
    {
        String [] a;
        a = NumberCleaner(number);

        //changes the base of a number
        String converted = Integer.toString(Integer.parseInt(a[1],Integer.parseInt(a[0])),destination);

        return converted;
    }

    private static void EnableDissable(Component[] components,JComboBox boxIns,
                                       JTextField TextPCoffset9, JTextField TextPCoffset11,JTextField Textimm5,JTextField Textoffset6,JButton generate)
    {
        //Logic to Enable or Disable the corrects operands
        boxIns.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(!generate.isEnabled()){generate.setEnabled(true);}
                text.setText("");

                //Disable all the operands every time we select and opcode
                for(int i = 2; i < components.length;i++)
                {
                    components[i].setEnabled(false);
                }
                String ins= String.valueOf(boxIns.getSelectedItem());

                if(ins.equals("ADD") || ins.equals("AND"))
                {
                    for(int i = 2; i <= 7;i++){components[i].setEnabled(true);}
                }
                else if(ins.equals("ADD(Immediate)") || ins.equals("AND(Immediate)"))
                {
                    for(int i = 2; i <= 5;i++){components[i].setEnabled(true);}
                    components[14].setEnabled(true);
                    components[15].setEnabled(true);
                }
                else if(ins.equals("JMP") || ins.equals("JSRR"))
                {
                    components[16].setEnabled(true);
                    components[17].setEnabled(true);
                }

                else if(ins.equals("LD") || ins.equals("LDI") || ins.equals("LEA"))
                {
                    components[2].setEnabled(true);
                    components[3].setEnabled(true);
                    components[8].setEnabled(true);
                    components[9].setEnabled(true);
                }

                else if(ins.equals("ST") || ins.equals("STI"))
                {
                    components[4].setEnabled(true);
                    components[5].setEnabled(true);
                    components[8].setEnabled(true);
                    components[9].setEnabled(true);
                }

                else if(ins.equals("JSR") )
                {
                    components[10].setEnabled(true);
                    components[11].setEnabled(true);
                }

                else if(ins.equals("BR") )
                {
                    components[8].setEnabled(true);
                    components[9].setEnabled(true);
                    components[20].setEnabled(true);
                    components[21].setEnabled(true);
                    components[22].setEnabled(true);
                    components[23].setEnabled(true);

                }

                else if(ins.equals("LDR") )
                {
                    components[2].setEnabled(true);
                    components[3].setEnabled(true);
                    components[12].setEnabled(true);
                    components[13].setEnabled(true);
                    components[16].setEnabled(true);
                    components[17].setEnabled(true);
                }
                else if(ins.equals("STR") )
                {
                    components[4].setEnabled(true);
                    components[5].setEnabled(true);
                    components[12].setEnabled(true);
                    components[13].setEnabled(true);
                    components[16].setEnabled(true);
                    components[17].setEnabled(true);
                }

                else if(ins.equals("TRAP") )
                {
                    components[18].setEnabled(true);
                    components[19].setEnabled(true);
                }

                else if(ins.equals("TRAP") )
                {
                    components[18].setEnabled(true);
                    components[19].setEnabled(true);
                }
                else if(ins.equals("NOT") ){for(int i = 2; i <= 5;i++){components[i].setEnabled(true);}}


            }
        });

    }

        public static void main(String[] args) {

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            double width = screenSize.getWidth();
            double height = screenSize.getHeight();




            java.awt.EventQueue.invokeLater(() ->
            {
                new LC3Encoder(width,height).setVisible(true);

            });

    }
}
