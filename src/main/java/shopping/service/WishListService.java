package shopping.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.common.exception.ErrorCode;
import shopping.common.exception.NotFoundException;
import shopping.entity.Product;
import shopping.entity.User;
import shopping.entity.WishList;
import shopping.model.response.ProductListResponse;
import shopping.repository.ProductRepository;
import shopping.repository.UserRepository;
import shopping.repository.WishListRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishListService {
    private final WishListRepository wishListRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Long addWishList(User user, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));

        if(wishListRepository.findByUserAndProduct(user, product).isPresent()) {
            throw new RuntimeException("이미 위시리스트에 해당 상품이 추가되어있습니다.");
        }

        WishList wishList = new WishList(user, product);
        wishListRepository.save(wishList);

        return wishList.getId();
    }

    @Transactional
    public ProductListResponse getWishList(User user) {
        List<Product> productList = wishListRepository.findAllByUser(user)
                .stream()
                .map(WishList::getProduct)
                .toList();

        return ProductListResponse.of(productList);
    }

    @Transactional
    public void deleteWishList(User user, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        WishList wishList = wishListRepository.findByUserAndProduct(user, product).orElseThrow(()-> new NotFoundException(ErrorCode.WISHLIST_NOT_FOUND));

        wishListRepository.delete(wishList);
    }
}
