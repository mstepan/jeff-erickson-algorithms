package com.max.erickson.greedy;

public class MinimumNumberOfSteps {


    public static void main(String[] args){

        int x = 13;

        // TODO: we can double check if number os steps is minimal by doing BFS like.

        System.out.println(minSteps(x));

        System.out.println("min steps done...");
    }

    /** 16.
     *
     * Consider the following process. At all times you have a single positive
     * integer x, which is initially equal to 1. In each step, you can either
     * increment x or double x. Your goal is to produce a target value n. For
     * example, you can produce the integer 10 in four steps as follows:
     * 1 + 1 = 2
     * 2 * 2 = 4
     * 4 + 1 = 5
     * 5 * 2 = 10
     * Obviously you can produce any integer n using exactly n􀀀1 increments, but
     * for almost all values of n, this is horribly inefficient. Describe and analyze
     * an algorithm to compute the minimum number of steps required to produce
     * any given integer n.
     * time: O(lgN)
     * space: O(lgN)
     */
    static String minSteps(int value){
        assert value > 0 : "negative value detected";

        if( value == 1){
            return "1";
        }

        if( value == 2){
            return "1 + 1";
        }

        if( isOdd(value) ){
            return minSteps(value-1) + " + 1";
        }
        else {
            return minSteps(value/2) + " * 2";
        }
    }

    private static boolean isOdd(int value){
        return (value & 1) != 0;
    }
}
