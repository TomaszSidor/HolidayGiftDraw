package pl.sidor.holidaygiftdraw.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sidor.holidaygiftdraw.model.HolidayEvent;
import pl.sidor.holidaygiftdraw.model.dto.EventCreationRequest;
import pl.sidor.holidaygiftdraw.service.HolidayEventService;

import javax.validation.Valid;

@Controller
@RequestMapping(path ="/holidayevent/")
public class HolidayEventController {
    @Autowired
    private HolidayEventService holidayEventService;

    @GetMapping ("")
    public String getHolidayEventPage(){ return "holidayEvent";}

    @GetMapping("/add")
    public String getHolidayEventForm(){
        return "holidayEvent-form";}

    @PostMapping("/add")
    public String createNewHolidayEvent(HolidayEvent holidayEvent, EventCreationRequest request){
        holidayEventService.add(holidayEvent, request);
        return "redirect:/holidayevent/list";
    }


}
