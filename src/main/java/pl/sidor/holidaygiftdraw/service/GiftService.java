package pl.sidor.holidaygiftdraw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sidor.holidaygiftdraw.model.Account;
import pl.sidor.holidaygiftdraw.model.Gift;
import pl.sidor.holidaygiftdraw.repository.AccountRepository;
import pl.sidor.holidaygiftdraw.repository.GiftRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class GiftService {
    @Autowired
    private GiftRepository giftRepository;
    @Autowired
    private AccountRepository accountRepository;


    public void addGift(Gift gift, String username) {
        Account account = accountRepository.findByUsername(username).get();
        gift.setAccount(account);
        giftRepository.save(gift);
        account.getGiftSet().add(gift);

    }

    public List<Gift> getAllByUserName(String username) {
        List<Gift> giftList = new ArrayList<>();
        Gift gift = giftRepository.findByAccount(accountRepository.findByUsername(username).get()).get();
        if (gift.getAccount().getUsername().equals(username)){
            giftList.add(gift);
        }
        return giftList;
    }
}