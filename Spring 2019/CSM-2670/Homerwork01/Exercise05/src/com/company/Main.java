package com.company;

public class Main {

    public static void main(String[] args) {
	tree(9, 4);
    }
    public static void tree(int seg, int height){

        if(seg < 1 || seg > 10 || height < 1 || height > 10){
            System.out.print("The values must be > 1 and  < 10");
        return;}
        int two = 0;
        int one = seg;
        int counter = 0;

        for(int i = 0; i < seg;i++){

            for(int h = 1; h <= height;h++){
                for(int s = (height -h+1)+ one; s > 1; s--){
                    System.out.print(" ");
                }
                counter = 0;
                for(int estre = 0; estre < (2*h-1)+two ;estre++,counter++){
                    System.out.print("*");
                }
                System.out.println(); }
            two += 2;
            one--;}
        for(int y = 0; y < 2;y++){
            for(int stick = 0; stick <= (counter / 2) ;stick++){
                System.out.print(" "); }
            System.out.print("*");
            System.out.println(); }
        for(int floor = 0; floor <= 2;floor++){
            System.out.print(" "); }
        for(int suelo = 0; suelo < counter - 4; suelo++){
            System.out.print("*");}


    }
}
