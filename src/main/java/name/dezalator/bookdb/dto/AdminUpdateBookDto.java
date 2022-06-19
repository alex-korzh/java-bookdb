package name.dezalator.bookdb.dto;

import lombok.Data;

@Data
public class AdminUpdateBookDto {
    private String image;
    private String title;
    private int year;
    private String publisher;
    private String language;
    private String isbn;
    private int size;
}
