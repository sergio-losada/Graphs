package app;

import dto.Graph;

public class Main {

	public static void main(String[] args) {
		// Graph builder
		Graph<String> g = new Graph<String>();
		g.addVertex("A");
		g.addVertex("B");
		g.addVertex("C");
		g.addVertex("D");

		g.addEdge("A", "B", 5.0);
		g.addEdge("A", "C", 10.0);
		g.addEdge("C", "B", 7.0);
		g.addEdge("D", "A", 3.0);
		g.addEdge("D", "C", 8.0);

		// Graph traversal
		System.out.print("[Breadth-first search]: ");
		Graph.BFT(g);
		System.out.print("\n[Depth-first search]: ");
		Graph.DFT(g);

		// Method invokers
		System.out.println("\n\nInDegree vertex A: " + g.inDegree("A"));
		System.out.println("InDegree vertex B: " + g.inDegree("B"));
		System.out.println("InDegree vertex D: " + g.inDegree("D")+"\n");

		System.out.println("OutDegree vertex B: " + g.outDegree("B"));
		System.out.println("OutDegree vertex C: " + g.outDegree("C"));
		System.out.println("OutDegree vertex D: " + g.outDegree("D")+"\n");

		System.out.println("Is there and edge from A to B: " + g.hasEdge("A", "B"));
		System.out.println("Is there and edge from B to A: " + g.hasEdge("B", "A")+"\n");

		System.out.println("Edge A-C weight: " + g.weightEdge("A", "C"));
		System.out.println("Edge C-D weight: " + g.weightEdge("C", "D")+"\n");

		System.out.println("Number of vertices: " + g.getNumVertices());
		System.out.println("Number of edges: " + g.getNumEdges());
		System.out.println("List of vertices: " + g.getAllVertex());

		System.out.println("Adjacent vertices to A: " + g.adjacentTo("A"));
		System.out.println("Adjacent vertices to B: " + g.adjacentTo("B"));

		g.removeEdge("A", "B");
		System.out.println("\nAfter deleting A-B edge");
		System.out.println("Is there and edge from A to B: " + g.hasEdge("A", "B"));
		System.out.println("Edge A-B weight: " + g.weightEdge("A", "B"));

		g.removeVertex("C");
		System.out.println("\nAfter deleting vertex C");
		System.out.println("Number of vertices: " + g.getNumVertices());
		System.out.println("Number of edges: " + g.getNumEdges());
		System.out.println("List of vertices: " + g.getAllVertex());

		g.removeVertex("A");
		System.out.println("\nAfter deleting vertex A");
		System.out.println("Number of vertices: " + g.getNumVertices());
		System.out.println("Number of edges: " + g.getNumEdges());
		System.out.println("List of vertices: " + g.getAllVertex());
	}
	
}

