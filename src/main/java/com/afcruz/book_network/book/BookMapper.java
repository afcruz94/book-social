package com.afcruz.book_network.book;

import org.springframework.stereotype.Service;

@Service
public class BookMapper {
    public Book toBook(BookRequest bookRequest) {
        return Book
                .builder()
                .id(bookRequest.id())
                .title(bookRequest.title())
                .authorName(bookRequest.authorName())
                .isbn(bookRequest.isbn())
                .synopsis(bookRequest.synopsis())
                .isArchived(false)
                .isShareable(bookRequest.isShareable())
                .build();
    }

    public BookResponse toBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authorName(book.getAuthorName())
                .isbn(book.getIsbn())
                .synopsis(book.getSynopsis())
                .isShareable(book.isShareable())
                .isArchived(book.isArchived())
                .rate(book.getRate())
                .owner(book.getOwner().getFullName())
                //.cover(book.getBookCover().getBytes())
                .build();
    }
}