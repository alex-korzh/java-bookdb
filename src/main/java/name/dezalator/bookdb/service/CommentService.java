package name.dezalator.bookdb.service;

import lombok.AllArgsConstructor;
import name.dezalator.bookdb.dto.UpdateCommentDto;
import name.dezalator.bookdb.model.Comment;
import name.dezalator.bookdb.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;

    public void updateComment(Comment comment, UpdateCommentDto updateCommentDto) {
        comment.setText(updateCommentDto.getText());
        commentRepository.save(comment);
    }

    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }
}
