package gui;

import controller.TrafficController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class TrafficSignalGUI extends JFrame {
    private JLabel signalLabel = new JLabel("RED");
    private JLabel movingVehicleLabel = new JLabel("No vehicle moving");
    private JTextArea queueArea = new JTextArea(8, 25);
    private JButton emergencyBtn = new JButton("Enable Emergency Mode");
    private TrafficController controller;
    private Random rand = new Random();

    public TrafficSignalGUI() {
        setTitle("Traffic Signal Management");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Traffic Signal:"));
        add(signalLabel);

        add(new JLabel("Now Moving:"));
        add(movingVehicleLabel);

        queueArea.setEditable(false);
        add(new JScrollPane(queueArea));

        JButton addVehicleBtn = new JButton("Add Vehicle");
        addVehicleBtn.addActionListener(e -> {
            String id = "V" + rand.nextInt(100);
            Vehicle.Type type = rand.nextInt(5) == 0
                ? (rand.nextBoolean() ? Vehicle.Type.AMBULANCE : Vehicle.Type.FIRE_TRUCK)
                : Vehicle.Type.NORMAL;
            controller.addVehicle(new Vehicle(id, type));
        });

        emergencyBtn.addActionListener(e -> controller.toggleEmergencyMode());

        add(addVehicleBtn);
        add(emergencyBtn);

        setVisible(true);
    }

    public void setController(TrafficController controller) {
        this.controller = controller;
    }

    public void updateSignal(TrafficLight.Signal signal) {
        SwingUtilities.invokeLater(() -> signalLabel.setText(signal.toString()));
    }

    public void moveVehicle(Vehicle vehicle) {
        SwingUtilities.invokeLater(() -> movingVehicleLabel.setText(vehicle.toString()));
    }

    public void updateQueue(String status) {
        SwingUtilities.invokeLater(() -> queueArea.setText(status));
    }

    public void setEmergencyMode(boolean enabled) {
        emergencyBtn.setText(enabled ? "Disable Emergency Mode" : "Enable Emergency Mode");
    }
}
