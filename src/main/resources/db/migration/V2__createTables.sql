drop table if exists app_role;
create table app_role(id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(250) UNICODE);
drop table if exists app_user;
create table app_user(id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(250) UNICODE, password VARCHAR(250));
drop table if exists authorities_of_users;
create table authorities_of_users(app_user_id INT , role_id INT );
