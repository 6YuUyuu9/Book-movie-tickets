public class Book {
    private int bookId; // [cite: 81]
    private Show showtime; // [cite: 82]
    private int seatCount; // [cite: 83]
    private Seat seatId; // เปลี่ยนเป็นประเภท Seat ตาม Diagram ใหม่ [cite: 84]
    private int price; // [cite: 85]

    // --- Constructor 1: กำหนดค่าครบ ---
    public Book(int bookId, Show showtime, int seatCount, Seat seatId, int price) {
        this.bookId = bookId;
        this.showtime = showtime;
        this.seatCount = seatCount;
        this.seatId = seatId;
        this.price = price;
    }

    // --- Constructor 2: สำหรับเริ่มสร้างการจองใหม่ ---
    public Book() {
        this.seatCount = 0;
        this.price = 0;
    }

    // --- Methods ตาม Diagram [cite: 100] ---
    public void createBooking(Show showtime, Seat seatId) {
        this.showtime = showtime;
        this.seatId = seatId;
        this.seatCount = 1; // ตัวอย่างจองทีละ 1 ที่นั่ง
        this.seatId.setBooked(); // เปลี่ยนสถานะที่นั่งใน Object Seat ทันที [cite: 104]
        System.out.println("Booking Created Successfully!");
    }

    // --- Getter & Setter ครบทุกตัว [cite: 86-89, 96-99] ---
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Show getShowtime() {
        return showtime;
    } // [cite: 87]

    public void setShowtime(Show showtime) {
        this.showtime = showtime;
    } // [cite: 86]

    public int getSeatCount() {
        return seatCount;
    } // [cite: 89]

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    } // [cite: 88]

    public Seat getSeatId() {
        return seatId;
    } // [cite: 97]

    public void setSeatId(Seat seatId) {
        this.seatId = seatId;
    } // [cite: 96]

    public int getPrice() {
        return price;
    } // [cite: 99]

    public void setPrice(int price) {
        this.price = price;
    } // [cite: 98]
}