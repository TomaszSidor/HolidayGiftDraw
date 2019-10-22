package pl.sidor.holidaygiftdraw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sidor.holidaygiftdraw.model.HolidayEvent;

import java.util.Optional;

public interface HolidayEventRepository extends JpaRepository<HolidayEvent, Long> {
    Optional<HolidayEvent> findByName(String name);

}
