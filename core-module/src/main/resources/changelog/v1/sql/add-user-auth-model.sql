create table roles
(
 id integer not null primary key autoincrement,
 name text not null,
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
  name text not null
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