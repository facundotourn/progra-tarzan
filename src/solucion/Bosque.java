package solucion;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import algoritmos.Dijkstra;

public class Bosque {
	private List<Arbol> arboles;
	private HashMap<Integer, HashMap<Integer, Integer>> arbolesCerca;
	private int origen;
	private int destino;
	
	public Bosque() {
		arboles = new ArrayList<Arbol>();
		arbolesCerca = new HashMap<Integer, HashMap<Integer, Integer>>();
	}
	
	// función para testear la correcta carga del grafo
	public void mostrarArbolesCerca() {
		for (Arbol a : arboles) {
			HashMap<Integer, Integer> misArbolesCercanos = arbolesCerca.get(a.getUniqueId());
			
			for (Integer key : misArbolesCercanos.keySet()) {
				Arbol current = arboles.get(key);
				System.out.println("[" + misArbolesCercanos.get(key) + "] El arbol " + a.getUniqueId() + " (" + a.getX() + ", " + a.getY() + ") está a " + a.getCoordenadas().distance(current.getCoordenadas()) + " metros del arbol " + current.getUniqueId() + " (" + current.getX() + ", " + current.getY() + ").");
			}
		}
	}
	
	// función que añade un arbol, no sin antes revisar la cercanía con otros árboles
	public void añadirArbol(Arbol newArbol) {
		revisarCercanias(newArbol);
		
		arboles.add(newArbol);
		
		origen = arboles.get(0).getUniqueId();
		destino = arboles.get(arboles.size() - 1).getUniqueId();
	}
	
	public void añadirArbol(int id, int x, int y) {
		añadirArbol(new Arbol(id, new Point(x, y)));
	}
	
	// añade una arista a la lista de adjacencia
	private void añadirArbolesCerca(int id1, int id2, int peso) {
		arbolesCerca.putIfAbsent(id1, new HashMap<Integer, Integer>());
		arbolesCerca.putIfAbsent(id2, new HashMap<Integer, Integer>());
		
		arbolesCerca.get(id1).put(id2, peso);
		arbolesCerca.get(id2).put(id1, peso);
	}

	// elimina una arista del grafo
	private void eliminarArbolesCerca(int idDesde, int idHasta) {
		arbolesCerca.get(idDesde).remove(idHasta);
		arbolesCerca.get(idHasta).remove(idDesde);
	}

	// función que dado un newArbol revisa su cercanía con cada [arboles], si es necesario crea la conexión ArbolesCerca
	private void revisarCercanias(Arbol newArbol) {
		for (Arbol a : arboles) {
			double distancia = a.getCoordenadas().distance(newArbol.getCoordenadas());
			if (distancia <= 50) {
				añadirArbolesCerca(a.getUniqueId(), newArbol.getUniqueId(), 1);
			} else if (distancia <= 100) {
				añadirArbolesCerca(a.getUniqueId(), newArbol.getUniqueId(), 2);
			}
		}
	}
	
	// función principal en la resolución del problema
	public Stack<Arbol> encontrarMenorCantidadSaltos() {
		Dijkstra d = new Dijkstra(arboles.size(), arboles, arbolesCerca);
		ArrayList<Integer> camino;
		
		boolean caminoValido = false;
		
		// calculo y valido mi camino
		camino = d.menorCamino(destino, origen);
		caminoValido = validarCamino(camino);
		
		// mientras que el camino no sea válido, elimino una arista de supersalto y calculo de nuevo
		while (!caminoValido) {
			mostrarCamino(camino);
			
			removerSuperSaltoCamino(camino);
			d.setAdjList(arbolesCerca);
			
			camino = d.menorCamino(destino, origen);
			caminoValido = validarCamino(camino);			
		}

		mostrarCamino(camino);
		
		// devuelvo el camino
		return construirCamino(camino);
	}

	// arma un stack de Arbol a partir del array de ids enteros 
	private Stack<Arbol> construirCamino(ArrayList<Integer> camino) {
		Stack<Arbol> colaCamino = new Stack<Arbol>();
		
		int id = destino;
		do {
			colaCamino.add(arboles.get(id));
			
			id = camino.get(id);
		} while (id != origen);
		colaCamino.add(arboles.get(origen));
		
		return colaCamino;
	}

	// elimina el primer supersalto del camino
	private void removerSuperSaltoCamino(ArrayList<Integer> camino) {
		int id = destino;
		HashMap<Integer, Integer> supersaltos = new HashMap<Integer, Integer>();
		
		do {
			if (arbolesCerca.get(camino.get(id)).get(id) == 2)
				supersaltos.put(camino.get(id), id);
			
			id = camino.get(id);
		} while (id != origen);

		int idDesde = 0;
		int idHasta = 0;
		for (Integer key : supersaltos.keySet()) {
			idDesde = key;
			idHasta = supersaltos.get(key);
			break;
		}
		
		eliminarArbolesCerca(idDesde, idHasta);
	}

	// valida que solo se hayan hecho un máximo de 1 supersaltos en el camino
	private boolean validarCamino(ArrayList<Integer> camino) {
		int id = destino;
		int cantSuperSaltos = 0;
		do {
			cantSuperSaltos += arbolesCerca.get(camino.get(id)).get(id) == 2 ? 1 : 0;
			id = camino.get(id);
		} while (id != origen);
		
		return cantSuperSaltos < 2;
	}

	// función para mostrar por consola un camino
	private void mostrarCamino(ArrayList<Integer> camino) {
		int id = destino;
		
		System.out.println("-- NUEVO CAMINO ENCONTRADO --");
		do {
			System.out.println("Llegué al arbol #" + id + " viniendo del arbol #" + camino.get(id));
			System.out.println("Este fue un " + (arbolesCerca.get(camino.get(id)).get(id) == 1 ? "salto normal" : "super salto."));
			System.out.println();
			id = camino.get(id);
		} while (id != origen);
	}
}
