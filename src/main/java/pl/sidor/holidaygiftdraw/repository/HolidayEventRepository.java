package pl.sidor.holidaygiftdraw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sidor.holidaygiftdraw.model.HolidayEvent;

public interface HolidayEventRepository extends JpaRepository<HolidayEvent, Long> {
}