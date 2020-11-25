create table addresses(
  id integer not null primary key autoincrement,
  postal_code text not null,
  country text not null,
  state text,
  city text not null,
  district text,
  street text,
  house text not null,
  room integer,
  specific_part text,
  unique (postal_code,country,state,city,district,street,house,room,specific_part)
);
/
create table persons(
  id integer not null primary key autoincrement,
  name text not null,
  surname text not null,
  patronymic text,
  birth_date TIMESTAMP not null,
  phone text not null,
  estate_address_id bigint not null,
  unique (name,surname,patronymic,birth_date),
  unique (phone),
  foreign key (estate_address_id) references addresses(id) on delete cascade on update cascade
);
/
create table orders(
  id integer not null primary key autoincrement,
  order_date TIMESTAMP not null default current_timestamp,
  deliver_date TIMESTAMP,
  order_address_plain text,
  description text not null,
  payed integer default 0,
  order_address_id bigint,
  person_id bigint not null,
  unique(person_id,order_address_id,order_address_plain,order_date),
  foreign key (order_address_id) references addresses(id) on delete cascade on update cascade,
  foreign key (person_id ) references persons(id) on delete cascade on update cascade
);
/
create table tickets(
  id integer not null primary key autoincrement,
  summ real,
  order_id bigint not null,
  foreign key (order_id) references orders(id) on delete cascade on update cascade
);
/
create table categories(
  id integer not null primary key autoincrement,
  name text not null,
  parent_category_id bigint,
  unique (name,parent_category_id)
);
/
create table producers(
  id integer not null primary key autoincrement,
  name text not null,
  producer_address_id bigint not null,
  unique(name,producer_address_id),
  foreign key (producer_address_id) references addresses(id)on delete cascade on update cascade
);
/
create table goods(
 id integer not null primary key autoincrement,
 name text not null,
 description text not null,
 price real not null,
 img_file_path text,
 quantity double not null default 0,
 producer_id bigint not null,
 category_id bigint not null,
 unique (name,producer_id,category_id),
 foreign key (category_id) references categories(id) on delete cascade on update cascade,
 foreign key (producer_id) references producers(id) on delete cascade on update cascade
);
/
create table actions(
  id integer not null primary key autoincrement,
  name text not null,
  description text not null,
  discount_fixed real default 0,
  discount_percent real default 0,
  start_date TIMESTAMP not null,
  end_date TIMESTAMP not null,
  category_id bigint,
  good_id bigint ,
  unique(name,category_id,good_id),
  foreign key (category_id) references categories(id) on delete cascade on update cascade,
  foreign key (good_id) references goods(id)on delete cascade on update cascade
);
/
create table order_positions(
  id integer not null primary key autoincrement,
  discount real not null default 0,
  quantity real not null default 1,
  order_id bigint not null,
  good_id bigint not null,
  action_id bigint,
  unique (order_id,good_id),
  foreign key (order_id) references orders(id) on delete cascade on update cascade,
  foreign key (good_id) references goods(id) on delete cascade on update cascade,
  foreign key (action_id) references
  actions(id)on delete cascade on update cascade
);





