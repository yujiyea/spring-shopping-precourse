package shopping.model;

import lombok.*;


@Getter
@Builder
@EqualsAndHashCode
public class TokenDto {
    private String accessToken;
    private String refreshToken;

    public static TokenDto of(String accessToken, String refreshToken){
        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken).build();
    }
}
