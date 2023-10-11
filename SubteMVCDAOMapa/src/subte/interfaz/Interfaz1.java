package subte.interfaz;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputListener;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
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

public class Interfaz1 extends JFrame {

    private final Set<MyWaypoint> waypoints = new HashSet<>();
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JXMapViewer mapViewer;
	
	private Coordinador coordinador;
	
	
	public Interfaz1() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
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

        javax.swing.GroupLayout jXMapViewerLayout = new javax.swing.GroupLayout(mapViewer);
        mapViewer.setLayout(jXMapViewerLayout);
		init();
		this.setVisible(true);
		
	}
	
	private void createMouseMovement() {
		MouseInputListener movementListener = new PanMouseInputListener(mapViewer);
		mapViewer.addMouseListener(movementListener);
		mapViewer.addMouseMotionListener(movementListener);
		ZoomMouseWheelListenerCenter zoomListener = new ZoomMouseWheelListenerCenter(mapViewer);
		mapViewer.addMouseWheelListener(zoomListener);
	}

	private void init() {
		TileFactoryInfo info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
		DefaultTileFactory tileFactory = new DefaultTileFactory(info);
		mapViewer.setTileFactory(tileFactory);

		createMouseMovement();		
		
	}
	
	public void resultado(List<Tramo> recorrido) {
		
		List<Estacion> estaciones = new ArrayList<>();
		
		for (Tramo t : recorrido) {
			estaciones.add(t.getEstacion1());
			if (!estaciones.contains(t.getEstacion2())) {
				estaciones.add(t.getEstacion2());
			}

		}
		createWaypoints(estaciones);
	}
	
	private void createWaypoints(List<Estacion> estaciones) {
        WaypointPainter<MyWaypoint> wp = new WaypointRenderer();
        
        List<GeoPosition> fastestRoute = new ArrayList<>();
        for (int i = 0; i < estaciones.size() - 1; i += 2) {
        	String coords = estaciones.get(i).getLng() + ","+ estaciones.get(i).getLat() + ";" 
        	        + estaciones.get(i + 1).getLng() + "," + estaciones.get(i + 1).getLat();
        	List<GeoPosition> route = HttpRequester.getFastestRoute(coords);
        	if (route != null)
        		fastestRoute.addAll(route);        		   
        }
        

		GeoPosition startingPoint = new GeoPosition(estaciones.get(0).getLat(), estaciones.get(0).getLng());

		mapViewer.setAddressLocation(startingPoint);
		mapViewer.setZoom(3);
        
        for (GeoPosition point : fastestRoute)
        	waypoints.add(new MyWaypoint("ruta", point, true));

        for (Estacion e : estaciones) {
        	waypoints.add(new MyWaypoint(e.getCodigo(), new GeoPosition(e.getLat(), e.getLng()), false));
        }
        
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
		return estaciones.get(1);
	}

}
