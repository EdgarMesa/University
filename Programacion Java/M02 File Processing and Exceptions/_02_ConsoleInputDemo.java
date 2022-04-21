
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class _02_ConsoleInputDemo
{

  public static void main(String[] args) throws IOException
  {
    /* Console input is done by reading from System.in. However System.in is a
     * byte stream. This makes it cumbersome to work with. Thus, it is normally
     * wrapped in a BufferedReader to create a character stream.
     */
    BufferedReader reader = 
        new BufferedReader(new InputStreamReader(System.in));
    
    System.out.println("Enter characters with 'q' to denote the end.");
    
    char c;
    do
    {
      /* To read a character from a BufferedReader use read. Note that while 
       * both return an int a Reader's read gets a Unicode character  while an 
       * InputStream's read gets a byte.
       * 
       * Note that when you run this program no input is actually passed to the 
       * program until enter is pressed. This limits the utility of read for 
       * console input.
       * 
       * Also read can throw an IOException which must be caught or declared to
       * be thrown. In a robust program we would handle such exceptions.
       */
      c = (char) reader.read();
      System.out.println(c);
    } while (c != 'q');
  }
}
