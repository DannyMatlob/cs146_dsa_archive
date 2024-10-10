
public class Utility {
	static boolean[] find(UndirectedGraph g) {
		int index = 0;
		boolean[] vertexCover; 
		UndirectedGraph removedGraph;
		int minCover = 0;
		
		//Initialize array to all false
		vertexCover = new boolean[g.size()];
		
		//Edge Cases
		if(g.size()==0) return vertexCover;
		if(g.has(0)) return vertexCover;
		
		//Find the size of the smallest vertex cover
		while (!g.has(minCover)) {
			minCover++;
		}
		//VertexCover = 5
		//mincover = 0; !g.has(0)
		
		//Iterate through vertices of the graph
		//Remove the vertex we are on. 
		//If the size of vertex cover decreases, then that vertex belongs in the minimum vertex cover
		//set vertex index to true in array.
		while (minCover>0) {
			removedGraph=g.remove(index);
			if (removedGraph.has(minCover-1)) {
				vertexCover[index] = true;
				g=removedGraph;
				minCover--;
			} else {
				index++;
			}
		}
		return vertexCover;
	}
}
