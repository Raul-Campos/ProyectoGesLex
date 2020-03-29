create database geslexbd;

create table datospersonales(
	dni varchar(9) primary key,
    nombre_completo varchar(70) not null,
    fecha_nacimiento date not null,
    situacion_familiar enum('Soltero/a','Casado/a','Divorciado/a','Viudo/a') not null,
    situacion_laboral enum('Empleado/a','Desempleado/a') not null
);

create table datosincidentes(
	codigo int primary key auto_increment,
	matricula varchar(7) not null,
    fecha_hora datetime not null,
    lugar varchar(30) not null,
    aseguradora varchar(20) not null,
    numero_poliza varchar(20) not null,
    defensa varchar(250),
    matricula_contrario varchar(7),
    aseguradora_contrario varchar(20),
    numero_poliza_contrario varchar(20),
    parte varchar(250),
    eniado_por varchar(70) not null
);

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
)