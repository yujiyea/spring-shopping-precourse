package shopping.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shopping.config.jwt.JwtTokenProvider;
import shopping.entity.Email;
import shopping.entity.User;
import shopping.model.TokenDto;
import shopping.model.request.SingUpRequest;
import shopping.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("회원가입 - 성공")
    void singUp_success() throws IllegalAccessException {
        String email = "test@test.com";
        String password = "123456";
        String token = "dsjklfajklfweToken";
        given(userRepository.findByEmail(new Email(email))).willReturn(Optional.empty());
        given(jwtTokenProvider.createAccessToken(any(User.class))).willReturn(token);

        TokenDto tokenDto = userService.singUp(new SingUpRequest(email,password));

        assertEquals(tokenDto.getAccessToken(), token);
        verify(userRepository, times(1)).save(any(User.class));
    }


}