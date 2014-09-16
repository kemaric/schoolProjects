import java.util.*;

/**
 * This class is a wrapper around a Graph object.  It can be used
 * to perform Dijsktra's algorithm, or a depth first search on the
 * graph.
 */
public class GraphSolver<T extends Comparable<T>> {

	private Graph<T> graph;


	/* STUDENTS:  Add further instance variables, as needed for implementing
	 * Dijkstra's algorithm.
	 */
	private int shortestDistance;
	private List<T> shortestPath;

	private Map<T,Integer> cost = new HashMap<T, Integer>();
	private Map<T,T> predecessor = new HashMap<T, T>();
	private T to, from;
	/**
	 * @param graph the graph that this GraphSolver wraps
	 */
	public GraphSolver(Graph<T> graph) {
		this.graph = graph;
	}

	/**
	 * Performs a Breadth First Search on the internal graph, using the
	 * parameter as the starting vertex.
	 *
	 * Note:  Whenever there is a choice, elements will be visited according
	 * to the natural ordering of the type T.
	 * 
	 * @param start the data element of the starting vertex
	 * @return a correctly ordered list of all nodes visited during 
	 * Breadth First Search
	 */
	public List<T> BFS(T start) {
		List<T> finalSearch = new ArrayList<T>();
		Set<T> items = graph.getAllElements();
		int indexOfIt = 0;
		T element = null;
		//List<T> starts = new ArrayList<T>();

		while(!items.isEmpty()){
			Iterator<T> it = graph.getNeighbors(start);
			T firstTempNeighbor = it.next();
			while(it.hasNext()){
				if(indexOfIt == 0){
					finalSearch.add(start);
					indexOfIt++;
					items.remove(start);
				}
				if(!finalSearch.contains(firstTempNeighbor) ){
					finalSearch.add(firstTempNeighbor);
					items.remove(finalSearch.get(indexOfIt));
					element = finalSearch.get(indexOfIt);
					indexOfIt++;
				}
				firstTempNeighbor = it.next();
				if(!it.hasNext() && !finalSearch.contains(firstTempNeighbor)){
					finalSearch.add(firstTempNeighbor);
					items.remove(finalSearch.get(indexOfIt));
					element = finalSearch.get(indexOfIt);
					indexOfIt++;
				}
			}
			int indexOfStart = finalSearch.indexOf(start);
			start = finalSearch.get(indexOfStart+1);


			//	if(!starts.contains(starts)){
			//	starts.add(start);
			//}s
			/*Iterator<T> neighbors = graph.getNeighbors(start);
			T tempNeighbor = neighbors.next();
			while(element != null && neighbors.hasNext()){
				if(element.compareTo(tempNeighbor) > 0 && !starts.contains(element)){
					element = tempNeighbor;
				}
				tempNeighbor = neighbors.next();
				start = element;
			}*/

		}
		return finalSearch;
	}

	/**
	 * Performs Dijsktra's algorithm on the graph.  The results are stored
	 * internally and can be accessed afterward by calls to the 
	 * getShortestDistance and getShortestPath methods.
	 * 
	 * @param start data element of starting vertex
	 */
	public void doDijkstra(T start) {
		Set<T> notDoneThat = graph.getAllElements();
		Set<T> doneThat = new HashSet<T>();
		for(T node: graph.getAllElements()){
			if(node.equals(start)){
				cost.put(node, 0);
				predecessor.put(node, node);
			}else{
				cost.put(node, Integer.MAX_VALUE);
				predecessor.put(node, null);
			}
		}
		while(!notDoneThat.isEmpty() && !doneThat.containsAll(graph.getAllElements())){
			Iterator<T> it = graph.getNeighbors(start);
			T firstTempNeighbor = it.next();
			while(it.hasNext()){
				int weightOfTravel = graph.getWeight(start, firstTempNeighbor)+ cost.get(start);
				if(weightOfTravel < cost.get(firstTempNeighbor)){
					cost.put(firstTempNeighbor,weightOfTravel);
					predecessor.put(firstTempNeighbor, start);
				}
				firstTempNeighbor = it.next();
				if(!it.hasNext()){
					weightOfTravel = graph.getWeight(start, firstTempNeighbor)+ cost.get(start);
					if(weightOfTravel < cost.get(firstTempNeighbor)){
						cost.put(firstTempNeighbor,weightOfTravel);
						predecessor.put(firstTempNeighbor, start);
					}
				}
			}
			doneThat.add(start);
			notDoneThat.remove(start);
			int minimum = Integer.MAX_VALUE;
			for(T element: notDoneThat){
				if(cost.get(element) < minimum){
					minimum = cost.get(element);
					start = element;
				}
			}

		}
	}

	/**
	 * IMPORTANT:  This method may only be called after the doDijkstra method 
	 * has already been invoked.
	 * 
	 * This method will return the shortest distance from the starting node 
	 * (specified in the call to the doDijkstra method) to the parameter.
	 * 
	 * @param destination the destination for computing shortest distance
	 * @return The shortest distance from the starting vertex
	 * (specified in the earlier call to doDijsktra) to the parameter.
	 */
	public int getShortestDistance(T destination) {
		int distance = 0;
		//T temporaryElem = predecessor.get(destination);
		//while(!destination.equals(predecessor.get(destination))){

		distance += (cost.get(destination));

		//	destination = predecessor.get(destination);
		//	}

		/*int distance = Integer.MAX_VALUE;
		for(T element: graph.getAllElements()){
			if(element != destination){
				this.doDijkstra(element);
				int weight = graph.getWeight(element, destination);
				T temmporary = destination;
				while()
				if(cost.get(predecessor.) < distance){
					distance = cost.get(graph.getWeight(element, destination));
				}
			}else{
				distance = 0;
			}
		}
		 */
		return distance;
	}

	/**
	 * IMPORTANT:  This method may only be called after the doDijkstra method 
	 * has already been invoked.
	 * 
	 * This method will compute the shortest Path from the starting node 
	 * (specified in the call to the doDijkstra method) to the parameter.
	 * 
	 * @param destination the destination of the path to be found
	 * @return A list of data elements representing a path from the starting 
	 * vertex (specified in the earlier call to doDijsktra) to the parameter.  
	 * The first element of the list will be the starting element; the last 
	 * element of the list will be the parameter.
	 */
	public List<T> getShortestPath(T destination) {
		List<T> path = new ArrayList<T>();
		int distance = this.getShortestDistance(destination);
		List<T> temp = new ArrayList<T>();
		while(distance >= 0 && !temp.contains(destination)){
			temp.add(destination);
			if(distance > 0){
				distance -= graph.getWeight(predecessor.get(destination), destination);
			}
			destination = predecessor.get(destination);
		}
		for(int index = temp.size()-1; index >= 0; index--){
			path.add(temp.get(index));
		}
		return path;
	}
}
