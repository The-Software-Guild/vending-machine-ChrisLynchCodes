package org.chris.dao;

import java.time.LocalDate;

public interface VendingMachineAuditDao {
    void writeAuditEntry(String entry) throws ItemPersistenceException;
}
