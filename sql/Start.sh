#!/bin/bash
sudo docker pull postgres:latest
sudo docker run -d --name=db --rm -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=BookStore postgres
sudo timeout 2 ping 127.0.0.1
sudo docker exec -it db psql -v ON_ERROR_STOP=1 -U postgres -c "\c BookStore;" -c "
create table orders
(
    id            bigserial                                primary key,
    total_price   double precision                         not null,
    book_id       bigint                      unique       not null,
    client_id     bigint                      unique       not null,
    date_of_order timestamp with time zone                 not null,
    order_status  varchar(9)                               not null,
    quantity      integer                                  not null
);

create table requests
(
    id              bigserial                              primary key,
    book_id         bigint                    unique       not null,
    quantity        integer                                not null,
    date_of_request timestamp with time zone               not null,
    is_completed    boolean default false                  not null
);

create table books
(
    id            bigserial                      primary key,
    name          varchar                        not null,
    author        varchar                        not null,
    price         double precision               not null,
    description   varchar                        not null,
    date_of_issue timestamp with time zone       not null,
    availability  boolean                        not null,
    stock         integer                        not null,
    receipt_date  timestamp with time zone       not null,
    foreign key (id) references orders (book_id) on delete cascade,
    foreign key (id) references requests (book_id) on delete cascade
);

create table clients
(
    id           bigserial     primary key,
    name         varchar       not null,
    phone_number varchar(12)   not null,
    foreign key (id) references orders (client_id) on delete cascade
);

create table roles
(
    id   serial       not null
        constraint roles_pk
            primary key,
    name varchar(20)  not null
);

create table users
(
    id         serial not null
        constraint users_pk
            primary key,
    login           varchar(50),
    password        varchar(500),
    role_id         integer
        constraint users_roles_id_fk
            references roles
);

create unique index users_login_uindex
    on users (login);

insert into roles(name) values ('ROLE_ADMIN');
insert into roles(name) values ('ROLE_USER');

insert into users(login, password, role_id) values ('root', '$2a$10$9dHyenI7skKOVHqWSY2BdeWrZ2WpxCN4oKvxPRkCAbaHRg0wvveUS', 1);

insert into orders(total_price, book_id, client_id, date_of_order, order_status, quantity)
 values (2700, 1, 1, to_date('12/3/2020', 'DD/M/YYYY'), 'CANCELED', 6);
insert into orders(total_price, book_id, client_id, date_of_order, order_status, quantity)
 values (2000, 2, 2, to_date('23/5/2020', 'DD/M/YYYY'), 'COMPLETED', 10);
insert into orders(total_price, book_id, client_id, date_of_order, order_status, quantity)
 values (3300, 3, 3, to_date('24/7/2021', 'DD/M/YYYY'), 'NEW', 3);

insert into requests(book_id, quantity, date_of_request, is_completed)
 values (1, 1, to_date('12/3/2020', 'DD/M/YYYY'), true);
insert into requests(book_id, quantity, date_of_request, is_completed)
 values (2, 10, to_date('23/5/2020', 'DD/M/YYYY'), true);
insert into requests(book_id, quantity, date_of_request, is_completed)
 values (3, 2, to_date('24/7/2021', 'DD/M/YYYY'), false);

insert into books(name, author, price, description, date_of_issue, availability, stock, receipt_date)
 values ('In Search of Lost Time', 'Marcel Proust', 450, 'aaa', to_date('11/1/1988', 'DD/M/YYYY'), true, 5, to_date('12/3/2020', 'DD/M/YYYY'));
insert into books(name, author, price, description, date_of_issue, availability, stock, receipt_date)
 values ('Ulysses', 'James Joyce', 200, 'bbb', to_date('22/2/1899', 'DD/M/YYYY'), false, 0, to_date('23/5/2020', 'DD/M/YYYY'));
insert into books(name, author, price, description, date_of_issue, availability, stock, receipt_date)
 values ('Don Quixote', 'Miguel de Cervantes', 1100, 'ccc', to_date('23/3/1987', 'DD/M/YYYY'), true, 1, to_date('24/7/2021', 'DD/M/YYYY'));

insert into clients(name, phone_number)
 values ('Alexey', '1111111111');
insert into clients(name, phone_number)
 values ('Andrew', '2222222222');
insert into clients(name, phone_number)
 values ('Maks', '3333333333');"