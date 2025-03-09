package com.afcruz.book_network.book;

import com.afcruz.book_network.common.PageResponse;
import com.afcruz.book_network.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookMapper bookMapper;

    private final BookRepository bookRepository;

    public void saveBook(BookRequest bookRequest, Authentication loggedUser) {
        var user = (User) loggedUser.getPrincipal();

        var book = bookMapper.toBook(bookRequest);
        book.setOwner(user);

        bookRepository.save(book);
    }

    public BookResponse findById(Integer bookId) {
        return bookRepository.findById(bookId)
                .map(bookMapper::toBookResponse)
                .orElseThrow(() -> new EntityNotFoundException("Book " + bookId + " not found!"));
    }

    public PageResponse<BookResponse> findAllBooks(int page, int size, Authentication loggedUser) {
        var user = (User) loggedUser.getPrincipal();

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Book> books = bookRepository.findAllDisplayableBooks(pageable, user.getId());
        List<BookResponse> bookResponseList = books.stream().map(bookMapper::toBookResponse).toList();

        return new PageResponse<>(
                bookResponseList, books.getNumber(), books.getSize(),
                books.getTotalElements(), books.getTotalPages(), books.isFirst(), books.isLast()
        );
    }

    public PageResponse<BookResponse> findAllBooksByOwner(int page, int size, Authentication loggedUser) {
        var user = (User) loggedUser.getPrincipal();

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Book> books = bookRepository.findAll(BookSpecification.withOwnerId(user.getId()), pageable);
        List<BookResponse> bookResponseList = books.stream().map(bookMapper::toBookResponse).toList();

        return new PageResponse<>(
                bookResponseList, books.getNumber(), books.getSize(),
                books.getTotalElements(), books.getTotalPages(), books.isFirst(), books.isLast()
        );
    }
}