package model;

public class TrafficLight {
    public enum Signal { RED, GREEN }

    private Signal current = Signal.RED;

    public synchronized void toggle() {
        current = (current == Signal.RED) ? Signal.GREEN : Signal.RED;
    }

    public synchronized Signal getSignal() {
        return current;
    }
}
