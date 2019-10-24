package pl.sidor.holidaygiftdraw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sidor.holidaygiftdraw.model.Account;
import pl.sidor.holidaygiftdraw.model.Gift;

import java.util.List;
import java.util.Optional;

public interface GiftRepository extends JpaRepository <Gift, Long> {
    List<Gift> findByAccount (Account account);

}
