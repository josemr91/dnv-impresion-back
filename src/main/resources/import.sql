INSERT INTO usuario (id, username, password, enabled, apellido, cuit, nombre, prioridad) VALUES (1,'admin','$2a$10$I7HfAkqcCNPRKrrWzyLWK.HIKn2d695/rkYMGO0AF4p6g3L.RC0uK',1,'adminApellido','1', 'adminNombre',1);
INSERT INTO usuario (id, username, password, enabled, apellido, cuit, nombre, prioridad) VALUES (2,'agente','$2a$10$I7HfAkqcCNPRKrrWzyLWK.HIKn2d695/rkYMGO0AF4p6g3L.RC0uK',1,'agenteApellido','2', 'agenteNombre',1);
INSERT INTO usuario (id, username, password, enabled, apellido, cuit, nombre, prioridad) VALUES (3,'centro','$2a$10$I7HfAkqcCNPRKrrWzyLWK.HIKn2d695/rkYMGO0AF4p6g3L.RC0uK',1,'ccydApellido','3', 'ccydNombre',1);

INSERT INTO role (id, nombre) VALUES (1,'ROLE_ADMIN');
INSERT INTO role (id, nombre) VALUES (2,'ROLE_AGENTE');
INSERT INTO role (id, nombre) VALUES (3,'ROLE_CCYD');

INSERT INTO usuario_role (usuario_id, role_id) VALUES (1,1);
INSERT INTO usuario_role (usuario_id, role_id) VALUES (2,2);
INSERT INTO usuario_role (usuario_id, role_id) VALUES (3,3);

INSERT INTO pedido_impresion (id, cantidad_copias, cantidad_hojas, color, disenio, doble_fax, fecha, feedback_pedido, nombre_archivo_clave, nombre_archivo_impresion, tamanio_papel, ubicacion_fisica, usuario_id) VALUES (1,1,2,0,'Vertical',1,'2020-10-04 20:03:27.950000',null,'b1a7f75a-000f-4027-b65a-f1e9bece589a_IF-2020-65912795-APN-DMZA%DNV(1).pdf','IF-2020-65912795-APN-DMZA%DNV(1).pdf','A4',0,2);
INSERT INTO pedido_impresion (id, cantidad_copias, cantidad_hojas, color, disenio, doble_fax, fecha, feedback_pedido, nombre_archivo_clave, nombre_archivo_impresion, tamanio_papel, ubicacion_fisica, usuario_id) VALUES (2,1,2,0,'Vertical',1,'2020-10-04 20:03:27.950000',null,'066d4122-7edd-457a-b66d-47d531ac2a46_IF-2020-65938821-APN-DMZA%DNV.pdf','IF-2020-65938821-APN-DMZA%DNV.pdf','A4',0,2);
INSERT INTO pedido_impresion (id, cantidad_copias, cantidad_hojas, color, disenio, doble_fax, fecha, feedback_pedido, nombre_archivo_clave, nombre_archivo_impresion, tamanio_papel, ubicacion_fisica, usuario_id) VALUES (3,1,2,0,'Vertical',1,'2020-10-04 20:03:27.950000',null,'b1a7f75a-000f-4027-b65a-f1e9bece589a_IF-2020-65912795-APN-DMZA%DNV(1).pdf','IF-2020-65912795-APN-DMZA%DNV(1).pdf','A4',0,2);

INSERT INTO pedido_impresion_estado (id, estado_pedido_impresion, fecha_fin, fecha_inicio, username, full_name_username, pedido_impresion_id) VALUES (1,'SIN_ASIGNAR',null,'2020-10-04 20:03:27.950000',null,null,1);

INSERT INTO pedido_impresion_estado (id, estado_pedido_impresion, fecha_fin, fecha_inicio, username, full_name_username, pedido_impresion_id) VALUES (2,'SIN_ASIGNAR','2020-10-04 20:04:27.950000','2020-10-04 20:03:27.950000','centro','ccydApellido, ccydNombre',2);
INSERT INTO pedido_impresion_estado (id, estado_pedido_impresion, fecha_fin, fecha_inicio, username, full_name_username, pedido_impresion_id) VALUES (3,'PARA_IMPRIMIR',null,'2020-10-04 20:03:27.950000',null,null,2);

INSERT INTO pedido_impresion_estado (id, estado_pedido_impresion, fecha_fin, fecha_inicio, username, full_name_username, pedido_impresion_id) VALUES (4,'SIN_ASIGNAR',null,'2020-10-04 20:03:27.950000',null,null,3);