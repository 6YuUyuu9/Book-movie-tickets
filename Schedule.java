import java.util.Date; // ต้อง import เพื่อใช้งานเรื่องวันที่

public class Schedule {
    // กำหนดตัวแปรเป็น private ตาม Diagram
    private Date date;
    private Show showtime; // อ้างอิงถึง Class Show

    // Constructor
    public Schedule(Date d, Show s) {
        this.date = d;
        this.showtime = s;
    }

    // Getter และ Setter
    public Date getDate() { 
        return date; 
    }
    public void setDate(Date d) { 
        this.date = d; 
    }

    public Show getShowtime() { 
        return showtime; 
    }
    public void setShowtime(Show s) { 
        this.showtime = s; 
    }
}