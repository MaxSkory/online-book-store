package mskory.bookstore.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import mskory.bookstore.validation.FieldMatch;

@FieldMatch(fieldName = "password", fieldMatchName = "repeatedPassword")

public record UserUpdateRequestDto(
        @Email(regexp = "^\\w+([\\.\\-\\+]?\\w*)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$")
        String email,
        String oldPassword,
        String password,
        @Size
        String repeatedPassword,
        @Pattern(regexp = "\\w+")
        String firstName,
        @Pattern(regexp = "\\w+")
        String lastName,
        String shippingAddress
) {
}
