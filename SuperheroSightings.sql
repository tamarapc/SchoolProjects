drop database if exists SuperheroSightings;
create database SuperheroSightings;
use SuperheroSightings;

create table Powers(
Id int primary key auto_increment,
`Name` varchar(50) not null
);

create table Supers(
Id int primary key auto_increment,
`Name` varchar(30) not null,
Description varchar(300) null,
PowerId int not null,

foreign key fk_Supers_Powers (PowerId) 
references Powers (Id)
);


create table Organizations(
Id int primary key auto_increment,
`Name` varchar(50) not null,
Description varchar(300) not null,
Contact varchar(200) not null
);

create table SupersInOrganizations(
SuperId int not null,
OrganizationId int not null,

primary key (SuperId, OrganizationId),

foreign key fk_SupersInOrganizations_Supers (SuperId) 
references Supers (Id),

foreign key fk_SupersInOrganizations_Organizations (OrganizationId)
references Organizations (Id)
);

create table Locations(
Id int primary key auto_increment,
`Name` varchar(50) not null,
Description varchar(300) not null,
Address varchar(200) not null,
Latitude decimal(9,6) not null,
Longitude decimal(9,6) not null
);

create table Sightings(
Id int primary key auto_increment,
LocationId int not null,
SightDate date not null,

foreign key fk_Sightings_Location (LocationId) 
references Locations (Id)
);

create table SupersPerSightings(
SuperId int not null,
SightingId int not null,

primary key(SuperId, SightingId),
foreign key fk_SupersPerSightings_Supers (SuperId)
references Supers (Id),
foreign key fk_SupersPerSightings_Sightings (SightingId)
references Sightings (Id)
);

insert into Powers(`Name`) 
values('SuperSpeed');

insert into Locations(`Name`, Description, Address, Latitude, Longitude)
values
('Minneapolis Skyway', 'Yep', '5555 Nicolett Mall', 44.979166, -93.271894);

insert into Organizations(`Name`, Description, Contact)
values ('Justice League', 'Pretty Cool', 'Take three lefts, one right, a left, and then four rights and you will find them');