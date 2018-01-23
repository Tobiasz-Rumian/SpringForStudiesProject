package com.rumian.projekt1.invoice.repository;

import com.rumian.projekt1.invoice.model.entity.Invoice;
import com.rumian.projekt1.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findAllByOwner(User userId);
}
