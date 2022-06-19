package name.dezalator.bookdb.controller;

import name.dezalator.bookdb.dto.UpdateCommentDto;
import name.dezalator.bookdb.model.Comment;
import name.dezalator.bookdb.service.CommentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/comments/")
public class AdminCommentController {
    private final CommentService commentService;

    public AdminCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PutMapping("{id}")
    public void moderateComment(@PathVariable("id") Comment comment, @RequestBody UpdateCommentDto updateCommentDto) {
        commentService.updateComment(comment, updateCommentDto);
    }

    @DeleteMapping("{id}")
    public void deleteComment(@PathVariable("id") Comment comment) {
        commentService.deleteComment(comment);
    }

}
