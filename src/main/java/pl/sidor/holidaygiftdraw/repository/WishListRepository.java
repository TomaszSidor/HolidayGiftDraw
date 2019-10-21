package pl.sidor.holidaygiftdraw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sidor.holidaygiftdraw.model.WishList;

public interface WishListRepository extends JpaRepository <WishList, Long> {
}
