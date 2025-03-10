package DataAccess.DTO;

public class BookDTO {

    private Integer id;
    private String title;
    private String author;
    private String publisher;
    private Integer year;
    private Boolean available;

    public BookDTO() {
    }

    public BookDTO(String title, String author, String publisher, Integer year, Boolean available) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.available = available;
    }

    public BookDTO(Integer id, String title, String author, String publisher, Integer year, Boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.available = available;
    }

    public BookDTO(Integer id, String title, String author, String publisher, Integer year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Id: " + id +
                "\nTitle: " + title +
                "\nAuthor: " + author +
                "\nPublisher: " + publisher +
                "\nYear: " + year +
                "\nAvailable: " + available + "\n";
    }
}
