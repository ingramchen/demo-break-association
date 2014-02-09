package io.ingramchen.association.article;

import io.ingramchen.association.account.Account;
import io.ingramchen.association.account.AccountDao;
import io.ingramchen.association.social.SocialDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleDao
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SocialDao socialDao;

    public Article createArticle(final Account author, final String content)
    {
        /*
         * Article, ShareableEntity, LikeableEntity use the same primary key. so we can give single
         * entity more than one behaviors. primary key use UUID so it won't collision.
         * 
         * This is impossible in Hibernate "Table per subclass" strategy
         * (http://docs.jboss.org/hibernate
         * /orm/4.3/manual/en-US/html/ch10.html#inheritance-strategies)
         * 
         * because Hibernate must extends abstract class, multiple inheritance is impossible
         */
        final Article article = Article.create(author, content);
        socialDao.makeShareable(article);
        socialDao.makeLikeable(article);

        jdbcTemplate.update("INSERT INTO Article (article_id, author_id, content) VALUES (?,?,?)",
            article.getArticleId(),
            article.getAuthor().getAccountId(),
            article.getContent());

        return article;
    }

    public Article findById(final String articleId)
    {
        final String sql = //
        "       SELECT * " //
            + "   FROM Article " //
            + "   JOIN Account ON (Article.author_id = Account.account_id) "
            + "  WHERE article_id = ?";

        final List<Article> results = jdbcTemplate.query(sql, new RowMapper<Article>()
        {
            @Override
            public Article mapRow(final ResultSet rs, final int rowNum) throws SQLException
            {
                final Account author = AccountDao.accountMapper("author_id").mapRow(rs, rowNum);
                return new Article(rs.getString("article_id"), author, rs.getString("content"));
            }

        }, articleId);
        return results.isEmpty() ? null : results.get(0);
    }
}
