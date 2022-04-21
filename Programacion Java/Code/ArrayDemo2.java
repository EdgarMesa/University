package mat2670;

public class ArrayDemo2
{

    public static void main(String[] args)
    {
        final int[] myArray1 = new int[10];

        for(int index = 0; index < myArray1.length; index++)
        {
            myArray1[index] = index * 3 + 1;
        }

        printArray(myArray1);
        
        final int[] myArray2 = {2, 3, 5, 7, 9};

        printArray(myArray2);
    }

    private static void printArray(final int[] myArray)
    {
        System.out.print(myArray[0]);
        for(int index = 1; index < myArray.length; index++)
        {
            System.out.print(", " + myArray[index]);
        }
        System.out.println();
    }
    
}
