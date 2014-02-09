package io.ingramchen.association.comment;

import io.ingramchen.association.account.Account;
import io.ingramchen.association.article.Article;
import io.ingramchen.association.social.LikeableEntity;

import java.util.UUID;

public class Comment implements LikeableEntity
{
    public static Comment create(final Article article, final Account commenter, final String detail)
    {
        return new Comment(UUID.randomUUID().toString(), article.getArticleId(), commenter, detail);
    }

    private final String commentId;
    private final String detail;
    private final Account commenter;
    private final String articleId;

    Comment(final String commentId,
        final String articleId,
        final Account commenter,
        final String detail)
    {
        super();
        this.commentId = commentId;
        this.detail = detail;
        this.commenter = commenter;
        this.articleId = articleId;
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
        if (!(obj instanceof Comment))
        {
            return false;
        }
        final Comment other = (Comment) obj;
        if (commentId == null)
        {
            if (other.commentId != null)
            {
                return false;
            }
        }
        else if (!commentId.equals(other.commentId))
        {
            return false;
        }
        return true;
    }

    public String getArticleId()
    {
        return articleId;
    }

    public Account getCommenter()
    {
        return commenter;
    }

    public String getCommentId()
    {
        return commentId;
    }

    public String getDetail()
    {
        return detail;
    }

    @Override
    public String getLikeableId()
    {
        return commentId;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((commentId == null) ? 0 : commentId.hashCode());
        return result;
    }

    @Override
    public String toString()
    {
        return "Comment [commentId=" + commentId
            + ", detail="
            + detail
            + ", commenter="
            + commenter
            + ", articleId="
            + articleId
            + "]";
    }

}
