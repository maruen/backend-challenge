package com.invillia.acme.enums;

/**
 * @author Maruen Mehana <maruen@gmail.com>
 */

public enum OrderStatus {
    
    PENDING,
    AWAITING_PAYMENT,
    AWAITING_FULFILLMENT,
    AWAITING_SHIPMENT,
    AWAITING_PICKUP,
    PARTIALLY_SHIPPED,
    COMPLETED,
    SHIPPED,
    CANCELLED,
    DECLINED,
    REFUNDED,
    DISPUTED,
    MANUAL_VERIFICACTION_REQUIRED,
    PARTIALLY_REFUNDED;
    

    public static String getAllOrderStatusesAsString() {
        StringBuilder stringbuilder = new StringBuilder();
        for (OrderStatus status : OrderStatus.values()) {
            stringbuilder.append(status.name()).append(",");
        }
        if (stringbuilder.indexOf(",") > -1) {
            stringbuilder.deleteCharAt(stringbuilder.lastIndexOf(","));
        }
        return stringbuilder.toString();
    }

}
