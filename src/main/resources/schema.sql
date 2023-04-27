drop table if exists tag;
drop table if exists gift_certificate;

CREATE TABLE IF NOT EXISTS tag (
id bigserial PRIMARY KEY,
name VARCHAR(255));

CREATE TABLE IF NOT EXISTS gift_certificate (
id bigserial PRIMARY KEY,
name VARCHAR(255),
description VARCHAR(255),
price INTEGER,
duration INTEGER,
create_date timestamptz,
last_update_date timestamp
);

CREATE TABLE IF NOT EXISTS certificate_tag (
id bigserial PRIMARY KEY,
tag_id bigint,
certificate_id bigint,
FOREIGN KEY (tag_id) REFERENCES tag(id),
FOREIGN KEY (certificate_id) REFERENCES gift_certificate(id)
);
