public class Movie {
    // กำหนดตัวแปรเป็น private ตาม Diagram
    private int movieId;
    private String movieName;

    // Constructor เอาไว้สร้างข้อมูลหนังตอนเริ่มโปรแกรม
    public Movie(int id, String name) {
        this.movieId = id;
        this.movieName = name;
    }

    // Getter และ Setter
    public int getMovieId() { 
        return movieId; 
    }
    public void setMovieId(int id) { 
        this.movieId = id; 
    }

    public String getMovieName() { 
        return movieName; 
    }
    public void setMovieName(String name) { 
        this.movieName = name; 
    }
}