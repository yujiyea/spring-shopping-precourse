package shopping.entity;

import jakarta.persistence.Embeddable;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Password {
    private String value;

    @Builder
    public Password(String rawPassword, PasswordEncoder encoder) {
        value = encoder.encode(rawPassword);
    }

    public boolean checkPassword(String rawPassword, PasswordEncoder encoder) {
        return encoder.matches(value, rawPassword);
    }
}
