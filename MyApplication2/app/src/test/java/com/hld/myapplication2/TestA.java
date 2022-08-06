package com.hld.myapplication2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestA {

    @Test
    public void test() {
//        int inputNum = 8;
//        String line = "186 186 150 200 160 130 197 200";
        int inputNum = 124;
        String line = "16 103 132 23 211 75 155 82 32 48 79 183 13 91 51 172 109 102 189 121 12 120 116 133 79 120 116 208 47 110 65 187 69 143 140 173 203 35 184 49 245 50 179 63 204 34 218 11 205 100 90 19 145 203 203 215 72 108 58 198 95 116 125 235 156 133 220 236 125 29 235 170 130 165 155 54 127 128 204 62 59 226 233 245 46 3 14 108 37 94 52 97 159 190 143 67 24 204 39 222 245 233 11 80 166 39 224 12 38 13 85 21 47 25 180 219 140 201 11 42 110 209 77 136";
        String[] sgsStr = line.split(" ");
        int[] sgs = new int[sgsStr.length];
        for (int i = 0; i < sgsStr.length; i++) {
            sgs[i] = Integer.parseInt(sgsStr[i]);
        }
        int minChuLie = inputNum;
        for (int i = 1; i < (inputNum - 1); i++) {
            int tempCl = algorithm(i, sgs);
            if (minChuLie > tempCl) {
                minChuLie = tempCl;
            }
        }
        System.out.println(minChuLie);
    }

    private int algorithm(int index, int[] sgs) {
        LeftMinCount leftMinCount = new LeftMinCount();
        leftMinCount.leftCount(sgs, index - 1, index);
        leftMinCount.rightCount(sgs, index + 1, index);
        return leftMinCount.leftCount + leftMinCount.rightCount;
    }
}

class LeftMinCount {
    int leftCount = Integer.MAX_VALUE;
    int rightCount = Integer.MAX_VALUE;

    public void leftCount(int[] arr, int leftIndex, int centerIndex) {
        int centerShenGao = arr[centerIndex];
        int lastShenGao = arr[leftIndex];
        if (lastShenGao < centerShenGao && leftIndex > 0) {
            int chuLieCountLeft = centerIndex - leftIndex - 1;
            for (int i = leftIndex - 1; i >= 0; i--) {
                if (arr[i] >= lastShenGao) {
                    chuLieCountLeft++;
                } else {
                    lastShenGao = arr[i];
                }
            }

            if (chuLieCountLeft < leftCount) {
                leftCount = chuLieCountLeft;
            }
        }

        if (leftIndex > 0) {
            leftCount(arr, leftIndex - 1, centerIndex);
        }
    }

    public void rightCount(int[] arr, int rightIndex, int centerIndex) {
        int chuLieCountRight = rightIndex - centerIndex - 1;
        int centerShenGap = arr[centerIndex];
        int lastShenGao = arr[rightIndex];
        if (lastShenGao < centerShenGap && rightIndex < (arr.length - 1)) {
            for (int i = rightIndex + 1; i < arr.length; i++) {
                if (arr[i] >= lastShenGao) {
                    chuLieCountRight++;
                } else {
                    lastShenGao = arr[i];
                }
            }
            if (chuLieCountRight < rightCount) {
                rightCount = chuLieCountRight;
            }
        }

        if (rightIndex < (arr.length-1)) {
            rightCount(arr, rightIndex + 1, centerIndex);
        }
    }

}


//车船费 600
//交强险 900
//商业险 （车损险 2600，第三责任险 1500(保300w)，医保外责任险 几十块）
