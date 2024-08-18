package shopping.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shopping.common.exception.ErrorCode;
import shopping.common.exception.NotFoundException;
import shopping.config.jwt.JwtTokenProvider;
import shopping.entity.Email;
import shopping.entity.User;
import shopping.model.TokenDto;
import shopping.model.request.LoginRequest;
import shopping.model.request.SignUpRequest;
import shopping.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    public TokenDto signUp(SignUpRequest signUpRequest) throws IllegalAccessException {
        Optional<User> existUser = userRepository.findByEmail(new Email(signUpRequest.getEmail()));
        if (existUser.isPresent()) {
            throw new IllegalAccessException("이메일이 이미 사용되고 있습니다.");
        }

        User user = User.builder()
                .email(signUpRequest.getEmail())
                .password(signUpRequest.getPassword())
                .passwordEncoder(passwordEncoder)
                .build();

        userRepository.save(user);

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);
        return TokenDto.of(accessToken,refreshToken);
    }

    public TokenDto login(LoginRequest loginRequest){
        User user = userRepository.findByEmail(new Email(loginRequest.getEmail())).orElseThrow(()-> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        if(!user.getPassword().checkPassword(loginRequest.getPassword(), passwordEncoder)){
            throw new RuntimeException("email 또는 password가 틀렸습니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);
        return TokenDto.of(accessToken,refreshToken);
    }
}
