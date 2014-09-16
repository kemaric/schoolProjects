import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;


/*
 * STUDENTS:  THIS CLASS HAS BEEN WRITTEN FOR YOU.  DO NOT MODIFY IT!!!
 */


/**
 * This class represents an undirected weighted Graph.  The parameterized type, 
 * T, specifies the type of data element that will be stored in each vertex of 
 * the graph.
 * 
 * @author (C)2011, Fawzi Emad
 * @param <T> type of data element to be stored in each vertex
 */
public class Graph<T extends Comparable<T>> {

	private Set<Vertex> nodes = new HashSet<Vertex>();
	
	/**
	 * This static method creates an undirected weighted graph (with a String
	 * element at each vertex) by reading in the contents of a text file 
	 * containing edge information.
	 * 
	 * Each line in the text file represents one edge.  For example, if the 
	 * graph included an edge from vertex "P" to vertex "Q" of weight 15, then 
	 * the text file would include the line "P Q 15".
	 * 
	 * @param fileName file containing edge information
	 * @return the graph that has been constructed
	 */
	public static Graph<String> createGraph(String fileName) {
		Graph<String> graph = new Graph<String>();
		try {
			BufferedReader fin = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = fin.readLine()) != null) {
				String[] tokens = line.split("\\s");
				String from = tokens[0];
				String to = tokens[1];
				Integer weight = Integer.valueOf(tokens[2]);
				graph.addEdge(from, to, weight);
			}
		}catch (IOException e) {
			System.out.println("Failure reading from file.");
		} 
		return graph;
	}

	/**
	 * Return the set of ALL elements that are contained in the graph.
	 * 
	 * @return set of elements contained in this graph
	 */
	public Set<T> getAllElements() {
		Set<T> elements = new HashSet<T>();
		for (Vertex v : nodes) {
			elements.add(v.getData());
		}
		return elements;
	}

	/**
	 * Returns an Iterator over the elements that are adjacent to the parameter.
	 * You may assume that this iterator will move through the elements 
	 * according to their natural order.
	 * 
	 * @param element element whose neighbors are sought
	 * @return Iterator over neighbors of the parameter
	 */
	public Iterator<T> getNeighbors(T element) {
		Vertex curr = getVertex(element);
		if (curr == null) {
			String errorMsg = "You have called getNeighbors for an element " +
			                  "that is not contained in the graph.";
			throw new RuntimeException(errorMsg);
		}
		return curr.getNeighbors().iterator();
	}

	/**
	 * Returns the weight associated with a particular edge.
	 * @param from one vertex this edge connects
	 * @param to the other vertex this edge connects
	 * @return weight of the edge connecting the two parameters
	 */
	public int getWeight(T from, T to) {
		Vertex curr = getVertex(from);
		if (curr == null) {
			String errorMsg = "You have called getWeight for an element " +
			                  "that is not contained in the graph.";
			throw new RuntimeException(errorMsg);
		}
		return getVertex(from).getWeight(to);
	}

	private void addEdge(T from, T to, int weight) {
		Vertex fromVertex = getVertex(from);
		Vertex toVertex = getVertex(to);
		if (fromVertex == null) {
			fromVertex = new Vertex(from);
			nodes.add(fromVertex);
		}
		if (toVertex == null) {
			toVertex = new Vertex(to);
			nodes.add(toVertex);
		}
		fromVertex.addNeighbor(to, weight);
		toVertex.addNeighbor(from, weight);
	}

	private Vertex getVertex(T data) {
		for (Vertex node : nodes) {
			if (node.getData().equals(data)) {
				return node;
			}
		}
		return null;
	}

	public String toString() {
		StringBuffer retVal = new StringBuffer();
		Set<T> vals = getAllElements();
		for (T value : vals) {
			retVal.append(value.toString() + " is connected to: \n");
			Iterator<T> neighbors = getNeighbors(value);
			while (neighbors.hasNext()) {
				T neighbor = neighbors.next();
				retVal.append("    " + neighbor.toString() + "  " + 
						               getWeight(value, neighbor) + "\n");
			}
			retVal.append("\n");
		}
		return new String(retVal);
	}

	private class Vertex implements Comparable<Vertex>{

		private T data;
		private Map<T, Integer> neighbors;

		private Vertex(T data) {
			this.data = data;
			neighbors = new TreeMap<T, Integer>();
		}

		private void addNeighbor(T neighbor, Integer weight) {
			neighbors.put(neighbor, weight);
		}

		private T getData() {
			return data;
		}

		private Set<T> getNeighbors() {
			return neighbors.keySet();
		}

		private int getWeight(T vertex) {
			if (!neighbors.containsKey(vertex)) {
				String errorMsg = "You have requested the weight of an edge " +
				                  "that is not in the graph.";
				throw new RuntimeException(errorMsg);
			}
			return neighbors.get(vertex);
		}

		public int hashCode() {
			return data.hashCode();
		}

		public boolean equals(Object x) {
			if (x == null) {
				return false;
			}
			if (!(x instanceof Graph<?>.Vertex)) {
				return false;
			}	
			return data.equals(((Vertex)x).data);
		}

		public int compareTo(Vertex o) {
			return data.compareTo(o.data);
		}
	}
}
