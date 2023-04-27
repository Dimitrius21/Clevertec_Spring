INSERT INTO tag (name) VALUES ('relax'), ('sport'), ('quiz'), ('motor')

INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('Rest1', 'sauna', 1900, 10, '2023-04-17', '2023-04-17'),
('Rest2', 'spa light', 2500, 10, '2023-04-17', '2023-04-17'),
('Rest3', 'spa inclusive', 9500, 15, '2023-04-18', '2023-04-18'),
('Auto','Carting', 3000, 15, '2023-04-18', '2023-04-18'),
('Quest Zone1', 'WILD WEST: GOLD RUSH', 8000, 14, '2023-04-19', '2023-04-19'),
('Quest Zone2', 'THE MAGEs TOWER', 8500, 14, '2023-04-19', '2023-04-19'),
('Quad bike', 'Road 10km, 1 person', 7000, 30, '2023-04-20', '2023-04-20');


INSERT INTO certificate_tag (tag_id, certificate_id) VALUES (1, 1), (1, 2), (1, 3),
(2, 4), (3, 5), (3, 6), (4, 7), (4, 4)

