
import java.util.ArrayList;

import java.util.Random;



/**
 *
 * @author Edgar Mesa
 */
public class TrieSTPhoneNumberClient {
    
    public static final Random ran = new Random();
    public static void main(String[] args)
    {
        int N = Integer.valueOf(args[0]);
        int[] PhoneNumber = new int[10];
        ArrayList<String> samePhones = new ArrayList<String>();
        int count = 1;
        
        TrieST PhoneTrie = new TrieST(10);
        
          for(int i = 0; i < N;i++)
        {
            String phone = "";

            
            while(PhoneTrie.contains(phone) || phone.length() == 0)
               
            {
                if(PhoneTrie.contains(phone) && phone.length() !=0)samePhones.add(phone);
               
                for(int n = 0; n < PhoneNumber.length;n++)
                {
                    PhoneNumber[n] = ran.nextInt(10);
                    
                        
                }
                
                for(int num : PhoneNumber)
                {
                        
                    phone += String.valueOf(num);

                }
                System.out.printf("%d. (%d%d%d) %d%d%d-%d%d%d%d\n",count,PhoneNumber[0],PhoneNumber[1],PhoneNumber[2],PhoneNumber[3],PhoneNumber[4],PhoneNumber[5],PhoneNumber[6],PhoneNumber[7],PhoneNumber[8],PhoneNumber[9]);

            
            }
            PhoneTrie.put(phone, i);
            count++;
            
        }
          int count2 = 1;
          System.out.println("PHONES THAT WERE THE SAME");
          if(samePhones.size() == 0)System.out.println("NONE");
          for(String samenum : samePhones)
                {
                        
                    System.out.printf("%d. (%s%s%s) %s%s%s-%s%s%s%s\n",count2,samenum.charAt(0),samenum.charAt(1),samenum.charAt(2),samenum.charAt(3),samenum.charAt(4),samenum.charAt(5),samenum.charAt(6),samenum.charAt(7),samenum.charAt(8),samenum.charAt(9));
                    count2++;
                }
          
     
        
        
        
        
    
    }
    
}
