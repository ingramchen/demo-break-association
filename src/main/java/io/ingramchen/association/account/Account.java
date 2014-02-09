package io.ingramchen.association.account;

import java.util.UUID;

public class Account
{
    public static Account create(final String name)
    {
        return new Account(UUID.randomUUID().toString(), name);
    }

    private final String accountId;

    private final String name;

    Account(final String accountId, final String name)
    {
        this.accountId = accountId;
        this.name = name;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof Account))
        {
            return false;
        }
        final Account other = (Account) obj;
        if (accountId == null)
        {
            if (other.accountId != null)
            {
                return false;
            }
        }
        else if (!accountId.equals(other.accountId))
        {
            return false;
        }
        return true;
    }

    public String getAccountId()
    {
        return accountId;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
        return result;
    }

    @Override
    public String toString()
    {
        return "Account [accountId=" + accountId + ", name=" + name + "]";
    }

}
