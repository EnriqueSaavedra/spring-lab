/* Populate tabla clientes */
INSERT INTO regiones (id,nombre) VALUES (1,'Sudamérica');
INSERT INTO regiones (id,nombre) VALUES (2,'Centroamérica');
INSERT INTO regiones (id,nombre) VALUES (3,'Norteamérica');
INSERT INTO regiones (id,nombre) VALUES (4,'Europa');
INSERT INTO regiones (id,nombre) VALUES (5,'Asia');
INSERT INTO regiones (id,nombre) VALUES (6,'Africa');
INSERT INTO regiones (id,nombre) VALUES (7,'Oceanía');
INSERT INTO regiones (id,nombre) VALUES (8,'Antártida');

INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(1, 'Andrés', 'Guzmán', 'profesor@bolsadeideas.com', '2018-01-01');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(2, 'Mr. John', 'Doe', 'john.doe@gmail.com', '2018-01-02');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(3, 'Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2018-01-03');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(4, 'Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2018-01-04');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(5, 'Erich', 'Gamma', 'erich.gamma@gmail.com', '2018-02-01');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(1, 'Richard', 'Helm', 'richard.helm@gmail.com', '2018-02-10');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(3, 'Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2018-02-18');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(5, 'John', 'Vlissides', 'john.vlissides@gmail.com', '2018-02-28');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(7, 'Dr. James', 'Gosling', 'james.gosling@gmail.com', '2018-03-03');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(1, 'Magma', 'Lee', 'magma.lee@gmail.com', '2018-03-04');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(8, 'Tornado', 'Roe', 'tornado.roe@gmail.com', '2018-03-05');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(2, 'Jade', 'Doe', 'jane.doe@gmail.com', '2018-03-06');

INSERT INTO usuarios (username,clave,activo, nombre, apellido, email) VALUES('esaavedraAdmin','$2y$12$Tq8IFWlbg4iA0.Pv8Pbn6.YFPTCwAtC04tIBR.A9RBn49q7CKtnH6 ',1,'Enrique','Saavedra','esaavedra@pragma.cl');
INSERT INTO usuarios (username,clave,activo, nombre, apellido, email) VALUES('esaavedraUser','$2y$12$DeyUbPf4xTR0p9SC.gUdOupNg81k6PSKSCJ00WPadZZIgQ4yaB1dm ',1,'Enrique','Saavedra','enrique.saavedra.perez@gmail.com');

INSERT INTO roles (nombre) VALUES('ROLE_USER');
INSERT INTO roles (nombre) VALUES('ROLE_ADMIN');

INSERT INTO usuario_roles (usuario_id,rol_id) VALUES(1,2);
INSERT INTO usuario_roles (usuario_id,rol_id) VALUES(1,1);
INSERT INTO usuario_roles (usuario_id,rol_id) VALUES(2,1);
