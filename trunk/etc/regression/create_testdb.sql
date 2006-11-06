drop table customer;

create table customer(
	id bigint,
	name varchar(20),
	vip bool,
	max numeric(8),
	min numeric(8)
);

create unique index cust_index_1 on customer (id);

insert into customer values (1, "John Smith", true, 100000, 5000);
insert into customer values (2, "Robert Ford", false, 25000, 100);
insert into customer values (3, "Tom Jones", false, 25000, 100);
insert into customer values (4, "Henry Trull", true, 100000, 500);
insert into customer values (5, "Poke Yui", false, 25000, 500);
insert into customer values (6, "Gerda Getter", false, 25000, 200);

	
