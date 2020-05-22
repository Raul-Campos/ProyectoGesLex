

create table cliente(
	dni varchar(9) primary key,
    nombre varchar(20) not null,
    apellidos varchar(60) not null,
    fecha_nacimiento date not null,
    sexo enum('Mujer', 'Hombre') not null,
    situacion_familiar enum('Soltero/a','Casado/a','Divorciado/a','Viudo/a') not null,
    situacion_laboral enum('Empleado/a','Desempleado/a') not null
);

insert into cliente values( '1111111A', 'María Montero Pérez',
'1990-02-11', 'Soltero/a', 'Empleado/a');


/*nsert into vehiculo values ( '1234CVN', 'Citróën', 'Saxo', 'Verde', '111111111',
'Aseguradora1', '1235544234');
insert into vehiculo values ( '3475HGE', 'Citroën', 'C4', 'Verde', '111111111',
'Aseguradora1', '1235544234');*/

create table letrado(
    dni_letrado varchar(9) primary key,
    nombre varchar(20) not null,
    apellidos varchar(60) not null,
    colegio varchar(30) not null,
    direccion varchar(40) not null,
    telefono int(9) not null,
    email varchar(40) not null
);

create table procurador(
    dni_procurador varchar(9) primary key,
    nombre varchar(20) not null,
    apellidos varchar(60) not null,
    direccion varchar(40) not null,
    telefono int(9) not null,
    email varchar(40) not null
);

create table perito(
    dni_perito varchar(9) primary key,
    nombre varchar(20) not null,
    apellidos varchar(60) not null,
    direccion varchar(40) not null,
    provincia varchar(50) not null,
    telefono int(9) not null,
    email varchar(40) not null
);

create table expediente(
	codigo int primary key auto_increment,
    fecha_creacion date not null,
    fecha_cierre date,
    dni varchar(9) not null,
    dni_letrado varchar(9) not null,
    dni_procurador varchar(9) not null,
    foreign key (dni) references cliente(dni),
    foreign key (dni_letrado) references letrado(dni_letrado),
    foreign key (dni_procurador) references procurador(dni_procurador)
);

create table expediente_perito(
	dni_perito varchar(9),
    codigo_expediente int,
    primary key (dni_perito, codigo_expediente),
    foreign key (dni_perito) references perito(dni_perito),
    foreign key (codigo_expediente) references expediente(codigo)
);

create table incidente(
	codigo_expediente int primary key,
    fecha_hora datetime not null,
    lugar varchar(30) not null,
    defensa varchar(250),
    parte enum('Amistoso', 'Asestado') not null,
    enviado_por varchar(70) not null,
    fallecidos enum('Si', 'No') not null,
    foreign key (codigo_expediente) references expediente(codigo)
);


create table vehiculo(
    matricula varchar(7) primary key,
    marca varchar(15) not null,
    modelo varchar(15) not null,
    color varchar(15) not null,
    numero_bastidor varchar(20) not null,
    aseguradora varchar(20) not null,
    numero_poliza varchar(20) not null,
    rol enum('Cliente','Implicado/a') not null
);

create table expediente_vehiculo(
    cod_expediente int,
    matricula varchar(7),
    primary key (cod_expediente, matricula),
    foreign key (cod_expediente) references expediente(codigo),
    foreign key (matricula) references vehiculo(matricula)
);

create table sentencia(
	cod_expediente int,
	titulo varchar(40),
    fecha_publicacion date not null,
    descripcion varchar(150),
    pdf blob not null,
    primary key (cod_expediente, titulo),
    foreign key (cod_expediente) references expediente(codigo)
);

create table documento(
	cod_expediente int,
	nombre varchar(30),
    fecha date not null,
    descripcion varchar(150),
    pdf blob not null,
    aportado_por varchar(80) not null,
    primary key (cod_expediente, nombre),
    foreign key (cod_expediente) references expediente(codigo)
);

create table hoja_encargo(
	cod_expediente int primary key,
    hoja blob not null,
    foreign key (cod_expediente) references expediente(codigo)
);

create table aviso(
	id_aviso int primary key auto_increment,
    fecha date not null,
    email varchar(40) not null,
    descripcion varchar(200) not null,
    cod_expediente int not null,
    foreign key (cod_expediente) references expediente(codigo)
);

create table usuarios(
	nombre varchar(20) primary key,
    contrasena varchar(20) not null
);

insert into usuarios values ('admin', 'admin');