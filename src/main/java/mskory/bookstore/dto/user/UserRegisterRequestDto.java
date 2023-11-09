package mskory.bookstore.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import mskory.bookstore.validation.FieldMatch;

@FieldMatch(fieldName = "password", fieldMatchName = "repeatedPassword")
public record UserRegisterRequestDto(
        @NotBlank
        @Email(regexp = "^\\w+([\\.\\-\\+]?\\w*)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$")
        String email,
        @NotBlank
        String password,
        @NotBlank
        String repeatedPassword,
        @NotBlank
        @Pattern(regexp = "\\w+")
        String firstName,
        @NotBlank
        @Pattern(regexp = "\\w+")
        String lastName,
        String shippingAddress
) {
}
