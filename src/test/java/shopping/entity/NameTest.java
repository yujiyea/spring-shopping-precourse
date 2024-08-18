package shopping.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NameTest {
    @Test
    @DisplayName("상품 이름은 최대 15자까지 입력할 수 있다.")
    void checkProductNameLength(){
        assertThatThrownBy(()-> new Name("아아아아                    아")).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품 이름은 최대");
    }

    @Test
    @DisplayName("상품 이름에 (), [], +, -, &, /, _ 이외의 특수 문자가 들어온 경우 - 실패")
    void checkProductNameCharFail(){
        assertThatThrownBy(() -> new Name("(과자)엄마손파이%%$")).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("(), [], +, -, &, /, _");
    }

    @Test
    @DisplayName("상품 이름 생성")
    void createName(){
        Name name = new Name("(과자)엄마손파이++");
        assertThat(name.getName()).isEqualTo("(과자)엄마손파이++");
    }
}