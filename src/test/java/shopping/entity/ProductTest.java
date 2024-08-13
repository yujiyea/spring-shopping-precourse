package shopping.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {
    @Test
    @DisplayName("이름, 가격, 이미지 url을 받아 상품을 생성한다.")
    void createProduct(){
        Product product = Product.builder()
                .name("쿠키").price(1500).image("http://test.com").build();

        assertThat(product.getName()).isEqualTo("쿠키");
        assertThat(product.getPrice()).isEqualTo(1500);
    }

}