// Demonstrate several Stream operations. 

import java.util.*;
import java.util.stream.*;

public class StreamDemo
{

    public static void main(String[] args)
    {

        // Create a list of Integer values. 
        final ArrayList<Integer> data = new ArrayList<>();
        final int size = 10;
        final Random rand = new Random();
        for(int i = 0; i < size; i++)
        {
            data.add(rand.nextInt(20));
        }

        System.out.println("Original list: " + data);

        // Obtain a Stream to the array list. 
        Stream<Integer> stream = data.stream();

        // Obtain the minimum and maximum value by uses of min(), 
        // max(), isPresent(), and get(). 
        Optional<Integer> minVal = stream.min(Integer::compare);
        if (minVal.isPresent())
        {
            System.out.println("Minimum value: "
                    + minVal.get());
        }

        // Must obtain a new stream because previous call to min() 
        // is a terminal operation that consumed the stream. 
        stream = data.stream();
        Optional<Integer> maxVal = stream.max(Integer::compare);
        if (maxVal.isPresent())
        {
            System.out.println("Maximum value: "
                    + maxVal.get());
        }

        // Sort the stream by use of sorted(). 
        Stream<Integer> sortedStream = data.stream().sorted();

        // Display the sorted stream by use of forEach(). 
        System.out.print("Sorted stream: ");
        sortedStream.forEach((n) -> System.out.print(n + " "));
        System.out.println();
        // Display only the odd values by use of filter(). 
        Stream<Integer> oddVals
                = data.stream().sorted().filter((n) -> (n % 2) == 1);
        System.out.print("Odd values: ");
        oddVals.forEach((n) -> System.out.print(n + " "));
        System.out.println();

        // Display only the odd values that are greater than 5. Notice that 
        // two filter operations are pipelined. 
        oddVals = data.stream().filter((n) -> (n % 2) == 1)
                .filter((n) -> n > 5);
        System.out.print("Odd values greater than 5: ");
        oddVals.forEach((n) -> System.out.print(n + " "));
        System.out.println();
    }
}