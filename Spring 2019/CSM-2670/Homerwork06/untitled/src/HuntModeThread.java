/** class that extends thread in order to controls the huntmode*/

public class HuntModeThread extends Thread {

    private boolean running = true;
    private boolean mode;

    /** changes the mode */
    public HuntModeThread(boolean mode){this.mode = mode;}

    /**
     * @return the Thread´s mode
     */
    public Boolean GetMode(){return mode;}

    /** keeps running the thread*/
    public boolean keepRunning()
    {
        return running;
    }

    /** stops running the thread*/
    public void stopRunning()
    {
        running = false;
    }



    @Override
    public void run()
    {
        while (keepRunning())
        {
            //mode = is running
            this.mode = true;
            try
            {
                sleep(5000);
            }
            catch (InterruptedException ex)
            {
            }
            this.mode = false;
            // one time thread
            stopRunning();
        }

    }
}
