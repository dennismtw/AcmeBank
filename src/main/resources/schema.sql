CREATE TABLE IF NOT EXISTS `account`(
    `acc_no` int AUTO_INCREMENT  PRIMARY KEY,
    `acc_type` varchar(10) NOT NULL,
    `customer_id` int NOT NULL,
    `currency_code` char(3) NOT NULL,
    `acc_balance` DECIMAL (20,3) NOT NULL,
    `created_at` date NOT NULL,
    `created_by` varchar(20) NOT NULL,
    `updated_at` date DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `transaction_detail`(
    `id` int AUTO_INCREMENT  PRIMARY KEY,
    `txn_no` char(20) NOT NULL,
    `acc_no` int NOT NULL,
    `currency_code` char(3) NOT NULL,
    `amount` DECIMAL (20,3) NOT NULL,
    `transfer_dt` timestamp NOT NULL,
    `status` int NOT NULL COMMENT '0-INIT, 1-DEBITED, 2-CREDITED, 3-ERROR',
    `created_at` date NOT NULL,
    `created_by` varchar(20) NOT NULL,
    `updated_at` date DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL
);
