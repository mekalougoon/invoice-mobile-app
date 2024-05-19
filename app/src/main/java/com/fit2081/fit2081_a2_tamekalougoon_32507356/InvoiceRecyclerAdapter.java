package com.fit2081.fit2081_a2_tamekalougoon_32507356;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fit2081.fit2081_a2_tamekalougoon_32507356.provider.Invoice;

import java.util.ArrayList;
import java.util.List;

// creates the view holders and binds the data to them
public class InvoiceRecyclerAdapter extends RecyclerView.Adapter<InvoiceRecyclerAdapter.ViewHolder>{

    private final InvoiceRecyclerInterface recyclerViewInterface;
    ArrayList<Invoice> invoices;

    private final int HEADER_CARD_TYPE = 0;
    private final int VALUE_CARD_TYPE = 1;

    // receives the data from outside
    public void setItemData(ArrayList<Invoice> data) {
        invoices = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == VALUE_CARD_TYPE) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.invoice_card, parent, false); //CardView inflated as RecyclerView list item
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.invoice_card_header, parent, false); //CardView inflated as RecyclerView list item
        }

        // create a view holder and pass the view
        ViewHolder viewHolder = new ViewHolder(v, recyclerViewInterface);

        // return to the parent (android) which is responsible for how many and when to create
        return viewHolder;
    }

    public InvoiceRecyclerAdapter(ArrayList<Invoice> invoices, InvoiceRecyclerInterface recyclerViewInterface) {
        this.invoices = invoices;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    // sets a header for the recycler by determining if it is the first one shown
    public int getItemViewType(int position) {
        if (position == 0)
            return HEADER_CARD_TYPE;
        else return VALUE_CARD_TYPE;
    }

    // return of the view data becomes the input for the bind data
    // sets the data
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (position != 0) {

            // set the values

            holder.invoiceIdTv.setText(String.valueOf(invoices.get(position-1).getInvoiceId()));
            holder.invoiceIssuerTV.setText(invoices.get(position-1).getInvoiceIssuerName());
            holder.invoiceBuyerTV.setText(invoices.get(position-1).getInvoiceBuyerName());
            holder.invoiceTotalTV.setText(String.valueOf(invoices.get(position-1).getInvoiceTotal()));

        }

    }

    // determines the number of items
    @Override
    public int getItemCount() {
        return invoices.size()+1;
    }

    // creates object that holds references to the views
    public class ViewHolder extends RecyclerView.ViewHolder {

        // contains the elements
        TextView invoiceIdTv;
        TextView invoiceIssuerTV;
        TextView invoiceBuyerTV;
        TextView invoiceTotalTV;
        public ViewHolder(@NonNull View itemView, InvoiceRecyclerInterface invoiceRecyclerInterface) {
            super(itemView);
            invoiceIdTv = itemView.findViewById(R.id.invoice_card_id);
            invoiceIssuerTV = itemView.findViewById(R.id.invoice_card_issuer_name);
            invoiceBuyerTV = itemView.findViewById(R.id.invoice_card_buyer_name);
            invoiceTotalTV = itemView.findViewById(R.id.invoice_card_total);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    // checks that it is not null
                    if (recyclerViewInterface != null ) {
                    int position = getAdapterPosition()-1;

                    // check that the position is valid
                    if (position != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onInvoiceClick(position);
                    }
                    }
                }
            });
        }
    }
}
