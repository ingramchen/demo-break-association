package io.ingramchen.association.social;

import io.ingramchen.association.account.Account;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.google.common.annotations.VisibleForTesting;

@Repository
public class SocialDao
{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void deleteLikeable(final String likeableId)
    {
        jdbcTemplate.update("DELETE FROM LikeableEntity WHERE likeable_id = ? ", likeableId);
    }

    @VisibleForTesting
    LikeableEntity generateLikeable()
    {
        final String id = UUID.randomUUID().toString();
        insertLikeableId(id);
        return new LikeableEntity()
        {
            @Override
            public String getLikeableId()
            {
                return id;
            }
        };
    }

    @VisibleForTesting
    ShareableEntity generateShareable()
    {
        final String id = UUID.randomUUID().toString();
        insertShareableId(id);
        return new ShareableEntity()
        {
            @Override
            public String getShareableId()
            {
                return id;
            }
        };
    }

    public int getLikedCount()
    {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ItemLiked", Integer.class);
    }

    public int getSharedCount()
    {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ItemShared", Integer.class);
    }

    private void insertLikeableId(final String id)
    {
        jdbcTemplate.update("INSERT INTO LikeableEntity (likeable_id) VALUES (?)", id);
    }

    private void insertShareableId(final String id)
    {
        jdbcTemplate.update("INSERT INTO ShareableEntity (shareable_id) VALUES (?)", id);
    }

    public boolean isLiked(final LikeableEntity likeable)
    {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ItemLiked WHERE likeable_id = ?",
            Integer.class,
            likeable.getLikeableId()) > 0;
    }

    public boolean isSavedAsLikeable(final LikeableEntity likeable)
    {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM LikeableEntity WHERE likeable_id = ?",
            Integer.class,
            likeable.getLikeableId()) > 0;
    }

    public boolean isSavedAsShareable(final ShareableEntity shareable)
    {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ShareableEntity WHERE shareable_id = ?",
            Integer.class,
            shareable.getShareableId()) > 0;
    }

    public boolean isShared(final ShareableEntity shareable)
    {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ItemShared WHERE shareable_id = ?",
            Integer.class,
            shareable.getShareableId()) > 0;
    }

    public ItemLiked like(final LikeableEntity likeable, final Account liker)
    {
        final ItemLiked liked = ItemLiked.create(likeable, liker);
        jdbcTemplate.update("INSERT INTO ItemLiked (item_liked_id, likeable_id, liker_id) VALUES (?,?,?)",
            liked.getItemLikedId(),
            liked.getLikeableId(),
            liked.getLiker().getAccountId());
        return liked;
    }

    public void makeLikeable(final LikeableEntity likeable)
    {
        insertLikeableId(likeable.getLikeableId());
    }

    public void makeShareable(final ShareableEntity shareable)
    {
        insertShareableId(shareable.getShareableId());
    }

    public ItemShared share(final ShareableEntity shareable, final Account sharer)
    {
        final ItemShared shared = ItemShared.create(shareable, sharer);
        jdbcTemplate.update("INSERT INTO ItemShared (item_shared_id, shareable_id, sharer_id) VALUES (?,?,?)",
            shared.getItemSharedId(),
            shared.getShareableId(),
            shared.getSharer().getAccountId());
        return shared;
    }

}
