package com.acmebank.banking.account.util;


public enum CodePrefixCode {

    /**
     * No Prefix
     */
    CODE_NO_PREFIX("","No Prefix"),

    /**
     * Transaction Prefix
     */
    CODE_TRANSACTION_PREFIX("TXN_","Transaction Prefix");


    private String code;
    private String desc;

    CodePrefixCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
