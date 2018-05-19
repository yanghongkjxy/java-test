import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.mllib.classification.SVMWithSGD
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.sql.SparkSession

class SVM {
  //  val sparkSession
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("com.king").set("spark.sql.warehouse.dir", "d:/spark_wherehouse").set("spark.executor.memory", "3g")
      .setMaster("local[3]");
    val sparkSession = SparkSession.builder()
      .appName("Java Spark SQL basic example")
      .master("local[*]")
      .config(conf)
      .getOrCreate();
    // 加载和解析数据文件
    val sc = new SparkContext(conf);

    val data = sc.textFile("mllib/data/sample_svm_data.txt", 0);

    val parsedData = data.map { line =>

      val parts = line.split(' ')
    val v=  parts.tail.map(x => x.toDouble).toArray
    // LabeledPoint(parts(0).toDouble, v)

    }
    // 设置迭代次数并进行进行训练

//    val numIterations = 20
//
//    val model = SVMWithSGD.train(parsedData, numIterations)
//    // 统计分类错误的样本比例
//
//    val labelAndPreds = parsedData.map { point =>
//
//      val prediction = model.predict(point.features)
//
//      (point.label, prediction)

    //}
   // val trainErr = labelAndPreds.filter(r => r._1 != r._2).count.toDouble / parsedData.count;
   // System.out.println("Training Error = " + trainErr);


  }
}
