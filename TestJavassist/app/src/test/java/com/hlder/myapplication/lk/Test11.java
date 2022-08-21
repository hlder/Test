package com.hlder.myapplication.lk;

import org.junit.Test;

public class Test11 {

    @Test
    public void test() {
        int[] arr = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println("========" + algorithm(arr));
    }

    public int algorithm(int[] height) {
        int maxItem = 0;
        int max = 0;
        for (int i = 0; i < (height.length - 1); i++) {
            int item = height[i];
            if (item > maxItem) {
                maxItem = item;
                for (int j = i + 1; j < height.length; j++) {
                    int item2 = height[j];
                    int minItem = Math.min(item, item2);
                    int rj = (j - i) * minItem;
                    if (rj > max) {
                        max = rj;
                    }
                }
            }
        }
        return max;
    }
}
