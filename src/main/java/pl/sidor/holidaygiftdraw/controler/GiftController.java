package pl.sidor.holidaygiftdraw.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sidor.holidaygiftdraw.model.Gift;
import pl.sidor.holidaygiftdraw.service.GiftService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(path = "/gift")
public class GiftController {
    @Autowired
    private GiftService giftService;
    private Principal principal;
    private Model model;

    @GetMapping("")
    public String getGiftsList(Principal principal, Model model){
        this.principal = principal;
        this.model = model;
        List<Gift> accountList = giftService.getAll(principal.getName());
        model.addAttribute("accounts", accountList);

        return "gift-list";
    }



}
