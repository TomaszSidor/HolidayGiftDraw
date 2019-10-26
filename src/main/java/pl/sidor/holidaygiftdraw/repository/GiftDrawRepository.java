package pl.sidor.holidaygiftdraw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sidor.holidaygiftdraw.model.GiftDraw;

public interface GiftDrawRepository extends JpaRepository <GiftDraw, Long> {
}
