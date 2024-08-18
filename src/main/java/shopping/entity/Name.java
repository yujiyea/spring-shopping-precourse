package shopping.entity;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class Name {
    private static final int MAX_NAME_LENGTH = 15;
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9가-힣()\\s\\[\\]+\\-/&_]*$");

    private String name;

    @Builder
    public Name(String name){
        validate(name);
        this.name = name;
    }

    private void validate(String name){
        if(name==null || name.isEmpty()){
            throw new IllegalArgumentException("상품 이름은 필수 입력 사항입니다.");
        }

        if(name.length() > MAX_NAME_LENGTH){
            throw new IllegalArgumentException("상품 이름은 최대 15자까지 입력 가능합니다.");
        }

        if(!NAME_PATTERN.matcher(name).matches()){
            throw new IllegalArgumentException("상품 이름에 허용 되지 않은 특수 문자가 포함되어 있습니다. (), [], +, -, &, /, _만 가능합니다.");
        }
    }


}
