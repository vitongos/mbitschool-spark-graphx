import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD

case class Node(fname: String, 
  lname: String, 
  gender: String, 
  city: String, 
  country: String, 
  birthdate: String)

val vertexRDD: RDD[(Long, Node)]  =
  sc.textFile("file:///opt/spark-data/nodes.csv").filter(!_.startsWith("id")).filter(!_.equals("")).map { 
    line =>
      val fields = line.split(",")
      (
        fields(0).toLong, 
        Node(
          fields(1), 
          fields(2), 
          fields(3), 
          fields(4), 
          fields(5), 
          fields(6)
        )
      )
  }

println(vertexRDD.count().toString)

val edgeRDD: RDD[Edge[String]] =
  sc.textFile("file:///opt/spark-data/relationships.csv").filter(!_.startsWith("id")).filter(!_.equals("")).map { 
    line =>
      val fields = line.split(",")
      Edge(fields(0).toLong, fields(1).toLong, fields(2))
  }

println(edgeRDD.count().toString)

val graph: Graph[Node, String] = Graph(vertexRDD, edgeRDD)

val ranks = graph.pageRank(0.0001)

val users = vertexRDD.map {
  case (id, user) => (id, user.fname + " " + user.lname)
}

val ranksByName = users.join(ranks.vertices).map {
  case (id, (username, rank)) => (username, rank)
}

println(ranksByName.sortBy {-_._2}.collect().mkString("\n"))
