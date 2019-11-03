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
    }

    public List<Gift> getAllByUserName(String username) {
        Account account = accountRepository.findByUsername(username).get();
        List<Gift> giftList = giftRepository.findByAccount(account);
        return giftList;
    }

    public void editGift(Gift gift) {
        //to do
    }

    public List<Gift> getAllUnderMaxPrice(Account account, int giftMaxPrice) {
        return giftRepository.findAllByAccountAndPriceLessThanEqual(account, giftMaxPrice);
    }
}
