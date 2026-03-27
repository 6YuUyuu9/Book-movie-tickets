import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public class MainGUI extends JFrame {
    private ArrayList<Show> showList = new ArrayList<>();
    private DefaultListModel<String> showModel = new DefaultListModel<>();
    private JList<String> showJList = new JList<>(showModel);
    private ArrayList<Book> bookingHistory = new ArrayList<>();

    private final Color COLOR_NAVY = new Color(44, 62, 80);
    private final Color COLOR_PRIMARY = new Color(52, 152, 219);
    private final Color COLOR_BG = new Color(248, 249, 250);

    public MainGUI() {
        setTitle("Movie Booking System");
        setSize(1100, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- 1. NAVBAR ---
        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 12));
        navBar.setBackground(COLOR_NAVY);

        JButton btnAdd = createNavButton("Add New Show", new Color(46, 204, 113));
        JButton btnBook = createNavButton("Book Tickets", COLOR_PRIMARY);
        JButton btnSearch = createNavButton("Search Ticket ID", new Color(149, 165, 166));

        navBar.add(btnAdd);
        navBar.add(btnBook);
        navBar.add(btnSearch);
        add(navBar, BorderLayout.NORTH);

        // --- 2. MAIN CONTENT ---
        JPanel mainContent = new JPanel(new GridLayout(1, 2, 30, 0));
        mainContent.setBorder(new EmptyBorder(25, 25, 25, 25));
        mainContent.setBackground(COLOR_BG);

        JPanel listPanel = new JPanel(new BorderLayout(0, 10));
        listPanel.setOpaque(false);
        JLabel lblHeader = new JLabel("AVAILABLE SHOWTIMES");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
        listPanel.add(lblHeader, BorderLayout.NORTH);

        showJList.setFont(new Font("Monospaced", Font.BOLD, 14));
        showJList.setSelectionBackground(COLOR_PRIMARY);
        showJList.setSelectionForeground(Color.WHITE);
        showJList.setFixedCellHeight(45);
        showJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting())
                repaint();
        });

        JScrollPane scrollPane = new JScrollPane(showJList);
        scrollPane.setBorder(new LineBorder(new Color(220, 220, 220), 1));
        listPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel seatMapPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawSeatMap(g);
            }
        };
        seatMapPanel.setBackground(Color.WHITE);
        seatMapPanel.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(new Color(200, 200, 200), 1), " Seat Map ",
                TitledBorder.CENTER, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12)));

        mainContent.add(listPanel);
        mainContent.add(seatMapPanel);
        add(mainContent, BorderLayout.CENTER);

        // --- 3. ACTIONS ---

        btnAdd.addActionListener(e -> {
            JTextField nameField = new JTextField();
            JTextField startField = new JTextField("14:00");
            JTextField endField = new JTextField("16:00");
            Object[] message = {"Movie Name:", nameField, "Start (HH:mm):", startField, "End (HH:mm):", endField};

            if (JOptionPane.showConfirmDialog(this, message, "Add Show", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                try {
                    String name = nameField.getText().trim();
                    LocalTime newStart = LocalTime.parse(startField.getText());
                    LocalTime newEnd = LocalTime.parse(endField.getText());

                    if (name.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please enter a movie name.");
                        return;
                    }
                    
                    if (!newEnd.isAfter(newStart)) {
                        JOptionPane.showMessageDialog(this, "End time must be after start time!");
                        return;
                    }

                    // --- ส่วนที่ปรับปรุง: เช็กเวลาทับซ้อน (Overlap Check) ---
                    boolean isOverlapping = false;
                    String conflictingMovie = "";

                    for (Show existingShow : showList) {
                        LocalTime exStart = existingShow.getStartTime();
                        LocalTime exEnd = existingShow.getEndTime();

                        // สูตรเช็กการทับซ้อน: (StartA < EndB) AND (EndA > StartB)
                        if (newStart.isBefore(exEnd) && newEnd.isAfter(exStart)) {
                            isOverlapping = true;
                            conflictingMovie = existingShow.getMovie().getMovieName() + 
                                               " (" + exStart + " - " + exEnd + ")";
                            break;
                        }
                    }

                    if (isOverlapping) {
                        JOptionPane.showMessageDialog(this, 
                            "Time Conflict! This slot is already taken by:\n" + conflictingMovie, 
                            "Scheduling Error", 
                            JOptionPane.ERROR_MESSAGE);
                        return; 
                    }
                    // -------------------------------------------------------

                    Show s = new Show(showList.size() + 1, new Date(), newStart, newEnd, new Movie(showList.size() + 1, name));
                    showList.add(s);
                    showModel.addElement(String.format("%-18s | %s - %s", s.getMovie().getMovieName(), s.getStartTime(), s.getEndTime()));
                    
                } catch (Exception ex) { 
                    JOptionPane.showMessageDialog(this, "Format Error! Please use HH:mm"); 
                }
            }
        });

        btnBook.addActionListener(e -> handleBooking());

        btnSearch.addActionListener(e -> {
            String searchId = JOptionPane.showInputDialog(this, "Enter Ticket ID:");
            if (searchId == null)
                return;

            // ค้นหาและรวบรวมที่นั่งทั้งหมดที่มี Booking ID เดียวกัน
            String result = "";
            ArrayList<String> seatsFound = new ArrayList<>();
            Book firstMatch = null;

            for (Book b : bookingHistory) {
                if (String.valueOf(b.getBookId()).equals(searchId)) {
                    if (firstMatch == null)
                        firstMatch = b;
                    seatsFound.add(b.getSeatId().getSeatId());
                }
            }

            if (firstMatch != null) {
                result = "--- BOOKING DETAILS ---\n" +
                        "Ticket ID: " + searchId + "\n" +
                        "Movie: " + firstMatch.getShowtime().getMovie().getMovieName() + "\n" +
                        "Time: " + firstMatch.getShowtime().getStartTime() + "\n" +
                        "Seats: " + String.join(", ", seatsFound) + "\n" +
                        "Total Paid: " + (firstMatch.getPrice() * seatsFound.size()) + " THB";
                JOptionPane.showMessageDialog(this, result, "Search Result", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "ID not found.");
            }
        });

        setVisible(true);
    }

    private void handleBooking() {
        // 1. ตรวจสอบว่ามีรอบหนังในระบบหรือยัง
        if (showList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No shows available. Please add a show first!");
            return;
        }

        // 2. สร้าง Dropdown สำหรับเลือกหนัง
        String[] movieOptions = new String[showList.size()];
        for (int i = 0; i < showList.size(); i++) {
            Show s = showList.get(i);
            movieOptions[i] = s.getMovie().getMovieName() + " (" + s.getStartTime() + ")";
        }

        JComboBox<String> movieSelect = new JComboBox<>(movieOptions);
        Object[] selectionMsg = { "Select Movie Showtime:", movieSelect };

        int selectionResult = JOptionPane.showConfirmDialog(this, selectionMsg, "Book Ticket",
                JOptionPane.OK_CANCEL_OPTION);

        if (selectionResult == JOptionPane.OK_OPTION) {
            int selectedIdx = movieSelect.getSelectedIndex();
            Show selectedShow = showList.get(selectedIdx);

            // อัปเดต GUI ฝั่งซ้ายให้ตรงกับที่เลือกใน Pop-up
            showJList.setSelectedIndex(selectedIdx);

            int price = 200;

            // 3. ถามจำนวนที่นั่ง
            String countStr = JOptionPane.showInputDialog(this,
                    "How many seats for " + selectedShow.getMovie().getMovieName() + "?");
            if (countStr == null || countStr.isEmpty())
                return;

            try {
                int count = Integer.parseInt(countStr);
                ArrayList<Seat> selectedSeats = new ArrayList<>();

                // --- สร้าง Ticket ID ไว้รอแสดงใน Payment ---
                int currentBookingId = bookingHistory.size() + 1001;

                // 4. วนลูปกรอกรหัสที่นั่ง
                for (int i = 1; i <= count; i++) {
                    String sId = JOptionPane.showInputDialog(this, "Enter Seat ID #" + i + " (e.g. A1):").toUpperCase()
                            .trim();

                    Seat seat = selectedShow.getSeats().stream()
                            .filter(s -> s.getSeatId().equals(sId))
                            .findFirst().orElse(null);

                    if (seat == null) {
                        seat = new Seat(sId);
                        selectedShow.getSeats().add(seat);
                    }

                    if (seat.isBooked()) {
                        JOptionPane.showMessageDialog(this, "Seat " + sId + " is already taken!");
                        return;
                    }
                    selectedSeats.add(seat);
                }

                // 5. สรุปข้อมูล (เพิ่ม Ticket ID เข้าไปที่นี่)
                String seatListStr = selectedSeats.stream().map(Seat::getSeatId).collect(Collectors.joining(", "));
                String summary = "--- CONFIRM PAYMENT ---\n" +
                        "Ticket ID   : " + currentBookingId + "\n" + // แสดง ID ตรงนี้
                        "Movie       : " + selectedShow.getMovie().getMovieName() + "\n" +
                        "Seats       : " + seatListStr + "\n" +
                        "Total Price : " + (price * count) + " THB\n\n" +
                        "Do you want to proceed with payment?";

                if (JOptionPane.showConfirmDialog(this, summary, "Payment Confirmation",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    for (Seat s : selectedSeats) {
                        Book b = new Book();
                        b.setBookId(currentBookingId);
                        b.setPrice(price);
                        b.createBooking(selectedShow, s);
                        bookingHistory.add(b);
                    }

                    // แสดงใบเสร็จสุดท้ายอีกรอบหลังจ่ายเงินสำเร็จ
                    String finalReceipt = "--- PAYMENT SUCCESSFUL ---\n" +
                            "Ticket ID : " + currentBookingId + "\n" +
                            "Movie     : " + selectedShow.getMovie().getMovieName() + "\n" +
                            "Seats     : " + seatListStr + "\n" +
                            "Status    : PAID";
                    JOptionPane.showMessageDialog(this, finalReceipt, "Receipt", JOptionPane.INFORMATION_MESSAGE);
                    repaint();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number of seats.");
            }
        }
    }

    private void drawSeatMap(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int idx = showJList.getSelectedIndex();
        if (idx == -1)
            return;

        Show currentShow = showList.get(idx);
        int xStart = 60, yStart = 80, size = 48, gap = 15;
        String[] rows = { "A", "B", "C", "D" };

        for (int r = 0; r < 4; r++) {
            for (int c = 1; c <= 5; c++) {
                String sName = rows[r] + c;
                int x = xStart + (c - 1) * (size + gap), y = yStart + r * (size + gap);
                boolean taken = currentShow.getSeats().stream()
                        .anyMatch(s -> s.getSeatId().equals(sName) && s.isBooked());

                g2.setColor(taken ? new Color(231, 76, 60) : new Color(46, 204, 113));
                g2.fillRoundRect(x, y, size, size, 10, 10);
                g2.setColor(Color.WHITE);
                if (taken) {
                    g2.setStroke(new BasicStroke(2));
                    g2.drawLine(x + 15, y + 15, x + size - 15, y + size - 15);
                    g2.drawLine(x + size - 15, y + 15, x + 15, y + size - 15);
                } else {
                    g2.drawOval(x + 12, y + 12, 24, 24);
                }
                g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
                g2.drawString(sName, x + 15, y + 30);
            }
        }
    }

    private JButton createNavButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        SwingUtilities.invokeLater(MainGUI::new);
    }
}