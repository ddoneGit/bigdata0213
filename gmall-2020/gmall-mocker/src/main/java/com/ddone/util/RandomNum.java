package com.ddone.util;

import java.util.Random;

/**
 * @author ddone
 * @date 2020/7/15-14:29
 */
public class RandomNum {
    public static int getRandInt(int fromNum,int toNum){
        return   fromNum+ new Random().nextInt(toNum-fromNum+1);
    }
}
