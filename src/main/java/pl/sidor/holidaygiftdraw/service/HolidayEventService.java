package pl.sidor.holidaygiftdraw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sidor.holidaygiftdraw.model.HolidayEvent;
import pl.sidor.holidaygiftdraw.model.dto.EventCreationRequest;
import pl.sidor.holidaygiftdraw.repository.HolidayEventRepository;

@Service
public class HolidayEventService {
    @Autowired
    private HolidayEventRepository holidayEventRepository;
    public void add(HolidayEvent holidayEvent, EventCreationRequest request) {

        holidayEvent.setId(null);
        holidayEventRepository.save(holidayEvent);

    }
}
