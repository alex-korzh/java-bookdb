package name.dezalator.bookdb.repository;

import name.dezalator.bookdb.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
