DROP TABLE IF EXISTS Account CASCADE;
DROP TABLE IF EXISTS Likeable CASCADE;
DROP TABLE IF EXISTS Shareable CASCADE;
DROP TABLE IF EXISTS Article CASCADE;
DROP TABLE IF EXISTS Comment CASCADE;
DROP TABLE IF EXISTS ItemLiked CASCADE;
DROP TABLE IF EXISTS ItemShared CASCADE;

CREATE TABLE Account (
   account_id UUID NOT NULL PRIMARY KEY,
   name varchar NOT NULL UNIQUE
);

CREATE TABLE Likeable (
   likeable_id UUID NOT NULL PRIMARY KEY
);

CREATE TABLE Shareable (
   shareable_id UUID NOT NULL PRIMARY KEY
);

CREATE TABLE Article (
   article_id UUID NOT NULL PRIMARY KEY,
   author_id UUID NOT NULL REFERENCES Account (account_id),
   content varchar NOT NULL,
   FOREIGN KEY (article_id) REFERENCES Likeable(likeable_id),
   FOREIGN KEY (article_id) REFERENCES Shareable(shareable_id)
);

CREATE TABLE Comment (
   comment_id UUID NOT NULL PRIMARY KEY, 
   detail varchar NOT NULL,
   article_id UUID NOT NULL REFERENCES Article (article_id),
   commenter_id UUID NOT NULL REFERENCES Account (account_id),
   FOREIGN KEY (comment_id) REFERENCES Likeable(likeable_id)
);

CREATE TABLE ItemLiked (
   item_liked_id UUID NOT NULL PRIMARY KEY,
   likeable_id UUID NOT NULL REFERENCES Likeable (likeable_id) ON DELETE CASCADE,
   liker_id UUID NOT NULL REFERENCES Account (account_id),
   UNIQUE (likeable_id, liker_id)
);

CREATE TABLE ItemShared (
   item_shared_id UUID NOT NULL PRIMARY KEY,
   shareable_id UUID REFERENCES Shareable (shareable_id) ON DELETE CASCADE,
   sharer_id UUID NOT NULL REFERENCES Account (account_id),
   UNIQUE (shareable_id, sharer_id)
);