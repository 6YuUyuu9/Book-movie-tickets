import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class MainGUI extends JFrame {
    private ArrayList<Show> showList = new ArrayList<>();
    private DefaultListModel<String> showModel = new DefaultListModel<>();
    private JList<String> showJList = new JList<>(showModel);
    private JTextArea statusLog = new JTextArea(5, 10);
    private ArrayList<Book> bookingHistory = new ArrayList<>();

    public MainGUI() {
        // --- Window Settings ---
        setTitle("CineReserve | Movie Booking System");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(245, 245, 245));

        // --- Main Layout ---
        setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        // 1. Header
        JLabel lblTitle = new JLabel("MOVIE TICKETING SYSTEM", JLabel.LEFT);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(44, 62, 80));
        add(lblTitle, BorderLayout.NORTH);

        // 2. Center Panel (Showtime List)
        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));
        centerPanel.setOpaque(false);
        JLabel lblSubtitle = new JLabel("Available Showtimes");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        centerPanel.add(lblSubtitle, BorderLayout.NORTH);

        showJList.setFont(new Font("Consolas", Font.PLAIN, 14));
        JScrollPane listScroll = new JScrollPane(showJList);
        centerPanel.add(listScroll, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // 3. Right Panel (Actions & Status)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setPreferredSize(new Dimension(250, 0));
        rightPanel.setOpaque(false);

        JButton btnAdd = createStyledButton("Add New Show", new Color(46, 204, 113));
        JButton btnBook = createStyledButton("Book Selected Seat", new Color(52, 152, 219));
        JButton btnSearch = createStyledButton("Search by ID", new Color(52, 73, 94));

        statusLog.setEditable(false);
        statusLog.setBackground(new Color(230, 230, 230));
        statusLog.setFont(new Font("Tahoma", Font.PLAIN, 11));
        statusLog.setBorder(BorderFactory.createTitledBorder("System Status"));

        rightPanel.add(btnAdd);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(btnBook);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(btnSearch);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(new JScrollPane(statusLog));
        add(rightPanel, BorderLayout.EAST);

        // --- Button Events ---

        // 1. Add Movie with Auto-Generated Time
        btnAdd.addActionListener(e -> {
            String movieName = JOptionPane.showInputDialog(this, "Enter Movie Name:");
            if (movieName != null && !movieName.trim().isEmpty()) {
                int randomHour = (int) (Math.random() * 12) + 10;
                int randomMinute = (Math.random() > 0.5) ? 30 : 0;
                LocalTime startTime = LocalTime.of(randomHour, randomMinute);
                LocalTime endTime = startTime.plusMinutes(120);

                int generatedId = showList.size() + 101;
                Movie movie = new Movie(generatedId, movieName);
                Show newShow = new Show(generatedId, new Date(), startTime, endTime, movie);

                showList.add(newShow);
                showModel.addElement(movie.getMovieName() + " | " + startTime + " - " + endTime);
                statusLog.append("Auto-Gen: Added " + movie.getMovieName() + " [" + startTime + "]\n");
            }
        });

        // 2. Book Seat with Details Confirmation
        btnBook.addActionListener(e -> {
            int selectedIndex = showJList.getSelectedIndex();
            if (selectedIndex != -1) {
                Show selectedShow = showList.get(selectedIndex);

                // 1. รับรหัสที่นั่งจากผู้ใช้ (เช่น A1, A2, B1)
                String seatIdInput = JOptionPane.showInputDialog(this, "Enter Seat ID (e.g., A1, A2, B1):", "A1");

                if (seatIdInput != null && !seatIdInput.trim().isEmpty()) {
                    // 2. ค้นหาที่นั่งใน List ของ Show นั้นๆ
                    Seat targetSeat = null;
                    for (Seat s : selectedShow.getSeats()) {
                        if (s.getSeatId().equalsIgnoreCase(seatIdInput.trim())) {
                            targetSeat = s;
                            break;
                        }
                    }

                    // 3. ถ้าไม่พบที่นั่ง ให้สร้างใหม่ (หรือแจ้งเตือน)
                    if (targetSeat == null) {
                        targetSeat = new Seat(seatIdInput.trim().toUpperCase());
                        selectedShow.getSeats().add(targetSeat);
                    }

                    // 4. ตรวจสอบสถานะการจอง
                    if (!targetSeat.isBooked()) {
                        Book booking = new Book();
                        int newId = bookingHistory.size() + 1001;
                        booking.setBookId(newId);
                        booking.setPrice(200);

                        // สรุปข้อมูลยืนยัน
                        String details = "--- Booking Details ---\n" +
                                "Ticket ID: " + newId + "\n" +
                                "Movie: " + selectedShow.getMovie().getMovieName() + "\n" +
                                "Seat: " + targetSeat.getSeatId() + "\n" +
                                "Confirm booking?";

                        int confirm = JOptionPane.showConfirmDialog(this, details, "Confirm",
                                JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            booking.createBooking(selectedShow, targetSeat);
                            bookingHistory.add(booking);
                            statusLog
                                    .append("System: Seat " + targetSeat.getSeatId() + " booked (ID: " + newId + ")\n");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Sorry, Seat " + targetSeat.getSeatId() + " is already taken!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a showtime first.");
            }
        });

        // 3. Search Booking by Ticket ID
        btnSearch.addActionListener(e -> {
            String searchIdStr = JOptionPane.showInputDialog(this, "Enter Ticket ID to Search:");
            if (searchIdStr != null && !searchIdStr.trim().isEmpty()) {
                try {
                    int searchId = Integer.parseInt(searchIdStr);
                    boolean found = false;
                    for (Book b : bookingHistory) {
                        if (b.getBookId() == searchId) {
                            statusLog.append("\n--- Found Booking ID: " + searchId + " ---\n");
                            statusLog.append("Movie: " + b.getShowtime().getMovie().getMovieName() + "\n");
                            statusLog.append("Seat: " + b.getSeatId().getSeatId() + "\n");
                            statusLog.append("Price: " + b.getPrice() + " THB\n");
                            statusLog.append("----------------------------\n");
                            found = true;
                            break;
                        }
                    }
                    if (!found)
                        JOptionPane.showMessageDialog(this, "Ticket ID not found!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid numeric ID.");
                }
            }
        });

        setVisible(true);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI());
    }
}