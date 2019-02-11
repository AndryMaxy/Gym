INSERT INTO UserRole ( Role )
	VALUES
    ( 'admin' ),
    ( 'trainer' ),
    ( 'visitor' );

INSERT INTO Membership ( Name, VisitCount, Price )
  VALUES
    ( 'ULTRA', 30, 1000 ),
    ( 'SUPER', 20, 800 ),
    ( 'STANDARD', 15, 600 ),
    ( 'EASY', 10, 500),
    ( 'ONE', 1, 100);

INSERT INTO User ( UserRoleId, Login, Hash, Salt, Name, Surname )
  VALUES
    ( 1, 'admin', 'admin', 'a', 'Андрей', 'Администратов'),
    ( 2, 'besttrainer', 'trainer', 't', 'Сергей', 'Самылев');

BEGIN;
INSERT INTO User ( UserRoleId, Login, Hash, Salt, Name, Surname )
  VALUES
  ( 3, 'vitaly1', 'qwe1', 'q', 'Витя', 'Варламов');
INSERT INTO Visitor (UserId, Discount, Vip, Regular, Balance)
  VALUES
  (LAST_INSERT_ID(), 10 , 1, NULL, 1000);
COMMIT;

BEGIN;
INSERT INTO User ( UserRoleId, Login, Hash, Salt, Name, Surname )
  VALUES
  ( 3, 'pety2', 'qwe2', 'q', 'Петр', 'Первый');
INSERT INTO Visitor ( UserId, Discount, Vip, Regular, Balance )
  VALUES
  (LAST_INSERT_ID(), 5, NULL, 1, 1000);
COMMIT;

BEGIN;
INSERT INTO User ( UserRoleId, Login, Hash, Salt, Name, Surname )
  VALUES
  ( 3, 'bestguy', 'bestguy1', 'b', 'Николай', 'Соболев');
INSERT INTO Visitor ( UserId, Discount, Vip, Regular, Balance )
  VALUES
  (LAST_INSERT_ID(), 25, 1, 1, 10000);
COMMIT;

BEGIN;
INSERT INTO User ( UserRoleId, Login, Hash, Salt, Name, Surname )
  VALUES
  ( 3, 'serq', 'serq2', 's', 'Сергей', 'Семенов');
INSERT INTO Visitor ( UserId, Discount, Vip, Regular, Balance )
  VALUES
  (LAST_INSERT_ID(), NULL, NULL, NULL, 1000);
COMMIT;

INSERT INTO Booking ( UserId, VisitCountLeft, Feedback )
	VALUES
	  ( 3, 2, 'Нужно больше зеркал!' ),
    ( 4, 10, 'Всё круто!' ),
    ( 5, 12, null ),
    ( 3, 1, 'Вот теперь отлично' ),
    ( 6, 3, 'Понравилось, приду ещё' );

INSERT INTO Product ( Name )
	VALUES
    ( 'Курица' ),
    ( 'Яйца' ),
    ( 'Рыба' ),
    ( 'Овощи' ),
    ( 'Фрукты' ),
    ( 'Орехи' ),
    ( 'Перловка' ),
    ( 'Творог' );

INSERT INTO ProductAppointment ( UserId, ProductId, GramInDay )
	VALUES
    ( 3, 1, 250 ),
    ( 3, 2, 200 ),
    ( 3, 5, 500 ),
    ( 3, 8, 100 ),
    ( 4, 1, 180 ),
    ( 4, 8, 150 ),
    ( 4, 7, 200 ),
    ( 4, 6, 180 ),
    ( 4, 2, 250 ),
	  ( 5, 1, 180 ),
    ( 5, 8, 150 ),
    ( 5, 7, 200 ),
    ( 5, 6, 180 ),
	  ( 5, 3, 180 ),
    ( 6, 1, 220 ),
    ( 6, 2, 250 ),
    ( 6, 7, 280 ),
    ( 6, 8, 250 );

INSERT INTO Exercise ( Name )
	VALUES
    ( 'Штанга' ),
    ( 'Пресс' ),
    ( 'Гантели' ),
    ( 'Отжимания' ),
    ( 'Подтягивания' ),
    ( 'Тяга' );
    
INSERT INTO ExerciseAppointment ( UserId, ExerciseId, SetCount, RepCount, Weight )
	VALUES
    ( 3, 1, 10, 2, 10 ),
    ( 3, 2, 20, 3, NULL ),
    ( 3, 4, 20, 3, NULL ),
    ( 4, 2, 30, 3, NULL ),
    ( 4, 4, 10, 2, NULL ),
    ( 4, 3, 5, 3, 20 ),
	  ( 5, 1, 10, 3, 30 ),
    ( 5, 2, 15, 2, 20 ),
    ( 5, 6, 10, 1, 50 ),
    ( 6, 1, 5, 2, 15 ),
    ( 6, 2, 30, 2, NULL ),
    ( 6, 5, 30, 3, NULL ),
    ( 6, 6, 10, 2, 20 );