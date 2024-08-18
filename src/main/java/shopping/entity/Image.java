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

    private String imageUrl;

    @Builder
    public Image(String imageUrl){
        validate(imageUrl);
        this.imageUrl = imageUrl;
    }

    private void validate(String imageUrl){
        if(!IMAGE_URL_PATTERN.matcher(imageUrl).matches()){
            throw new IllegalArgumentException("이미지 URL의 형식이 올바르지 않습니다. " + imageUrl);
        }
    }
}
