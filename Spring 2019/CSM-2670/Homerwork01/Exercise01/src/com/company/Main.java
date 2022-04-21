package com.company;

public class Main {

    public static void main(String[] args) {
        piramide(20);

    }

    public static void piramide(int rows){
        if( rows < 1 || rows > 80){
            System.out.print("The rows must be > 1 and < 80");

        }else {
            for(int i = 1; i <= rows; i++){
                for(int c = 1; c <= i; c++){

                    System.out.print("* ");
                }
                System.out.println();
            }
        }
    }
}
