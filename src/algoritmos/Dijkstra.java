package algoritmos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import solucion.Arbol;

public class Dijkstra {
	private int V;
	private HashMap<Integer, HashMap<Integer, Integer>> adjList;
	private List<Arbol> vertices;
	
	public Dijkstra(int V, List<Arbol> vertices, HashMap<Integer, HashMap<Integer, Integer>> adjList) {
		this.V = V;
		this.adjList = adjList;
		this.vertices = vertices;
	}
	
	public void setAdjList(HashMap<Integer, HashMap<Integer, Integer>> adjList) {
		this.adjList = adjList;
	}

	public ArrayList<Integer> menorCamino(int destino, int origen) {
		// init
		PriorityQueue<Arbol> cola = new PriorityQueue<Arbol>();
		HashMap<Integer, Boolean> completados = new HashMap<Integer, Boolean>(V);
		ArrayList<Integer> camino = new ArrayList<Integer>(V);
		
		// inicializo el peso de los vértices
		for (Arbol a : vertices) {
			a.setPeso(a.getUniqueId() == origen ? 0 : Integer.MAX_VALUE);
			cola.add(a);
			
			camino.add(a.getUniqueId(), null);
		}
		
		// no puedo fijarme si la cola está vacía porque no puedo sacar a los adyacentes cuando los quiero actualizar
		// lo que hago es armar otro vértice nuevo y mandarlo a la cola (entonces los adyacentes viejos desactualizados quedan ahi)
		// es decir, la cola nunca se vacía
		while (completados.size() != V) {
			// tomo el vértice con menor peso
			Arbol current = cola.remove();
			
			// si llegué al destino, salgo
			if (current.getUniqueId() == destino) break;
			
			// marco este nodo como completado
			completados.put(current.getUniqueId(), true);
			
			// traigo los adyacentes a mi nodo
			HashMap<Integer, Integer> adyacentes = adjList.get(current.getUniqueId());
			
			// por cada adyacente
			for (Integer id : adyacentes.keySet()) {
				if (completados.containsKey(id)) continue;
				
				Arbol currentAdy = vertices.get(id);
				
				// si el peso del adyacente es mayor al peso de el nodo previo + 1 (todos los saltos valen 1), lo reemplazo
				if (currentAdy.getPeso() > current.getPeso() + 1) {
					currentAdy.setPeso(current.getPeso() + 1);
					
					camino.set(id, current.getUniqueId());
					
					cola.add(currentAdy);
				}
			}
		}
		
		return camino;
	}
	
}
