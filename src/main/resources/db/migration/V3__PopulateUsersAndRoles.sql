-- START TRANSACTION

INSERT INTO app_user (name, password) VALUES ('user2', '$2a$10$jiTWRBzr6PPMP4ECCXUuCeHi.RMnYa8zFE84.iMGeeJTvd/yH9WJm');
SET @user2Key = LAST_INSERT_ID();

INSERT INTO app_user (name, password) VALUES ('admin2', '$2a$10$C0cPZ9Sll7J8295No3HeceX.HgShqK3ZS./dVA3Gz.T3pYAQU411i');
SET @admin2Key = LAST_INSERT_ID();

INSERT INTO app_role (name) VALUES ('ROLE_ADMIN');
SET @roleAdminKey = LAST_INSERT_ID();

INSERT INTO app_role (name) VALUES ('ROLE_USER');
SET @roleUserKey = LAST_INSERT_ID();

INSERT INTO app_role (name) VALUES ('ROLE_VIP');
SET @roleVipKey = LAST_INSERT_ID();

INSERT INTO authorities_of_users(app_user_id , role_id) VALUES (@user2Key, @roleUserKey);
INSERT INTO authorities_of_users(app_user_id , role_id) VALUES (@user2Key, @roleVipKey);
INSERT INTO authorities_of_users(app_user_id , role_id) VALUES (@admin2Key, @roleAdminKey);
INSERT INTO authorities_of_users(app_user_id , role_id) VALUES (@admin2Key, @roleVipKey);


-- COMMIT