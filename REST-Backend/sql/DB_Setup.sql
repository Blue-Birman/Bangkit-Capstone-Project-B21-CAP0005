CREATE DATABASE skin_cancer_app;

CREATE USER 'db_user'@'localhost' IDENTIFIED BY 'password';
CREATE USER 'db_user'@'%' IDENTIFIED BY 'password';

GRANT ALL PRIVILEGES ON *.* TO 'db_user'@'localhost';
GRANT ALL PRIVILEGES ON *.* TO 'db_user'@'%';`