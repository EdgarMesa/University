
import java.util.ArrayList;
import java.util.List;


public class ThreadDemo06RaceToTheList
{
  private static final List<Integer> myList = new ArrayList<>();
  private static int i = 0;
  private static final int N = 10000;
  
  private static void up()
  {
    myList.add(i++);
  }
  
  private static void down()
  {
    while(myList.isEmpty())
    {
        try
        {
            Thread.sleep(1);
        }
        catch (InterruptedException ex)
        {
        }
    }
        myList.remove(0);
  }
  
  private static class WorkerThread extends Thread
  {

    @Override
    public void run()
    {
      for (int count = 0; count < N; count++)
      {
        up();
      }
    }
  }

  public static void main(String[] args)
  {
    // Create a new thread
    WorkerThread bob = new WorkerThread();
    bob.start();

    // Do some work of our own
    for (int count = 0; count < N; count++)
    {
      down();
    }
    
    try
    {
      bob.join();
    } catch (InterruptedException ex)
    {
      System.exit(1);
    }
    
    System.out.println(myList);
  }
}
