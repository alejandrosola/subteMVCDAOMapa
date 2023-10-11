package subte.controlador;

import java.util.List;

import subte.interfaz.Interfaz;
import subte.interfaz.Interfaz1;
import subte.modelo.Estacion;
import subte.modelo.Tramo;
import subte.negocio.Calculo;
import subte.negocio.Empresa;
import subte.negocio.LineaExisteException;

public class AplicacionConsultas {

	// l�gica
	private Empresa empresa;
	private Calculo calculo;

	// vista
	private Interfaz1 interfaz;

	// controlador
	private Coordinador coordinador;

	public static void main(String[] args) throws LineaExisteException {
		AplicacionConsultas miAplicacion = new AplicacionConsultas();
		miAplicacion.iniciar();
		miAplicacion.consultar();
	}

	private void iniciar() throws LineaExisteException {
		/* Se instancian las clases */
		empresa = Empresa.getEmpresa();
		calculo = new Calculo();
		coordinador = new Coordinador();
		interfaz = new Interfaz1();

		/* Se establecen las relaciones entre clases */
		calculo.setCoordinador(coordinador);
		interfaz.setCoordinador(coordinador);

		/* Se establecen relaciones con la clase coordinador */
		coordinador.setEmpresa(empresa);
		coordinador.setCalculo(calculo);

		calculo.cargarDatos(coordinador.listarTramos(), coordinador.listarEstaciones());
	}

	private void consultar() throws LineaExisteException {

		// Ingreso datos usuario
		int opcion = interfaz.opcion();
		Estacion origen = interfaz.ingresarEstacionOrigen(coordinador.listarEstaciones());
		Estacion destino = interfaz.ingresarEstacionDestino(coordinador.listarEstaciones());

		// Realizar c�lculo
		calculo.cargarDatos(coordinador.listarTramos(), coordinador.listarEstaciones());

		List<Tramo> recorrido = null;
		if (opcion == Constantes.MAS_RAPIDO)
			recorrido = calculo.rapido(origen, destino);

		// Mostrar resultado
		interfaz.resultado(recorrido);
	}

}
