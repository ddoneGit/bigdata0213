package com.likou;

/**
 * @author ddone
 * @date 2020/7/13-12:45
 */

import java.util.Arrays;
import java.util.HashMap;

/**
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
 *
 *
 * 给定 nums = [2, 7, 11, 15], target = 9
 *
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 *
 */
public class TwoNumSum {
    public static void main(String[] args) {
        int[] nums = {2, 15, 11, 7};
        int target = 9;
        int[] result = twoSum1(nums, target);
        System.out.println(Arrays.toString(result));


    }
    public static int[] twoSum1(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i+1; j < nums.length; j++) {
                if(nums[j]==target-nums[i]){
                    return new int[]{i, j};
                }
            }
        }

        throw new IllegalArgumentException("Not Found Two Number Which Sum is "+target);
    }
    //采用HashMap 时间复杂度O(n) 空间复杂度O(n)
    public static int[] twoSum2(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int resultKey = target - nums[i];
            if(map.containsKey(resultKey)){
                return new int[]{map.get(resultKey),i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("Not Found Two Number Which Sum is "+target);
    }
}
