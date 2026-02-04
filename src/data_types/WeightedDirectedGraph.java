package data_types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Provides the functionality of a weighted directed graph abstract data structure using an adjacency list implementation
 * */
public class WeightedDirectedGraph {

	//Architecture based on https://www.youtube.com/watch?v=Gq2-MCalkrw
	
	/*Class containing object details of an edge in the graph*/
	private class GraphEdge {
		
		int source;
		int destination;
		int weight;
		
		private GraphEdge (int source, int destination, int weight) {
			this.source = source;
			this.destination = destination;
			this.weight = weight;
		}
		
		public int getSource() {
			return this.source;
		}
		
		public int getDestination() {
			return this.destination;
		}
		
		public int getWeight() {
			return this.weight;
		}
		
		
	}
	
	private int vertices;
	
	//List containing all current directed graph edges
	private List<GraphEdge>[] adjacencyList;
	
	
	public WeightedDirectedGraph(int vertices) {
		this.vertices = vertices;
		this.adjacencyList = new ArrayList[vertices];
		
		for(int i = 0; i < vertices; i++) {
			this.adjacencyList[i] = new ArrayList<>();
		}
		
		
	}
	
	
	/**Adds a new edge to the graph
	 * 
	 * @param source The source vertex of the new edge
	 * @param destination The destination vertex of the new edge
	 * @param weight The weight of the new edge
	 * */
	public void addEdge (int source, int destination, int weight) {
		GraphEdge edge = new GraphEdge(source, destination, weight);
		
		this.adjacencyList[source].add(edge);
		
	}
	

	public List<GraphEdge>[] getVertices(){
		return this.adjacencyList;
	}
	
	
	/**Performs the breadth-first search algorithm to find a node and returns it, or -1 if it is inaccessible
	 * 
	 * @param s The source vertex
	 * @param v The destination vertex
	 * */
	public int BFS (int s, int v) {
		boolean[] seen = new boolean[this.vertices];
		
		PriorityQueue<Integer> q = new PriorityQueue<Integer>();
		
		q.push(s, 0);
		
		while (!q.isEmpty()){
			int n = q.pop();
			seen[n] = true;
			
			for(GraphEdge e: this.adjacencyList[n]) {
				int d = e.getDestination();
			
				if(d == v) {
					return v;
				}
				
				if(!seen[d]) {
					q.push(d, 0);
				}
				
			}
			
		}
		return -1;
		
		
	}
	
	
	/**Performs the depth-first search algorithm to find a node and returns it, or -1 if it is inaccessible
	 * 
	 * @param s The source vertex
	 * @param v The destination vertex
	 * */
	public int DFS (int s, int v) {
		boolean[] seen = new boolean[this.vertices];
		
		Stack<Integer> stack = new Stack<Integer>();
		
		stack.push(s);

		
		while (!stack.isEmpty()){
			int n = stack.pop();
			seen[n] = true;
			for(GraphEdge e: this.adjacencyList[n]) {
				int d = e.getDestination();
			
				if(d == v) {
					return v;
				}
				
				if(!seen[d]) {
					stack.push(d);
				}
				
			}
			
		}
		return -1;
	}
	
	
	/**Tries to find shortest distance past between s and v
	 * 
	 * @param s The source vertex
	 * @param v The destination vertex
	 * */
	public int Dijkstra (int s, int v) {
		
		boolean[] visited = new boolean[this.vertices];
		int[] distances = new int[this.vertices];
		
		Arrays.fill(distances, Integer.MAX_VALUE);
		distances[s] = 0;
		
		PriorityQueue<Integer> q = new PriorityQueue<Integer>();
		
		q.push(s, 0);

		while (!q.isEmpty()){
			
			int n = q.pop();
			visited[n] = true;
			
			for(GraphEdge e: this.adjacencyList[n]) {
				
				int d = e.getDestination();
				//Update distances to nodes
				distances[d] = Math.min(distances[n] + e.getWeight(), distances[d]);
				
				//Put unvisited nodes in the queue, lowest distance first
				if(!visited[d]) {
					q.push(d, -distances[d]);
				}
				
			}
			
		}
		
		return distances[v];
	}
	
	
}
