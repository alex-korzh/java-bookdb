package name.dezalator.bookdb.repository;

import name.dezalator.bookdb.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
