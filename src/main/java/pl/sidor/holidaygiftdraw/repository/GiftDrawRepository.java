package pl.sidor.holidaygiftdraw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sidor.holidaygiftdraw.model.GiftDraw;
import pl.sidor.holidaygiftdraw.model.HolidayEvent;

import java.util.List;

public interface GiftDrawRepository extends JpaRepository <GiftDraw, Long> {
    List<GiftDraw> allDrawsByHolidayEvent (HolidayEvent holidayEvent);
}
