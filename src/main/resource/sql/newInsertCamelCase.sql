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

INSERT INTO User ( UserRoleId, Login, Hash, Salt, Name, Surname, Discount, Balance )
VALUES
( 1, 'adminka', '�|�xDm7ȂC���"8', '�w݆��C��:0�%�B', 'Андрей', 'Администратов', NULL, NULL),
( 2, 'besttrainer', '�u���)�Mf���', '�)H5E���.�D6�', 'Сергей', 'Самылев', NULL, NULL),
( 3, 'vitaly1', '�,��զ7�4�ɇz�5�', '�8C���v�O��֜tQ', 'Витя', 'Варламов', 10, 500),
( 3, 'petty2', '�8C���v�O��֜tQ', '�8C���v�O��֜tQ', 'Петр', 'Первый', 5, 0),
( 3, 'bestguy', 'bestguy1', 'b', 'Николай', 'Соболев', 25, 10000),
( 3, 'serqeq', 'serqeq2', 's', 'Сергей', 'Семенов', 0, 1000);

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
( 'Жим' ),
( 'Пресс' ),
( 'Гантели' ),
( 'Отжимания' ),
( 'Подтягивания' ),
( 'Тяга' ),
( 'Приседания' ),
( 'Брусья' );

INSERT INTO ExerciseAppointment ( UserId, ExerciseId, SetCount, RepCount, Weight )
VALUES
( 3, 1, 2, 10, 10 ),
( 3, 2, 3, 20, NULL ),
( 3, 4, 3, 20, NULL ),
( 3, 8, 2, 30, NULL ),
( 4, 2, 3, 30, NULL ),
( 4, 4, 2, 10, NULL ),
( 4, 3, 3, 5, 20 ),
( 5, 1, 3, 10, 30 ),
( 5, 2, 2, 15, 20 ),
( 5, 8, 2, 15, 5 ),
( 5, 6, 1, 10, 50 ),
( 6, 1, 2, 5, 15 ),
( 6, 7, 2, 20, NULL ),
( 6, 2, 2, 30, NULL ),
( 6, 5, 3, 30, NULL ),
( 6, 6, 2, 10, 20 );