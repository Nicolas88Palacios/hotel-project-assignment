USE Library;


INSERT INTO book (title, ISBN, publishingDate, rating) VALUES 
('The Fellowship of the Ring', '9789113084909', '2019', '5.0'),
('The Two Towers', '9789113084916', '2019', '4.7'),
('The Return of the King', '97891130849230', '2019','4.9'),

('The Book of Five Rings', '9789178940592', '2013', '4.5'),
('The Art of War', '9780857080097', '2010', '4.0'),


('The Outcasts','9789132162190','2013', '3.8'),
('The Invaders','9789132162206','2014','3.9'),
('The Hunters', '9789132162213', '2015','3.5'),
('The Slaves of Socorro','9789132168543','2016','4.2'),
('Scorpion Mountain', '9789132179778', '2017', '4.0'),
('the Ghostfaces','9789132201356','2018','4.1'), 
('The Caldera','9789132209864','2019',' 3.9'),
('Return of the Temujai','9789132212031','2020','4.5');
 
INSERT INTO member (firstName, lastName) VALUES 
('Alexander', 'Papas'),
('Joakim','Falk'),
('Testa','Testsson');

 
INSERT INTO author (firstName, lastName) VALUES 
('J.R.R','Tolkien'),
('Miyamoto','Mushashi'),
('Sun','Tzu'),
('John','Flanagan');

INSERT INTO book_author (bookID, authorID) VALUES 
-- tolkien 
(1001,1001),
(1002,1001),
(1003,1001),

-- Miyamoto 
(1004,1002),

-- sun tzu 
(1005,1003),

-- John Flanagan
(1006,1004),
(1007,1004),
(1008,1004),
(1009,1004),
(1010,1004),
(1011,1004),
(1012,1004),
(1013,1004);

INSERT INTO inventory(bookID) VALUES 
(1001),
(1002),
(1003),
(1004),
(1005),
(1006),
(1007),
(1008),
(1009),
(1010),
(1011),
(1012),
(1013);

INSERT INTO borrow (inventoryID, memberID, startDate) VALUES
(1001,1001,CURRENT_TIMESTAMP),
(1002,1002,CURRENT_TIMESTAMP),
(1003,1003,CURRENT_TIMESTAMP);


SELECT * FROM book

