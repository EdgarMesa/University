package com.company;

public class Main {
    public static void main(String[] args) {
        piramidH(1);

    }

    public static void piramidH(int rows) {

        if(rows < 1 || rows > 80){
            System.out.print(" The rows must be > 1 and < 80");

        }else{
            System.out.print("* ");
            System.out.println();

            for(int i = 0; i < rows-2; i++){
                for(int p = 0; p < 1; p++){
                    System.out.print("* ");
                }

                for(int s = i; s > 0;s --) {
                    System.out.print("  ");
                }

                for(int r = 0; r < 1; r++) {
                    System.out.print("*");

                }

                System.out.println();
            }
            for(int line = 1; line < rows; line++){
                System.out.print(" *");
            }
        }
    }

}
