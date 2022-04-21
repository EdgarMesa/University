public class ArgDemo {

    public static void main(String[] args) {
        
        if(args.length == 0)
        {
            System.out.println("No size given!");
            return;
        }
        
        int size = Integer.valueOf(args[0]);
        
        for(int i = 0; i < size; i++)
        {
            System.out.println("*");
        }
    }
}
