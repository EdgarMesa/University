
import edu.princeton.cs.algs4.StdOut;


public class Alphabet {

    public char[] alphabet;
    private int R;


    public Alphabet(String Alphabet)
    {
        this.R = Alphabet.length();
        alphabet = new char[R];

        alphabet = Alphabet.toCharArray();

    }

    public char toChar(int index)
    {
        return alphabet[index];
    }

    public int toIndex(char c)
    {
        for(int i = 0; i < R;i++ )
        {

            if(toChar(i) == c)
            {
                return i;
            }


        }

        throw new IllegalArgumentException("Character " + c + " not in alphabet");


    }

    public boolean contains(char c)
    {
        for(int i = 0; i < R;i++ )
        {

            if(toChar(i) == c)
            return true;
        }
        return false;
    }

    public int R(){return R;}

    public int LgR()
    {
        int Lgr = 0;

        for(int i = R; i >= 1;i /= 2 )
        {
            Lgr++;
        }
        return Lgr;

    }

    int[] toIndices(String s)
    {

        char[] word = s.toCharArray();
        int[] indices = new int[s.length()];

        for(int i = 0; i < s.length() ;i ++ )
        {
            indices[i] = toIndex(word[i]);
        }
        return indices;
    }

    public String toChars(int[] indices)
    {

        String word = "";


        for(int index = 0; index < indices.length ;index ++ )
        {
            if (index < 0 || index >= R) {
                throw new IllegalArgumentException("index must be between 0 and " + R + ": " + index);}
            word = word + toChar(index);
        }
        return word;

    }



    public static void main(String[] args) {

        Alphabet al = new Alphabet("ZYXWVUTSRQPONMLKJIHGFEDCBA");


        System.out.println("Radix = "+ al.R());
        System.out.println("Iretarions binary search = "+ al.LgR());
        System.out.println("Alphabet contais? " + al.contains('H'));
        System.out.println("Alphabet contais? " + al.contains('Ñ'));
        System.out.println("Index 3 corresponds to " + al.toChar(3));
        System.out.println("Character C correspond to " + al.toIndex('C'));
        
        int[] indices = al.toIndices("ABCD");
        System.out.print("indices for ABCD: ");
        for(int i : indices)System.out.print(i+" ");
        System.out.println();
        System.out.println("indices {0,1,2,3} correspond to the characters "+ al.toChars(indices));
        
        
        String[] tosort = new String[al.R()];
        String[] tosort2 = new String[al.R()];
       
        int i = 0;
        for(char c : al.alphabet)
        {
            tosort[i] = Character.toString(c);
            tosort2[i] = Character.toString(c);
            i++;
        }
        
        LSD lsd = new LSD(tosort,tosort[0].length(),tosort.length);
        MSD msd = new MSD(tosort,tosort.length);
        lsd.sort();
        msd.sort();
        
        for (int j = 0; j < tosort.length; j++){
        StdOut.print(lsd.array()[j]);

        }
        StdOut.println();

        for (int j = 0; j < tosort.length; j++){
        StdOut.print(msd.array()[j]);

        }
        StdOut.println();

        
        
        
        



    }


}