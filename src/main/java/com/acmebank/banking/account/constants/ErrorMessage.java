package com.acmebank.banking.account.constants;

public class ErrorMessage {
    public static final String ACCT_WITH_CUST_ID_NOT_EXIST = "Account with customer id: {0,number,#} does not exist";
    public static final String ACCT_WITH_ACC_NO_NOT_EXIST = "Account with account no: {0,number,#} does not exist";

    public static final String SENDER_ACC_NO_NOT_EXIST = "Sender Account not found in system: {0,number,#}";
    public static final String RECEIVER_ACC_NO_NOT_EXIST = "Receiver Account not found in system: {0,number,#}";

    public static final String SENDER_NOT_ENOUGH_BALANCE = "Sender Account: {0,number,#} does not have enough balance: {1,number,#.000}";
    public static final String SENDER_RECEIVER_CURRENCY_NOT_MATCH = "Sender account currency: {0} does not match Receiver account currency: {1}.";
}
