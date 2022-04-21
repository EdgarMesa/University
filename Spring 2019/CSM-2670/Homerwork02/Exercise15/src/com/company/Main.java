package com.company;

public class Main {

    public static void main(String[] args) {
        pascal(25,3);
    }

    public static void pascal(int n,int mod) {
        /* if the number digits are even add +1*/
        int gap = digits(max(n));

        if(gap % 2 == 0 || gap == 1){
            gap++; }
        gap++;

        /* outside loop for the number of rows*/

        for (int i = 0; i <= n; i++) {
            /*this take care of the right hand side ones of the pyramid*/
            int sum = 1;
            int coeff = 1 + i;
            /*spacing*/
            for (int spaces = n - i; spaces > 0; spaces--) {

                System.out.format("%-" + gap/2 + "s","  ");
            }
            /*calculate numbers*/
            for (int colum = 0; colum <= i; colum++) {
                /* if colum > 0....left hand side ones*/
                if (colum > 0) {
                    sum = sum * (coeff - colum) / colum;

                }

                System.out.format("%-" + gap +"d", sum%mod);
            }
            System.out.println();
        }
    }

        /*method to calculate the highest value of the pyramid*/
    public static int max(int n) {
        int[] arr = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            /*this take care of the right hand side ones of the pyramid*/
            int sum = 1;
            int coeff = 1 + i;

            for (int colum = 0; colum <= i; colum++) {
                /* if colum > 0....left hand side ones*/
                if (colum > 0) {
                    sum = (sum * (coeff - colum) / colum);
                    if (i == n) {
                        arr[colum] = sum; }
                }
            }
        }
        /* go through the array and find the greatest */
        int max =1;
        for(int t = 0; t < arr.length;t++){
            if(arr[t] > max){
                max = arr[t];
            }
        }
    return max;}

    /*count the digits of a number*/
    public static int digits(int num){
        int count = 0;
        if(num <10){
            count = 1;
            return count;
        }

        while(num != 0)
        { num /= 10;
        ++count;
        }

        return count;}
    }

