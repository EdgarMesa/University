
public class DrawRect
{

    public static void echoPrint(String output, int n)
    {
        for (int i = 0; i < n; ++i)
        {
            System.out.print(output);
        }
    }

    public static void main(String[] args)
    {
        if (args.length < 2)
        {
            System.err.println("Need to give both a row and column!");
            return;
        }

        int row    = Integer.valueOf(args[0]);
        int column = Integer.valueOf(args[1]);

        drawRect(row, column);
    }

    private static void drawRect(int row, int column)
    {
        drawLine(column);

        for (int i = 0; i < row - 2; ++i)
        {
            System.out.print("*");
            echoPrint(" ", column - 2);
            System.out.println("*");
        }
        
        drawLine(column);
    }

    private static void drawLine(int column)
    {
        echoPrint("*", column);
        System.out.println();
    }
}
