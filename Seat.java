public class Seat {

    private String seatId;
    private boolean isBook;

    public Seat(String id) {
        this.seatId = id;
        this.isBook = false; 
    }

    public Seat(String id, boolean isBook) {
        this.seatId = id;
        this.isBook = isBook;
    }

    public String getSeatId() { 
        return seatId; 
    }
    public void setSeatId(String id) { 
        this.seatId = id; 
    }

    public boolean isBooked() { 
        return isBook; 
    }

    public void setBooked() { 
        this.isBook = true; 
    }
}