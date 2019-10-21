package pl.sidor.holidaygiftdraw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sidor.holidaygiftdraw.model.Gift;

public interface GiftRepository extends JpaRepository <Gift, Long> {
}
