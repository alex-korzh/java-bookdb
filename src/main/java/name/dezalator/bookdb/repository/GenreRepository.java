package name.dezalator.bookdb.repository;

import name.dezalator.bookdb.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
