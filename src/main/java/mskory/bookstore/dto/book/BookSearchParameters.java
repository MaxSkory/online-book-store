package mskory.bookstore.dto.book;

import mskory.bookstore.validation.EachMatches;

public record BookSearchParameters(
        String[] authors,
        @EachMatches(regex = "\\d+(\\.\\d+)?")
        String[] price
) {
}
