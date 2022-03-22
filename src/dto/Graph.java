package dto;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * Generally, a graph in computer science is used to represent complex,
 * non-linear relationships between objects. This class implements a
 * representation of a graph, consisting in nodes (vertex) connected by
 * edges (arc), where every link has an specific weight
 * @param <Vertex> Data type of the vertices that conforms the graph
 */
@SuppressWarnings("unused")
public class Graph<Vertex> {
	
	/**
	 * Internal representation of a graph using maps
	 * Every vertex of the graph has an associated map, where its edges
	 * to its adjacent vertices and the weight of these links are specified
	 */
	private Map<Vertex, TreeMap<Vertex, Double>> adjacentList;
	
	// Number of vertices of the graph
	private int numVertices;
	
	// Number of edges of the graph
	private int numEdges;
	
	
	/**
	 * Returns the list of adjacent vertices and their edges in the graph
	 * @return the adjacent vertices list of the graph
	 */
	public Map<Vertex, TreeMap<Vertex, Double>> getAdjacentList() {
		return this.adjacentList;
	}

	/**
	 * Sets a new list of adjacent vertices to the graph
	 * @param adjacentList the new list of adjacent vertices
	 */
	public void setAdjacentList(Map<Vertex, TreeMap<Vertex, Double>> adjacentList) {
		this.adjacentList = adjacentList;
	}
	
	/**
	 * Returns the number of vertices in the graph
	 * The number of keys in the map must be equal to the field numVertices
	 * @return the total number of vertices
	 */
	public int getNumVertices() {
		// Equal to return this.numVertices;
		return this.adjacentList.size();
	}
	
	/**
	 * Sets a new number of vertices in the graph, as a result 
	 * of adding or deleting a vertex.
	 * @param numVertices the new number of vertices contained in the graph
	 */
	public void setNumVertices(int numVertices) {
		this.numVertices = numVertices;
	}
	
	/**
	 * Returns the number of edges in the graph
	 * @return The sum of the sizes of the TreeMaps associated with each key (vertex), 
	 * that is, the number of adjacent vertices (value) that each vertex (key) has
	 */
	public int getNumEdges() {
		// Equal to return this.numEdges;
		int counter = 0;
		for(Entry<Vertex,TreeMap<Vertex,Double>> entry: this.adjacentList.entrySet()) {
			counter += entry.getValue().size();
		}
		return counter;
	}

	/**
	 * Sets a new number of edges in the graph, as a result 
	 * of adding or deleting an edge of a vertex.
	 * @param numEdges the new number of edges contained in the graph
	 */
	public void setNumEdges(int numEdges) {
		this.numEdges = numEdges;
	}

	/**
	 * Default constructor
	 */
	public Graph() {
		this.adjacentList = new TreeMap<Vertex, TreeMap<Vertex,Double>>();
		this.numVertices = 0;
		this.numEdges = 0;
	}

	/**
	 * Copy constructor
	 * @param graph the graph to copy
	 */
	public Graph(Graph<Vertex> graph) {
		this.adjacentList = new TreeMap<Vertex, TreeMap<Vertex,Double>>(graph.getAdjacentList());
		this.numVertices = graph.getNumVertices();
		this.numEdges = graph.getNumEdges();
	}

	/**
	 * Whether a graph contains the specified vertex or not
	 * @param vertex the vertex to evaluate whether it is present in the graph
	 * @return true if the graph contains the vertex, false otherwise
	 */
	public boolean hasVertex(Vertex vertex) {
		if(vertex == null)
			return false;
		return this.adjacentList.containsKey(vertex);
	}
	
	/**
	 * Returns whether the graph contains an edge between the specified vertices
	 * @param from vertex where the edge would come from
	 * @param to vertex where the edge would point to
	 * @return true if the specified edge exists in the graph, false otherwise
	 */
	public boolean hasEdge(Vertex from, Vertex to) {
		// Verify that the vertices specified are valid
		if(!this.hasVertex(from) || !this.hasVertex(to))
			return false;
		for(Entry<Vertex, Double> entry: this.adjacentList.get(from).entrySet()) {
			// If the second vertex is contained in the list of adjacent vertices of the first vertex
			if(entry.getKey().equals(to))
				return true;
		}
		return false;
	}

	/**
	 * Returns the in-degree of a vertex
	 * @param vertex the specified vertex 
	 * @return the number of edges that point to the vertex
	 */
	public int inDegree(Vertex vertex) {
		// Verify that the graph contains the specified vertex
		if(!this.adjacentList.containsKey(vertex))
			throw new RuntimeException();
		int counter = 0;
		for(Entry<Vertex, TreeMap<Vertex,Double>> entry: adjacentList.entrySet()) {
			// If the vertex is contained in the list of adjacent vertices of other vertex
			if(entry.getValue().containsKey(vertex))
				counter++;
		}
		return counter;
	}

	/**
	 * Returns the out-degree of a vertex
	 * @param vertex the specified vertex 
	 * @return the number of edges that come out of the vertex
	 */
	public int outDegree(Vertex vertex) {
		// Verify that the graph contains the specified vertex
		if(!this.adjacentList.containsKey(vertex))
			throw new RuntimeException();
		return this.adjacentList.get(vertex).size();
	}

	/**
	 * Adds a new vertex to the graph, as long as it was not already present
	 * @param vertex the vertex to insert in the graph
	 * @return true if the insertion was completed successfully, false otherwise
	 */
	public boolean addVertex(Vertex vertex){
		// No duplicates allowed
		if (this.hasVertex(vertex)) 
			return false;
		// Add the new vertex with an empty list of adjacent vertices
		adjacentList.put(vertex, new TreeMap<Vertex, Double>()); 
		// Update the number of vertices
		this.setNumVertices(this.getNumVertices() + 1);
		return true;
	}
	
	/**
	 * Adds an edge between the specified vertices
	 * @param from the vertex from where the edge will come out
	 * @param to the vertex to where the edge will point
	 * @param weight cost associated to that edge
	 * @return true if the insertion was completed successfully, false otherwise
	 */
	public boolean addEdge(Vertex from, Vertex to, Double weight){
		// Verify that the graph not contains already the specified edge
		if(this.hasEdge(from,to)) 
			return false;
		// Update the number of edges
		this.setNumEdges(this.getNumEdges() + 1);
		// Only inserts if the vertex is not already present
		this.addVertex(from);
		this.addVertex(to);
		// Insert the edge with its weight between the two vertices
		this.adjacentList.get(from).put(to, weight);
		return true;
	}
	
	/**
	 * Removes a vertex from the graph, as well as its edges
	 * @param vertex the vertex to delete
	 * @return true if the removal was completed successfully, false otherwise
	 */
	public boolean removeVertex(Vertex vertex) {
		// Verify that the graph contains the specified vertex
		if(!this.hasVertex(vertex))
			return false;
		// Update the number of vertices
		this.setNumVertices(this.getNumVertices() - 1);
		// Update the number of edges (2 steps)
		// Firstly, subtract the out-degree (edges that came from that vertex) 
		this.setNumEdges(this.getNumEdges() - this.outDegree(vertex));
		// Update the list of vertices
		this.adjacentList.remove(vertex);
		// Secondly, subtract every edge that pointed to the vertex
		for(Entry<Vertex, TreeMap<Vertex,Double>> e: adjacentList.entrySet()) {
			if(e.getValue().remove(vertex)!=null)
				this.setNumEdges(this.getNumEdges() - 1);
		}
		return true;
	}
	
	/**
	 * Removes and edge between the specified vertices
	 * @param from the vertex from where the edge will come out
	 * @param to the vertex to where the edge will point
	 * @return true if the removal was completed successfully, false otherwise
	 */
	public boolean removeEdge(Vertex from, Vertex to) {
		// Verify that the graph contains the specified vertices
		if(!this.hasVertex(from) || !this.hasVertex(to))
			return false;
		// Verify that the graph contains the specified edge
		if(!hasEdge(from,to))
			return false;
		// Update the number of edges
		this.setNumEdges(this.getNumEdges() - 1);
		for(Entry<Vertex, Double> entry: this.adjacentList.get(from).entrySet()) {
			if(entry.getKey().equals(to)) {
				// Remove from the edge to the second vertex from the list of adjacent vertices of the first vertex
				this.adjacentList.get(from).remove(entry.getKey());
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the weight or cost associated to an edge between two vertices
	 * @param from the vertex from where the edge will come out
	 * @param to the vertex to where the edge will point
	 * @return the value of the weight associated to that edge, 0 otherwise
	 */
	public Double weightEdge(Vertex from, Vertex to) {
		// Verify that the graph contains the specified vertices and edge
		if(!this.hasVertex(from) || !this.hasVertex(to) || !hasEdge(from,to))
			return 0.0;

		for(Entry<Vertex, Double> entry: this.adjacentList.get(from).entrySet()) {
			if(entry.getKey().equals(to))
				// Return the weight associated to that edge
				return entry.getValue();
		}
		return 0.0;
	}

	
	/**
	 * Return the vertices that are adjacent to the specified vertex
	 * @param the vertex to retrieve its adjacent vertices
	 * @return a collection of the adjacent vertices of the vertex
	 */
	public Collection<Vertex> adjacentTo(Vertex vertex){
		if(!hasVertex(vertex))
			return Collections.<Vertex>emptyList();
		// Return every vertex pointed to by an edge of the specified vertex
		return this.adjacentList.get(vertex).keySet();
	}

	/**
	 * Returns the whole set of vertices
	 * @return a collection of the vertices present in the graph
	 */
	public Collection<Vertex> getAllVertex(){
		return this.adjacentList.keySet();
	}

	
	/**
	 * Traverses over the graph using the Depth-first algorithm
	 * @param <Vertex> Data type of the vertexes that conforms the graph 
	 * @param graph the graph that is going to be traversed
	 */
	public static <Vertex> void DFT(Graph<Vertex> graph) {
		HashSet<Vertex> explored = new HashSet<Vertex>(graph.getNumVertices() * 2);
		for(Vertex vertex : graph.getAllVertex()) {
			if(!explored.contains(vertex))
				// Search in every non-explored vertex
				DFS(graph, vertex, explored);
		}
	}

	/**
	 * Searches over the graph using the Depth-first search algorithm
	 * @param <Vertex> Data type of the vertexes that conforms the graph 
	 * @param graph the graph that is going to be traversed
	 * @param vertex the vertex that we are iterating through
	 * @param explored whether the vertex has been already explored or not
	 */
	public static <Vertex> void DFS(Graph<Vertex> graph, Vertex vertex, Collection<Vertex> explored) {
		System.out.print(vertex + " "); 
		explored.add(vertex);
		for(Vertex adjacent : graph.adjacentTo(vertex)) {
			// Search in every non-explored adjacent vertex
			if(!explored.contains(adjacent))
				DFS(graph, adjacent, explored);
		}
	}

	/**
	 * Traverses over the graph using the Breadth-first algorithm
	 * @param <Vertex> Data type of the vertexes that conforms the graph 
	 * @param graph the graph that is going to be traversed
	 */
	public static <Vertex> void BFT(Graph<Vertex> graph) {
		HashSet<Vertex> explored = new HashSet<Vertex>(graph.getNumVertices() * 2);
		for(Vertex vertex : graph.getAllVertex()) {
			// Search in every non-explored vertex
			if(!explored.contains(vertex))
				BFS(graph, vertex, explored);
		}
	}

	/**
	 * Searches over the graph using the Breadth-first search algorithm
	 * @param <Vertex> Data type of the vertexes that conforms the graph 
	 * @param graph the graph that is going to be traversed
	 * @param vertex the vertex that we are iterating through
	 * @param explored whether the vertex has been already explored or not
	 */
	public static <Vertex> void BFS(Graph<Vertex> graph, Vertex vertex, Collection<Vertex> explored) {
		// According to the algorithm, a queue is needed
		LinkedList<Vertex> queue = new LinkedList<Vertex>();
		queue.addLast(vertex); 
		explored.add(vertex);
		while(!queue.isEmpty()){
			Vertex head = queue.removeFirst();
			System.out.print(head + " ");
			for(Vertex adjacent : graph.adjacentTo(head)) {
				// Search in every non-explored adjacent vertex
				if(!explored.contains(adjacent)){
					explored.add(adjacent);
					queue.addLast(adjacent);
				} 
			} 
		}
	}
	
}