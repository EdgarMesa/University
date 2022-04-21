public class ThreadDemo05
{
    
  private static class Echo
  {

    public void echoPrint(String message,
        int numberOfEchoes,
        int delay)
    {
      for (int i = 0; i < numberOfEchoes; i++)
      {
        System.out.print(message);
        System.out.print(" ");
        System.out.flush();
        try
        {
          Thread.sleep(delay);
        } catch (InterruptedException e)
        {
        }
      }
      System.out.println();
    }
  }

  static class EchoThread extends Thread
  {

    private static final String mon = "I am here to help"; 
    private final String message;
    private final Echo target;
    private final int delay, numberOfEchoes;
    
    public EchoThread(Echo target, String message,
        int numberOfEchoes, int delay)
    {
      super("EchoThread");
      this.target = target;
      this.message = message;
      this.delay = delay;
      this.numberOfEchoes = numberOfEchoes;
    }

    @Override
    public void run()
    {        
      synchronized(mon){
        target.echoPrint(message, numberOfEchoes, delay);
      }
    }
  }

  public static void main(String args[])
  {
    Echo target = new Echo();
    new EchoThread(new Echo(), "a", 10, 200).start();
    new EchoThread(new Echo(), "b", 10, 200).start();
    new EchoThread(new Echo(), "c", 10, 200).start();
    new EchoThread(new Echo(), "d", 10, 200).start();
  }
}