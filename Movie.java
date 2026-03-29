public class Movie {

    private int movieId;
    private String movieName;

    public Movie(int id, String name) {
        this.movieId = id;
        this.movieName = name;
    }

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