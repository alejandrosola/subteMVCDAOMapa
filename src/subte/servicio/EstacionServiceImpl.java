package subte.servicio;

import java.util.List;

import subte.dao.EstacionDAO;
import subte.dao.secuencial.EstacionSecuencialDAO;
import subte.modelo.Estacion;

public class EstacionServiceImpl implements EstacionService {

	private EstacionDAO estacionDAO; 
		
	public EstacionServiceImpl(){
		estacionDAO = new EstacionSecuencialDAO();
	}
	
	@Override
	public void insertar(Estacion estacion) {
		estacionDAO.insertar(estacion);				
	}

	@Override
	public void actualizar(Estacion estacion) {
		estacionDAO.actualizar(estacion);						
	}

	@Override
	public void borrar(Estacion estacion) {
		estacionDAO.borrar(estacion);
		
	}

	@Override
	public List<Estacion> buscarTodos() {
		return estacionDAO.buscarTodos();
		
	}

}
