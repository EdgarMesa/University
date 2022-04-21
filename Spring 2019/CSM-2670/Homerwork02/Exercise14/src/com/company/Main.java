package com.company;
import java.util.*;
/*
            int azar = ran.nextInt(2)*2-1;
            int azar2 = ran.nextInt(2);


*/
public class Main {

    public static void main(String[] args) {
        random();

    }
    public static void random(){
        // Two random numbers. One to go from index 0 and 1 and the other random number por the different moves
        //right(1,0), left(-1,0), up(0,1), down(0,-1)

        Random  ran = new Random();
        ArrayList<Integer> l = new ArrayList<>(2);
        // starting position different from (0,0)
        l.add(0);
        l.add(0);
        int first = ran.nextInt(2)*2-1;
        int second = ran.nextInt(2);
        l.set(second,l.get(second)+first);
        System.out.print(l);
        System.out.println();
        while(l.get(0) != 0 || l.get(1) != 0){
            int r1 = ran.nextInt(2)*2-1;
            int r2 = ran.nextInt(2);

            l.set(r2,l.get(r2)+r1);
            System.out.print(l);
            System.out.println();

        }

        System.out.print("You came back to the starting position");
    }

}
