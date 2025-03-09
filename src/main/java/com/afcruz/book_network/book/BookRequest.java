package com.afcruz.book_network.book;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BookRequest(
        Integer id,
        @NotNull(message = "Book title is mandatory")
        @NotEmpty(message = "Book title is mandatory")
        String title,
        @NotNull(message = "Book author is mandatory")
        @NotEmpty(message = "Book author is mandatory")
        String authorName,
        @NotNull(message = "Book ISBN is mandatory")
        @NotEmpty(message = "Book ISBN is mandatory")
        String isbn,
        @NotNull(message = "Book synopsis is mandatory")
        @NotEmpty(message = "Book synopsis is mandatory")
        String synopsis,
        boolean isShareable,
        boolean isArchived) {
}