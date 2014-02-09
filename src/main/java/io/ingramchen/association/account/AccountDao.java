package io.ingramchen.association.account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao
{
    public static RowMapper<Account> accountMapper()
    {
        return accountMapper("account_id");
    }

    public static RowMapper<Account> accountMapper(final String idName)
    {
        return new RowMapper<Account>()
        {
            @Override
            public Account mapRow(final ResultSet rs, final int rowNum) throws SQLException
            {
                return new Account(rs.getString(idName), rs.getString("name"));
            }
        };
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Account create(final String name)
    {
        final Account account = Account.create(name);
        jdbcTemplate.update("INSERT INTO Account (account_id, name) VALUES (?,?)",
            account.getAccountId(),
            account.getName());
        return account;
    }

    public Account findById(final String accountId)
    {
        final List<Account> results = jdbcTemplate.query("SELECT * FROM Account WHERE account_id = ?",
            accountMapper(),
            accountId);
        return results.isEmpty() ? null : results.get(0);
    }
}
