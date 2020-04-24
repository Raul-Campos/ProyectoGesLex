

create table datospersonales(
	dni varchar(9) primary key,
    nombre_completo varchar(70) not null,
    fecha_nacimiento date not null,
    situacion_familiar enum('Soltero/a','Casado/a','Divorciado/a','Viudo/a') not null,
    situacion_laboral enum('Empleado/a','Desempleado/a') not null
);

insert into datospersonales values( '1111111A', 'María Montero Pérez',
'1990-02-11', 'Soltero/a', 'Empleado/a');

create table coches(
    matricula varchar(7) primary key,
    marca varchar(15),
    modelo varchar(15),
    color varchar(15),
    numero_bastidor varchar(20),
    aseguradora varchar(20) not null,
    numero_poliza varchar(20) not null
);

insert into coches values ( '1234CVN', 'Citróën', 'Saxo', 'Verde', '111111111',
'Aseguradora1', '1235544234');
insert into coches values ( '3475HGE', 'Citroën', 'C4', 'Verde', '111111111',
'Aseguradora1', '1235544234');

create table datosincidentes(
	codigo int primary key auto_increment,
    fecha_hora datetime not null,
    lugar varchar(30) not null,
    defensa varchar(250),
    coche varchar(7)not null,
    coche_contrario varchar(7),
    parte varchar(250),
    enviado_por varchar(70) not null,
    foreign key (coche) references coches(matricula),
    foreign key (coche_contrario) references coches(matricula)
);

insert into datosincidentes (fecha_hora, lugar, coche, enviado_por) values  
('2020-02-03 14:00:45', 'Málaga', '1234CVN',  'Raúl');

create table expedientes(
	codigo int primary key auto_increment,
    fecha_creacion date not null,
    dni varchar(9) not null,
    cod_incidente int not null,
    foreign key (dni) references datospersonales(dni),
    foreign key (cod_incidente) references datosincidentes(codigo)
);


create table usuarios(
	nombre varchar(20) primary key,
    contrasena varchar(20) not null
);

insert into usuarios values ('admin', 'admin');