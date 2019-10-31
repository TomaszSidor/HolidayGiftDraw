package pl.sidor.holidaygiftdraw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sidor.holidaygiftdraw.model.HolidayEvent;

import java.util.List;
import java.util.Optional;

public interface HolidayEventRepository extends JpaRepository<HolidayEvent, Long> {
    Optional<HolidayEvent> findByName(String name);
    List<HolidayEvent> findAllByIsDrawn(boolean isDrawn);

    Optional<HolidayEvent> findByIdentifier(String uuid);
//    Optional<HolidayEvent> holidayEventOptional = holidayEventService.findById(id);

}
