import java.io.BufferedOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ChangeFaster
{
    private static final Scanner cin = new Scanner(System.in);
    private static final PrintStream cout = new PrintStream(new BufferedOutputStream(System.out));
    private static Integer[][][][][] cache = new Integer[7490][2][2][2][2];

    public static void main(String[] args)
    {
        while(cin.hasNextInt())
        {
            cout.println(makeChange(cin.nextInt(), 1, 1, 1, 1));
        }
        cout.flush();
    }

    private static int makeChange(int amount, int f, int q, int d, int n)
    {
        if(amount < 0) return 0;
        if(amount == 0) return 1;
        if(cache[amount][f][q][d][n] != null) return cache[amount][f][q][d][n];
        int result = 0;
        if(f != 0) result += makeChange(amount-50, 1, 1, 1, 1);
        if(q != 0) result += makeChange(amount-25, 0, 1, 1, 1);
        if(d != 0) result += makeChange(amount-10, 0, 0, 1, 1);
        if(n != 0) result += makeChange(amount- 5, 0, 0, 0, 1);
        return cache[amount][f][q][d][n] = result + makeChange(amount- 1, 0, 0, 0, 0);
    }
}
