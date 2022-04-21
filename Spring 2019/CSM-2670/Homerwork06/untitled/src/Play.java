import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FilenameFilter;
/** class that given the name of the audio file, plays it*/
public class Play
{
    String path;
    Clip clip;

    /** Sets the desire path */
    public Play(String path){this.path = path;}

    /**

     * @return the clip object
     */
    public Clip GetClip(){return clip;}

    public void play()
    {
        // gets the file from the sounds dictionary
        File directory = new File("src\\sounds");
        File[] arr = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.contains(path) && name.endsWith("wav");
            }
        });
            try
            {
                File file = new File(arr[0].toString());
                clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(file));
                clip.start();
            }catch(Exception e){System.out.println(e.getMessage());}
    }
}
