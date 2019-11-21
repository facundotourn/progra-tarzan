package solucion;

import java.util.Stack;

import input.LeerArchivo;

public class Solucion {
	private Bosque b;
	private Stack<Arbol> camino;
	
	public Solucion (String path) {
		b = new Bosque();
		cargarBosque(path);
		
		camino = b.encontrarMenorCantidadSaltos();
	}
	
	public void mostrarCamino() {
		while (!camino.isEmpty()) {
			Arbol a = camino.pop();
			System.out.println((int) a.getX() + " " + (int) a.getY());
		}
	}
	
	private void cargarBosque(String path) {
		LeerArchivo leerArchivo = new LeerArchivo(path);
		int id = 0;
		
		while (leerArchivo.hayNumero()) {
			int x = (int) leerArchivo.siguienteNumero();
			int y = (int) leerArchivo.siguienteNumero();
			
			b.añadirArbol(id, x, y);
			id++;
		}
	}
	
	
}
