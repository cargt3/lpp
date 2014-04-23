/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Test;

import java.util.Arrays;

/**
 *
 * @author Karol
 */
public class Test {
    public static void main(String args[]) throws Exception
    {
        int seq = 900;
        int n = 10 - String.valueOf(seq).length();;
        char[] spaces = new char[n];
        Arrays.fill(spaces, ' ');
        String str = Integer.toString(seq) + new String(spaces) + "";
        System.out.println(str);
        
        int m = Integer.parseInt("900");
        System.out.println(m);
    }
}
