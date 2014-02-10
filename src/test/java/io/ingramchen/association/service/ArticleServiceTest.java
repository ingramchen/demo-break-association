package io.ingramchen.association.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.ingramchen.association.JdbcTestCases;
import io.ingramchen.association.model.account.Account;
import io.ingramchen.association.model.article.Article;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ArticleServiceTest extends JdbcTestCases
{
    @Autowired
    private ArticleService service;

    @Test
    public void likeArticle() throws Exception
    {
        final Article article = createArticle("Alice", "very good content");
        final Account liker = createAccount("Louis");
        service.likeArticle(article.getArticleId(), liker.getAccountId());
        assertTrue(socialDao.isLiked(article));
    }

    @Test
    public void shareArticle() throws Exception
    {
        final Article article = createArticle("Alice", "very good content");
        final Account sharer = createAccount("Sue");
        service.shareArticle(article.getArticleId(), sharer.getAccountId());
        assertTrue(socialDao.isShared(article));
    }

    @Test
    public void testCreateArticle() throws Exception
    {
        final Account author = createAccount("Alice");
        final Article article = service.createArticle(author.getAccountId(), "My great post");

        assertEquals("My great post", article.getContent());
        assertEquals(author, article.getAuthor());
        assertEquals(article, articleDao.findById(article.getArticleId()));
        assertTrue("article should be a shareable entity", socialDao.isSavedAsShareable(article));
        assertTrue("article should be a likeable entity", socialDao.isSavedAsLikeable(article));
    }

}
