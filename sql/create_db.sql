CREATE DATABASE blog;
USE blog;

CREATE TABLE IF NOT EXISTS users (
  id bigint unsigned NOT NULL AUTO_INCREMENT,
  username varchar(50) UNIQUE NOT NULL,
  password varchar(100) NOT NULL,
  
  PRIMARY KEY (id)
);


CREATE TABLE IF NOT  EXISTS categorie (
  nome varchar(25) NOT NULL,
  
  PRIMARY KEY (nome)
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
  autore			  bigint unsigned NOT NULL,		## foreign key verso uers
  categoria  		  varchar(25) NOT NULL, 		## foreign key verso categories
  
  PRIMARY KEY (id)
);


CREATE TABLE IF NOT  EXISTS tag (
  nome varchar(25) NOT NULL,
  
  PRIMARY KEY (nome)
);


CREATE TABLE IF NOT EXISTS articolo_tag (
  id_articolo bigint unsigned, 	  ## foreign key verso articolo
  nome_tag	 varchar(25),		  ## foreign key verso tag

  PRIMARY KEY (id_articolo, nome_tag)
);


/* AGGIUNGO LE FOREIGN KEY */
ALTER TABLE articoli
ADD CONSTRAINT `fk_articoli_users` 
	FOREIGN KEY (`autore`) 
	REFERENCES `users`(`id`)
	ON DELETE CASCADE;

ALTER TABLE articoli
ADD CONSTRAINT `fk_articoli_categorie` 
	FOREIGN KEY (`categoria`) 
	REFERENCES `categorie`(`nome`)
	ON DELETE CASCADE;

ALTER TABLE articolo_tag
ADD CONSTRAINT `fk_articolo_tag_articoli` 
	FOREIGN KEY (`id_articolo`) 
	REFERENCES `articoli`(`id`)
	ON DELETE CASCADE;

ALTER TABLE articolo_tag
ADD CONSTRAINT `fk_articolo_tag_tag` 
	FOREIGN KEY (`nome_tag`) 
	REFERENCES `tag`(`nome`)
	ON DELETE CASCADE;

INSERT INTO categorie VALUES ('viaggi'), ('tecnologia'), ('cibo');

INSERT INTO tag VALUES ('Londra'), ('Parigi'), ('Android'), ('IOS'), ('Windows');
