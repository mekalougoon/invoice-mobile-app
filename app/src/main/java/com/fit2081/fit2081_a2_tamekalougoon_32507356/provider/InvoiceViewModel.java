package com.fit2081.fit2081_a2_tamekalougoon_32507356.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.fit2081.fit2081_a2_tamekalougoon_32507356.InvoiceActivity;

import java.util.List;
public class InvoiceViewModel extends AndroidViewModel {

    private InvoiceRepo repo;
    private InvoiceActivity clear;
    private LiveData<List<Invoice>> invoiceList;


    // create constructor
    public InvoiceViewModel(@NonNull Application application) {
        super(application);
        repo = new InvoiceRepo(application);
        invoiceList = repo.getAllMyInvoices();
    }

    public void insertNewInvoice(Invoice invoice) {
        repo.addNewInvoice(invoice);

    }

    public void deleteInvoice(Invoice invoice) {
        repo.deleteInvoice(invoice);

    }


    public LiveData<List<Invoice>> getMyInvoices() {
        return invoiceList;
    }

}
