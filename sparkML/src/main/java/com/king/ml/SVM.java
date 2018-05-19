package com.king.ml;


import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;

import org.apache.spark.mllib.classification.SVMWithSGD;

import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;

public class SVM {
    static SparkSession sparkSession;
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("com.king").set("spark.sql.warehouse.dir", "d:/spark_wherehouse")
                .setMaster("local[3]");
        sparkSession = SparkSession
                .builder()
                .appName("Java Spark SQL basic example")
                .master("local[*]")
                .config(conf)
                .getOrCreate();
        // 加载和解析数据文件
        SparkContext sc= sparkSession.sparkContext();
       RDD<String> data = sc.textFile("D:\\Soft\\BigData\\spark-2.2.1-bin-hadoop2.7\\data\\mllib\\data\\sample_svm_data.txt", 0);

        RDD<String> parsedData = data.map((line)=>{
            String []parts = line.split(' ');
        LabeledPoint(parts(0).toDouble, parts.tail.map(x =>Double.parseDouble(x)));
            });
        // 设置迭代次数并进行进行训练

        int numIterations = 20

        val model = SVMWithSGD.train(parsedData, numIterations)
        // 统计分类错误的样本比例

        val labelAndPreds = parsedData.map { point =>

            val prediction = model.predict(point.features);

            (point.label, prediction)

        }
        val trainErr = labelAndPreds.filter(r => r._1 != r._2).count.toDouble / parsedData.count;
        System.out.println("Training Error = " + trainErr);
        // Create a dense vector (1.0, 0.0, 3.0).
        Vector dv = Vectors.dense(1.0, 0.0, 3.0);
// Create a sparse vector (1.0, 0.0, 3.0) by specifying its indices and values corresponding to nonzero entries.
        Vector sv = Vectors.sparse(3, new int[] {0, 2}, new double[] {1.0, 3.0});

        // Create a labeled point with a positive label and a dense feature vector.
        LabeledPoint pos = new LabeledPoint(1.0, Vectors.dense(1.0, 0.0, 3.0));

// Create a labeled point with a negative label and a sparse feature vector.
        LabeledPoint neg = new LabeledPoint(0.0, Vectors.sparse(3, new int[] {0, 2}, new double[] {1.0, 3.0}));

    }
}
