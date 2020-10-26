create table addresses(
  id integer not null primary key,
  postalCode text not null,
  country text not null,
  state text,
  city text not null,
  district text,
  street text,
  house text not null,
  room integer,
  specificPart text,
  unique (postalCode)
);
/
create table persons(
  id integer not null primary key,
  name text not null,
  surname text not null,
  patronymic text,
  birthDate numberic not null,
  phone text not null,
  estateAddressId integer not null,
  foreign key (estateAddressId) references addresses(id),
  unique (name,surname,patronymic,birthDate)
);
/
create table orders(
  id integer not null primary key,
  orderDate numberic not null default CURRENT_DATE,
  deliverDate numberic,
  orderAddressPlain text,
  orderAddressId integer,
  personId integer not null,
  description text not null,
  payed integer default 0,
  foreign key (orderAddressId) references addresses(id)
);
/
create table tickets(
  id integer not null primary key,
  summ real,
  orderId integer not null,
  foreign key (orderId) references orders(id)
);
create table categories(
  id integer not null primary key,
  name text not null,
  parentCategoryId integer
);
/
create table producers(
  id integer not null primary key,
  name text not null,
  producerAddressId integer not null,
  foreign key (producerAddressId) references addresses(id)
);
/
create table goods(
 id integer not null primary key,
 name text not null,
 description text not null,
 price real not null,
 imgFilePath text,
 quantity double not null default 0,
 producerId integer not null,
 categoryId integer not null,
 foreign key (categoryId) references categories(id),
 foreign key (producerId) references producers(id)
);
/
create table actions(
  id integer not null primary key,
  name text not null,
  description text not null,
  discountFixed real default 0,
  discountPercent real default 0,
  startDate numberic not null,
  endDate numberic not null,
  categoryId integer,
  goodId integer not null,
  foreign key (categoryId) references categories(id),
  foreign key (goodId) references goods(id)
);
/
create table orderPositions(
  id integer not null primary key,
  discount real not null default 0,
  quantity real not null default 1,
  orderId integer not null,
  goodId integer not null,
  actionId integer,
  foreign key (orderId) references orders(id),
  foreign key (goodId) references goods(id),
  foreign key (actionId) references actions(id)
);





