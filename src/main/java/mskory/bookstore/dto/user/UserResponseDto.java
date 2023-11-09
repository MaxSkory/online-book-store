package mskory.bookstore.dto.user;

import java.util.Set;

public record UserResponseDto(
        Long id,
        String email,
        String firstName,
        String lastName,
        String shippingAddress,
        Set<String> roles
) {
}
