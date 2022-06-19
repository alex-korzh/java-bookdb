package name.dezalator.bookdb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @ManyToMany
    @JoinTable(
            name = "book_to_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;

    @ManyToMany
    @JoinTable(
            name = "book_to_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres;

    @Column(name = "image")
    private String image;

    @Column(name = "title")
    @ToString.Include
    private String title;

    @Column(name = "year")
    private int year;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "language")
    private String language;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "size")
    private int size;

    @OneToMany(targetEntity = Comment.class, mappedBy = "book", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Comment> comments;
}

