package mskory.bookstore.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class Encryptor {
    private static final String ENCRYPTION_TYPE = "AES";
    private final Cipher cipher;
    private final Key key;

    @SneakyThrows
    public Encryptor() {
        cipher = Cipher.getInstance(ENCRYPTION_TYPE);
        key = new SecretKeySpec(new SecureRandom().generateSeed(32), ENCRYPTION_TYPE);
    }

    @SneakyThrows
    public String encrypt(String text) {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        byte[] encoded = Base64.getEncoder().encode(encrypted);
        return new String(encoded);
    }

    @SneakyThrows
    public String decrypt(String text) {
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(text));
        return new String(bytes);
    }
}
