package mat2670;

import java.util.ArrayList;

public class ArrayListDemo
{

    public static void main(String[] args)
    {
        ArrayList<Integer> myArray = new ArrayList<>(10);
        
        for(int index = 0; index < 10; index++)
        {
            myArray.add(2 * index);
        }
        
        printArrayList(myArray);

        for(int index = 0; index < myArray.size(); index++)
        {
            myArray.set(index, index * 3 + 1);
        }

        printArrayList(myArray);
    }

    private static void printArrayList(ArrayList myArray)
    {
        System.out.print(myArray.get(0));
        for(int index = 1; index < myArray.size(); index++)
        {
            System.out.print(", " + myArray.get(index));
        }
        System.out.println();
    }
    
}
