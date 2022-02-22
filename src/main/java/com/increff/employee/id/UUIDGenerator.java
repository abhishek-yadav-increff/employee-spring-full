package com.increff.employee.id;

import java.util.UUID;
import org.hibernate.Session;
import org.hibernate.tuple.ValueGenerator;

public class UUIDGenerator implements ValueGenerator<String> {
    public String generateValue(Session session, Object owner) {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
