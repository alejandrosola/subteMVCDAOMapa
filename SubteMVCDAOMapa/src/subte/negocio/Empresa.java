package subte.negocio;

import java.util.ArrayList;
import java.util.List;

import subte.modelo.Estacion;
import subte.modelo.Linea;
import subte.modelo.Tramo;
import subte.servicio.EstacionService;
import subte.servicio.EstacionServiceImpl;
import subte.servicio.LineaService;
import subte.servicio.LineaServiceImpl;
import subte.servicio.TramoService;
import subte.servicio.TramoServiceImpl;

public class Empresa {

	private static Empresa empresa = null;	

	private String nombre;
	private List<Linea> lineas;
	private LineaService lineaService;
	private List<Estacion> estaciones;
	private EstacionService estacionService;
	private List<Tramo> tramos;
	private TramoService tramoService;

	
	public static Empresa getEmpresa() {
        if (empresa == null) {
            empresa = new Empresa();
        }
        return empresa;
    }
	
	private Empresa() {
		super();
		lineas = new ArrayList<Linea>();
		lineaService = new LineaServiceImpl();
		lineas.addAll(lineaService.buscarTodos());
		estaciones = new ArrayList<Estacion>();
		estacionService = new EstacionServiceImpl();
		estaciones.addAll(estacionService.buscarTodos());
		tramos = new ArrayList<Tramo>();
		tramoService = new TramoServiceImpl();				
		tramos.addAll(tramoService.buscarTodos());
	}

	public void agregarLinea(Linea linea) throws LineaExisteException {
		if (lineas.contains(linea))
			throw new LineaExisteException();
		lineas.add(linea);
		lineaService.insertar(linea);		
	}

	public void modificarLinea(Linea linea) {		
		int pos = lineas.indexOf(linea);		
		lineas.set(pos, linea);
		lineaService.actualizar(linea);		
	}

	public void borrarLinea(Linea linea) {
		Linea emp = buscarLinea(linea);
		lineas.remove(emp);
		lineaService.borrar(linea);		
	}

	public Linea buscarLinea(Linea Linea) {
		int pos = lineas.indexOf(Linea);
		if (pos == -1)
			return null;
		return lineas.get(pos);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Linea> getLineas() {
		return lineas;
	}
	
	public List<Estacion> getEstaciones() {
		return estaciones;
	}

	public List<Tramo> getTramos() {
		return tramos;
	}


}
