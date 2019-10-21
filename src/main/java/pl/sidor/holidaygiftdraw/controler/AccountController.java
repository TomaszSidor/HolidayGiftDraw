package pl.sidor.holidaygiftdraw.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sidor.holidaygiftdraw.service.AccountService;

@Controller
public class AccountController {
    @Autowired
    private  AccountService accountService;

}
