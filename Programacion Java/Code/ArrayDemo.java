
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayDemo {
    
    public static void main(String args[])
    {
        ArrayList<Integer> data1 = new ArrayList<>(9);
        data1.add(0); data1.add(1); data1.add(2);
        data1.add(2); data1.add(0); data1.add(1);
        data1.add(1); data1.add(2); data1.add(0);

        int row = 1;
        int column = 2;
        int whoWon = data1.get(row * 3 + column);
        
        List<Integer> data2 = Arrays.asList(0, 1, 2, 
                                            2, 0, 1,
                                            1, 2, 0);
        
        whoWon = data2.get(row * 3 + column);

        ArrayList<ArrayList<Integer>> data3 = new ArrayList<>(3);
        ArrayList<Integer> temp = new ArrayList<>(3);
        temp.add(0); temp.add(1); temp.add(2);
        data3.add(temp);

        temp = new ArrayList<>(3);
        temp.add(2); temp.add(0); temp.add(1);
        data3.add(temp);
        
        temp = new ArrayList<>(3);
        temp.add(1); temp.add(2); temp.add(0);
        data3.add(temp);
        
        whoWon = data3.get(row).get(column);

        ArrayList<List<Integer>> data4 = new ArrayList<>(3);
        data4.add(Arrays.asList(0, 1, 2));
        data4.add(Arrays.asList(2, 0, 1));
        data4.add(Arrays.asList(1, 2, 0));
        
        whoWon = data4.get(row).get(column);

        int data5[] = {
            0, 1, 2,
            2, 0, 1,
            1, 2, 0
        };
        
        whoWon = data5[row * 3 + column];

        int data6[][] = {
            {0, 1, 2},
            {2, 0, 1},
            {1, 2, 0}
        };
        
        whoWon = data6[row][column];
    }
    
}
