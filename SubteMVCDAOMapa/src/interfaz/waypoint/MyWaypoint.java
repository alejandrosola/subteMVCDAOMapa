package interfaz.waypoint;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

public class MyWaypoint  extends DefaultWaypoint {

    private String name;
    private JLabel icon;
    
    public MyWaypoint(String name, GeoPosition coord, boolean isStep) {
        super(coord);
        this.name = name;
        
        if (!isStep) {
            this.icon = new JLabel();
            ImageIcon icon = new ImageIcon(getClass().getResource("/interfaz/icon/pin.png"));
            this.icon.setIcon(icon);
            this.icon.setText("SOY UN MARKER");
            this.icon.setSize(new Dimension(24, 24));        	
        } else {
        	this.icon = new JLabel();
            ImageIcon icon = new ImageIcon(getClass().getResource("/interfaz/icon/step1.png"));
            this.icon.setIcon(icon);
            this.icon.setText("SOY UN STEP");
            this.icon.setSize(new Dimension(5, 5));
        }

    }
	
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JLabel getIcon() {
        return icon;
    }

    public void setIcon(JLabel icon) {
        this.icon = icon;
    }

    

}