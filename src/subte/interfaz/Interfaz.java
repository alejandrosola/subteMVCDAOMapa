package subte.interfaz;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputListener;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;

import interfaz.apiRequests.HttpRequester;
import interfaz.waypoint.MyWaypoint;
import interfaz.waypoint.WaypointRenderer;
import subte.controlador.Constantes;
import subte.controlador.Coordinador;
import subte.modelo.Estacion;
import subte.modelo.Tramo;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class Interfaz extends JFrame {

    private final Set<MyWaypoint> waypoints = new HashSet<>();
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JXMapViewer mapViewer;
	
	private Coordinador coordinador;
	
	
	public Interfaz() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		// Crear visualizador del mapa
		mapViewer = new JXMapViewer();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(mapViewer, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(mapViewer, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
		);
		contentPane.setLayout(gl_contentPane);

        GroupLayout jXMapViewerLayout = new GroupLayout(mapViewer);
        mapViewer.setLayout(jXMapViewerLayout);
		init();
		this.setVisible(true);
		
	}
	
	private void createMouseMovement() {
		// Crear evento del mouse para detectar zoom y movimiento del mapa
		MouseInputListener movementListener = new PanMouseInputListener(mapViewer);
		mapViewer.addMouseListener(movementListener);
		mapViewer.addMouseMotionListener(movementListener);
		ZoomMouseWheelListenerCenter zoomListener = new ZoomMouseWheelListenerCenter(mapViewer);
		mapViewer.addMouseWheelListener(zoomListener);
	}

	private void init() {
		// Inicializar visaulizador del mapa
		TileFactoryInfo info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
		DefaultTileFactory tileFactory = new DefaultTileFactory(info);
		mapViewer.setTileFactory(tileFactory);

		createMouseMovement();		
		
	}
	
	public void mostrarResultado(List<Tramo> recorrido) {
		// Crear los marcadores (waypoints) a mostrar en el mapa según el recorrido
		List<Estacion> estaciones = new ArrayList<>();
		
		// Crear lista de estaciones sin repeticiones
		for (Tramo t : recorrido) {
			if (!estaciones.contains(t.getEstacion1()))
				estaciones.add(t.getEstacion1());
			if (!estaciones.contains(t.getEstacion2())) {
				estaciones.add(t.getEstacion2());
			}
		}
		createWaypoints(estaciones);
	}
	
	private void createWaypoints(List<Estacion> estaciones) {
        WaypointPainter<MyWaypoint> wp = new WaypointRenderer();
        // Hacer llamada a la API de OpenStreetMap para cada par de 
        List<GeoPosition> fastestRoute = new ArrayList<>();
        for (int i = 0; i < estaciones.size() - 1; i++) {
        	// Conseguir coordenadas de las estaciones en el formato en el que lo espera la API
        	String coords = estaciones.get(i).getLng() + ","+ estaciones.get(i).getLat() + ";" 
        	        + estaciones.get(i + 1).getLng() + "," + estaciones.get(i + 1).getLat();
        	// Hacer request y agregarlo a la lista
        	List<GeoPosition> route = HttpRequester.getFastestRoute(coords);
        	if (route != null)
        		fastestRoute.addAll(route);        		   
        }
        

		GeoPosition startingPoint = new GeoPosition(estaciones.get(0).getLat(), estaciones.get(0).getLng());

		mapViewer.setAddressLocation(startingPoint);
		mapViewer.setZoom(3);
        
		// Agregar puntos obtenidos de la API, indicando que son pasos de la trayectoria 
        for (GeoPosition point : fastestRoute)
        	waypoints.add(new MyWaypoint("ruta", point, true));
        
        // Agregar puntos originales (estaciones)
        for (Estacion e : estaciones) {
        	waypoints.add(new MyWaypoint(e.getCodigo(), new GeoPosition(e.getLat(), e.getLng()), false));
        }
        
        // Dibujar los puntos en el mapa
        wp.setWaypoints(waypoints);
        mapViewer.setOverlayPainter(wp);
		for (MyWaypoint d : waypoints) {
        	mapViewer.add(d.getIcon());
        }
    }
	
	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}
	
	public int opcion() {
		return Constantes.MAS_RAPIDO;
	}
	
	public Estacion ingresarEstacionOrigen(
			List<Estacion> estaciones) {
		return estaciones.get(0);
	}

	// Usuario ingresa estaci�n destino
	public Estacion ingresarEstacionDestino(
			List<Estacion> estaciones) {
		return estaciones.get(2);
	}

}
