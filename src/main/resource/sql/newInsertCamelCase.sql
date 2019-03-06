INSERT INTO UserRole ( Role )
VALUES
( 'admin' ),
( 'trainer' ),
( 'visitor' ),
( 'guest' );

INSERT INTO Membership ( Name, VisitCount, Price )
VALUES
( 'ULTRA', 30, 1000 ),
( 'SUPER', 20, 800 ),
( 'STANDARD', 15, 600 ),
( 'EASY', 10, 500),
( 'ONE', 1, 100);

INSERT INTO User ( UserRoleId, Login, Hash, Salt, Name, Surname, Discount, Balance )
VALUES
( 1, 'adminka', 'Og7ѕяЇ§@^,5dъIЦ+', 'ы–Б.н$Ћ2ѕП’®bt', 'Андрей', 'Администратов', NULL, NULL ),
( 2, 'besttrainer', 'Og7ѕяЇ§@^,5dъIЦ+', 'ы–Б.н$Ћ2ѕП’®bt', 'Сергей', 'Самылев', NULL, NULL ),
( 3, 'bestguy', 'Og7ѕяЇ§@^,5dъIЦ+', 'ы–Б.н$Ћ2ѕП’®bt', 'Николай', 'Соболев', 25, 10000 ),
( 3, 'vitaly1', 'Og7ѕяЇ§@^,5dъIЦ+', 'ы–Б.н$Ћ2ѕП’®bt', 'Витя', 'Варламов', 10, 500 ),
( 3, 'petty2', 'Og7ѕяЇ§@^,5dъIЦ+', 'ы–Б.н$Ћ2ѕП’®bt', 'Петр', 'Первый', 5, 0 ),
( 3, 'andrey', 'Og7ѕяЇ§@^,5dъIЦ+', 'ы–Б.н$Ћ2ѕП’®bt', 'Андрей', 'Семенов', 5, 1000 ),
( 3, 'valera', 'Og7ѕяЇ§@^,5dъIЦ+', 'ы–Б.н$Ћ2ѕП’®bt', 'Валерий', 'Миладзе', 20, 1000 );

INSERT INTO Booking ( UserId, MembershipId, VisitCountLeft, NeedAppointment, Feedback )
VALUES
( 4, 4, 10, 1, 'Всё очень круто, мне понравилось. Вежливый персонал, отличный тренер. А сайт у вас просто супер.' ),
( 3, 2, 2, 1, 'Неплохо, но нужно больше зеркал!' ),
( 5, 5, 12, 1, NULL ),
( 3, 1, 1, 1, 'Вот теперь отлично' );

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

INSERT INTO ProductAppointment ( BookingId, ProductId, GramInDay )
VALUES
( 1, 1, 250 ),
( 1, 2, 200 ),
( 1, 5, 500 ),
( 1, 8, 100 ),
( 2, 1, 180 ),
( 2, 8, 150 ),
( 2, 7, 200 ),
( 2, 6, 180 ),
( 2, 2, 250 ),
( 3, 1, 180 ),
( 3, 8, 150 ),
( 3, 7, 200 ),
( 3, 6, 180 ),
( 3, 3, 180 ),
( 4, 1, 220 ),
( 4, 2, 250 ),
( 4, 7, 280 ),
( 4, 8, 250 );

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

INSERT INTO ExerciseAppointment ( BookingId, ExerciseId, SetCount, RepCount, Weight )
VALUES
( 1, 1, 2, 10, 10 ),
( 1, 2, 3, 20, NULL ),
( 1, 4, 3, 20, NULL ),
( 1, 8, 2, 30, NULL ),
( 2, 2, 3, 30, NULL ),
( 2, 4, 2, 10, NULL ),
( 2, 3, 3, 5, 20 ),
( 3, 1, 3, 10, 30 ),
( 3, 2, 2, 15, 20 ),
( 3, 8, 2, 15, 5 ),
( 3, 6, 1, 10, 50 ),
( 4, 1, 2, 5, 15 ),
( 4, 7, 2, 20, NULL ),
( 4, 2, 2, 30, NULL ),
( 4, 5, 3, 30, NULL ),
( 4, 6, 2, 10, 20 );