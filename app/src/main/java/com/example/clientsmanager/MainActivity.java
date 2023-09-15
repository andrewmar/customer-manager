package com.example.clientsmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> addCustomerLauncher;
    private DatabaseHelper dbHelper;
    CustomerListAdapter adapter;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emptyView = findViewById(R.id.emptyView);
        dbHelper = new DatabaseHelper(MainActivity.this);
        Button buttonAddCustomer = findViewById(R.id.btnAddCustomer);
        RecyclerView recyclerView = findViewById(R.id.rv_customers);

        // Create and set the layout manager for the RecyclerView
        List<CustomerModel> customerList = dbHelper.getAllCustomers();
        updateEmptyViewVisibility(customerList);

        // Create and set the adapter for the RecyclerView
        adapter = new CustomerListAdapter(MainActivity.this, this, customerList);
        recyclerView.setAdapter(adapter);

        // Set the layout manager for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addCustomerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::handleAddCustomerResult
        );

        buttonAddCustomer.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddCustomerActivity.class);
            addCustomerLauncher.launch(intent);
        });
    }

    private void handleAddCustomerResult(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            updateCustomerList();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            updateCustomerList();
        }
    }

    private void updateCustomerList() {
        List<CustomerModel> updatedCustomerList = dbHelper.getAllCustomers();
        adapter.setCustomers(updatedCustomerList);
        updateEmptyViewVisibility(updatedCustomerList);
        adapter.notifyDataSetChanged();
    }

    private void updateEmptyViewVisibility(List<CustomerModel> customerList) {
        if (customerList.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
    }

}
