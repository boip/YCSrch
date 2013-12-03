package com.yuchengtech.tail4solr.client.tail;

import java.util.UUID;   

public class UUIDGenerator {   
    public UUIDGenerator() {   
    }   
  
    public static String getUUID() {   
        UUID uuid = UUID.randomUUID();   
        String str = uuid.toString();   
     // 去掉"-"符号    
        String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);   
        return temp;   
    }   
    //获得指定数量的UUID    
    public static String[] getUUID(int number) {   
        if (number < 1) {   
            return null;   
        }   
        String[] ss = new String[number];   
        for (int i = 0; i < number; i++) {   
            ss[i] = getUUID();   
        }   
        return ss;   
    }   
  
    public static void main(String[] args) {
    	
       /* String[] ss = getUUID(100000);   
        for (int i = 0; i < ss.length; i++) {   
            System.out.println("ss["+i+"]====="+ss[i]);   
        }   */
        
        long total = 0;
        for (int j = 0; j < 10; j++) {

            long tick = System.currentTimeMillis();
            for (int i = 0; i < 100000L; i++) {
            	System.out.println(getUUID());
            	
            }
            long interval = System.currentTimeMillis() - tick;
            System.out.println(interval);
            total += interval;
        }

        System.out.println("AVG:" + total / 10);
    }
        
}  
