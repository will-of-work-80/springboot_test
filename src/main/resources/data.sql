-- Disable foreign key constraints
SET REFERENTIAL_INTEGRITY FALSE;

-- Clear existing data
DELETE FROM attendance_details;
DELETE FROM attendance_months;
DELETE FROM users;

-- Enable foreign key constraints
SET REFERENTIAL_INTEGRITY TRUE;

-- Initial data for users table
INSERT INTO users (id, company_name, department, user_name, staff_code) VALUES
(1, '〇〇 Company', '△△部署', '秋山　◇◇', '999999999901'),
(2, '〇〇 Company', '営業部', '田中　太郎', '999999999902'),
(3, '〇〇 Company', '企画部', '鈴木　花子', '999999999903'),
(4, '〇〇 Company', 'IT部', '佐藤　次郎', '999999999904');

-- Reset auto-increment
ALTER TABLE users ALTER COLUMN id RESTART WITH 5;
