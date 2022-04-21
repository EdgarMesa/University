import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

class Main
{

    private static final Scanner cin = new Scanner(System.in);
    private static final PrintStream cout = new PrintStream(new BufferedOutputStream(System.out));
    
    public static void main(String[] args) throws IOException
    {
        int numberOfRows = cin.nextInt();
        int numberOfColumns = cin.nextInt();
        while(numberOfRows > 0)
        {
            boolean[][] oilField = readField(numberOfRows, numberOfColumns);
            cout.println(findDeposits(oilField));
            numberOfRows = cin.nextInt();
            numberOfColumns = cin.nextInt();
        }
        cout.flush();
    }

    private static boolean[][] readField(int numberOfRows, int numberOfColumns)
    {
        boolean[][] field = new boolean[numberOfRows+2][numberOfColumns+2];
        for(int row = 1; row <= numberOfRows; row++)
        {
            String line = cin.next();
            for(int column = 1; column <= numberOfColumns; column++)
            {
                field[row][column] = line.charAt(column-1) == '@';
            }
        }
        return field;
    }

    private static int findDeposits(boolean[][] oilField)
    {
        int numberOfDepositsSeen = 0;
        for(int row = 1; row < oilField.length - 1; row++)
        {
            for(int column = 1; column < oilField[0].length - 1; column++)
            {
                if(oilField[row][column])
                {
                    numberOfDepositsSeen++;
                    mark(oilField, row, column);
                }
            }
        }
        return numberOfDepositsSeen;
    }

    private static void mark(boolean[][] oilField, int row, int column)
    {
        if(!oilField[row][column]) return;
        oilField[row][column] = false;
        mark(oilField, row - 1, column - 1);
        mark(oilField, row - 1, column - 0);
        mark(oilField, row - 1, column + 1);
        mark(oilField, row - 0, column - 1);
        mark(oilField, row - 0, column + 1);
        mark(oilField, row + 1, column - 1);
        mark(oilField, row + 1, column - 0);
        mark(oilField, row + 1, column + 1);
    }

}
