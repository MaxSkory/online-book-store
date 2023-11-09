package mskory.bookstore.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDto(
        @NotBlank
        @Email(regexp = "^\\w+([\\.\\-\\+]?\\w*)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$")
        String email,
        @Size(min = 6, max = 100)
        @Pattern(regexp = "\\w+")
        String password
) {
}
