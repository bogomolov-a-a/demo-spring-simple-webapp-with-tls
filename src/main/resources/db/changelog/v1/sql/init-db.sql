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
  specificPart text
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
  FOREIGN KEY (estateAddressId) references addresses(id)
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
  FOREIGN KEY (orderAddressId) references addresses(id)
);
/
create table tickets(
  id integer not null primary key,
  summ real,
  orderId integer not null,
  FOREIGN KEY (orderId) references orders(id)
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
  producerId integer not null,
  FOREIGN KEY (producerId) references addresses(id)
);
/
create table goods(
 id integer not null primary key,
 name text not null,
 description text not null,
 price real not null,
 producerId integer not null,
 categoryId integer not null,
 FOREIGN KEY (categoryId) references categories(id),
 FOREIGN KEY (producerId) references producers(id)
);
/
create table actions(
  id integer not null primary key,
  categoryId integer,
  goodId integer not null,
  discountFixed real default 0,
  discountPercent real default 0,
  FOREIGN KEY (categoryId) references categories(id),
  FOREIGN KEY (goodId) references goods(id)
);
/
create table orderPositions(
  id integer not null primary key,
  discount real not null default 0,
  quantity real not null default 0,
  orderId integer not null,
  goodId integer not null,
  actionId integer,
  FOREIGN KEY (orderId) references orders(id),
  FOREIGN KEY (goodId) references goods(id),
  FOREIGN KEY (actionId) references actions(id)
);





