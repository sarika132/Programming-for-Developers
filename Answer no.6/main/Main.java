package main;

import model.*;
import javax.swing.SwingUtilities;
import controller.*;
import gui.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TrafficSignalGUI gui = new TrafficSignalGUI();
            TrafficLight light = new TrafficLight();
            VehicleManager manager = new VehicleManager();
            TrafficController controller = new TrafficController(light, manager, gui);
            gui.setController(controller);
        });
    }
}
