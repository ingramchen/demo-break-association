package io.ingramchen.association;

import io.ingramchen.association.account.Account;
import io.ingramchen.association.account.AccountDao;
import io.ingramchen.association.article.Article;
import io.ingramchen.association.article.ArticleDao;
import io.ingramchen.association.comment.CommentDao;
import io.ingramchen.association.social.SocialDao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ContextConfiguration
public abstract class JdbcTestCases extends AbstractTransactionalJUnit4SpringContextTests
{
    @ComponentScan(basePackages = "io.ingramchen.association")
    @Configuration
    @EnableTransactionManagement
    public static class Config
    {
        @Value("classpath:db-schema.sql")
        private Resource schemaScript;

        private DatabasePopulator databasePopulator()
        {
            final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            populator.addScript(schemaScript);
            return populator;
        }

        @Bean
        public DataSource dataSource()
        {
            final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
            dataSource.setDriverClass(org.h2.Driver.class);
            dataSource.setUsername("sa");
            dataSource.setUrl("jdbc:h2:mem");
            dataSource.setPassword("");
            return dataSource;
        }

        @Bean
        public DataSourceInitializer dataSourceInitializer(final DataSource dataSource)
        {
            final DataSourceInitializer initializer = new DataSourceInitializer();
            initializer.setDataSource(dataSource);
            initializer.setDatabasePopulator(databasePopulator());
            return initializer;
        }

        @Bean
        public JdbcTemplate jdbcTemplate()
        {
            return new JdbcTemplate(dataSource());
        }

        @Bean
        public PlatformTransactionManager txManager()
        {
            return new DataSourceTransactionManager(dataSource());
        }

    }

    @Autowired
    protected AccountDao accountDao;

    @Autowired
    protected CommentDao commentDao;

    @Autowired
    protected ArticleDao articleDao;

    @Autowired
    protected SocialDao socialDao;

    // fixture methods
    protected Account createAccount(final String name)
    {
        return accountDao.create(name);
    }

    protected Article createArticle(final String authorName, final String articleContent)
    {
        final Account author = createAccount(authorName);
        return articleDao.createArticle(author, articleContent);
    }

}
