package com.fit2081.fit2081_a2_tamekalougoon_32507356.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

//inform that this is a dao - deifines list of operations database will provide
@Dao
public interface InvoiceDao {

    // function so that android will add invoice to the database.
    @Insert
    void addInvoice(Invoice invoice);

    @Delete
    void deleteInvoice(Invoice invoice);

    // retrieve all invoices
    @Query("select * from invoices")
    LiveData<List<Invoice>> getAllInvoices();

}
