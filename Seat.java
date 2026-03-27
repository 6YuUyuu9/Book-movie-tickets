public class Seat {
    // กำหนดตัวแปรเป็น private ตาม Diagram
    private String seatId;
    private boolean isBook;

    // Constructor ตัวที่ 1: รับแค่ชื่อที่นั่ง (กำหนดให้ว่างอัตโนมัติ)
    public Seat(String id) {
        this.seatId = id;
        this.isBook = false; 
    }

    // Constructor ตัวที่ 2: รับทั้งชื่อที่นั่งและสถานะ (เพิ่มตัวนี้เข้ามาเพื่อแก้ Error)
    public Seat(String id, boolean isBook) {
        this.seatId = id;
        this.isBook = isBook;
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