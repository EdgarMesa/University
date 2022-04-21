package com.company;

public class Main {

    public static void main(String[] args) {
	stairs(15);

    }
    public static void head(){ System.out.print("º  ******");}
    public static void body(){ System.out.print("/|\\ *");}
    public static void legs(){ System.out.print("/ \\ *");}


    public static void stairs(int n){
        /*error message*/
        if( n < 1 || n > 20 ){
            System.out.print(" The  value must be > 1 and < 20");
        return;}
            int coeff = n;
            /* first spaces*/
            for(int value = 0; value < n; value++){
                for(int first = 0; first < 5*coeff -3;first ++){
                    System.out.print(" ");
                } /* head and second spaces*/
                head();
                for(int f = 0; f < value * 5;f++){
                    System.out.print(" ");
                }/* wall*/
                System.out.print("*");
                System.out.println();

                /* first spaces*/

                for(int second = 0; second < coeff *5 -4;second++){
                    System.out.print(" ");
                } /*body and second spaces*/
                body();
                for(int s = 0; s < value * 5 + 5;s++){
                    System.out.print(" ");
                }/*wall*/
                System.out.print("*");
                System.out.println();

                /* first spaces*/

                for(int third = 0; third < coeff * 5 -4;third++){
                    System.out.print(" ");
                }/*legs and second spaces*/
                legs();
                for(int s = 0; s < value * 5 + 5;s++){
                    System.out.print(" ");
                }/*wall*/
                System.out.print("*");
                System.out.println();
                coeff--;

            }/*floor*/
        for(int line = 0; line < n* 5 + 7;line++){
            System.out.print("*");
        }




        }

    }

