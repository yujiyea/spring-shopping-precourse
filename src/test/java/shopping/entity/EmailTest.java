package shopping.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class EmailTest {
    @ParameterizedTest
    @DisplayName("이메일 형식이 올바르지 않습니다.")
    @ValueSource(strings = {"sdjfklweljkjkldskl.dosk", "sksjek@DOFMSK", "아거자@spdkfj.com"})
    void createEmail_fail(String email){
        assertThatThrownBy(()->new Email(email)).isInstanceOf(IllegalArgumentException.class);
    }

}