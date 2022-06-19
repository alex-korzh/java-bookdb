package name.dezalator.bookdb.controller;

import name.dezalator.bookdb.dto.AdminUpdateBookDto;
import name.dezalator.bookdb.model.Book;
import name.dezalator.bookdb.repository.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/books/")
public class AdminBookController {
    private final BookRepository bookRepository;

    @Autowired
    public AdminBookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public Page<Book> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return bookRepository.findAll(PageRequest.of(page, size));
    }

    @GetMapping("{id}")
    public Book getOne(@PathVariable("id") Book book) {
        return book;
    }

    @PostMapping
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PutMapping("{id}")
    public Book update(
            @PathVariable("id") Book bookFromDb,
            @RequestBody AdminUpdateBookDto book
    ) {
        BeanUtils.copyProperties(book, bookFromDb);
        return bookRepository.save(bookFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Book book) {
        bookRepository.delete(book);
    }

}
