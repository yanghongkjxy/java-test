import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.rdd.RDD;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Function;

public class WordCount {
    public static void  main(String args[]) {
        //创建环境变量
        SparkConf conf = new SparkConf().setMaster("local").setAppName("wordCount");
        //创建环境变量实例
       //JavaSparkContext sc = new JavaSparkContext(conf);
       SparkContext sc = new SparkContext(conf);

        //读取文件
        JavaRDD<String> data = sc.textFile("D:\\Soft\\BigData\\SampleData\\wordcount.txt",1).toJavaRDD();
        //word计数

       // data.flatMap(s->s.split(""))
       // data.map(s-> Arrays.stream(s.split(" ")).map(ss->1);.reduce(0,1)).collect();

        JavaRDD<String> flatMapRDD = data.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                String[] split = s.split("\\s+");
                return Arrays.asList(split).iterator();
            }
        });
        Function2<Integer, Integer, Integer> reduceSumFunc = (accum, n) -> (accum + n);
        JavaPairRDD<String, Integer> rddX =
                flatMapRDD.mapToPair(e -> new Tuple2<String,Integer>(e, 1));

        JavaPairRDD<String, Integer> rddY = rddX.reduceByKey(reduceSumFunc);

        //Print tuples
        for(Tuple2<String, Integer> element : rddY.collect()){
        System.out.println("("+element._1+", "+element._2+")");
    }
       //System.out.println( flatMapRDD.map(s->1).reduce(Integer::sum).longValue());
    }
}
