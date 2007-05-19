// Cut & paste the below into the MySQL command prompt, after performing "use test"

use test

drop table mytesttable;

create table mytesttable(
	mybigint bigint,
	myint integer,
	myvarchar varchar(20),
	mychar char(20),
	mybool bool,
	mynumeric numeric(8),
	mydecimal decimal(8),
	mydouble double,	
	myreal real	
);

create unique index mytesttable_index_1 on mytesttable(mybigint);

insert into mytesttable values (1, 10, "A", "Z", true, 5000, 100, 1.2, 1.3);
insert into mytesttable values (2, 20, "B", "Y", false, 100, 200, 2.2, 2.3);
insert into mytesttable values (3, 30, "C", "X", false, 100, 300, 3.2, 3.3);
insert into mytesttable values (4, 40, "D", "W", true, 500, 400, 4.2, 4.3);
insert into mytesttable values (5, 50, "E", "V", false, 500, 500, 5.2, 5.3);
insert into mytesttable values (6, 60, "F", "T", false, 200, 600, 6.2, 6.3);
insert into mytesttable values (7, 70, "G", "S", true, null, 700, 7.2, 7.3);
insert into mytesttable values (8, 80, "H", "R", true, null, 800, 8.2, 8.3);
insert into mytesttable values (9, 90, "I", "Q", true, null, 900, 9.2, 9.3);
insert into mytesttable values (10, 100,  "J", "P", true, null, 1000, 10.2, 10.3);

	
