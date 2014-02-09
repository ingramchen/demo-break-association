demo-break-association
======================

Demostration how to break deep associations in RDBMS design

### Sample codes

##### Schema

all database schema is in `src/main/resources/db-schema.sql`. the sample codes use HSQLDB for
demostration. 

##### Break association

`SocialDao` and `SocialDaoTest` isolated from concrete classes `Article` and `Comment`.
The codes is modular and easier to test. 

##### ppolymophic associations

`Comment` and `Article` both implementss LikeableEntity, demostration how to do 
polymorphic associations. see `CommentService.likeComment` and `ArticleService.likeArticle`.

##### Multiple associations

`Article` demo how to implement two associations at the same time. Article can implement 
`LikeableEntity` and `ShareableEntity` interfaces because it use UUID as primary key.

### Run tests

You need to install Gradle first. then execute below command to run unit tests. 

```
gradle test
```

### Import into Eclipse

the command will generate Eclipse specfic setting files:

```
gradle cleanEclipse eclipse
```

