package controller;

import model.*;
import gui.TrafficSignalGUI;

public class TrafficController {
    private final TrafficLight light;
    private final VehicleManager manager;
    private final TrafficSignalGUI gui;
    private boolean emergencyMode = false;

    public TrafficController(TrafficLight light, VehicleManager manager, TrafficSignalGUI gui) {
        this.light = light;
        this.manager = manager;
        this.gui = gui;
        startThreads();
    }

    private void startThreads() {
        // Traffic light toggler
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                    light.toggle();
                    gui.updateSignal(light.getSignal());
                } catch (InterruptedException ignored) {}
            }
        }).start();

        // Vehicle movement
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    if (light.getSignal() == TrafficLight.Signal.GREEN) {
                        Vehicle v = manager.pollVehicle(emergencyMode);
                        if (v != null) {
                            gui.moveVehicle(v);
                        }
                    }
                } catch (InterruptedException ignored) {}
            }
        }).start();
    }

    public void addVehicle(Vehicle vehicle) {
        manager.addVehicle(vehicle);
        gui.updateQueue(manager.getQueueStatus());
    }

    public void toggleEmergencyMode() {
        emergencyMode = !emergencyMode;
        gui.setEmergencyMode(emergencyMode);
    }
}
