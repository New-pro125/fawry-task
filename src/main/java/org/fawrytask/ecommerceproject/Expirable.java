package org.fawrytask.ecommerceproject;

import java.time.LocalDate;

interface Expirable {
    boolean isExpired();
    LocalDate getExpiryDate();
}
