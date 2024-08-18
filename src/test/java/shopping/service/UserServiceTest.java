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
import shopping.entity.Password;
import shopping.entity.User;
import shopping.model.TokenDto;
import shopping.model.request.LoginRequest;
import shopping.model.request.SignUpRequest;
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
    void signUp_success() throws IllegalAccessException {
        String email = "test@test.com";
        String password = "123456";
        String token = "dsjklfajklfweToken";
        given(userRepository.findByEmail(new Email(email))).willReturn(Optional.empty());
        given(jwtTokenProvider.createAccessToken(any(User.class))).willReturn(token);

        TokenDto tokenDto = userService.signUp(new SignUpRequest(email,password));

        assertEquals(tokenDto.getAccessToken(), token);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("로그인 성공")
    void signIn_success(){
        String email = "test@test.com";
        String password = "123456";
        String encodePassword = "encodePassword";
        String token = "dsjklfajklfweToken";
        User user = new User(email, password, passwordEncoder);

        given(userRepository.findByEmail(new Email(email))).willReturn(Optional.of(user));
        given(new Password(password, passwordEncoder).checkPassword(password, passwordEncoder)).willReturn(true);
        given(jwtTokenProvider.createAccessToken(any(User.class))).willReturn(token);

        TokenDto tokenDto = userService.login(new LoginRequest(email,password));

        assertEquals(tokenDto.getAccessToken(), token);
        verify(userRepository, times(1)).findByEmail(new Email(email));
    }

}