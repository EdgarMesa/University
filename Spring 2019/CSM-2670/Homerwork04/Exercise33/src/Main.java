import java.util.ArrayList;
import java.util.*;
import java.io.*;
import java.util.Map;

public class Main {

    public static void main(String[] args)throws FileNotFoundException {
        File file = new File("GamesOfThrones.txt");
        Map<String,Person> persons;

        PrintStream out = new PrintStream(System.out,true);
        PrintStream err = new PrintStream(System.err, true);

        Scanner console = new Scanner(System.in);
        out.println("Make sure to type the right names with the right capital letters.\n");
        out.println("HERE IS THE LIST OF ALL THE NAMES IN THE DATABASE");
        out.println("_________________________________________________");

        String yesno = "yes";
        String information = ReadFile(file);
        persons = MapStringPerson(information,out,err);
        out.println();

            while(!yesno.equalsIgnoreCase("no"))
        {
           yesno = Display(console,out,err,yesno,persons);
        }

        }


    /** Displays all the references of the persons and keeps going until the users types "no"
     * @param console Scanner to prompt
     * @param out PrintStream
     * @param err PrintStream
     * @param yesno answer to keep going or not
     * @param persons
     * @return map between their names and their respective person object
     */
    static public String Display(Scanner console,PrintStream out,PrintStream err,String yesno,Map persons){
        try
        {
            out.print("Name of the person? >> ");
            String namepicked = console.useDelimiter(System.lineSeparator()).nextLine();
            out.println(persons.get(namepicked).toString());
            out.print("\nWould you like to type the name of another person?\n" +
                    "Type \"yes\" or \"no\": >> ");
            yesno = console.useDelimiter(System.lineSeparator()).nextLine();
            while(!yesno.equalsIgnoreCase("yes") && !yesno.equalsIgnoreCase("no" ))
            {
                out.println("That is not a yes or a not. ");
                yesno = console.useDelimiter(System.lineSeparator()).nextLine();
            }
            return yesno;

        }catch (NullPointerException ex)
        {
            err.println("The name do not match with any of the person´s name\n" +
                    "in the database.Try again.\n");
        }

        return "yes";
    }

        /** Make sure the files exist and is readable and returns the data that will be used
         * @param file the database
         * @retur the data to be used
         * @throws FileNotFoundException
         */
    static public String ReadFile(File file)throws FileNotFoundException{

        if(!file.exists() || !file.canRead()){throw new FileNotFoundException("The File either does not exist or can not be read");}
        Scanner input = new Scanner(new FileReader(file));
        input.useDelimiter("%");
        // skipping the intro
        input.next();
        String information = input.next();
        return information;
    }

    /** slip the data in line and uses other method to get the different kind of information for each
     * person. Returns a map between their names and their respective person object
     * @param information = the data
     * @param out = PrintStream
     * @param err = PrintStream
     * @return map between their names and their respective person object
     */
    static public Map<String,Person> MapStringPerson(String information,PrintStream out,PrintStream err){
        String[] NameFatherMother;
        ArrayList<String> Children;
        Map<String,Person> persons = new HashMap<>();

        String line;
        Scanner lines = new Scanner(information);
        lines.useDelimiter("\n");
        //skip first new line
        lines.next();

        while(lines.hasNext())
        {
            line = lines.next();
            //array with name,father and mother
            NameFatherMother = NameAndReferences(line);

            //Displays the names of each peron
            out.println(NameFatherMother[0]);

            //ArrayList with the children´s name
            Children = children(line);

            //person object
            persons.put(NameFatherMother[0],new Person(NameFatherMother[0],NameFatherMother[1],NameFatherMother[2],Children));
        }
        return persons;
    }

    /** goes through each line and gets the name,the father and mother for eac person
     * @param line = String with the information of each person
     * @return an array where [0] = name, [1] father and [2] mother
     */
    static public String[] NameAndReferences(String line){
        String [] NameFatherMother= new String[3];
        Scanner division = new Scanner(line);
        division.useDelimiter(":");
        NameFatherMother[0] = division.next();
        NameFatherMother[1] = division.next();
        NameFatherMother[2] = division.next();
        return NameFatherMother;
    }

    /** Gets the children information of each person
     * @param line the line of information for each person
     * @return an Arraylist with the name of the children
     */
    static public ArrayList<String> children(String line){
        Scanner listkids = new Scanner(line);
        ArrayList<String> children = new ArrayList<>();
        listkids.useDelimiter(":");
        //skip name
        listkids.next();
        //skip father
        listkids.next();
        // skip mother
        listkids.next();
        String kids = listkids.next();
        int l = kids.length()-1;
        if(kids.contains("\r"))
        {
            kids = kids.substring(0,l);
        }

        if(!kids.contains(","))
        {
            children.add(kids);
            return children;
        }

        Scanner commas = new Scanner(kids);
        commas.useDelimiter(",");
        while(commas.hasNext())
        {
            children.add(commas.next());
        }
        return children;



    }


}



