package pl.sidor.holidaygiftdraw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sidor.holidaygiftdraw.model.Gift;
import pl.sidor.holidaygiftdraw.repository.AccountRepository;
import pl.sidor.holidaygiftdraw.repository.GiftRepository;

import java.util.List;

@Service
public class GiftService {
    @Autowired
    private GiftRepository giftRepository;
    @Autowired
    private AccountRepository accountRepository;

    public List<Gift> getAll(String username) {
        List<Gift> giftsList = giftRepository.findAll();
        return giftsList;
    }
}
