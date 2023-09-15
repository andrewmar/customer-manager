package com.example.clientsmanager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {
    Button btnUpdate, btnDelete;
    EditText etNameUpdate;
    EditText etEmailUpdate;
    EditText etPhoneUpdate;
    EditText etAddressUpdate;
    Switch swIsActiveUpdate;
    String name, email, phone, address;
    long  id;
    boolean isActiveValue;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_item);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        etNameUpdate = findViewById(R.id.etNameUpdate);
        etEmailUpdate = findViewById(R.id.etEmailUpdate);
        etPhoneUpdate = findViewById(R.id.etPhoneUpdate);
        etAddressUpdate = findViewById(R.id.etAddressUpdate);
        swIsActiveUpdate = findViewById(R.id.swIsActiveUpdate);

        getIntentData();

        swIsActiveUpdate.setChecked(isActiveValue);
        swIsActiveUpdate.setOnCheckedChangeListener((buttonView, isChecked) -> isActiveValue = isChecked);

        btnUpdate.setOnClickListener(v -> {
            databaseHelper = new DatabaseHelper(UpdateActivity.this);
            name = etNameUpdate.getText().toString();
            email = etEmailUpdate.getText().toString();
            phone = etPhoneUpdate.getText().toString();
            address = etAddressUpdate.getText().toString();
            isActiveValue = swIsActiveUpdate.isChecked();


            databaseHelper.updateCustomer(id, name, email, phone, address, isActiveValue);

            setResult(RESULT_OK);
            finish();
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }

    protected void getIntentData() {
        if (getIntent().hasExtra("name")
                && getIntent().hasExtra("email")
                && getIntent().hasExtra("phone")
                && getIntent().hasExtra("address")
                && getIntent().hasExtra("isActive")) {
            name = getIntent().getStringExtra("name");
            email = getIntent().getStringExtra("email");
            phone = getIntent().getStringExtra("phone");
            address = getIntent().getStringExtra("address");
            isActiveValue = getIntent().getBooleanExtra("isActive", false);
            id = getIntent().getLongExtra("id",  -1);

            etNameUpdate.setText(name);
            etEmailUpdate.setText(email);
            etPhoneUpdate.setText(phone);
            etAddressUpdate.setText(address);
            swIsActiveUpdate.setChecked(isActiveValue);


        }
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + "  customer?");
        builder.setMessage("Are you sure you want to delete " + name + " customer?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseHelper = new DatabaseHelper(UpdateActivity.this);
                databaseHelper.deleteCustomer(id);
                setResult(RESULT_OK);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();

    }
}