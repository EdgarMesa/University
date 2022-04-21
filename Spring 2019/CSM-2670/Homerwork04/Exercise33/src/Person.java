import java.util.ArrayList;

public class Person {
    private String name;
    private String mother;
    private String father;
    private ArrayList<String> children;

    public Person(String name,String father,String mother,ArrayList<String> children){
    this.name = name;
    this.mother = mother;
    this.father = father;
    this.children = children;
    }

    /**Returns the information of a person(name,father,mother,children)
     * @return String with the information of each person
     */
    public String toString(){
        String childrens = getchildren();

        return String.format("\nMaternal Line:\n\t%-15s\n\t\t%-15s\n" +
                "Paternal Line:\n\t%-15s\n\t\t%-15s\n" +
                "Children:\n%-30s",name, mother,name,father,childrens);
    }
    public String getName(){return name;}

    public String getFather(){return father;}

    public String getMother(){return mother;}


    public String getchildren(){

        String childrens;
        //if the arraylist is empty, print none
        if(children.size()== 1)
        {
            childrens = children.get(0);
            return childrens;

        }
        //if non empty, creates a new String with the name of the children

        else
        {
            childrens = "\t" + children.get(0) + "\n";

            for(int index = 1; index < children.size();index++)
            {
                childrens += "\t" + children.get(index) + "\n";
            }
            return childrens;
        }
    }


}
