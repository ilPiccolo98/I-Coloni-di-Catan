-- Da compilare dopo le tabelle e prima dei trigger/procedure

insert into Partite values(1, to_date('16/03/2019', 'dd/mm/yyyy'), 60);
insert into EsagoniTerreno values(1, 'Deserto', null);
insert into Esagoni values(1, 0, null, 1, 1);
insert into EsagoniTerreno values(2, 'Foresta', 5);
insert into Esagoni values(2, 1, null, 2, 1);
insert into EsagoniTerreno values(3, 'Foresta', 6);
insert into Esagoni values(3, 8, null, 3, 1);
insert into EsagoniTerreno values(4, 'Foresta', 11);
insert into Esagoni values(4, 12, null, 4, 1);
insert into EsagoniTerreno values(5, 'Foresta', 3);
insert into Esagoni values(5, 16, null, 5, 1);
insert into EsagoniTerreno values(6, 'Pascolo', 12);
insert into Esagoni values(6, 2, null, 6, 1);
insert into EsagoniTerreno values(7, 'Pascolo', 4);
insert into Esagoni values(7, 10, null, 7, 1);
insert into EsagoniTerreno values(8, 'Pascolo', 10);
insert into Esagoni values(8, 11, null, 8, 1);
insert into EsagoniTerreno values(9, 'Pascolo', 9);
insert into Esagoni values(9, 17, null, 9, 1);
insert into EsagoniTerreno values(10, 'Montagna', 8);
insert into Esagoni values(10, 3, null, 10, 1);
insert into EsagoniTerreno values(11, 'Montagna', 5);
insert into Esagoni values(11, 5, null, 11, 1);
insert into EsagoniTerreno values(12, 'Montagna', 11);
insert into Esagoni values(12, 13, null, 12, 1);
insert into EsagoniTerreno values(13, 'Collina', 10);
insert into Esagoni values(13, 4, null, 13, 1);
insert into EsagoniTerreno values(14, 'Collina', 8);
insert into Esagoni values(14, 6, null, 14, 1);
insert into EsagoniTerreno values(15, 'Collina', 2);
insert into Esagoni values(15, 18, null, 15, 1);
insert into EsagoniTerreno values(16, 'Collina', 10);
insert into Esagoni values(16, 4, null, 16, 1);
insert into EsagoniTerreno values(17, 'Campo', 6);
insert into Esagoni values(17, 14, null, 17, 1);
insert into EsagoniTerreno values(18, 'Campo', 3);
insert into Esagoni values(18, 15, null, 18, 1);
insert into EsagoniTerreno values(19, 'Campo', 4);
insert into Esagoni values(19, 7, null, 19, 1);
insert into EsagoniTerreno values(20, 'Campo', 9);
insert into Esagoni values(20, 9, null, 20, 1);
insert into EsagoniMareAperto values(1);
insert into EsagoniMare values(1, null, 1);
insert into Esagoni values(21, 20, 1, null, 1);
insert into EsagoniMareAperto values(2);
insert into EsagoniMare values(2, null, 2);
insert into Esagoni values(22, 22, 2, null, 1);
insert into EsagoniMareAperto values(3);
insert into EsagoniMare values(3, null, 3);
insert into Esagoni values(23, 24, 3, null, 1);
insert into EsagoniMareAperto values(4);
insert into EsagoniMare values(4, null, 4);
insert into Esagoni values(24, 26, 4, null, 1);
insert into EsagoniMareAperto values(5);
insert into EsagoniMare values(5, null, 5);
insert into Esagoni values(25, 28, 5, null, 1);
insert into EsagoniMareAperto values(6);
insert into EsagoniMare values(6, null, 6);
insert into Esagoni values(26, 30, 6, null, 1);
insert into EsagoniMareAperto values(7);
insert into EsagoniMare values(7, null, 7);
insert into Esagoni values(27, 32, 7, null, 1);
insert into EsagoniMareAperto values(8);
insert into EsagoniMare values(8, null, 8);
insert into Esagoni values(28, 34, 8, null, 1);
insert into EsagoniMareAperto values(9);
insert into EsagoniMare values(9, null, 9);
insert into Esagoni values(29, 36, 9, null, 1);
insert into EsagoniPorto values(1, 2, 'Minerale');
insert into EsagoniMare values(10, 1, null);
insert into Esagoni values(30, 19, 10, null, 1);
insert into EsagoniPorto values(2, 2, 'Lana');
insert into EsagoniMare values(11, 2, null);
insert into Esagoni values(31, 21, 11, null, 1);
insert into EsagoniPorto values(3, 3, 'InputUtente');
insert into EsagoniMare values(12, 3, null);
insert into Esagoni values(32, 23, 12, null, 1);
insert into EsagoniPorto values(4, 3, 'InputUtente');
insert into EsagoniMare values(13, 4, null);
insert into Esagoni values(33, 25, 13, null, 1);
insert into EsagoniPorto values(5, 2, 'Grano');
insert into EsagoniMare values(14, 5, null);
insert into Esagoni values(34, 27, 14, null, 1);
insert into EsagoniPorto values(6, 3, 'InputUtente');
insert into EsagoniMare values(15, 6, null);
insert into Esagoni values(35, 29, 15, null, 1);
insert into EsagoniPorto values(7, 2, 'Argilla');
insert into EsagoniMare values(16, 7, null);
insert into Esagoni values(36, 31, 16, null, 1);
insert into EsagoniPorto values(8, 3, 'InputUtente');
insert into EsagoniMare values(17, 8, null);
insert into Esagoni values(37, 33, 17, null, 1);
insert into EsagoniPorto values(9, 2, 'Legno');
insert into EsagoniMare values(18, 9, null);
insert into Esagoni values(38, 35, 18, null, 1);
insert into Vertici values(1, 0);
insert all
    into VerticiEsagoni values(1, 1, 1)
    into VerticiEsagoni values(2, 1, 2)
    into VerticiEsagoni values(3, 1, 6)
select * from dual;
insert into Vertici values(2, 1);
insert all
    into VerticiEsagoni values(4, 2, 1)
    into VerticiEsagoni values(5, 2, 6)
    into VerticiEsagoni values(6, 2, 10)
select * from dual;
insert into Vertici values(3, 2);
insert all
    into VerticiEsagoni values(7, 3, 1)
    into VerticiEsagoni values(8, 3, 10)
    into VerticiEsagoni values(9, 3, 16)
select * from dual;
insert into Vertici values(4, 3);
insert all
    into VerticiEsagoni values(10, 4, 1)
    into VerticiEsagoni values(11, 4, 16)
    into VerticiEsagoni values(12, 4, 11)
select * from dual;
insert into Vertici values(5, 4);
insert all
    into VerticiEsagoni values(13, 5, 1)
    into VerticiEsagoni values(14, 5, 11)
    into VerticiEsagoni values(15, 5, 14)
select * from dual;
insert into Vertici values(6, 5);
insert all
    into VerticiEsagoni values(16, 6, 1)
    into VerticiEsagoni values(17, 6, 2)
    into VerticiEsagoni values(18, 6, 14)
select * from dual;
insert into Vertici values(7, 6);
insert all
    into VerticiEsagoni values(19, 7, 6)
    into VerticiEsagoni values(20, 7, 3)
    into VerticiEsagoni values(21, 7, 2)
select * from dual;
insert into Vertici values(8, 7);
insert all
    into VerticiEsagoni values(22, 8, 3)
    into VerticiEsagoni values(23, 8, 20)
    into VerticiEsagoni values(24, 8, 6)
select * from dual;
insert into Vertici values(9, 8);
insert all
    into VerticiEsagoni values(25, 9, 6)
    into VerticiEsagoni values(26, 9, 20)
    into VerticiEsagoni values(27, 9, 7)
select * from dual;
insert into Vertici values(10, 9);
insert all
    into VerticiEsagoni values(28, 10, 6)
    into VerticiEsagoni values(29, 10, 10)
    into VerticiEsagoni values(30, 10, 7)
select * from dual;
insert into Vertici values(11, 10);
insert all
    into VerticiEsagoni values(31, 11, 7)
    into VerticiEsagoni values(32, 11, 8)
    into VerticiEsagoni values(33, 11, 10)
select * from dual;
insert into Vertici values(12, 11);
insert all
    into VerticiEsagoni values(34, 12, 10)
    into VerticiEsagoni values(35, 12, 8)
    into VerticiEsagoni values(36, 12, 4)
select * from dual;
insert into Vertici values(13, 12);
insert all
    into VerticiEsagoni values(37, 13, 10)
    into VerticiEsagoni values(38, 13, 4)
    into VerticiEsagoni values(39, 13, 16)
select * from dual;
insert into Vertici values(14, 13);
insert all
    into VerticiEsagoni values(40, 14, 16)
    into VerticiEsagoni values(41, 14, 4)
    into VerticiEsagoni values(42, 14, 12)
select * from dual;
insert into Vertici values(15, 14);
insert all
    into VerticiEsagoni values(43, 15, 16)
    into VerticiEsagoni values(44, 15, 12)
    into VerticiEsagoni values(45, 15, 17)
select * from dual;
insert into Vertici values(16, 15);
insert all
    into VerticiEsagoni values(46, 16, 11)
    into VerticiEsagoni values(47, 16, 16)
    into VerticiEsagoni values(48, 16, 17)
select * from dual;
insert into Vertici values(17, 16);
insert all
    into VerticiEsagoni values(49, 17, 11)
    into VerticiEsagoni values(50, 17, 18)
    into VerticiEsagoni values(51, 17, 17)
select * from dual;
insert into Vertici values(18, 17);
insert all
    into VerticiEsagoni values(52, 18, 5)
    into VerticiEsagoni values(53, 18, 11)
    into VerticiEsagoni values(54, 18, 18)
select * from dual;
insert into Vertici values(19, 18);
insert all
    into VerticiEsagoni values(55, 19, 14)
    into VerticiEsagoni values(56, 19, 11)
    into VerticiEsagoni values(57, 19, 5)
select * from dual;
insert into Vertici values(20, 19);
insert all
    into VerticiEsagoni values(58, 20, 9)
    into VerticiEsagoni values(59, 20, 14)
    into VerticiEsagoni values(60, 20, 5)
select * from dual;
insert into Vertici values(21, 20);
insert all
    into VerticiEsagoni values(61, 21, 15)
    into VerticiEsagoni values(62, 21, 14)
    into VerticiEsagoni values(63, 21, 9)
select * from dual;
insert into Vertici values(22, 21);
insert all
    into VerticiEsagoni values(64, 22, 2)
    into VerticiEsagoni values(65, 22, 14)
    into VerticiEsagoni values(66, 22, 15)
select * from dual;
insert into Vertici values(23, 22);
insert all
    into VerticiEsagoni values(67, 23, 2)
    into VerticiEsagoni values(68, 23, 19)
    into VerticiEsagoni values(69, 23, 15)
select * from dual;
insert into Vertici values(24, 23);
insert all
    into VerticiEsagoni values(70, 24, 19)
    into VerticiEsagoni values(71, 24, 3)
    into VerticiEsagoni values(72, 24, 2)
select * from dual;
insert into Vertici values(25, 24);
insert all
    into VerticiEsagoni values(73, 25, 21)
    into VerticiEsagoni values(74, 25, 3)
    into VerticiEsagoni values(75, 25, 19)
select * from dual;
insert into Vertici values(26, 25);
insert all
    into VerticiEsagoni values(76, 26, 21)
    into VerticiEsagoni values(77, 26, 31)
    into VerticiEsagoni values(78, 26, 3)
select * from dual;
insert into Vertici values(27, 26);
insert all
    into VerticiEsagoni values(79, 27, 31)
    into VerticiEsagoni values(80, 27, 20)
    into VerticiEsagoni values(81, 27, 3)
select * from dual;
insert into Vertici values(28, 27);
insert all
    into VerticiEsagoni values(82, 28, 31)
    into VerticiEsagoni values(83, 28, 22)
    into VerticiEsagoni values(84, 28, 20)
select * from dual;
insert into Vertici values(29, 28);
insert all
    into VerticiEsagoni values(85, 29, 22)
    into VerticiEsagoni values(86, 29, 32)
    into VerticiEsagoni values(87, 29, 20)
select * from dual;
insert into Vertici values(30, 29);
insert all
    into VerticiEsagoni values(88, 30, 20)
    into VerticiEsagoni values(89, 30, 32)
    into VerticiEsagoni values(90, 30, 7)
select * from dual;
insert into Vertici values(31, 30);
insert all
    into VerticiEsagoni values(91, 31, 32)
    into VerticiEsagoni values(92, 31, 23)
    into VerticiEsagoni values(93, 31, 7)
select * from dual;
insert into Vertici values(32, 31);
insert all
    into VerticiEsagoni values(94, 32, 23)
    into VerticiEsagoni values(95, 32, 8)
    into VerticiEsagoni values(96, 32, 7)
select * from dual;
insert into Vertici values(33, 32);
insert all
    into VerticiEsagoni values(97, 33, 23)
    into VerticiEsagoni values(98, 33, 33)
    into VerticiEsagoni values(99, 33, 8)
select * from dual;
insert into Vertici values(34, 33);
insert all
    into VerticiEsagoni values(100, 34, 8)
    into VerticiEsagoni values(101, 34, 33)
    into VerticiEsagoni values(102, 34, 24)
select * from dual;
insert into Vertici values(35, 34);
insert all
    into VerticiEsagoni values(103, 35, 8)
    into VerticiEsagoni values(104, 35, 24)
    into VerticiEsagoni values(105, 35, 4)
select * from dual;
insert into Vertici values(36, 35);
insert all
    into VerticiEsagoni values(106, 36, 4)
    into VerticiEsagoni values(107, 36, 24)
    into VerticiEsagoni values(108, 36, 34)
select * from dual;
insert into Vertici values(37, 36);
insert all
    into VerticiEsagoni values(109, 37, 4)
    into VerticiEsagoni values(110, 37, 34)
    into VerticiEsagoni values(111, 37, 12)
select * from dual;
insert into Vertici values(38, 37);
insert all
    into VerticiEsagoni values(112, 38, 12)
    into VerticiEsagoni values(113, 38, 34)
    into VerticiEsagoni values(114, 38, 25)
select * from dual;
insert into Vertici values(39, 38);
insert all
    into VerticiEsagoni values(115, 39, 12)
    into VerticiEsagoni values(116, 39, 25)
    into VerticiEsagoni values(117, 39, 35)
select * from dual;
insert into Vertici values(40, 39);
insert all
    into VerticiEsagoni values(118, 40, 17)
    into VerticiEsagoni values(119, 40, 12)
    into VerticiEsagoni values(120, 40, 35)
select * from dual;
insert into Vertici values(41, 40);
insert all
    into VerticiEsagoni values(121, 41, 17)
    into VerticiEsagoni values(122, 41, 35)
    into VerticiEsagoni values(123, 41, 26)
select * from dual;
insert into Vertici values(42, 41);
insert all
    into VerticiEsagoni values(124, 42, 18)
    into VerticiEsagoni values(125, 42, 17)
    into VerticiEsagoni values(126, 42, 26)
select * from dual;
insert into Vertici values(43, 42);
insert all
    into VerticiEsagoni values(127, 43, 18)
    into VerticiEsagoni values(128, 43, 26)
    into VerticiEsagoni values(129, 43, 36)
select * from dual;
insert into Vertici values(44, 43);
insert all
    into VerticiEsagoni values(130, 44, 27)
    into VerticiEsagoni values(131, 44, 18)
    into VerticiEsagoni values(132, 44, 36)
select * from dual;
insert into Vertici values(45, 44);
insert all
    into VerticiEsagoni values(133, 45, 5)
    into VerticiEsagoni values(134, 45, 18)
    into VerticiEsagoni values(135, 45, 27)
select * from dual;
insert into Vertici values(46, 45);
insert all
    into VerticiEsagoni values(136, 46, 37)
    into VerticiEsagoni values(137, 46, 5)
    into VerticiEsagoni values(138, 46, 27)
select * from dual;
insert into Vertici values(47, 46);
insert all
    into VerticiEsagoni values(139, 47, 9)
    into VerticiEsagoni values(140, 47, 5)
    into VerticiEsagoni values(141, 47, 37)
select * from dual;
insert into Vertici values(48, 47);
insert all
    into VerticiEsagoni values(142, 48, 28)
    into VerticiEsagoni values(143, 48, 9)
    into VerticiEsagoni values(144, 48, 37)
select * from dual;
insert into Vertici values(49, 48);
insert all
    into VerticiEsagoni values(145, 49, 38)
    into VerticiEsagoni values(146, 49, 9)
    into VerticiEsagoni values(147, 49, 28)
select * from dual;
insert into Vertici values(50, 49);
insert all
    into VerticiEsagoni values(148, 50, 38)
    into VerticiEsagoni values(149, 50, 15)
    into VerticiEsagoni values(150, 50, 9)
select * from dual;
insert into Vertici values(51, 50);
insert all
    into VerticiEsagoni values(151, 51, 29)
    into VerticiEsagoni values(152, 51, 15)
    into VerticiEsagoni values(153, 51, 38)
select * from dual;
insert into Vertici values(52, 51);
insert all
    into VerticiEsagoni values(154, 52, 29)
    into VerticiEsagoni values(155, 52, 19)
    into VerticiEsagoni values(156, 52, 15)
select * from dual;
insert into Vertici values(53, 52);
insert all
    into VerticiEsagoni values(157, 53, 30)
    into VerticiEsagoni values(158, 53, 19)
    into VerticiEsagoni values(159, 53, 29)
select * from dual;
insert into Vertici values(54, 53);
insert all
    into VerticiEsagoni values(160, 54, 30)
    into VerticiEsagoni values(161, 54, 21)
    into VerticiEsagoni values(162, 54, 19)
select * from dual;
insert into Lati values(1, 0);
insert all
    into LatiEsagoni values(1, 1, 1)
    into LatiEsagoni values(2, 1, 6)
select * from dual;
insert into Lati values(2, 1);
insert all
    into LatiEsagoni values(3, 2, 1)
    into LatiEsagoni values(4, 2, 10)
select * from dual;
insert into Lati values(3, 2);
insert all
    into LatiEsagoni values(5, 3, 1)
    into LatiEsagoni values(6, 3, 16)
select * from dual;
insert into Lati values(4, 3);
insert all
    into LatiEsagoni values(7, 4, 1)
    into LatiEsagoni values(8, 4, 11)
select * from dual;
insert into Lati values(5, 4);
insert all
    into LatiEsagoni values(9, 5, 1)
    into LatiEsagoni values(10, 5, 14)
select * from dual;
insert into Lati values(6, 5);
insert all
    into LatiEsagoni values(11, 6, 1)
    into LatiEsagoni values(12, 6, 2)
select * from dual;
insert into Lati values(7, 6);
insert all
    into LatiEsagoni values(13, 7, 2)
    into LatiEsagoni values(14, 7, 6)
select * from dual;
insert into Lati values(8, 7);
insert all
    into LatiEsagoni values(15, 8, 3)
    into LatiEsagoni values(16, 8, 6)
select * from dual;
insert into Lati values(9, 8);
insert all
    into LatiEsagoni values(17, 9, 20)
    into LatiEsagoni values(18, 9, 6)
select * from dual;
insert into Lati values(10, 9);
insert all
    into LatiEsagoni values(19, 10, 6)
    into LatiEsagoni values(20, 10, 7)
select * from dual;
insert into Lati values(11, 10);
insert all
    into LatiEsagoni values(21, 11, 6)
    into LatiEsagoni values(22, 11, 10)
select * from dual;
insert into Lati values(12, 11);
insert all
    into LatiEsagoni values(23, 12, 7)
    into LatiEsagoni values(24, 12, 10)
select * from dual;
insert into Lati values(13, 12);
insert all
    into LatiEsagoni values(25, 13, 10)
    into LatiEsagoni values(26, 13, 8)
select * from dual;
insert into Lati values(14, 13);
insert all
    into LatiEsagoni values(27, 14, 10)
    into LatiEsagoni values(28, 14, 4)
select * from dual;
insert into Lati values(15, 14);
insert all
    into LatiEsagoni values(29, 15, 10)
    into LatiEsagoni values(30, 15, 16)
select * from dual;
insert into Lati values(16, 15);
insert all
    into LatiEsagoni values(31, 16, 16)
    into LatiEsagoni values(32, 16, 4)
select * from dual;
insert into Lati values(17, 16);
insert all
    into LatiEsagoni values(33, 17, 16)
    into LatiEsagoni values(34, 17, 12)
select * from dual;
insert into Lati values(18, 17);
insert all
    into LatiEsagoni values(35, 18, 16)
    into LatiEsagoni values(36, 18, 17)
select * from dual;
insert into Lati values(19, 18);
insert all
    into LatiEsagoni values(37, 19, 11)
    into LatiEsagoni values(38, 19, 16)
select * from dual;
insert into Lati values(20, 19);
insert all
    into LatiEsagoni values(39, 20, 11)
    into LatiEsagoni values(40, 20, 17)
select * from dual;
insert into Lati values(21, 20);
insert all
    into LatiEsagoni values(41, 21, 11)
    into LatiEsagoni values(42, 21, 18)
select * from dual;
insert into Lati values(22, 21);
insert all
    into LatiEsagoni values(43, 22, 11)
    into LatiEsagoni values(44, 22, 5)
select * from dual;
insert into Lati values(23, 22);
insert all
    into LatiEsagoni values(45, 23, 14)
    into LatiEsagoni values(46, 23, 11)
select * from dual;
insert into Lati values(24, 23);
insert all
    into LatiEsagoni values(47, 24, 14)
    into LatiEsagoni values(48, 24, 5)
select * from dual;
insert into Lati values(25, 24);
insert all
    into LatiEsagoni values(49, 25, 9)
    into LatiEsagoni values(50, 25, 14)
select * from dual;
insert into Lati values(26, 25);
insert all
    into LatiEsagoni values(51, 26, 15)
    into LatiEsagoni values(52, 26, 14)
select * from dual;
insert into Lati values(27, 26);
insert all
    into LatiEsagoni values(53, 27, 2)
    into LatiEsagoni values(54, 27, 14)
select * from dual;
insert into Lati values(28, 27);
insert all
    into LatiEsagoni values(55, 28, 15)
    into LatiEsagoni values(56, 28, 2)
select * from dual;
insert into Lati values(29, 28);
insert all
    into LatiEsagoni values(57, 29, 19)
    into LatiEsagoni values(58, 29, 2)
select * from dual;
insert into Lati values(30, 29);
insert all
    into LatiEsagoni values(59, 30, 3)
    into LatiEsagoni values(60, 30, 2)
select * from dual;
insert into Lati values(31, 30);
insert all
    into LatiEsagoni values(61, 31, 19)
    into LatiEsagoni values(62, 31, 3)
select * from dual;
insert into Lati values(32, 31);
insert all
    into LatiEsagoni values(63, 32, 21)
    into LatiEsagoni values(64, 32, 3)
select * from dual;
insert into Lati values(33, 32);
insert all
    into LatiEsagoni values(65, 33, 31)
    into LatiEsagoni values(66, 33, 3)
select * from dual;
insert into Lati values(34, 33);
insert all
    into LatiEsagoni values(67, 34, 3)
    into LatiEsagoni values(68, 34, 20)
select * from dual;
insert into Lati values(35, 34);
insert all
    into LatiEsagoni values(69, 35, 31)
    into LatiEsagoni values(70, 35, 20)
select * from dual;
insert into Lati values(36, 35);
insert all
    into LatiEsagoni values(71, 36, 22)
    into LatiEsagoni values(72, 36, 20)
select * from dual;
insert into Lati values(37, 36);
insert all
    into LatiEsagoni values(73, 37, 20)
    into LatiEsagoni values(74, 37, 32)
select * from dual;
insert into Lati values(38, 37);
insert all
    into LatiEsagoni values(75, 38, 20)
    into LatiEsagoni values(76, 38, 7)
select * from dual;
insert into Lati values(39, 38);
insert all
    into LatiEsagoni values(77, 39, 32)
    into LatiEsagoni values(78, 39, 7)
select * from dual;
insert into Lati values(40, 39);
insert all
    into LatiEsagoni values(79, 40, 7)
    into LatiEsagoni values(80, 40, 23)
select * from dual;
insert into Lati values(41, 40);
insert all
    into LatiEsagoni values(81, 41, 7)
    into LatiEsagoni values(82, 41, 8)
select * from dual;
insert into Lati values(42, 41);
insert all
    into LatiEsagoni values(83, 42, 23)
    into LatiEsagoni values(84, 42, 8)
select * from dual;
insert into Lati values(43, 42);
insert all
    into LatiEsagoni values(85, 43, 8)
    into LatiEsagoni values(86, 43, 33)
select * from dual;
insert into Lati values(44, 43);
insert all
    into LatiEsagoni values(87, 44, 8)
    into LatiEsagoni values(88, 44, 24)
select * from dual;
insert into Lati values(45, 44);
insert all
    into LatiEsagoni values(89, 45, 4)
    into LatiEsagoni values(90, 45, 8)
select * from dual;
insert into Lati values(46, 45);
insert all
    into LatiEsagoni values(91, 46, 4)
    into LatiEsagoni values(92, 46, 24)
select * from dual;
insert into Lati values(47, 46);
insert all
    into LatiEsagoni values(93, 47, 4)
    into LatiEsagoni values(94, 47, 34)
select * from dual;
insert into Lati values(48, 47);
insert all
    into LatiEsagoni values(95, 48, 4)
    into LatiEsagoni values(96, 48, 12)
select * from dual;
insert into Lati values(49, 48);
insert all
    into LatiEsagoni values(97, 49, 12)
    into LatiEsagoni values(98, 49, 34)
select * from dual;
insert into Lati values(50, 49);
insert all
    into LatiEsagoni values(99, 50, 12)
    into LatiEsagoni values(100, 50, 25)
select * from dual;
insert into Lati values(51, 50);
insert all
    into LatiEsagoni values(101, 51, 12)
    into LatiEsagoni values(102, 51, 35)
select * from dual;
insert into Lati values(52, 51);
insert all
    into LatiEsagoni values(103, 52, 17)
    into LatiEsagoni values(104, 52, 12)
select * from dual;
insert into Lati values(53, 52);
insert all
    into LatiEsagoni values(105, 53, 17)
    into LatiEsagoni values(106, 53, 35)
select * from dual;
insert into Lati values(54, 53);
insert all
    into LatiEsagoni values(107, 54, 17)
    into LatiEsagoni values(108, 54, 26)
select * from dual;
insert into Lati values(55, 54);
insert all
    into LatiEsagoni values(109, 55, 18)
    into LatiEsagoni values(110, 55, 17)
select * from dual;
insert into Lati values(56, 55);
insert all
    into LatiEsagoni values(111, 56, 18)
    into LatiEsagoni values(112, 56, 26)
select * from dual;
insert into Lati values(57, 56);
insert all
    into LatiEsagoni values(113, 57, 18)
    into LatiEsagoni values(114, 57, 36)
select * from dual;
insert into Lati values(58, 57);
insert all
    into LatiEsagoni values(115, 58, 27)
    into LatiEsagoni values(116, 58, 18)
select * from dual;
insert into Lati values(59, 58);
insert all
    into LatiEsagoni values(117, 59, 5)
    into LatiEsagoni values(118, 59, 18)
select * from dual;
insert into Lati values(60, 59);
insert all
    into LatiEsagoni values(119, 60, 5)
    into LatiEsagoni values(120, 60, 27)
select * from dual;
insert into Lati values(61, 60);
insert all
    into LatiEsagoni values(121, 61, 37)
    into LatiEsagoni values(122, 61, 5)
select * from dual;
insert into Lati values(62, 61);
insert all
    into LatiEsagoni values(123, 62, 9)
    into LatiEsagoni values(124, 62, 5)
select * from dual;
insert into Lati values(63, 62);
insert all
    into LatiEsagoni values(125, 63, 9)
    into LatiEsagoni values(126, 63, 37)
select * from dual;
insert into Lati values(64, 63);
insert all
    into LatiEsagoni values(127, 64, 28)
    into LatiEsagoni values(128, 64, 9)
select * from dual;
insert into Lati values(65, 64);
insert all
    into LatiEsagoni values(129, 65, 38)
    into LatiEsagoni values(130, 65, 9)
select * from dual;
insert into Lati values(66, 65);
insert all
    into LatiEsagoni values(131, 66, 15)
    into LatiEsagoni values(132, 66, 9)
select * from dual;
insert into Lati values(67, 66);
insert all
    into LatiEsagoni values(133, 67, 38)
    into LatiEsagoni values(134, 67, 15)
select * from dual;
insert into Lati values(68, 67);
insert all
    into LatiEsagoni values(135, 68, 29)
    into LatiEsagoni values(136, 68, 15)
select * from dual;
insert into Lati values(69, 68);
insert all
    into LatiEsagoni values(137, 69, 19)
    into LatiEsagoni values(138, 69, 15)
select * from dual;
insert into Lati values(70, 69);
insert all
    into LatiEsagoni values(139, 70, 29)
    into LatiEsagoni values(140, 70, 19)
select * from dual;
insert into Lati values(71, 70);
insert all
    into LatiEsagoni values(141, 71, 30)
    into LatiEsagoni values(142, 71, 19)
select * from dual;
insert into Lati values(72, 71);
insert all
    into LatiEsagoni values(143, 72, 21)
    into LatiEsagoni values(144, 72, 19)
select * from dual;
insert all
    into VerticiLati values(1, 1, 6)
    into VerticiLati values(2, 1, 7)
    into VerticiLati values(3, 1, 1)
select * from dual;
insert all
    into VerticiLati values(4, 2, 2)
    into VerticiLati values(5, 2, 1)
    into VerticiLati values(6, 2, 11)
select * from dual;
insert all
    into VerticiLati values(7, 3, 2)
    into VerticiLati values(8, 3, 3)
    into VerticiLati values(9, 3, 15)
select * from dual;
insert all
    into VerticiLati values(10, 4, 4)
    into VerticiLati values(11, 4, 3)
    into VerticiLati values(12, 4, 19)
select * from dual;
insert all
    into VerticiLati values(13, 5, 5)
    into VerticiLati values(14, 5, 4)
    into VerticiLati values(15, 5, 23)
select * from dual;
insert all
    into VerticiLati values(16, 6, 6)
    into VerticiLati values(17, 6, 5)
    into VerticiLati values(18, 6, 27)
select * from dual;
insert all
    into VerticiLati values(19, 7, 30)
    into VerticiLati values(20, 7, 8)
    into VerticiLati values(21, 7, 7)
select * from dual;
insert all
    into VerticiLati values(22, 8, 34)
    into VerticiLati values(23, 8, 8)
    into VerticiLati values(24, 8, 9)
select * from dual;
insert all
    into VerticiLati values(25, 9, 9)
    into VerticiLati values(26, 9, 38)
    into VerticiLati values(27, 9, 10)
select * from dual;
insert all
    into VerticiLati values(28, 10, 10)
    into VerticiLati values(29, 10, 11)
    into VerticiLati values(30, 10, 12)
select * from dual;
insert all
    into VerticiLati values(31, 11, 12)
    into VerticiLati values(32, 11, 13)
    into VerticiLati values(33, 11, 41)
select * from dual;
insert all
    into VerticiLati values(34, 12, 13)
    into VerticiLati values(35, 12, 14)
    into VerticiLati values(36, 12, 45)
select * from dual;
insert all
    into VerticiLati values(37, 13, 15)
    into VerticiLati values(38, 13, 14)
    into VerticiLati values(39, 13, 16)
select * from dual;
insert all
    into VerticiLati values(40, 14, 16)
    into VerticiLati values(41, 14, 17)
    into VerticiLati values(42, 14, 48)
select * from dual;
insert all
    into VerticiLati values(43, 15, 18)
    into VerticiLati values(44, 15, 17)
    into VerticiLati values(45, 15, 52)
select * from dual;
insert all
    into VerticiLati values(46, 16, 19)
    into VerticiLati values(47, 16, 20)
    into VerticiLati values(48, 16, 18)
select * from dual;
insert all
    into VerticiLati values(49, 17, 21)
    into VerticiLati values(50, 17, 20)
    into VerticiLati values(51, 17, 55)
select * from dual;
insert all
    into VerticiLati values(52, 18, 22)
    into VerticiLati values(53, 18, 21)
    into VerticiLati values(54, 18, 59)
select * from dual;
insert all
    into VerticiLati values(55, 19, 22)
    into VerticiLati values(56, 19, 23)
    into VerticiLati values(57, 19, 24)
select * from dual;
insert all
    into VerticiLati values(58, 20, 24)
    into VerticiLati values(59, 20, 25)
    into VerticiLati values(60, 20, 62)
select * from dual;
insert all
    into VerticiLati values(61, 21, 25)
    into VerticiLati values(62, 21, 26)
    into VerticiLati values(63, 21, 66)
select * from dual;
insert all
    into VerticiLati values(64, 22, 26)
    into VerticiLati values(65, 22, 27)
    into VerticiLati values(66, 22, 28)
select * from dual;
insert all
    into VerticiLati values(67, 23, 28)
    into VerticiLati values(68, 23, 29)
    into VerticiLati values(69, 23, 69)
select * from dual;
insert all
    into VerticiLati values(70, 24, 29)
    into VerticiLati values(71, 24, 30)
    into VerticiLati values(72, 24, 31)
select * from dual;
insert all
    into VerticiLati values(73, 25, 31)
    into VerticiLati values(74, 25, 32)
    into VerticiLati values(75, 25, 72)
select * from dual;
insert all
    into VerticiLati values(76, 26, 32)
    into VerticiLati values(77, 26, 33)
select * from dual;
insert all
    into VerticiLati values(78, 27, 33)
    into VerticiLati values(79, 27, 34)
    into VerticiLati values(80, 27, 35)
select * from dual;
insert all
    into VerticiLati values(81, 28, 35)
    into VerticiLati values(82, 28, 36)
select * from dual;
insert all
    into VerticiLati values(83, 29, 36)
    into VerticiLati values(84, 29, 37)
select * from dual;
insert all
    into VerticiLati values(85, 30, 37)
    into VerticiLati values(86, 30, 38)
    into VerticiLati values(87, 30, 39)
select * from dual;
insert all
    into VerticiLati values(88, 31, 39)
    into VerticiLati values(89, 31, 40)
select * from dual;
insert all
    into VerticiLati values(90, 32, 40)
    into VerticiLati values(91, 32, 41)
    into VerticiLati values(92, 32, 42)
select * from dual;
insert all
    into VerticiLati values(93, 33, 42)
    into VerticiLati values(94, 33, 43)
select * from dual;
insert all
    into VerticiLati values(95, 34, 43)
    into VerticiLati values(96, 34, 44)
select * from dual;
insert all
    into VerticiLati values(97, 35, 44)
    into VerticiLati values(98, 35, 45)
    into VerticiLati values(99, 35, 46)
select * from dual;
insert all
    into VerticiLati values(100, 36, 46)
    into VerticiLati values(101, 36, 47)
select * from dual;
insert all
    into VerticiLati values(102, 37, 47)
    into VerticiLati values(103, 37, 48)
    into VerticiLati values(104, 37, 49)
select * from dual;
insert all
    into VerticiLati values(105, 38, 49)
    into VerticiLati values(106, 38, 50)
select * from dual;
insert all
    into VerticiLati values(107, 39, 50)
    into VerticiLati values(108, 39, 51)
select * from dual;
insert all
    into VerticiLati values(109, 40, 51)
    into VerticiLati values(110, 40, 52)
    into VerticiLati values(111, 40, 53)
select * from dual;
insert all
    into VerticiLati values(112, 41, 53)
    into VerticiLati values(113, 41, 54)
select * from dual;
insert all
    into VerticiLati values(114, 42, 54)
    into VerticiLati values(115, 42, 55)
    into VerticiLati values(116, 42, 56)
select * from dual;
insert all
    into VerticiLati values(117, 43, 56)
    into VerticiLati values(118, 43, 57)
select * from dual;
insert all
    into VerticiLati values(119, 44, 57)
    into VerticiLati values(120, 44, 58)
select * from dual;
insert all
    into VerticiLati values(121, 45, 58)
    into VerticiLati values(122, 45, 59)
    into VerticiLati values(123, 45, 60)
select * from dual;
insert all
    into VerticiLati values(124, 46, 61)
    into VerticiLati values(125, 46, 60)
select * from dual;
insert all
    into VerticiLati values(126, 47, 61)
    into VerticiLati values(127, 47, 62)
    into VerticiLati values(128, 47, 63)
select * from dual;
insert all
    into VerticiLati values(129, 48, 63)
    into VerticiLati values(130, 48, 64)
select * from dual;
insert all
    into VerticiLati values(131, 49, 64)
    into VerticiLati values(132, 49, 65)
select * from dual;
insert all
    into VerticiLati values(133, 50, 65)
    into VerticiLati values(134, 50, 66)
    into VerticiLati values(135, 50, 67)
select * from dual;
insert all
    into VerticiLati values(136, 51, 67)
    into VerticiLati values(137, 51, 68)
select * from dual;
insert all
    into VerticiLati values(138, 52, 68)
    into VerticiLati values(139, 52, 69)
    into VerticiLati values(140, 52, 70)
select * from dual;
insert all
    into VerticiLati values(141, 53, 70)
    into VerticiLati values(142, 53, 71)
select * from dual;
insert all
    into VerticiLati values(143, 54, 71)
    into VerticiLati values(144, 54, 72)
select * from dual;
insert into Giocatori values(1, 21);
insert into Giocatori values(2, 30);
insert into Giocatori values(3, 26);
insert into Giocatori values(4, 34);
insert all into GiocatoriPartite values(1, 1, 1, 'soldato', 'Rosso') into GiocatoriPartite values(2, 2, 1, 'tenente', 'Blu') into GiocatoriPartite values(3, 3, 1, 'caporale', 'Bianco') into GiocatoriPartite values(4, 4, 1, 'sergente', 'Arancione') select * from dual;
insert into CittaColonie values(1, 1, null);
insert into Strade values(1, 1, null, 6);
insert into CittaColonie values(2, 25, null);
insert into Strade values(2, 2, null, 31);
insert into CittaColonie values(3, 5, null);
insert into Strade values(3, 3, null, 5);
insert into CittaColonie values(4, 18, null);
insert into Strade values(4, 4, null, 22);
insert into CittaColonie values(5, 10, null);
insert into Strade values(5, 5, null, 10);
insert into CittaColonie values(6, 37, null);
insert into Strade values(6, 6, null, 47);
insert into CittaColonie values(7, 13, null);
insert into Strade values(7, 7, null, 15);
insert into CittaColonie values(8, 20, null);
insert into Strade values(8, 8, null, 24);
insert into Turni values(1, 0, 1, 1);
insert into TurniCittaColonie values(1, 1, 1);
insert into TurniCittaColonie values(2, 2, 1);
insert into CarteRisorsa values(1, 'Legno');
insert into Carte values(1, 1, null, 1);
insert into CarteTurni values(1, 1, 1);
insert into CarteRisorsa values(2, 'Grano');
insert into Carte values(2, 2, null, 1);
insert into CarteTurni values(2, 2, 1);
insert into StradeTurni values(1, 1, 1);
insert into StradeTurni values(2, 2, 1);
insert into Turni values(2, 0, 2, 1);
insert into CarteTurni values(3, 1, 2);
insert into CarteTurni values(4, 2, 2);
insert into TurniCittaColonie values(3, 3, 2);
insert into TurniCittaColonie values(4, 4, 2);
insert into CarteRisorsa values(3, 'Legno');
insert into Carte values(3, 3, null, 2);
insert into CarteTurni values(5, 3, 2);
insert into CarteRisorsa values(4, 'Minerale');
insert into Carte values(4, 4, null, 2);
insert into CarteTurni values(6, 4, 2);
insert into CarteRisorsa values(5, 'Grano');
insert into Carte values(5, 5, null, 2);
insert into CarteTurni values(7, 5, 2);
insert into StradeTurni values(3, 3, 2);
insert into StradeTurni values(4, 4, 2);
insert into Turni values(3, 0, 3, 1);
insert into CarteTurni values(8, 1, 3);
insert into CarteTurni values(9, 2, 3);
insert into CarteTurni values(10, 3, 3);
insert into CarteTurni values(11, 4, 3);
insert into CarteTurni values(12, 5, 3);
insert into TurniCittaColonie values(5, 5, 3);
insert into TurniCittaColonie values(6, 6, 3);
insert into CarteRisorsa values(6, 'Legno');
insert into Carte values(6, 6, null, 3);
insert into CarteTurni values(13, 6, 3);
insert into CarteRisorsa values(7, 'Minerale');
insert into Carte values(7, 7, null, 3);
insert into CarteTurni values(14, 7, 3);
insert into StradeTurni values(5, 5, 3);
insert into StradeTurni values(6, 6, 3);
insert into Turni values(4, 0, 4, 1);
insert into CarteTurni values(15, 1, 4);
insert into CarteTurni values(16, 2, 4);
insert into CarteTurni values(17, 3, 4);
insert into CarteTurni values(18, 4, 4);
insert into CarteTurni values(19, 5, 4);
insert into CarteTurni values(20, 6, 4);
insert into CarteTurni values(21, 7, 4);
insert into TurniCittaColonie values(7, 7, 4);
insert into TurniCittaColonie values(8, 8, 4);
insert into CarteRisorsa values(8, 'Lana');
insert into Carte values(8, 8, null, 4);
insert into CarteTurni values(22, 8, 4);
insert into CarteRisorsa values(9, 'Argilla');
insert into Carte values(9, 9, null, 4);
insert into CarteTurni values(23, 9, 4);
insert into CarteRisorsa values(10, 'Legno');
insert into Carte values(10, 10, null, 4);
insert into CarteTurni values(24, 10, 4);
insert into StradeTurni values(7, 7, 4);
insert into StradeTurni values(8, 8, 4);
insert into Turni values(5, 1, 1, 1);
insert into CarteTurni values(25, 1, 5);
insert into CarteTurni values(26, 2, 5);
insert into CarteTurni values(27, 3, 5);
insert into CarteTurni values(28, 4, 5);
insert into CarteTurni values(29, 5, 5);
insert into CarteTurni values(30, 6, 5);
insert into CarteTurni values(31, 7, 5);
insert into CarteTurni values(32, 8, 5);
insert into CarteTurni values(33, 9, 5);
insert into CarteTurni values(34, 10, 5);
insert into TurniCittaColonie values (9, 1, 5);
insert into TurniCittaColonie values (10, 2, 5);
insert into StradeTurni values (9, 1, 5);
insert into StradeTurni values (10, 2, 5);
insert into Brigante values(1, 1, 5);
insert into LanciDadi values(1, 6, 3, 5);
insert into Turni values(6, 1, 2, 1);
insert into CarteTurni values(35, 1, 6);
insert into CarteTurni values(36, 2, 6);
insert into CarteTurni values(37, 3, 6);
insert into CarteTurni values(38, 4, 6);
insert into CarteTurni values(39, 5, 6);
insert into CarteTurni values(40, 6, 6);
insert into CarteTurni values(41, 7, 6);
insert into CarteTurni values(42, 8, 6);
insert into CarteTurni values(43, 9, 6);
insert into CarteTurni values(44, 10, 6);
insert into TurniCittaColonie values (11, 3, 6);
insert into TurniCittaColonie values (12, 4, 6);
insert into StradeTurni values (11, 3, 6);
insert into StradeTurni values (12, 4, 6);
insert into Brigante values(2, 1, 6);
insert into LanciDadi values(2, 3, 1, 6);
insert into CarteRisorsa values(11, 'Grano');
insert into Carte values(11, 11, null, 1);
insert into CarteTurni values(45, 11, 6);
insert into Turni values(7, 1, 3, 1);
insert into CarteTurni values(46, 1, 7);
insert into CarteTurni values(47, 2, 7);
insert into CarteTurni values(48, 3, 7);
insert into CarteTurni values(49, 4, 7);
insert into CarteTurni values(50, 5, 7);
insert into CarteTurni values(51, 6, 7);
insert into CarteTurni values(52, 7, 7);
insert into CarteTurni values(53, 8, 7);
insert into CarteTurni values(54, 9, 7);
insert into CarteTurni values(55, 10, 7);
insert into CarteTurni values(56, 11, 7);
insert into TurniCittaColonie values (13, 5, 7);
insert into TurniCittaColonie values (14, 6, 7);
insert into StradeTurni values (13, 5, 7);
insert into StradeTurni values (14, 6, 7);
insert into Brigante values(3, 1, 7);
insert into LanciDadi values(3, 5, 3, 7);
insert into CarteRisorsa values(12, 'Argilla');
insert into Carte values(12, 12, null, 2);
insert into CarteTurni values(57, 12, 7);
insert into CarteRisorsa values(13, 'Minerale');
insert into Carte values(13, 13, null, 3);
insert into CarteTurni values(58, 13, 7);
insert into ScambiGiocatore values(1, 4);
insert into Scambi values(1, null, null, 1, 7);
insert into ScambiCarteRisorsaDate values(1, 7, 1);
delete from CarteTurni CT where CT.idCarta = 7 and CT.idTurno = 7;
insert into CarteRisorsa values(14, 'Minerale');
insert into Carte values(14, 14, null, 4);
insert into CarteTurni values(59, 14, 7);
insert into ScambiCarteRisorsaRicevute values(1, 9, 1);
delete from CarteTurni CT where CT.idCarta = 9 and CT.idTurno = 7;
insert into CarteRisorsa values(15, 'Argilla');
insert into Carte values(15, 15, null, 3);
insert into CarteTurni values(60, 15, 7);
delete from CarteTurni CT where CT.idCarta = 15 and CT.idTurno = 7;
delete from CarteTurni CT where CT.idCarta = 6 and CT.idTurno = 7;
insert into Strade values(9, null, null, 38);
insert into StradeSuccessive values(1, 5, 9);
insert into StradePrecedenti values(1, 9, 5);
insert into StradeTurni values(15, 9, 7);
insert into Turni values(8, 1, 4, 1);
insert into CarteTurni values(61, 1, 8);
insert into CarteTurni values(62, 2, 8);
insert into CarteTurni values(63, 3, 8);
insert into CarteTurni values(64, 4, 8);
insert into CarteTurni values(65, 5, 8);
insert into CarteTurni values(66, 8, 8);
insert into CarteTurni values(67, 10, 8);
insert into CarteTurni values(68, 11, 8);
insert into CarteTurni values(69, 12, 8);
insert into CarteTurni values(70, 13, 8);
insert into CarteTurni values(71, 14, 8);
insert into TurniCittaColonie values (15, 7, 8);
insert into TurniCittaColonie values (16, 8, 8);
insert into StradeTurni values (16, 7, 8);
insert into StradeTurni values (17, 8, 8);
insert into Brigante values(4, 14, 8);
insert into LanciDadi values(4, 3, 4, 8);
delete from CarteTurni CT where CT.idCarta = 12 and CT.idTurno = 8;
insert into CarteRisorsa values(16, 'Argilla');
insert into Carte values(16, 16, null, 4);
insert into CarteTurni values(72, 16, 8);
