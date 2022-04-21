package com.company;

public class Main {

    public static void main(String[] args) {
	    series(6);
    }
    public static void series(int n){
        double sum = 1.0;
        double denomi = 2.0;
        for(int i = 1; i < n; i++){
            sum = sum+ (1/denomi);
            denomi++;
        }
        System.out.print(sum);

    }
}
