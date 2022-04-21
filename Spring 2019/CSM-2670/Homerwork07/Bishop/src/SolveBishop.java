import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SolveBishop extends Thread {

    public int N,row,K;
    public List<List<Integer>> result = new ArrayList<>();
    public List<Integer> board = new ArrayList<>();
    public SolveBishop(int K,int N,  List<List<Integer>> result, List<Integer> board)
    {
        this.K = K;
        this.N = N;
//        this.row = r;
        this.result = result;
        this.board = board;
    }


    private boolean checkDiagonals( int col, int row)
    {



        for (int offset = 1; offset <= col; offset++)
        {


            int upAndLeft = col - offset + (row - offset) * N;
            int downAndLeft = col - offset + (row + offset) * N;
            int upAndRight = col + offset + (row - offset) * N;
            int downAndRight = col + offset + (row + offset) * N;


            ArrayList<Integer> solutions = new ArrayList<>();

            for(List<Integer> l : result)
            {
                int r = l.get(0);
                int c = l.get(1);
                int index = r*N+c;
                solutions.add(index);
            }

            for(int in : solutions)
            {
                if((upAndLeft >=  0 && upAndLeft != in) || (downAndLeft < board.size() && downAndLeft != in)
                        || (upAndRight >= 0 && upAndRight != in) || (downAndRight <board.size()  && downAndRight != in))
                {
                    return false;
                }
            }


        }


        return true;
    }

    public  List<List<Integer>> findBishop(int row) {


        if (result.size() == 2*(K-1)) {

//            if (row == N) {


                System.out.println(result);
            return result;


        }

//        try {
//            Thread.sleep(7000);
//        } catch (InterruptedException ex) {
//            System.err.println(ex.getMessage());
//        }

        for (int col = 0; col < N; col++)
        {

            ArrayList<Integer> choose = new ArrayList<>();
            choose.add(row);
            choose.add(col);

            result.add(choose);



            if ( col > 1 && !checkDiagonals( col - 1,row)) {


                System.out.println(row);
                findBishop(row+1);
                return result;

            }


            result.remove(result.size() - 1);


        }
        result.clear();
        return result;

    }
}
