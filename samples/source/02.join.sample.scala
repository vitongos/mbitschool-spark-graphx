import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD

val actors: RDD[(VertexId, String)] = sc.parallelize(List(
	(1L, "George Clooney"),(2L, "Julia Stiles"),
	(3L, "Will Smith"), (4L, "Matt Damon"),
	(5L, "Salma Hayek"))
)

val movies: RDD[Edge[String]] = sc.parallelize(List(
	Edge(1L, 4L,"Ocean's Eleven"),
	Edge(2L, 4L, "Bourne Ultimatum"),
	Edge(3L, 5L, "Wild Wild West"),
	Edge(1L, 5L, "From Dusk Till Dawn"),
	Edge(3L, 4L, "The Legend of Bagger Vance"))
)
	
val movieGraph = Graph(actors, movies)

movieGraph.triplets.foreach(t => println(
	t.srcAttr + " y " + t.dstAttr + " aparecen en " + t.attr))
	
movieGraph.vertices.foreach(println)

case class Biography(birthname: String, hometown: String)

val bio: RDD[(VertexId, Biography)] = sc.parallelize(List(
	(2, Biography("Julia O'Hara Stiles", "NY City, NY, USA")),
	(3, Biography("Willard Christopher Smith Jr.", "Philadelphia, PA, USA")),
	(4, Biography("Matthew Paige Damon", "Boston, MA, USA")),
	(5, Biography("Salma Valgarma Hayek-Jimenez", "Coatzacoalcos, Veracruz, Mexico")),
	(6, Biography("José Antonio Domínguez Banderas", "Málaga, Andalucía, Spain")),
	(7, Biography("Paul William Walker IV", "Glendale, CA, USA"))
))

def appendHometown(id: VertexId, name: String, bio: Biography):
	String = name + ":"+ bio.hometown
	
val movieJoinedGraph = movieGraph.joinVertices(bio)(appendHometown)

movieJoinedGraph.vertices.foreach(println)

val movieOuterJoinedGraph = movieGraph.outerJoinVertices(bio)((_, name, bio) => (name,bio))
	
movieOuterJoinedGraph.vertices.foreach(println)

val movieOuterJoinedGraph = movieGraph.outerJoinVertices(bio)((_, name, bio) =>
	(name,bio.getOrElse(Biography("NA","NA"))))
	
movieOuterJoinedGraph.vertices.foreach(println)

case class Actor(name: String, birthname: String, hometown: String)

val movieOuterJoinedGraph = movieGraph.outerJoinVertices(bio)((_,name, b) => b match {
	case Some(bio) => Actor(name, bio.birthname, bio.hometown)
	case None => Actor(name, "", "")
})

movieOuterJoinedGraph.vertices.foreach(println)
