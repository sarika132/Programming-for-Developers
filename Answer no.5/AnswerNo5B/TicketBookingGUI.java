import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.List;

public class TicketBookingGUI extends JFrame {

    private final Map<String, Seat> seats = new HashMap<>();
    private final Map<String, JButton> seatButtons = new HashMap<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    public TicketBookingGUI(List<String> seatIds) {
        setTitle("Online Ticket Booking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, seatIds.size(), 10, 10));
        setSize(600, 150);

        for (String seatId : seatIds) {
            Seat seat = new Seat(seatId);
            seats.put(seatId, seat);

            JButton btn = new JButton(seatId);
            btn.setBackground(Color.GREEN);
            btn.setOpaque(true);
            btn.setFont(new Font("Arial", Font.BOLD, 16));

            btn.addActionListener(e -> tryBookSeat(seatId));

            seatButtons.put(seatId, btn);
            add(btn);
        }
    }

    private void tryBookSeat(String seatId) {
        // Simulate a booking request from a user thread
        executor.submit(() -> bookSeat(seatId, "User-" + Thread.currentThread().getId()));
    }

    private void bookSeat(String seatId, String userName) {
        Seat seat = seats.get(seatId);
        if (seat == null) {
            showMessage(userName + ": Seat " + seatId + " does not exist.");
            return;
        }

        // Pessimistic locking: lock seat before booking
        if (seat.lock.tryLock()) {
            try {
                // Simulate delay in booking process
                Thread.sleep(500);

                // Optimistic locking: check if seat is still free
                if (!seat.isBooked) {
                    seat.isBooked = true;
                    seat.version++;
                    updateSeatButton(seatId, false);
                    showMessage(userName + ": Successfully booked seat " + seatId);
                } else {
                    showMessage(userName + ": Seat " + seatId + " is already booked.");
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } finally {
                seat.lock.unlock();
            }
        } else {
            showMessage(userName + ": Seat " + seatId + " is currently locked. Please try again.");
        }
    }

    private void updateSeatButton(String seatId, boolean available) {
        SwingUtilities.invokeLater(() -> {
            JButton btn = seatButtons.get(seatId);
            if (btn != null) {
                btn.setBackground(available ? Color.GREEN : Color.RED);
                btn.setEnabled(available);
            }
        });
    }

    private void showMessage(String message) {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, message));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<String> seatIds = Arrays.asList("A1", "A2", "A3", "A4", "A5");
            TicketBookingGUI gui = new TicketBookingGUI(seatIds);
            gui.setVisible(true);
        });
    }

    // Seat class with lock and version
    static class Seat {
        String id;
        boolean isBooked = false;
        int version = 0;
        final ReentrantLock lock = new ReentrantLock();

        public Seat(String id) {
            this.id = id;
        }
    }
}
