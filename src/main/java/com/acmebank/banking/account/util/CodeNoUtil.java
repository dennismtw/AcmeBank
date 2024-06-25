package com.acmebank.banking.account.util;



import java.util.UUID;

/**
 * <P>
 *  Unique ID Generator
 * </p>
 */
public class CodeNoUtil {
    /**
     * BusCode + UUID to generate business unique ID
     *
     * @param prefixCode
     * @return java.lang.String
     */
    public static String getNo(CodePrefixCode prefixCode) {
        return prefixCode.getCode() + UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }
}
