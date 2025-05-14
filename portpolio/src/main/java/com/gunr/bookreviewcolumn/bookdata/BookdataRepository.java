package com.gunr.bookreviewcolumn.bookdata;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookdataRepository extends JpaRepository<Bookdata, Long>{
	Optional<Bookdata> findByTitle(String title);
	Optional<Bookdata> findByIsbn(String isbn);
}
/*
mysql> desc bookdata;
+-------------+--------------+------+-----+---------+----------------+
| Field       | Type         | Null | Key | Default | Extra          |
+-------------+--------------+------+-----+---------+----------------+
| id          | bigint       | NO   | PRI | NULL    | auto_increment |
| author      | varchar(255) | YES  |     | NULL    |                |
| datatime    | datetime(6)  | YES  |     | NULL    |                |
| description | varchar(255) | YES  |     | NULL    |                |
| image       | varchar(255) | YES  |     | NULL    |                |
| isbn        | varchar(255) | NO   | UNI | NULL    |                |
| link        | varchar(255) | NO   |     | NULL    |                |
| price       | double       | NO   |     | NULL    |                |
| pubdate     | varchar(255) | YES  |     | NULL    |                |
| publisher   | varchar(255) | YES  |     | NULL    |                |
| title       | varchar(255) | YES  |     | NULL    |                |
| big_id      | bigint       | YES  | MUL | NULL    |                |
+-------------+--------------+------+-----+---------+----------------+
12 rows in set (0.00 sec)

mysql>
author  description   image   isbn  link  price  pubdate  publisher  title  big_id
*/
