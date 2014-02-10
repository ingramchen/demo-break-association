package io.ingramchen.association.model.comment;

import io.ingramchen.association.model.account.Account;
import io.ingramchen.association.model.account.AccountDao;
import io.ingramchen.association.model.article.Article;
import io.ingramchen.association.model.social.SocialDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CommentDao
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SocialDao socialDao;

    public Comment create(final Article article, final Account commenter, final String detail)
    {
        final Comment comment = Comment.create(article, commenter, detail);
        socialDao.makeLikeable(comment);

        jdbcTemplate.update("INSERT INTO Comment (comment_id, article_id, commenter_id, detail) VALUES (?,?,?,?)",
            comment.getCommentId(),
            comment.getArticleId(),
            comment.getCommenter().getAccountId(),
            comment.getDetail());

        return comment;
    }

    public void delete(final String commentId)
    {
        jdbcTemplate.update("DELETE FROM Comment WHERE comment_id = ? ", commentId);
        socialDao.deleteLikeable(commentId);
    }

    public Comment findById(final String commentId)
    {
        final String sql = //
        "      SELECT * " //
            + "  FROM Comment "
            + "  JOIN Account ON (Comment.commenter_id = Account.account_id) "
            + " WHERE comment_id = ? ";

        final RowMapper<Comment> rowMapper = new RowMapper<Comment>()
        {
            @Override
            public Comment mapRow(final ResultSet rs, final int rowNum) throws SQLException
            {
                final Account commenter = AccountDao.accountMapper("commenter_id").mapRow(rs,
                    rowNum);
                return new Comment(rs.getString("comment_id"),
                    rs.getString("article_id"),
                    commenter,
                    rs.getString("detail"));
            }
        };

        final List<Comment> results = jdbcTemplate.query(sql, rowMapper, commentId);
        return results.isEmpty() ? null : results.get(0);
    }
}
