package shopping.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shopping.entity.Product;
import shopping.entity.User;
import shopping.entity.WishList;
import shopping.model.response.ProductListResponse;
import shopping.repository.ProductRepository;
import shopping.repository.UserRepository;
import shopping.repository.WishListRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class WishListServiceTest {
    @Mock
    private WishListRepository wishListRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private WishListService wishListService;

    @Test
    @DisplayName("위시 리스트 상품을 추가한다.")
    void addWishList() {
        String email = "test@test.com";
        String password = "123456";

        User user = User.builder().email(email).password(password).passwordEncoder(passwordEncoder).build();
        Product product = Product.builder()
                .name("쿠키")
                .price(1500)
                .image("http://cookie.jpg")
                .build();

        given(productRepository.findById(product.getId())).willReturn(Optional.of(product));
        given(wishListRepository.findByUserAndProduct(user, product)).willReturn(Optional.empty());

        Long wishListId = wishListService.addWishList(user, product.getId());

        verify(wishListRepository, times(1)).findByUserAndProduct(user, product);
    }

    @Test
    @DisplayName("위시 리스트 상품을 조회한다.")
    void getWishList() {
        User user = User.builder().email("test@test.com").password("123456").passwordEncoder(passwordEncoder).build();
        Product product = Product.builder()
                .name("쿠키")
                .price(1500)
                .image("http://cookie.jpg")
                .build();
        WishList wishList = WishList.builder().user(user).product(product).build();

        given(productRepository.findById(product.getId())).willReturn(Optional.of(product));
        given(wishListRepository.findAllByUser(user)).willReturn(Collections.singletonList(wishList));

        ProductListResponse productListResponse = wishListService.getWishList(user);

        assertEquals(1, productListResponse.getProducts().size());
    }

    @Test
    @DisplayName("위시 리스트에서 상품을 삭제한다.")
    void deleteWishList() {
        User user = User.builder().email("test@test.com").password("123456").passwordEncoder(passwordEncoder).build();
        Product product = Product.builder()
                .name("쿠키")
                .price(1500)
                .image("http://cookie.jpg")
                .build();
        WishList wishList = WishList.builder().user(user).product(product).build();

        given(productRepository.findById(product.getId())).willReturn(Optional.of(product));
        given(wishListRepository.findByUserAndProduct(user, product)).willReturn(Optional.of(wishList));

        wishListService.deleteWishList(user, product.getId());

        verify(wishListRepository, times(1)).findByUserAndProduct(user, product);
        verify(wishListRepository, times(1)).delete(wishList);
    }
}