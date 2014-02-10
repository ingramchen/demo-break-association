package io.ingramchen.association.model.social;

import io.ingramchen.association.model.account.Account;

import java.util.UUID;

public class ItemShared
{
    public static ItemShared create(final ShareableEntity shareable, final Account sharer)
    {
        return new ItemShared(UUID.randomUUID().toString(), shareable.getShareableId(), sharer);
    }

    private final String itemSharedId;
    private final String shareableId;
    private final Account sharer;

    ItemShared(final String itemSharedId, final String shareableId, final Account sharer)
    {
        this.itemSharedId = itemSharedId;
        this.shareableId = shareableId;
        this.sharer = sharer;
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
        if (!(obj instanceof ItemShared))
        {
            return false;
        }
        final ItemShared other = (ItemShared) obj;
        if (itemSharedId == null)
        {
            if (other.itemSharedId != null)
            {
                return false;
            }
        }
        else if (!itemSharedId.equals(other.itemSharedId))
        {
            return false;
        }
        return true;
    }

    String getItemSharedId()
    {
        return itemSharedId;
    }

    public String getShareableId()
    {
        return shareableId;
    }

    public Account getSharer()
    {
        return sharer;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((itemSharedId == null) ? 0 : itemSharedId.hashCode());
        return result;
    }

    @Override
    public String toString()
    {
        return "ItemShared [itemSharedId=" + itemSharedId
            + ", shareableId="
            + shareableId
            + ", sharerr="
            + sharer
            + "]";
    }

}
