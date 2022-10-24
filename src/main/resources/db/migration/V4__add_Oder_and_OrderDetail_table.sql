DROP TABLE IF EXISTS tbl_orderDetail;
DROP TABLE IF EXISTS tbl_order;

CREATE TABLE tbl_order (
    id VARBINARY(16) UNIQUE NOT NULL PRIMARY KEY,
    datetime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    totalPrice BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE tbl_orderDetail (
    id VARBINARY(16) NOT NULL PRIMARY KEY,
    productId VARBINARY(16) NOT NULL,
    orderId VARBINARY(16) NOT NULL,
    price BIGINT NOT NULL DEFAULT 0,
    quantity INT NOT NULL DEFAULT 0,
    total BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (orderId) REFERENCES tbl_order(id) ON DELETE CASCADE,
    FOREIGN KEY (productId) REFERENCES tbl_product(id) ON DELETE CASCADE
);

INSERT INTO tbl_order (id, totalPrice)
    VALUES (UNHEX(REPLACE('73c20bd3-1789-4b7b-aa14-84cc93fb4ccc', '-', '')), 3700);
INSERT INTO tbl_order (id, totalPrice)
    VALUES (UNHEX(REPLACE('b8c1ec02-74cd-4b4a-8a5a-87bafd5f92b7', '-', '')), 2900);
INSERT INTO tbl_order (id, totalPrice)
    VALUES (UNHEX(REPLACE('0fa46336-5cb4-4b42-8078-5dcd4c8d95b7', '-', '')), 95);

INSERT INTO tbl_orderDetail (id, productId, orderId, price, quantity, total)
    VALUES (UNHEX(REPLACE(UUID(), '-', '')),
        UNHEX(REPLACE('ccc26f1b-7048-46f1-bea0-95d162045c8c', '-', '')),
        UNHEX(REPLACE('73c20bd3-1789-4b7b-aa14-84cc93fb4ccc', '-', '')), 3000, 1, 3000);
INSERT INTO tbl_orderDetail (id, productId, orderId, price, quantity, total)
    VALUES (UNHEX(REPLACE(UUID(), '-', '')),
        UNHEX(REPLACE('dba5218d-37ed-4d93-ba5e-d8f666187568', '-', '')),
        UNHEX(REPLACE('73c20bd3-1789-4b7b-aa14-84cc93fb4ccc', '-', '')), 350, 2, 700);

INSERT INTO tbl_orderDetail (id, productId, orderId, price, quantity, total)
    VALUES (UNHEX(REPLACE(UUID(), '-', '')),
        UNHEX(REPLACE('c7375cc1-4bf1-4177-90aa-d3fe123fc2c4', '-', '')),
        UNHEX(REPLACE('b8c1ec02-74cd-4b4a-8a5a-87bafd5f92b7', '-', '')), 2900, 1, 2900);

INSERT INTO tbl_orderDetail (id, productId, orderId, price, quantity, total)
    VALUES (UNHEX(REPLACE(UUID(), '-', '')),
        UNHEX(REPLACE('7336fba2-3c3c-4c09-903f-630ce56d12d9', '-', '')),
        UNHEX(REPLACE('0fa46336-5cb4-4b42-8078-5dcd4c8d95b7', '-', '')), 95, 1, 95);