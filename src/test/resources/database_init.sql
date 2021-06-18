CREATE TABLE IF NOT EXISTS users (
  id bigint unsigned NOT NULL AUTO_INCREMENT,
  username varchar(50) NOT NULL,
  password varchar(100) NOT NULL,
  
  PRIMARY KEY (id)
);


CREATE TABLE IF NOT  EXISTS categorie (
  categoria varchar(25) NOT NULL,
  
  PRIMARY KEY (categoria)
);


CREATE TABLE IF NOT  EXISTS articoli (
  id 		  		  bigint unsigned NOT NULL AUTO_INCREMENT,
  titolo 	  		  varchar(50) NOT NULL,
  sottotitolo	      varchar(100),
  testo		  		  text(1000) NOT NULL,
  data_publicazione   datetime,
  data_modifica       datetime,
  data_creazione 	  datetime NOT NULL,
  stato		          varchar(10) NOT NULL,
  autore			  bigint unsigned NOT NULL,
  categoria  		  varchar(25) NOT NULL,
  
  PRIMARY KEY (id),
  
  CONSTRAINT `fk_articoli_users` FOREIGN KEY (`autore`) REFERENCES `users`(`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_articoli_categorie` FOREIGN KEY (`categoria`) REFERENCES `categorie`(`categoria`) ON DELETE CASCADE
);


CREATE TABLE IF NOT  EXISTS tag (
  tag varchar(25) NOT NULL,
  
  PRIMARY KEY (tag)
);


CREATE TABLE IF NOT EXISTS articolo_tag (
  id_articolo bigint unsigned,
  tag	 varchar(25),

  PRIMARY KEY (id_articolo, tag),
  
  CONSTRAINT `fk_articolo_tag_articoli` FOREIGN KEY (`id_articolo`) REFERENCES `articoli`(`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_articolo_tag_tag` FOREIGN KEY (`tag`) REFERENCES `tag`(`tag`) ON DELETE CASCADE
);

INSERT INTO users
(username, password)
VALUES('ddinuzzo', '$2a$10$vj3PqvSqQSsLhknZpxU2oOIUOdmm6cpPu1shwcyXHVzba.xBWLe4K');