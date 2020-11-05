-- alter table post
-- add fulltext(title, content,summary);

create fulltext index post_index on post(content) with Parser ngram;

ALTER TABLE post DROP INDEX post_index;

SELECT title,summary,content,null as content, MATCH(content) AGAINST ("học đại học") as score FROM post WHERE MATCH(content) AGAINST ("học đại học") > 0.1 order by score desc;
SELECT id FROM post WHERE MATCH(content) AGAINST ("học đại học") > 0.1  order by  MATCH(content) AGAINST ("học đại học") desc;