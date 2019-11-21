package input;

import java.io.File;
import java.util.Locale;
import java.util.Scanner;

public class LeerArchivo {
	private Scanner sc;
	
	public LeerArchivo(String path) {
		try {
			sc = new Scanner(new File(path));
			sc.useLocale(Locale.ENGLISH);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
	
	public double siguienteNumero() {
		return sc.nextDouble();
	}
	
	public boolean hayNumero() {
		return sc.hasNextDouble();
	}
	
	public void cerrar() {
		sc.close();
	}
}
