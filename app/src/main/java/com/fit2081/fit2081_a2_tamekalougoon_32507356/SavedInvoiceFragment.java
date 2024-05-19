package com.fit2081.fit2081_a2_tamekalougoon_32507356;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fit2081.fit2081_a2_tamekalougoon_32507356.provider.Invoice;
import com.fit2081.fit2081_a2_tamekalougoon_32507356.provider.InvoiceRepo;
import com.fit2081.fit2081_a2_tamekalougoon_32507356.provider.InvoiceViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SavedInvoiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */



public class SavedInvoiceFragment extends Fragment implements InvoiceRecyclerInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public SavedInvoiceFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    InvoiceRecyclerAdapter invoiceAdapter;

    ArrayList<Invoice> invoices;
    private LinearLayoutManager layoutManager;

    private InvoiceViewModel invoiceViewModel;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    public static SavedInvoiceFragment newInstance(String param1, String param2) {
        SavedInvoiceFragment fragment = new SavedInvoiceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set up invoice click listener
        //invoices.setOnClickListener(new SavedInvoiceFragment.InvoiceListener());

        // Observe changes in the ViewModel
        invoiceViewModel = new ViewModelProvider(this).get(InvoiceViewModel.class);
        invoiceViewModel.getMyInvoices().observe(this, new Observer<List<Invoice>>() {
            @Override
            public void onChanged(List<Invoice> updatedInvoices) {
                // Update your UI (RecyclerView) here
                invoices.clear();
                invoices.addAll(updatedInvoices);
                //clear items here
                invoiceAdapter.notifyDataSetChanged();

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saved_invoice, container, false);

        invoices = new ArrayList<>();
        layoutManager = new LinearLayoutManager(requireActivity());
        invoiceAdapter = new InvoiceRecyclerAdapter(invoices, this);
        invoiceViewModel = new ViewModelProvider(this).get(InvoiceViewModel.class);

        // set up recycler view
        recyclerView = view.findViewById(R.id.invoice_recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(invoiceAdapter);


        return view;
    }

    // listener for when invoice is clicked
    @Override
    public void onInvoiceClick(int position) {
        //on click, display snackbar


// confirm user is sure of decision
            Snackbar.make(requireView(), "Are you sure?", Snackbar.LENGTH_SHORT).setAction("Delete", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //get position of which one is clicked
                    Invoice clickedInvoice = invoices.get(position);


                    // delete from the database
                    invoiceViewModel.deleteInvoice(clickedInvoice);
                }
        }).show();
    }
}


