package com.king;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;

import java.util.Arrays;

public class AggregateTest {

    public static void main(String[] args) {
        //创建环境变量
        SparkConf conf = new SparkConf().setMaster("local").setAppName("wordCount");
        //创建环境变量实例
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<Integer> data = sc.parallelize( Arrays.asList(1,2,3,4,5,6,7,8,9,10));
        Integer aggregateRDD = data.aggregate(0, new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        }, new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });
        System.out.println(aggregateRDD.longValue());
    }
}
