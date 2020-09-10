INSERT INTO usuario (id, username, password, enabled, apellido, cuit, nombre, prioridad) VALUES (1,'joserodriguez','',0,'Rodriguez','20-35580529-9', 'Jose Maria',1);

INSERT INTO role (id, nombre) VALUES (1,'ROLE_ADMIN');

INSERT INTO usuario_role (usuario_id, role_id) VALUES (1,1);
