ALTER TABLE tbl_product ADD price BIGINT NOT NULL DEFAULT 0;

UPDATE tbl_product SET price = 3000 WHERE tbl_product.name = 'Laptop DELL';
UPDATE tbl_product SET price = 2900 WHERE tbl_product.name = 'Laptop ASUS';
UPDATE tbl_product SET price = 350 WHERE tbl_product.name = 'Black Berry';
UPDATE tbl_product SET price = 120 WHERE tbl_product.name = 'Nokia';
UPDATE tbl_product SET price = 95 WHERE tbl_product.name = 'Nokia 1280';

UPDATE tbl_product SET id = UNHEX(REPLACE('ccc26f1b-7048-46f1-bea0-95d162045c8c', '-', '')) WHERE tbl_product.name = 'Laptop DELL';
UPDATE tbl_product SET id = UNHEX(REPLACE('c7375cc1-4bf1-4177-90aa-d3fe123fc2c4', '-', '')) WHERE tbl_product.name = 'Laptop ASUS';
UPDATE tbl_product SET id = UNHEX(REPLACE('dba5218d-37ed-4d93-ba5e-d8f666187568', '-', '')) WHERE tbl_product.name = 'Black Berry';
UPDATE tbl_product SET id = UNHEX(REPLACE('9886d91b-af75-43ba-9e19-6b3ff99ba78b', '-', '')) WHERE tbl_product.name = 'Nokia';
UPDATE tbl_product SET id = UNHEX(REPLACE('7336fba2-3c3c-4c09-903f-630ce56d12d9', '-', '')) WHERE tbl_product.name = 'Nokia 1280';