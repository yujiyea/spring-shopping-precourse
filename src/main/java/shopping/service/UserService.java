package shopping.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shopping.config.jwt.JwtTokenProvider;
import shopping.entity.Email;
import shopping.entity.User;
import shopping.model.TokenDto;
import shopping.model.request.SingUpRequest;
import shopping.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    public TokenDto singUp(SingUpRequest singUpRequest) throws IllegalAccessException {
        Optional<User> existUser = userRepository.findByEmail(new Email(singUpRequest.getEmail()));
        if (existUser.isPresent()) {
            throw new IllegalAccessException("이메일이 이미 사용되고 있습니다.");
        }

        User user = User.builder()
                .email(singUpRequest.getEmail())
                .password(singUpRequest.getPassword())
                .passwordEncoder(passwordEncoder)
                .build();

        userRepository.save(user);

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);
        return TokenDto.of(accessToken,refreshToken);
    }
}
