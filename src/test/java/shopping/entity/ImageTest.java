package shopping.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ImageTest {
    @ParameterizedTest
    @DisplayName("이미지 URL 형식이 잘못되었습니다.")
    @ValueSource(strings = {"htt", "https://test.com.jpgs", "https:/test.png", "http://test.gifp"})
    void checkImageUrlFail(String imageUrl){
        assertThatThrownBy(()-> new Image(imageUrl)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("이미지 생성")
    @ValueSource(strings = {"http://test.jpg", "http://test.png", "http://test.gif", "http://test.jpeg", "https://test.jpg",
    "https://test.png", "https://test.gif", "https://test.jpeg"})
    void createImage(String imageUrl){
        Image image = new Image(imageUrl);

        assertThat(image.getImage()).isEqualTo(imageUrl);
    }
}