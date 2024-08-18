package shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shopping.entity.Product;
import shopping.entity.User;
import shopping.entity.WishList;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {
    Optional<WishList> findByUserAndProduct(User user, Product product);
    List<WishList> findAllByUser(User user);
}
