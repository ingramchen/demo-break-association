package io.ingramchen.association.model.article;

import io.ingramchen.association.model.account.Account;
import io.ingramchen.association.model.social.LikeableEntity;
import io.ingramchen.association.model.social.ShareableEntity;
import io.ingramchen.association.model.social.SocialDao;

import java.util.UUID;

/**
 * Article are both likeable and shareable. this is possible because LikeableEntity and
 * ShareableEntity tables share the same primary key. see
 * {@link ArticleDao#createArticle(Account, String)} and {@link SocialDao}
 * 
 * @author ingram
 * 
 */
public class Article implements LikeableEntity, ShareableEntity
{
    public static Article create(final Account author, final String content)
    {
        return new Article(UUID.randomUUID().toString(), author, content);
    }

    private final String articleId;
    private final String content;
    private final Account author;

    Article(final String articleId, final Account author, final String content)
    {
        this.articleId = articleId;
        this.content = content;
        this.author = author;
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
        if (!(obj instanceof Article))
        {
            return false;
        }
        final Article other = (Article) obj;
        if (articleId == null)
        {
            if (other.articleId != null)
            {
                return false;
            }
        }
        else if (!articleId.equals(other.articleId))
        {
            return false;
        }
        return true;
    }

    public String getArticleId()
    {
        return articleId;
    }

    public Account getAuthor()
    {
        return author;
    }

    public String getContent()
    {
        return content;
    }

    @Override
    public String getLikeableId()
    {
        return articleId;
    }

    @Override
    public String getShareableId()
    {
        return articleId;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((articleId == null) ? 0 : articleId.hashCode());
        return result;
    }

    @Override
    public String toString()
    {
        return "Article [articleId=" + articleId
            + ", content="
            + content
            + ", author="
            + author
            + "]";
    }

}
