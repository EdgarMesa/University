
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class _03_ConsoleInputDemo
{

  public static void main(String[] args) throws IOException
  {
    BufferedReader reader = 
        new BufferedReader(new InputStreamReader(System.in));
    
    System.out.println("Enter lines of text using \"quit\" to denote the end.");
    
    String line;
    do
    {
      /* To read a line as a String from a BufferedReader use readLine. Once 
       * you have the line as a String you can parse and process it to convert
       * it other data types (as you would any String).
       */
      line = reader.readLine();
      System.out.println(line);
    } while (!line.equalsIgnoreCase("quit"));
  }
}
