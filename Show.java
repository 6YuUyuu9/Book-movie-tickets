import java.time.LocalTime;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Show {
    // Attributes จากเอกสาร [cite: 11, 21]
    private int showId; // [cite: 21]
    private Date date; // [cite: 21]
    private LocalTime startTime; // [cite: 21]
    private LocalTime endTime; // [cite: 21]
    private Movie movie; // [cite: 21]
    private List<Seat> seats; // [cite: 21]

    // --- Constructor แบบที่ 1: รับค่าครบทุกอย่าง ---
    public Show(int showId, Date date, LocalTime startTime, LocalTime endTime, Movie movie) {
        this.showId = showId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.movie = movie;
        this.seats = new ArrayList<>();
        generateDefaultSeats(); // สร้างที่นั่งเริ่มต้น
    }

    // --- Constructor แบบที่ 2: รับเฉพาะ ID และ Movie (ใช้กรณีตั้งค่าเวลาภายหลัง)
    // ---
    public Show(int showId, Movie movie) {
        this.showId = showId;
        this.movie = movie;
        this.date = new Date(); // วันที่ปัจจุบันเป็นค่าเริ่มต้น
        this.seats = new ArrayList<>();
        generateDefaultSeats();
    }

    // Method ภายในสำหรับสร้างที่นั่งเริ่มต้น [cite: 16-20]
    private void generateDefaultSeats() {
        String[] rows = { "A", "B" };
        for (String row : rows) {
            for (int i = 1; i <= 5; i++) {
                this.seats.add(new Seat(row + i, false));
            }
        }
    }

    // --- Getter & Setter สำหรับตัวแปรทุกตัว ---

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}