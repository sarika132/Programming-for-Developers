package controller;

import model.Vehicle;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class VehicleManager {
    private final LinkedBlockingQueue<Vehicle> normalQueue = new LinkedBlockingQueue<>();
    private final PriorityBlockingQueue<Vehicle> emergencyQueue = new PriorityBlockingQueue<>();

    public void addVehicle(Vehicle vehicle) {
        if (vehicle.getType() == Vehicle.Type.NORMAL)
            normalQueue.offer(vehicle);
        else
            emergencyQueue.offer(vehicle);
    }

    public Vehicle pollVehicle(boolean emergencyMode) {
        if (emergencyMode && !emergencyQueue.isEmpty())
            return emergencyQueue.poll();
        else
            return normalQueue.poll();
    }

    public String getQueueStatus() {
        return "Emergency Queue: " + emergencyQueue + "\nNormal Queue: " + normalQueue;
    }
}
