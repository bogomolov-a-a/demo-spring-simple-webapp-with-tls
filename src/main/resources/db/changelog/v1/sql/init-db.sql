create table addresses(
  id bigint not null primary key,
  postal_code text not null,
  country text not null,
  state text,
  city text not null,
  district text,
  street text,
  house text not null,
  room integer,
  specific_part text,
  unique (postal_code)
);
/
create table persons(
  id bigint not null primary key,
  name text not null,
  surname text not null,
  patronymic text,
  birth_date numberic not null,
  phone text not null,
  estate_address_id bigint not null,
  foreign key (estate_address_id) references addresses(id),
  unique (name,surname,patronymic,birth_date)
);
/
create table orders(
  id bigint not null primary key,
  order_date numberic not null default CURRENT_DATE,
  deliver_date numberic,
  order_address_plain text,
  description text not null,
  payed integer default 0,
  order_address_id bigint,
  person_id bigint not null,
  foreign key (order_address_id) references addresses(id),
  foreign key (person_id ) references persons(id)
);
/
create table tickets(
  id bigint not null primary key,
  summ real,
  order_id bigint not null,
  foreign key (order_id) references orders(id)
);
create table categories(
  id bigint not null primary key,
  name text not null,
  parent_category_id bigint
);
/
create table producers(
  id bigint not null primary key,
  name text not null,
  producer_address_id bigint not null,
  foreign key (producer_address_id) references addresses(id)
);
/
create table goods(
 id bigint not null primary key,
 name text not null,
 description text not null,
 price real not null,
 img_file_path text,
 quantity double not null default 0,
 producer_id bigint not null,
 category_id bigint not null,
 foreign key (category_id) references categories(id),
 foreign key (producer_id) references producers(id)
);
/
create table actions(
  id bigint not null primary key,
  name text not null,
  description text not null,
  discount_fixed real default 0,
  discount_percent real default 0,
  start_date numberic not null,
  end_date numberic not null,
  category_id bigint,
  good_id bigint not null,
  foreign key (category_id) references categories(id),
  foreign key (good_id) references goods(id)
);
/
create table order_positions(
  id bigint not null primary key,
  discount real not null default 0,
  quantity real not null default 1,
  order_id bigint not null,
  good_id bigint not null,
  action_id bigint,
  foreign key (order_id) references orders(id),
  foreign key (good_id) references goods(id),
  foreign key (action_id) references actions(id)
);





