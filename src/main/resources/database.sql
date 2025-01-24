drop table if exists Account;
drop table if exists Tokenized_Data;

create table Account(
    id int primary key auto_increment,
    email varchar(255) not null unique,
    password varchar(255) not null,
    firstname varchar(50),
    lastname varchar(50)
);

create table Tokenized_Data(
    id int primary key auto_increment,
    token varchar(512),
    data varchar(1000)
);

create table User_Data(
    id int primary key auto_increment,
    account_id int foreign key references Account(id),
    token_id int foreign key references Tokenized_Data(id)
);
