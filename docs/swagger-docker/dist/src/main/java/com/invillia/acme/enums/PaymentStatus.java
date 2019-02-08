package com.invillia.acme.enums;

/**
 * @author Maruen Mehana <maruen@gmail.com>
 */

public enum PaymentStatus {
    
    CANCELED_REVERSAL,
    COMPLETED,
    CREATED,
    DENIED,
    EXPIRED,
    FAILED,
    PENDING,
    REFUNDED,
    REVERSED,
    PROCESSED,
    VOIDED;
    

    public static String getAllPaymentStatusesAsString() {
        StringBuilder stringbuilder = new StringBuilder();
        for (PaymentStatus status : PaymentStatus.values()) {
            stringbuilder.append(status.name()).append(",");
        }
        if (stringbuilder.indexOf(",") > -1) {
            stringbuilder.deleteCharAt(stringbuilder.lastIndexOf(","));
        }
        return stringbuilder.toString();
    }

}
