import junit.framework.TestCase;
import java.util.List;

public class PublicTests extends TestCase {
	
	public void testEasyDFS() {
		Graph<String> g = Graph.createGraph("publicTestGraph.txt");
		GraphSolver<String> solver = new GraphSolver<String>(g);
		List<String> result = solver.BFS("A");
		String[] correctList = {"A", "B", "C"};
		assertTrue(matches(result, correctList));
		
		
		Graph<String> complicated = Graph.createGraph("complicatedGraph.txt");
		GraphSolver<String> s = new GraphSolver<String>(complicated);
		List<String> cS = s.BFS("A");
		String[] cCS = {"A", "B", "C", "D", "E", "F"};
		assertTrue(matches(cS, cCS));
		
		
	}
	
	public void testEasyDijskstraDistances() {
		Graph<String> g = Graph.createGraph("publicTestGraph.txt");
		GraphSolver<String> solver = new GraphSolver<String>(g);
		
		solver.doDijkstra("A");
		
		 assertEquals(0, solver.getShortestDistance("A")); 
		 assertEquals(5, solver.getShortestDistance("B")); 
		 assertEquals(12, solver.getShortestDistance("C")); 

	}
	public void testDijskstraDistances2() {
		Graph<String> g = Graph.createGraph("complicatedGraph.txt");
		GraphSolver<String> solver = new GraphSolver<String>(g);
		
		solver.doDijkstra("A");
		
		assertEquals(0, solver.getShortestDistance("A"));
		assertEquals(3, solver.getShortestDistance("B"));
		assertEquals(5, solver.getShortestDistance("C"));
		assertEquals(12, solver.getShortestDistance("F"));
		assertEquals(7, solver.getShortestDistance("E"));
		assertEquals(1, solver.getShortestDistance("D"));



	}
	
	public void testEasyDijkstraPaths() {
		Graph<String> g = Graph.createGraph("publicTestGraph.txt");
		GraphSolver<String> solver = new GraphSolver<String>(g);
		
		solver.doDijkstra("A");
		
		List<String> path = solver.getShortestPath("A");
		String[] correctPath1 = {"A"};
		assertTrue(matches(path, correctPath1));
		
		path = solver.getShortestPath("B");
		String[] correctPath2 = {"A", "B"};
		assertTrue(matches(path, correctPath2));
		
		path = solver.getShortestPath("C");
		String[] correctPath3 = {"A", "B", "C"};
		assertTrue(matches(path, correctPath3));
	}
	
	private boolean matches(List<String> path, String[] correctPath) {
		if (path.size() != correctPath.length) {
			return false;
		}
		for (int i = 0; i < path.size(); i++) {
			if (!path.get(i).equals(correctPath[i])) {
				return false;
			}
		}
		return true;
	}
	
}

