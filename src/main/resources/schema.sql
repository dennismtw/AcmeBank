CREATE TABLE IF NOT EXISTS `account`(
    `acc_no` int AUTO_INCREMENT  PRIMARY KEY,
    `acc_type` varchar(10) NOT NULL,
    `customer_id` int NOT NULL,
    `currency_code` char(3) NOT NULL,
    `acc_balance` DECIMAL (20,3) NOT NULL,
    `created_at` timestamp NOT NULL,
    `created_by` varchar(20) NOT NULL,
    `updated_at` timestamp DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `transaction_detail`(
    `txn_no` char(36) NOT NULL PRIMARY KEY,
    `sender_acc_no` int NOT NULL,
    `receiver_acc_no` int NOT NULL,
    `currency_code` char(3) NOT NULL,
    `amount` DECIMAL (20,3) NOT NULL,
    `transfer_dt` timestamp NOT NULL,
    `created_at` timestamp NOT NULL,
    `created_by` varchar(20) NOT NULL,
    `updated_at` timestamp DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL
);
