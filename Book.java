public class Book {
    private int bookId; 
    private Show showtime; 
    private int seatCount; 
    private Seat seatId; 
    private int price; 

    public Book(int bookId, Show showtime, int seatCount, Seat seatId, int price) {
        this.bookId = bookId;
        this.showtime = showtime;
        this.seatCount = seatCount;
        this.seatId = seatId;
        this.price = price;
    }

    public Book() {
        this.seatCount = 0;
        this.price = 0;
    }

    public void createBooking(Show showtime, Seat seatId) {
        this.showtime = showtime;
        this.seatId = seatId;
        this.seatCount = 1; 
        this.seatId.setBooked(); 
        System.out.println("Booking Created Successfully!");
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Show getShowtime() {
        return showtime;
    } 

    public void setShowtime(Show showtime) {
        this.showtime = showtime;
    } 

    public int getSeatCount() {
        return seatCount;
    } 

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    } 

    public Seat getSeatId() {
        return seatId;
    } 

    public void setSeatId(Seat seatId) {
        this.seatId = seatId;
    } 

    public int getPrice() {
        return price;
    } 

    public void setPrice(int price) {
        this.price = price;
    } 
}