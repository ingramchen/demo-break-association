package io.ingramchen.association.comment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import io.ingramchen.association.JdbcTestCases;
import io.ingramchen.association.account.Account;
import io.ingramchen.association.article.Article;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentServiceTest extends JdbcTestCases
{
    @Autowired
    private CommentService service;

    private Comment createComment()
    {
        final Article article = createArticle("Alice", "foo article");
        final Account commenter = createAccount("Coby");

        final Comment comment = service.leaveComment(article.getArticleId(),
            commenter.getAccountId(),
            "bar comment");
        return comment;
    }

    @Test
    public void deleteComment() throws Exception
    {
        final Comment comment = createComment();
        service.deleteComment(comment.getCommentId());

        assertNull(commentDao.findById(comment.getCommentId()));
        assertFalse("deleted comment should not likeable", socialDao.isLikeable(comment));
    }

    @Test
    public void leaveComment() throws Exception
    {
        final Article article = createArticle("Alice", "What's up?");
        final Account commenter = createAccount("Coby");
        final Comment comment = service.leaveComment(article.getArticleId(),
            commenter.getAccountId(),
            "Ask your heart.");

        assertEquals("Ask your heart.", comment.getDetail());
        assertEquals(commenter, comment.getCommenter());
        assertEquals(article.getArticleId(), comment.getArticleId());

        assertEquals(comment, commentDao.findById(comment.getCommentId()));
        assertTrue("comment should be a likeable entity", socialDao.isLikeable(comment));
    }

    @Test
    public void likeComment() throws Exception
    {
        final Comment comment = createComment();

        final Account liker = createAccount("Luffy");
        service.likeComment(comment.getCommentId(), liker.getAccountId());
        assertTrue(socialDao.isLiked(comment));
    }
}
