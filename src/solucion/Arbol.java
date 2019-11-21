package solucion;

import java.awt.Point;

public class Arbol implements Comparable<Arbol> {
	private Point coordenadas;
	private int uniqueId;
	private int peso;
	
	public Arbol(int uniqueId, Point coordenadas) {
		this.coordenadas = coordenadas;
		this.uniqueId = uniqueId;
	}
	
	public Point getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(Point coordenadas) {
		this.coordenadas = coordenadas;
	}

	public double getX() {
		return coordenadas.getX();
	}
	
	public double getY() {
		return coordenadas.getY();
	}

	public int getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	@Override
	public int compareTo(Arbol arg0) {
		return this.peso < arg0.peso ? -1 : 1;
	}
	
	public int hashCode() {
		return this.uniqueId;
	}
	
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Arbol)) return false;
		
		Arbol _obj = (Arbol) obj;
		return uniqueId == _obj.getUniqueId();
	}
}
