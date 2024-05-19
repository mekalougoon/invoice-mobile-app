package com.fit2081.fit2081_a2_tamekalougoon_32507356.provider;


import static com.fit2081.fit2081_a2_tamekalougoon_32507356.provider.Invoice.TABLE_NAME;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.fit2081.fit2081_a2_tamekalougoon_32507356.Item;

import java.util.ArrayList;

// inform android that class represents a table
@Entity(tableName = TABLE_NAME)
public class Invoice {

    // set table name
    public static final String TABLE_NAME="invoices";

    // create the columns
    // inform that this column comes with a name and ensure it is not null
    // set it as the primary key
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "invoice_id")
    private int invoiceId;
    @ColumnInfo(name = "invoice_issuer_name")
    private String invoiceIssuerName;
    @ColumnInfo(name = "invoice_buyer_name")
    private String invoiceBuyerName;

    @ColumnInfo(name = "invoice_buyer_address")
    private String invoiceBuyerAddress;

    @ColumnInfo(name = "invoice_isPaid")
    private boolean invoiceIsPaid;

    @ColumnInfo(name = "invoice_items")
    private ArrayList<Item> items;

    @ColumnInfo(name = "invoice_total")
    private int invoiceTotal;

    // create a constructor for name
    public Invoice(String invoiceIssuerName, String invoiceBuyerName, String invoiceBuyerAddress, boolean invoiceIsPaid, ArrayList<Item> items, int invoiceTotal) {
        this.invoiceIssuerName = invoiceIssuerName;
        this.invoiceBuyerName = invoiceBuyerName;
        this.invoiceBuyerAddress = invoiceBuyerAddress;
        this.invoiceIsPaid = invoiceIsPaid;
        this.items = items;
        this.invoiceTotal = invoiceTotal;
    }

    // set up getters
    @NonNull
    public int getInvoiceId() {
        return invoiceId;
    }

    public String getInvoiceIssuerName() {
        return invoiceIssuerName;
    }

    public String getInvoiceBuyerName() {
        return invoiceBuyerName;
    }

    public int getInvoiceTotal() {
        return invoiceTotal;
    }

    public String getInvoiceBuyerAddress() {
        return invoiceBuyerAddress;
    }

    public Boolean getInvoiceIsPaid() {
        return invoiceIsPaid;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    // create a setter for id

    public void setInvoiceId(@NonNull int invoiceId) {
        this.invoiceId = invoiceId;
    }
}
