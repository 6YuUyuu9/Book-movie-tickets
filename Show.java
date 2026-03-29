import java.time.LocalTime;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Show {
    private int showId;
    private Date date; 
    private LocalTime startTime; 
    private LocalTime endTime; 
    private Movie movie; 
    private List<Seat> seats; 

    public Show(int showId, Date date, LocalTime startTime, LocalTime endTime, Movie movie) {
        this.showId = showId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.movie = movie;
        this.seats = new ArrayList<>();
        generateDefaultSeats(); 
    }

    public Show(int showId, Movie movie) {
        this.showId = showId;
        this.movie = movie;
        this.date = new Date(); 
        this.seats = new ArrayList<>();
        generateDefaultSeats();
    }

    private void generateDefaultSeats() {
        String[] rows = { "A", "B", "C", "D" };
        for (String row : rows) {
            for (int i = 1; i <= 5; i++) {
                this.seats.add(new Seat(row + i, false));
            }
        }
    }

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