package com.king;

import com.king.entity.Person;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.apache.spark.sql.functions.col;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SparkTest {
    static SparkSession sparkSession;
    static Dataset<Row> df = null;
    private static String path = "e:/Soft/people.json";

    @BeforeAll
    public static void setUP() {

        SparkConf conf = new SparkConf().setAppName("com.king").set("spark.sql.warehouse.dir", "d:/spark_wherehouse")
                .setMaster("local[3]");
        sparkSession = SparkSession
                .builder()
                .appName("Java Spark SQL basic example")
                .master("local[*]")
                .config(conf)
                .getOrCreate();
        df = ( Dataset<Row> )sparkSession.read().json("e:/Soft/people.json");

        // 显示DataFrame的内容
        // df.show();
        // 以树形格式打印schema
        // df.printSchema();
        // 选择“name”列
        //  df.select("name").show();

        //查看多列并作计算 类似于MySQL: select name ,age+1 from people
        // df.select(col("name"), col("age").plus(1)).show();
        // 选择年龄“age”大于21的people
        // df.filter(col("age").gt(21)).show();
        // 根据年龄“age”分组并计数
        //df.groupBy("age").count().show();
    }

    /**
     * SparkSession的sql方法能够以编程方式运行SQL查询并返回Dataset<Row>
     */
    @Test
    public void testSql() {
        // 注册DataFrame为一个SQL的临时视图
        df.createOrReplaceTempView("people");

        Dataset<Row> sqlDF = ( Dataset<Row> )sparkSession.sql("SELECT * FROM people");
        sqlDF.show();
    }

    @Test
    public void testDataSets() {
        // 创建一个Person对象
        Person person = new Person();
        person.setName("Andy");
        person.setAge(32);

        // 创建Java beans的Encoders
        Encoder<Person> personEncoder = Encoders.bean(Person.class);
        Dataset<Person> javaBeanDS = sparkSession.createDataset(
                Collections.singletonList(person),
                personEncoder
        );
        javaBeanDS.show();
        // +---+----+
        // |age|name|
        // +---+----+
        // | 32|Andy|
        // +---+----+

        // Encoders类提供了常见类型的Encoders
        Encoder<Integer> integerEncoder = Encoders.INT();
        Dataset<Integer> primitiveDS = sparkSession.createDataset(Arrays.asList(1, 2, 3), integerEncoder);
        Dataset<Integer> transformedDS = primitiveDS.map((MapFunction<Integer, Integer>) value -> value + 1, integerEncoder);
        transformedDS.collect();// 返回 [2, 3, 4]
        transformedDS.show();
        //).forEach(i->{System.out.print("result:"+i);});;


// 通过指定Encoder转换DataFrames为Dataset，基于名字匹配
        String path = "e:/Soft/people.json";
        Dataset<Person> peopleDS = sparkSession.read().json(path).as(personEncoder);
        peopleDS.show();
    }

    /**
     * Spark SQL的临时视图是当前session有效的，也就是视图会与创建该视图的session终止而失效。如果需要一个跨session而且一直有效的直到Spark应用终止才失效的临时视图，可以使用全局临时视图。全局临时视图是与系统保留数据库global_temp绑定，所以使用的时候必须使用该名字去引用，例如，SELECT * FROM global_temp.view1。
     */
    @Test
    public void testTempView() throws AnalysisException {
        // 注册DataFrame为一个全局的SQL临时视图
        df.createGlobalTempView("people");

        // 全局临时视图与系统保留数据库global_temp绑定
        sparkSession.sql("SELECT * FROM global_temp.people").show();
        // +----+-------+
        // | age|   name|
        // +----+-------+
        // |null|Michael|
        // |  30|   Andy|
        // |  19| Justin|
        // +----+-------+

        // 全局临时视图是跨session的
        sparkSession.newSession().sql("SELECT * FROM global_temp.people").show();
    }

    /**
     * Spark SQL支持将JavaBean的RDD自动转换成DataFrame。目前的Spark SQL版本不支持包含Map field(s)的JavaBeans，但嵌套的JavaBeans和List或者Array fields是支持的。可以通过创建一个实现Serializable接口和包含所有fields的getters和setters方法的类来创建一个JavaBean。
     */
    @Test
    public void javaRDDTran() {
        // 通过一个文本文件创建Person对象的RDD
        JavaRDD<Person> peopleRDD = sparkSession.read()
                // .textFile("examples/src/main/resources/people.json")
                .json("e:/Soft/people.json")
                .javaRDD()
                .map(line -> {
                    //String[] parts = line.split(",");
                    Person person = new Person();
                    person.setName(line.getString(1));
                    person.setAge((int) line.getLong(0));
                    return person;
                });

        // 对JavaBeans的RDD指定schema得到DataFrame
        Dataset<Row> peopleDF = sparkSession.createDataFrame(peopleRDD, Person.class);
        // 注册该DataFrame为临时视图
        peopleDF.createOrReplaceTempView("people");

        // 执行SQL语句
        Dataset<Row> teenagersDF = sparkSession.sql("SELECT name FROM people WHERE age BETWEEN 13 AND 19");

        // The columns of a row in the result can be accessed by field index
        // 结果中的列可以通过属性的下标获取
        Encoder<String> stringEncoder = Encoders.STRING();
//        Dataset<String> teenagerNamesByIndexDF = teenagersDF.map(
//                (MapFunction<Row, String>) row -> "Name: " + row.getString(0),
//                stringEncoder);
//        teenagerNamesByIndexDF.show();
        // +------------+
        // |       value|
        // +------------+
        // |Name: Justin|
        // +------------+

        // 或者通过属性的名字获取
        Dataset<String> teenagerNamesByFieldDF = teenagersDF.map(
                (MapFunction<Row, String>) row -> "Name1: " + row.getAs("name"),
                stringEncoder);
        teenagerNamesByFieldDF.show();
    }

    /**
     * 通过编程接口指定Schema
     * 当JavaBean不能被事先定义的时候，通过编程创建Dataset<Row>需要三个步骤：
     * <p>
     * 通过原来的RDD创建一个Rows格式的RDD
     * 创建以StructType表现的schema，该StructType与步骤1创建的Rows结构RDD相匹配
     * 通过SparkSession的createDataFrame方法对Rows格式的RDD指定schema
     */
    @Test
    public void testCustomSchema() {
        // 创建一个RDD
        JavaRDD<String> peopleRDD = sparkSession.sparkContext()
                .textFile("e:/Soft/people.txt", 1)
                .toJavaRDD();

       // 使用string定义schema
        String schemaString = "age name";

        // 基于用字符串定义的schema生成StructType
        List<StructField> fields = new ArrayList<>();
        for (String fieldName : schemaString.split(" ")) {
            StructField field = DataTypes.createStructField(fieldName, DataTypes.StringType, true);
            fields.add(field);
        }
        StructType schema = DataTypes.createStructType(fields);

        // 把RDD (people)转换为Rows
        JavaRDD<Row> rowRDD = peopleRDD.map(record -> {
            String[] attributes = record.split(",");
            return RowFactory.create(attributes[0], attributes[1].trim());
        });

        // 对RDD应用schema
        Dataset<Row> peopleDataFrame = sparkSession.createDataFrame(rowRDD, schema);

        // 使用DataFrame创建临时视图
        peopleDataFrame.createOrReplaceTempView("people");

        // 运行SQL查询
        Dataset<Row> results = sparkSession.sql("SELECT name FROM people");

// SQL查询的结果是DataFrames类型，支持所有一般的RDD操作
// 结果的列可以通过属性的下标或者名字获取
        Dataset<String> namesDS = results.map((MapFunction<Row, String>) row -> "Name: " + row.getString(0), Encoders.STRING());
        namesDS.show();
    }
}
