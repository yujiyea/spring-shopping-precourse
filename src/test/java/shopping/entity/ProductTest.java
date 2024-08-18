package shopping.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ProductTest {
    @Test
    @DisplayName("이름, 가격, 이미지 url을 받아 상품을 생성한다.")
    void createProduct(){
        Name name = new Name("쿠키");

        Product product = Product.builder()
                .name("쿠키").price(1500).image("http://test.jpg").build();

        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getPrice()).isEqualTo(1500);
    }

    @Test
    @DisplayName("이름, 가격 이미지 url을 받아 상품을 생성한다. - 실패(잘못된 url형식)")
    void createProductFail(){
        assertThatIllegalArgumentException().isThrownBy(()-> Product.builder()
                .name("쿠키").price(1500).image("http://test.com").build());
    }
}