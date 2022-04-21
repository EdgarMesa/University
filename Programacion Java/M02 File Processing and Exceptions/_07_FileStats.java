
import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;

public class _07_FileStats
{
  
  public static void main(String[] args)
  {
    PrintWriter out = new PrintWriter(System.out, true);
    PrintWriter err = new PrintWriter(System.err, true);

    // Check that we have the right number of command line args.
    if (args.length != 1)
    {
      err.println("Usage: _07_FileStats filename");
      System.exit(-1);   
    }

    // The File class defines many methods for finding information about a file.
    File f = new File(args[0]);
    out.println("Filename: " + f.getName());
    out.println("Path: " + f.getPath());
    out.println("Absolute Path: " + f.getAbsolutePath());
    out.println("Parent: " + f.getParent());
    out.println("File " + (f.exists() ? "exists." : "does not exist."));
    out.println("File " + (f.canRead() ? "is " : "is not ") + "readable.");
    out.println("File " + (f.canWrite() ? "is " : "is not ") + "writeable.");
    out.println("File " + 
        (f.canExecute() ? "is " : "is not ") + "executable.");
    out.println("File " + 
        (f.isDirectory() ? "is " : "is not ") + "a directory.");
    out.println("Last Modified: " + 
        DateFormat.getInstance().format(new Date(f.lastModified())));
    out.println("Size: " + f.length() + " bytes");

    // The File class can also be used to do many other things such as delete
    // and rename files.
  }
}
