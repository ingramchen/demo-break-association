package io.ingramchen.association.article;

import io.ingramchen.association.account.Account;
import io.ingramchen.association.account.AccountDao;
import io.ingramchen.association.comment.CommentService;
import io.ingramchen.association.social.SocialDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticleService
{
    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private SocialDao socialDao;

    public Article createArticle(final String authorId, final String content)
    {
        final Account author = accountDao.findById(authorId);
        return articleDao.createArticle(author, content);
    }

    /**
     * demostration of polymophic assoication, see
     * {@link CommentService#likeComment(String, String)}
     */
    public void likeArticle(final String articleId, final String likerId)
    {
        final Account liker = accountDao.findById(likerId);
        final Article article = articleDao.findById(articleId);
        socialDao.like(article, liker);
    }

    /**
     * demostration Article are shareable and likeable
     */
    public void shareArticle(final String articleId, final String sharerId)
    {
        final Account sharer = accountDao.findById(sharerId);
        final Article article = articleDao.findById(articleId);
        socialDao.share(article, sharer);
    }
}
