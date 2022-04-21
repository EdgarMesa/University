
import java.io.*;

public class _06_CopyFile
{

  public static void main(String[] args) throws IOException
  {
    PrintWriter err = new PrintWriter(System.err, true);

    // Check that we have the right number of command line args.
    if(args.length != 2)
    {
      err.println("Usage: _06_CopyFile sourceFile outputFile");
      System.exit(-1); // Close the current JVM "returning" the given exit 
                       // code. Normally 0 is used to indicate success and any 
                       // thing else an error. If your exit codes have a  
                       // meaning it is good to document that.  
    }

    // Note FileStreams are byte streams
    FileInputStream fin = null;
    FileOutputStream fout = null;
    
    // Try to open the input file
    try
    {
      fin = new FileInputStream(args[0]);
    }
    catch(FileNotFoundException e)
    {
      err.println("Unable to open " + args[0]);
      System.exit(-2); 
    }

    // Try to open the output file
    try
    {
      fout = new FileOutputStream(args[1]);
    }
    catch(FileNotFoundException e)
    {
      err.println("Unable to open " + args[1]);
      System.exit(-3);
    }

    // Copy the file
    try
    {
      int i = fin.read();
      
      // read returns -1 at the end of a file
      while(i != -1)
      {
        fout.write(i);
        i = fin.read();
      }
    }
    catch(IOException e)
    {
      err.println("File I/O Error!");
    }
    finally
    {
      // While not closing a stream is not an error you may find that data is
      // not actually written to disk if a file is not closed.
      fout.close();
      fin.close();
    }
  }
}
