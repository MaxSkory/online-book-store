package mskory.bookstore.model;

import jakarta.persistence.Convert;

@Convert
public enum RoleName {
    USER,
    ADMIN
}
