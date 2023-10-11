package subte.controlador;

import java.util.List;

import subte.interfaz.Interfaz;
import subte.modelo.Estacion;
import subte.modelo.Linea;
import subte.modelo.Tramo;
import subte.negocio.Calculo;
import subte.negocio.Empresa;

public class Coordinador {

	private Empresa empresa;
	private Calculo calculo;
	private Interfaz interfaz;

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public Calculo getCalculo() {
		return calculo;
	}

	public void setCalculo(Calculo calculo) {
		this.calculo = calculo;
	}
	
	public Interfaz getInterfaz() {
		return interfaz;
	}

	public void setInterfaz(Interfaz interfaz) {
		this.interfaz = interfaz;
	}

	// Interfaz


	// LineaList


	// Empresa

	public Linea buscarLinea(Linea linea) {
		return empresa.buscarLinea(linea);
	}

	public List<Linea> listarLineas() {
		return empresa.getLineas();
	}

	public List<Estacion> listarEstaciones() {
		return empresa.getEstaciones();
	}

	public List<Tramo> listarTramos() {
		return empresa.getTramos();
	}



}
