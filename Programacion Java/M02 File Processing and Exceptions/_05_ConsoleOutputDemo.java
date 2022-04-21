
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Date;

public class _05_ConsoleOutputDemo
{

  public static void main(String[] args) throws IOException
  {
    /* While using System.out to write to the console is not deprecated it 
     * use is discouraged for "real-world" programs. This is because it is a 
     * byte stream which can be hard to internationalize.
     * 
     * Instead use the character stream PrintWriter. In the following 
     * constructor the boolean controls if auto-flushing is on. 
     */
     PrintWriter writer = new PrintWriter(System.out, true);
     
     String s = "I love Java!";
     double d = 3.14;
     int i = 13;
     Date now = new Date();
     
     /* The print and println methods of PrintWriter support all of the 
      * primitive types and Object. In the case of a non-primitive type the
      * object's toString method is called and the result is printed. 
      */
     writer.println(s);
     writer.println(d);
     writer.println(i);
     writer.println(now);
  }
}
