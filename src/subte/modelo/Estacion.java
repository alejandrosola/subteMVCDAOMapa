package subte.modelo;

import java.util.Objects;

public class Estacion {

	private String codigo;
	private String nombre;
	private Linea linea;
	private double lat;
	private double lng;
	
	public Estacion() {
		
	}

	public Estacion(String codigo, String nombre, Linea linea) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.linea = linea;
	}	
	
	public Estacion(String codigo, String nombre, Linea linea, double lat, double lng) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.linea = linea;
		this.lat = lat;
		this.lng = lng;
	}	

	public String getCodigo() {
		return codigo;
	}



	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public Linea getLinea() {
		return linea;
	}



	public void setLinea(Linea linea) {
		this.linea = linea;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	@Override
	public String toString() {
		return nombre + " ("+ linea + ")";
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estacion other = (Estacion) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
	
	
	
	
}
