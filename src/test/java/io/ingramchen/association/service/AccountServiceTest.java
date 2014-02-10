package io.ingramchen.association.service;

import static org.junit.Assert.assertEquals;
import io.ingramchen.association.JdbcTestCases;
import io.ingramchen.association.model.account.Account;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountServiceTest extends JdbcTestCases
{
    @Autowired
    private AccountService service;

    @Test
    public void createAccount() throws Exception
    {
        final Account alice = service.createAccount("Alice");
        assertEquals("Alice", alice.getName());
        assertEquals(alice, accountDao.findById(alice.getAccountId()));
    }
}
