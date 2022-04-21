
import java.util.Scanner;

public class _04_ScannerDemo
{

  public static void main(String[] args)
  {
    /* The Scanner class breaks a sequence of characters into "tokens". The 
     * tokens are normally separated by whitespace. The sequence may be an 
     * input stream.
     * 
     * The Scanner class simplifies the process of reading data from streams in 
     * many ways. For example, tokens can be converted into data values and 
     * IOExceptions are captured for you.
     */
    Scanner scanner = new Scanner(System.in);

    System.out.println("Enter lines of text using \"quit\" to denote the end.");
    String line;
    do
    {
      line = scanner.nextLine();
      System.out.println(line);
    } while (!line.equalsIgnoreCase("quit"));

    System.out.println("Enter text using \"quit\" to denote the end.\n" +
        "Output will be printed a word at a time.");
    String word;
    do
    {
      /* Note that next skips any leading delimiters (in this case whitespace)
       * and stops reading when it hit another one. Thus only the words are 
       * printed no whitespace.
       */
      word = scanner.next(); 
      System.out.println(word);
    } while (!word.equalsIgnoreCase("quit"));

    System.out.println("Enter integers (any non-integer to quit)");
    
    /* Note that hasNextInt, as all of the hasNext methods, will block, i.e. 
     * wait for input if it can. The hasNext methods are good ways to test if 
     * the next token is of the type you expect before calling the 
     * corresponding next. They can also be used to see if stream can been 
     * exhausted (at the end of a file and the like).
     */
    while(scanner.hasNextInt())
    {
      /* All next methods skip any leading delimiters (in this case whitespace)
       * and stops reading when it hit another one.
       */
      int i = scanner.nextInt(); 
      System.out.println(i);
    }
  }
}
