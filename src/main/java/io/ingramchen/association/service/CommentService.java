package io.ingramchen.association.service;

import io.ingramchen.association.model.account.Account;
import io.ingramchen.association.model.account.AccountDao;
import io.ingramchen.association.model.article.Article;
import io.ingramchen.association.model.article.ArticleDao;
import io.ingramchen.association.model.comment.Comment;
import io.ingramchen.association.model.comment.CommentDao;
import io.ingramchen.association.model.social.SocialDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentService
{
    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private SocialDao socialDao;

    public void deleteComment(final String commentId)
    {
        commentDao.delete(commentId);
    }

    public Comment leaveComment(final String articleId,
        final String commenterId,
        final String detail)
    {
        final Account commenter = accountDao.findById(commenterId);
        final Article article = articleDao.findById(articleId);
        return commentDao.create(article, commenter, detail);
    }

    /**
     * demostration of polymophic assoication, see
     * {@link ArticleService#likeArticle(String, String)}
     */
    public void likeComment(final String commentId, final String likerId)
    {
        final Comment comment = commentDao.findById(commentId);
        final Account liker = accountDao.findById(likerId);
        socialDao.like(comment, liker);
    }

}
