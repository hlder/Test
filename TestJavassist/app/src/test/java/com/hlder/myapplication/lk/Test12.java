package com.hlder.myapplication.lk;

import org.junit.Test;

public class Test12 {

    @Test
    public void test() {
        String str = intToRoman(1994);
        System.out.println("=======str:"+str);
    }

    public String intToRoman(int num) {
        String k = getK(num / 1000);
        String b = getB(num % 1000 / 100);
        String s = getS(num % 100 / 10);
        String g = getG(num % 10);
        return k+b+s+g;
    }

    private String getG(int g) {
        switch (g) {
            case 1:
                return "I";
            case 2:
                return "II";
            case 3:
                return "III";
            case 4:
                return "IV";
            case 5:
                return "V";
            case 6:
                return "VI";
            case 7:
                return "VII";
            case 8:
                return "VIII";
            case 9:
                return "IX";
            default:
                return "";
        }
    }

    private String getS(int s) {
        switch (s) {
            case 1:
                return "X";
            case 2:
                return "XX";
            case 3:
                return "XXX";
            case 4:
                return "XL";
            case 5:
                return "L";
            case 6:
                return "LX";
            case 7:
                return "LXX";
            case 8:
                return "LXXX";
            case 9:
                return "XC";
            default:
                return "";
        }
    }

    private String getB(int b) {
        switch (b) {
            case 1:
                return "C";
            case 2:
                return "CC";
            case 3:
                return "CCC";
            case 4:
                return "CD";
            case 5:
                return "D";
            case 6:
                return "DC";
            case 7:
                return "DCC";
            case 8:
                return "DCCC";
            case 9:
                return "CM";
            default:
                return "";
        }
    }

    private String getK(int k) {
        switch (k) {
            case 1:
                return "M";
            case 2:
                return "MM";
            case 3:
                return "MMM";
            default:
                return "";
        }
    }
}

// 1000 M
// 2000 MM
// 3000 MMM

// 100 C
// 200 CC
// 300 CCC
// 400 CD
// 500 D
// 600 DC
// 700 DCC
// 800 DCCC
// 900 CM

// 10 X
// 20 XX
// 30 XXX
// 40 XL
// 50 L
// 60 LX
// 70 LXX
// 80 LXXX
// 90 XC

// 1 I
// 2 II
// 3 III
// 4 IV
// 5 V
// 6 VI
// 7 VII
// 8 VIII
// 9 IX


