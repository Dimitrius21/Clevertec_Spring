CREATE TABLE IF NOT EXISTS tag (
id int auto_increment PRIMARY KEY,
name VARCHAR(255));

CREATE TABLE IF NOT EXISTS gift_certificate (
id int auto_increment PRIMARY KEY,
name VARCHAR(255),
description VARCHAR(255),
price INTEGER,
duration INTEGER,
create_date timestamp default CURRENT_TIMESTAMP(9),
last_update_date timestamp
);

CREATE TABLE IF NOT EXISTS certificate_tag (
id int auto_increment  PRIMARY KEY,
tag_id bigint,
certificate_id bigint,
FOREIGN KEY (tag_id) REFERENCES tag(id),
FOREIGN KEY (certificate_id) REFERENCES gift_certificate(id)
);
