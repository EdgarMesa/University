package com.company;

public class Main {
/* Piramide empty in the inside */
    public static void main(String[] args) {
	piramidR(Integer.valueOf(args[0]));
    }

    public static void piramidR(int rows){

        if(rows < 1 || rows > 80){
            System.out.print(" The rows must be > 1 and < 80");
        return;}

            /* First row*/
        for (int s = rows; s > 1; s--) {

                System.out.print(" ");
            }
            System.out.print("*");
            System.out.println();
    /* # outside loop for the number of rows */

        for(int i = 2; i < rows ; i++) {
            /* First bunch of blanks and star*/
                    for (int s = rows - i; s > 0; s--) {

                        System.out.print(" ");
                    }
                    System.out.print("*");
            /* Second bunch of blanks and star*/

            for(int d = 0; d < i * 2 - 3;d++)
                        System.out.print(" ");
                    System.out.print("*");

                    System.out.println();
            }
    /* Last line*/

        for( int line = 0; line < rows *2 -1 ;line++)
            System.out.print("*");
    }}
