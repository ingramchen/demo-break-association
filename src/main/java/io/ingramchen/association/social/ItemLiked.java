package io.ingramchen.association.social;

import io.ingramchen.association.account.Account;

import java.util.UUID;

public class ItemLiked
{
    public static ItemLiked create(final LikeableEntity likeable, final Account liker)
    {
        return new ItemLiked(UUID.randomUUID().toString(), likeable.getLikeableId(), liker);
    }

    private final String itemLikedId;
    private final String likeableId;

    private final Account liker;

    ItemLiked(final String itemLikedId, final String likeableId, final Account liker)
    {
        super();
        this.itemLikedId = itemLikedId;
        this.likeableId = likeableId;
        this.liker = liker;
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
        if (!(obj instanceof ItemLiked))
        {
            return false;
        }
        final ItemLiked other = (ItemLiked) obj;
        if (itemLikedId == null)
        {
            if (other.itemLikedId != null)
            {
                return false;
            }
        }
        else if (!itemLikedId.equals(other.itemLikedId))
        {
            return false;
        }
        return true;
    }

    String getItemLikedId()
    {
        return itemLikedId;
    }

    public String getLikeableId()
    {
        return likeableId;
    }

    public Account getLiker()
    {
        return liker;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((itemLikedId == null) ? 0 : itemLikedId.hashCode());
        return result;
    }

    @Override
    public String toString()
    {
        return "ItemLiked [itemLikedId=" + itemLikedId
            + ", likeableId="
            + likeableId
            + ", liker="
            + liker
            + "]";
    }

}
