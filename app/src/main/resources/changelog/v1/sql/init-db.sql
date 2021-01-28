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
  foreign key (estate_address_id) references addresses(id) on delete cascade on update cascade,
  unique (name,surname,patronymic,birth_date)
);
/
create table roles
(
 id integer not null primary key autoincrement,
 name text not null,
 description text not null,
 unique (name) -- role name must be unique
);
/
create table users(
  id integer not null primary key autoincrement,
  login text not null,
  password text not null,
  client_certificate_data text not null,
  avatar text,
  active integer not null default 1,
  person_id bigint not null,
  role_id bigint not null,
  foreign key (person_id) references persons(id) on delete cascade on update cascade,
  foreign key (role_id) references roles(id) on delete cascade on update cascade,
  unique (login),
  unique (person_id) -- disable multi_account for one person
);
/
create table authorities(
  id integer not null primary key autoincrement,
  name text not null,
  description text not null
);
/
create table role_authorities(
  id integer not null primary key autoincrement,
  role_id bigint not null,
  authority_id bigint not null,
  foreign key (role_id) references roles(id) on delete cascade on update cascade,
  foreign key (authority_id) references authorities(id) on delete cascade on update cascade
);
/
create table block_authorities(--for block authority for user.
  id integer not null primary key autoincrement,
  user_id bigint not null,
  authority_id bigint not null,
  foreign key (user_id) references users(id) on delete cascade on update cascade,
  foreign key (authority_id) references authorities(id) on delete cascade on update cascade
);
