package shopping.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    PRODUCT_NOT_FOUND(40401, "Product를 찾지 못했습니다."),
    USER_NOT_FOUND(40401, "유저를 찾지 못했습니다.");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
