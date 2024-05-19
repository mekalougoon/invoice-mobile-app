package com.fit2081.fit2081_a2_tamekalougoon_32507356;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.fit2081.fit2081_a2_tamekalougoon_32507356.provider.InvoiceViewModel;

public class ShowInvoiceActivity extends AppCompatActivity {

    InvoiceViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_invoice);

        // places fragment in the fragment container
        getSupportFragmentManager().beginTransaction().add(R.id.gesture_container_id, new SavedInvoiceFragment()).commit();

        // create view model
        viewModel = new ViewModelProvider(this).get(InvoiceViewModel.class);

    }
}
