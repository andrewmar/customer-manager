package com.example.clientsmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddCustomerActivity extends AppCompatActivity {
    Button btnAdd;
    EditText etName;
    EditText etEmail;
    EditText etPhone;
    EditText etAddress;
    Switch swIsActive;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        btnAdd = findViewById(R.id.btnAdd);
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        swIsActive = findViewById(R.id.swIsActive);
        swIsActive.setChecked(true);

        databaseHelper = new DatabaseHelper(AddCustomerActivity.this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String phone = etPhone.getText().toString();
                String address = etAddress.getText().toString();

                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                    Toast.makeText(AddCustomerActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                CustomerModel customerModel = new CustomerModel( name, email, phone, address, swIsActive.isChecked());

                databaseHelper.addCustomer(customerModel);
                Toast.makeText(AddCustomerActivity.this, "Customer added", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });

    }

}
