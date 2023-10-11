package subte.interfaz;

import java.util.List;

import subte.controlador.Constantes;
import subte.controlador.Coordinador;
import subte.modelo.Estacion;
import subte.modelo.Tramo;

public class Interfaz {
	
	private Coordinador coordinador;
	
	public Interfaz() {

	}
	
	// Usuario ingresa opci�n
	public int opcion() {
		return Constantes.MAS_RAPIDO;
	}

	// Usuario ingresa estaci�n origen
	public Estacion ingresarEstacionOrigen(
			List<Estacion> estaciones) {
		return estaciones.get(2);
	}

	// Usuario ingresa estaci�n destino
	public Estacion ingresarEstacionDestino(
			List<Estacion> estaciones) {
		return estaciones.get(3);
	}

	public void resultado(List<Tramo> recorrido) {
		int tiempoTotal = 0;
		int tiempoSubte = 0;
		int tiempoCaminando = 0;
		for (Tramo t : recorrido) {
			System.out.println(t.getEstacion1().getLinea().getCodigo() + "-"
					+ t.getEstacion1().getNombre() + " > "
					+ t.getEstacion2().getLinea().getCodigo() + "-"
					+ t.getEstacion2().getNombre() + " :" + t.getTiempo());
			tiempoTotal += t.getTiempo();
			if (t.getEstacion1().getLinea().equals(t.getEstacion2().getLinea()))
				tiempoSubte += t.getTiempo();
			else
				tiempoCaminando += t.getTiempo();
		}
		System.out.println("Tiempo Total: " + tiempoTotal);
		System.out.println("Tiempo Subte: " + tiempoSubte);
		System.out.println("Tiempo Caminando: " + tiempoCaminando);
	}
	
	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}

}
