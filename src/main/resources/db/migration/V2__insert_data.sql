-- id: 53d951ec-c108-4492-879f-604d8cf541b8
-- username: admin
-- password: admin
INSERT INTO tbl_account (id, username, password, roles)
    VALUES (UNHEX(REPLACE('53d951ec-c108-4492-879f-604d8cf541b8', '-', '')), 'admin',
    '$argon2id$v=19$m=4096,t=3,p=1$Nl3PLAywj9KPlkVFTA5KAw$ZlBBxVEp+fRFkOyudJwBrgyezoroXO9Tlt1bAZpZj7w',
    'READ, WRITE, EXECUTE');

INSERT INTO tbl_account (id, username, password, roles)
    VALUES (UNHEX(REPLACE('f1fee843-95cd-4ae7-a09d-309885d1c92c', '-', '')), 'user1',
    '$argon2id$v=19$m=4096,t=3,p=1$YoKFImsK65u42hBxlm6T2Q$HRhTSIGR7L+9a4D4MLTyIMYZkDNHEdBNePZfuqctm4s',
    'READ');

-- id: bde5586f-3796-4874-a950-7393f00c7f84
-- name: laptop
INSERT INTO tbl_category (id, name)
    VALUES (UNHEX(REPLACE('bde5586f-3796-4874-a950-7393f00c7f84', '-', '')), 'Laptop');

-- id: adabe502-3554-4322-8d36-b3618a7e5d54
-- name: mobile phone
INSERT INTO tbl_category (id, name)
    VALUES (UNHEX(REPLACE('adabe502-3554-4322-8d36-b3618a7e5d54', '-', '')), 'Mobile phone');

INSERT INTO tbl_product (id, name, categoryId, createdBy, updatedBy)
    VALUES (UNHEX(REPLACE(UUID(), '-', '')), 'Laptop DELL',
        UNHEX(REPLACE('bde5586f-3796-4874-a950-7393f00c7f84', '-', '')),
        UNHEX(REPLACE('53d951ec-c108-4492-879f-604d8cf541b8', '-', '')),
        NULL);

INSERT INTO tbl_product (id, name, categoryId, createdBy, updatedBy)
    VALUES (UNHEX(REPLACE(UUID(), '-', '')), 'Laptop ASUS',
        UNHEX(REPLACE('bde5586f-3796-4874-a950-7393f00c7f84', '-', '')),
        UNHEX(REPLACE('53d951ec-c108-4492-879f-604d8cf541b8', '-', '')),
        NULL);

INSERT INTO tbl_product (id, name, categoryId, createdBy, updatedBy)
    VALUES (UNHEX(REPLACE(UUID(), '-', '')), 'Black Berry',
        UNHEX(REPLACE('adabe502-3554-4322-8d36-b3618a7e5d54', '-', '')),
        UNHEX(REPLACE('53d951ec-c108-4492-879f-604d8cf541b8', '-', '')),
        NULL);

INSERT INTO tbl_product (id, name, categoryId, createdBy, updatedBy)
    VALUES (UNHEX(REPLACE(UUID(), '-', '')), 'Nokia',
        UNHEX(REPLACE('adabe502-3554-4322-8d36-b3618a7e5d54', '-', '')),
        UNHEX(REPLACE('53d951ec-c108-4492-879f-604d8cf541b8', '-', '')),
        UNHEX(REPLACE('f1fee843-95cd-4ae7-a09d-309885d1c92c', '-', '')));

INSERT INTO tbl_product (id, name, categoryId, createdBy, updatedBy)
    VALUES (UNHEX(REPLACE(UUID(), '-', '')), 'Nokia 1280',
        UNHEX(REPLACE('adabe502-3554-4322-8d36-b3618a7e5d54', '-', '')),
        UNHEX(REPLACE('f1fee843-95cd-4ae7-a09d-309885d1c92c', '-', '')),
        NULL);