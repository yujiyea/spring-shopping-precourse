package shopping.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.regex.Pattern;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Email {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)" +
            "*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

    private String value;

    @Builder
    public Email(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if(!EMAIL_PATTERN.matcher(value).matches()){
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }
    }
}
