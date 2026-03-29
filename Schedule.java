import java.util.Date; 

public class Schedule {

    private Date date;
    private Show showtime; 

    public Schedule(Date d, Show s) {
        this.date = d;
        this.showtime = s;
    }

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