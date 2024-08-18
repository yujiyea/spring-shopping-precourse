package shopping.entity;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Embeddable
@Getter
@NoArgsConstructor
public class Image {
    private static final Pattern IMAGE_URL_PATTERN = Pattern.compile("^(http://|https://).*\\.(jpg|jpeg|png|gif)$");

    private String image;

    @Builder
    public Image(String image){
        validate(image);
        this.image = image;
    }

    private void validate(String image){
        if(!IMAGE_URL_PATTERN.matcher(image).matches()){
            throw new IllegalArgumentException("이미지 URL의 형식이 올바르지 않습니다. " + image);
        }
    }
}
