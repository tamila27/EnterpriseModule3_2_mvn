package com.goit.gojavaonline.module3part2.hometask;

/**
 * Created by tamila on 6/18/16.
 */
public class Runner {

    public static void main(String[] args) {
        SquareSum squareSumCalculator = new SquareSumCalculator();
        System.out.println("result = "+squareSumCalculator.getSquareSum(new int[]{1, 2, 3, 10}, 3));
    }

}
