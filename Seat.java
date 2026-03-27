public class Seat {
    // กำหนดตัวแปรเป็น private ตาม Diagram
    private String seatId;
    private boolean isBook;

    // Constructor ตอนสร้างที่นั่งใหม่ ให้ถือว่ายังไม่มีคนจอง (false)
    public Seat(String id) {
        this.seatId = id;
        this.isBook = false; 
    }

    // Getter และ Setter
    public String getSeatId() { 
        return seatId; 
    }
    public void setSeatId(String id) { 
        this.seatId = id; 
    }

    // ตรวจสอบว่าถูกจองหรือยัง
    public boolean isBooked() { 
        return isBook; 
    }
    // เปลี่ยนสถานะเป็นถูกจองแล้ว
    public void setBooked() { 
        this.isBook = true; 
    }
}