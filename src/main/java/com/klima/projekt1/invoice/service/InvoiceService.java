package com.klima.projekt1.invoice.service;

import com.klima.projekt1.invoice.model.entity.Invoice;
import com.klima.projekt1.invoice.repository.InvoiceRepository;
import com.klima.projekt1.offer.model.entity.Offer;
import com.klima.projekt1.user.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class InvoiceService {
    private InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<Invoice> getAllUserInvoices(User userId) {
        return invoiceRepository.findAllByOwner(userId);
    }

    public void createInvoice(User user, Offer offer) {
        Invoice invoice = Invoice.builder()
                .invoiceNumber(user.getInvoices().size() + 1)
                .issueDate(user.getPayDate().minusMonths(1))
                .owner(user).paymentDate(ZonedDateTime.now())
                .paymentDeadline(user.getPayDate())
                .price(offer.getPrice())
                .build();
        invoiceRepository.save(invoice);

    }
}
