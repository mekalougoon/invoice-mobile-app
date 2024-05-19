package com.fit2081.fit2081_a2_tamekalougoon_32507356.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.fit2081.fit2081_a2_tamekalougoon_32507356.Item;

import java.util.List;

public class InvoiceRepo {

    private InvoiceDao invoiceDao;

    private LiveData<List<Invoice>> invoiceList;

    // set up constructor
    public InvoiceRepo(Application app) {
        InvoiceDatabase db = InvoiceDatabase.getInstance(app);
        invoiceDao = db.invoiceDao();

        // retrieve item list
        invoiceList = invoiceDao.getAllInvoices();
    }

    // give to executor and call insert product to pass to database
    void addNewInvoice(Invoice invoice) {
        InvoiceDatabase.databaseWriteExecutors.execute(() -> {
            invoiceDao.addInvoice(invoice);
        });
    }

    void deleteInvoice(Invoice invoice) {
        InvoiceDatabase.databaseWriteExecutors.execute(() -> {
            invoiceDao.deleteInvoice(invoice);
        });
    }

    LiveData<List<Invoice>> getAllMyInvoices() {
        return invoiceList;
    }


}
