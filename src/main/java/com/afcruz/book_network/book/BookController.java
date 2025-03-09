package com.afcruz.book_network.book;

import com.afcruz.book_network.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
@Tag(name = "book")
public class BookController {
    private final BookService bookService;

    @PostMapping("/save")
    public ResponseEntity<?> saveBook(@Valid @RequestBody BookRequest bookRequest, Authentication loggedUser) {
        bookService.saveBook(bookRequest, loggedUser);

        return ResponseEntity.accepted().build();
    }

    @GetMapping("{book-id}")
    public ResponseEntity<BookResponse> findBookById(@PathVariable("book-id") Integer bookId) {
        return ResponseEntity.ok(bookService.findById(bookId));
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication loggedUser) {
        return ResponseEntity.ok(bookService.findAllBooks(page, size, loggedUser));
    }

    @GetMapping("/owner")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooksByOwner(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication loggedUser
    ) {
        return ResponseEntity.ok(bookService.findAllBooksByOwner(page, size, loggedUser));
    }
}
