package com.javase.interview;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ddone
 * @date 2020/7/25-20:23
 */
//读取文件,并统计字符数量.
public class WordCountJava {
    public static void main(String[] args) throws IOException {
        String path = "input/word.txt";
        Map<String,Integer> resultMap= getWordCountFromFile(path);
        for (String key : resultMap.keySet()) {
            System.out.println(key+"->"+resultMap.get(key));
        }
    }

    private static Map<String, Integer> getWordCountFromFile(String path) throws IOException {
        HashMap<String, Integer> map = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String data;
        while ((data=br.readLine())!=null){
            String[] splits = data.split(" ");
            for (int i = 0; i < splits.length; i++) {
                if(map.containsKey(splits[i])){
                   map.put(splits[i],map.get(splits[i])+1);
                }else {
                    map.put(splits[i], 1);
                }
            }
        }
        return map;
    }
}
