
/** A simple program that echos all of the command line arguments.
 */
public class _01_CommandLineDemo
{

  public static void main(String[] args)
  {
    for (String s : args)
    {
      /* Java program perform I/O via stream. A stream can either produces or 
       * consume information. A stream can be linked to files, a keyboard, the
       * network, and the like. Java defines two types of streams, byte streams 
       * and character streams. Byte streams as user for binary data and 
       * character streams handling text. Character streams use Unicode and 
       * can internationalized more easily than byte streams.
       *  
       * System.out and System.err are PrintStreams, a type of byte stream, 
       * that are connected to the 
       * "console" the program is running in (note that these streams can be 
       * independently redirected). PrintStreams have the ability to print 
       * representations of various data types, such as ints, Strings, and 
       * doubles. 
       * 
       * Unlike other output streams, a PrintStream never throws an 
       * IOException. Instead, an internal flag is set that can be tested via 
       * the checkError method. 
       * 
       * PrintStreams can be created so as to flush automatically. 
       * This means that the flush method is automatically invoked after a
       * byte array is written, one of the println methods is invoked, a
       * newline character, or byte ('\n') is written.
       *
       * Normally an OutputStream calls flush to indicate that, any bytes that
       * have been 
       * buffered should be immediately written to their intended destination.
       * However, if the destination is an abstraction provided by operating
       * system, such as a file, then flushing the stream guarantees only that
       * the bytes are passed to the operating system. It does not guarantee 
       * that they are actually written to a physical device such as a 
       * disk drive.
       * 
       * Note that all characters printed by a PrintStream are converted into
       * bytes using the platform's default character encoding. The PrintWriter
       * class should be used in situations that require writing characters
       * rather than bytes. 
       */
      System.out.println(s);
    }
  }
}