package io.ingramchen.association.service;

import io.ingramchen.association.model.account.Account;
import io.ingramchen.association.model.account.AccountDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AccountService
{
    @Autowired
    private AccountDao accountDao;

    public Account createAccount(final String name)
    {
        return accountDao.create(name);
    }
}
