DROP TABLE IF EXISTS tbl_product;
DROP TABLE IF EXISTS tbl_account;
DROP TABLE IF EXISTS tbl_category;

CREATE TABLE tbl_account (
    id VARBINARY(16) NOT NULL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password TEXT NOT NULL,
    roles VARCHAR(255) NOT NULL
);

CREATE TABLE tbl_category (
    id VARBINARY(16) NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE tbl_product (
    id VARBINARY(16) NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    categoryId VARBINARY(16) NOT NULL,
    createdBy VARBINARY(16) NOT NULL,
    updatedBy VARBINARY(16),
    FOREIGN KEY (categoryId) REFERENCES tbl_category(id) ON DELETE CASCADE,
    FOREIGN KEY (createdBy) REFERENCES tbl_account(id) ON DELETE CASCADE,
    FOREIGN KEY (updatedBy) REFERENCES tbl_account(id) ON DELETE SET NULL
);