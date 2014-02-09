package io.ingramchen.association.social;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import io.ingramchen.association.JdbcTestCases;
import io.ingramchen.association.account.Account;
import io.ingramchen.association.article.Article;
import io.ingramchen.association.comment.Comment;
import io.ingramchen.association.comment.CommentServiceTest;

import org.junit.Test;

/**
 * <p>
 * SocialDao decouples from {@link Comment} and {@link Article}, so test cases are isolated from
 * those entities.
 * </p>
 * 
 * <p>
 * {@link SocialDaoTest#deleteLikeable()} and
 * {@link SocialDaoTest#deleteLikeableAlsoPurgeItemLiked()} cover various deletions logic, but
 * {@link CommentServiceTest#deleteComment()} just do happy path test.
 * </p>
 * 
 * @author ingram
 * 
 */
public class SocialDaoTest extends JdbcTestCases
{
    @Test
    public void deleteLikeable() throws Exception
    {
        final LikeableEntity likeable = socialDao.generateLikeable();
        socialDao.deleteLikeable(likeable.getLikeableId());
        assertFalse("deleted should not be likeable anymore", socialDao.isLikeable(likeable));
    }

    @Test
    public void deleteLikeableAlsoPurgeItemLiked() throws Exception
    {
        final LikeableEntity likeable = socialDao.generateLikeable();

        socialDao.like(likeable, createAccount("Louis"));
        socialDao.deleteLikeable(likeable.getLikeableId());

        assertFalse("deleted should not be likeable anymore", socialDao.isLikeable(likeable));
        assertFalse("liked should be purged", socialDao.isLiked(likeable));
    }

    @Test
    public void like() throws Exception
    {
        final Account liker = createAccount("Luffy");
        final LikeableEntity likeable = socialDao.generateLikeable();
        final ItemLiked itemLiked = socialDao.like(likeable, liker);
        assertTrue(socialDao.isLiked(likeable));
        assertEquals(liker, itemLiked.getLiker());
        assertEquals(likeable.getLikeableId(), itemLiked.getLikeableId());
    }

    @Test
    public void likeCount() throws Exception
    {
        final Account liker = createAccount("Luffy");
        final LikeableEntity likeable = socialDao.generateLikeable();
        final LikeableEntity likeable2 = socialDao.generateLikeable();

        assertEquals(0, socialDao.getLikedCount());

        socialDao.like(likeable, liker);
        assertEquals(1, socialDao.getLikedCount());

        socialDao.like(likeable2, liker);
        assertEquals(2, socialDao.getLikedCount());
    }

    @Test
    public void share() throws Exception
    {
        final Account sharer = createAccount("Shelly");
        final ShareableEntity shareable = socialDao.generateShareable();
        final ItemShared itemShared = socialDao.share(shareable, sharer);

        assertTrue(socialDao.isShared(shareable));
        assertEquals(sharer, itemShared.getSharer());
        assertEquals(shareable.getShareableId(), itemShared.getShareableId());
    }

    @Test
    public void shareCount() throws Exception
    {
        final Account sharer = createAccount("Shelly");
        final ShareableEntity shareable = socialDao.generateShareable();
        final ShareableEntity shareable2 = socialDao.generateShareable();

        assertEquals(0, socialDao.getSharedCount());

        socialDao.share(shareable, sharer);
        assertEquals(1, socialDao.getSharedCount());

        socialDao.share(shareable2, sharer);
        assertEquals(2, socialDao.getSharedCount());
    }

}
