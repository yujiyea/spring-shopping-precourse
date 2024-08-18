package shopping.entity;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class UserTest {
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("유저를 생성한다.")
    public void createUser(){
        String rawPassword = "12345678";
        User user = User.builder()
                .email("werkd234@gmail.com")
                .password(rawPassword)
                .passwordEncoder(passwordEncoder)
                .build();

        assertThat(user.getEmail().getValue()).isEqualTo("werkd234@gmail.com");
    }
}