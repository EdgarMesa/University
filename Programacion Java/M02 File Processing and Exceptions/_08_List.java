
import java.io.File;
import java.io.FilenameFilter;
import java.io.PrintWriter;

public class _08_List
{
  
  public static void main(String[] args)
  {
    PrintWriter out = new PrintWriter(System.out, true);
    PrintWriter err = new PrintWriter(System.err, true);

    // Check that we have the right number of command line args.
    if (args.length != 1 && args.length != 2)
    {
      err.println("Usage: _08_List filename [filter]");
      System.exit(-1);   
    }
    
    // get the file.
    File f = new File(args[0]);
    
    if(!f.isDirectory())
    {
      err.println(f.getName() + " is not a directory.");
      return;
    }
    
    // Get a list of files in the directory
    String[] listing;
    if(args.length == 1)
    {
     listing = f.list(); 
    }
    else
    {
      assert args.length == 2;
      // A file name filter can be used to only list file that match a certain
      // pattern.
      final String ext = args[1];
      listing = f.list(new FilenameFilter() {

        public boolean accept(File dir, String name)
        {
          return name.endsWith(ext);
        }
      });
    }

    // print all of the file names
    for(String s : listing)
    {
      out.println(s);
    }
  }
}
