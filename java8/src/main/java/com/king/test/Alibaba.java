package com.king.test;

import java.util.HashMap;
import java.util.Map;

public class Alibaba {

    public static void main(String age[])
    {
        String pattern1 = "abbaa", str1="北京 杭州 杭州 北京 北京";
        String pattern2 = "aabb", str2="北京 杭州 杭州 北京";
        String  pattern3 = "baaba", str3="北京 杭州 杭州 北京 杭州";
        Alibaba demo= new Alibaba();
        boolean result1=demo.validSourcePatter(str1,pattern1);
        System.out.println("result1:"+result1);
        boolean result2=demo.validSourcePatter(str2,pattern2);
        System.out.println("result2:"+result2);
        boolean result3=demo.validSourcePatter(str3,pattern3);
        System.out.println("result3:"+result3);
    }

    /**
     * 验证字符串是否与模式匹配
     * @param source
     * @param pattern
     * @return
     */
    public boolean validSourcePatter(String source,String pattern)
    {
        /**
         * 有一个字符串它的构成是词+空格的组合，如“北京 杭州 杭州 北京”， 要求输入一个匹配模式（简单的以字符来写）， 比如 aabb, 来判断该字符串是否符合该模式， 举个例子：
         1. pattern = "abba", str="北京 杭州 杭州 北京" 返回 true
         2. pattern = "aabb", str="北京 杭州 杭州 北京" 返回 false
         3. pattern = "baab", str="北京 杭州 杭州 北京" 返回 true
         */

        if(null==source || source.length()==0||null==pattern || pattern.length()==0)
        {
            return false;
        }
        String []sourceItems=source.split(" ");
        if(sourceItems.length!=pattern.length())
        {
            return false ;
        }

        Map<String,String> mapRelation=new HashMap<>();
        for(int i=0,len=sourceItems.length;i<len;i++)
        {
            String patternChar=pattern.substring(i,i+1);
            if(mapRelation.containsKey(patternChar))
            {
                if(mapRelation.get(patternChar).equals( sourceItems[i]))
                {
                    continue;
                }else
                {
                    return  false;
                }
            }else {
                mapRelation.put(patternChar, sourceItems[i]);
            }
        }
        return  true;

    }
}
