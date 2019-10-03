import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD

case class User(name: String, age: Int)

val users = Array(
  (1L, User("Andy", 29)), 
  (2L, User("Bob", 32)), 
  (3L, User("Carol", 28)),
  (4L, User("Doris", 36)), 
  (5L, User("Emma", 33)), 
  (6L, User("Frank", 30))
)

val edges = Array(
  Edge(2L, 1L, 7),
  Edge(2L, 4L, 2),
  Edge(3L, 2L, 4),
  Edge(3L, 6L, 3),
  Edge(4L, 1L, 1),
  Edge(5L, 2L, 2),
  Edge(5L, 3L, 8),
  Edge(5L, 6L, 3)
)

val vertexRDD: RDD[(Long, User)] = sc.parallelize(users)
val edgeRDD: RDD[Edge[Int]] = sc.parallelize(edges)

val graph: Graph[User, Int] = Graph(vertexRDD, edgeRDD)

//////////////////
// Mostrar los usuarios mayores de 30 años
//////////////////

//////////////////
// Variante 1
//////////////////
graph.vertices.filter { case (id, user) => user.age > 30 }.collect.foreach {
  case (id, user) => println(s"${user.name} is ${user.age}")
}

//////////////////
// Variante 2
//////////////////
graph.vertices.filter(v => v._2.age > 30).collect.foreach(v => println(s"${v._2.name} is ${v._2.age}"))

//////////////////
// Variante 3 ordenando descendentemente por edad
//////////////////
for ((id,user) <- graph
  .vertices
  .filter { case (id,user) => user.age > 30 }
  .sortBy { case (id, user) => -user.age }
  .collect) 
{
    println(s"${user.name} is ${user.age}")
}

