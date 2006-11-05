drop table customer;

create table customer(
	id bigint,
	name varchar(20),
	vip bool,
	max numeric(8),
	min numeric(8)
);

insert into customer values (1, "John Smith", true, 100000, 100);
insert into customer values (1, "Robert Ford", false, 25000, 100);

	
