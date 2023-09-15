package com.example.clientsmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String ID = "id";
    private static final String TABLE_NAME = "customers";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";
    private static final String ADDRESS = "address";
    private static final String IS_ACTIVE = "isActive";

    public DatabaseHelper(@Nullable Context context) {
        super(context, TABLE_NAME + ".db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableString = "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, " + EMAIL + " TEXT, " + PHONE + " TEXT, " + ADDRESS + " TEXT, " + IS_ACTIVE + " BOOL);";
        db.execSQL(createTableString);
    }

    public void addCustomer(CustomerModel customerModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, customerModel.getName());
        contentValues.put(EMAIL, customerModel.getEmail());
        contentValues.put(PHONE, customerModel.getPhone());
        contentValues.put(ADDRESS, customerModel.getAddress());
        contentValues.put(IS_ACTIVE, customerModel.isActive());

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            Toast.makeText(context, "Failed to create", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Customer added", Toast.LENGTH_SHORT).show();
        }
    }

    public List<CustomerModel> getAllCustomers() {
        List<CustomerModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                long customerId = cursor.getInt(0);
                String customerName = cursor.getString(1);
                String customerEmail = cursor.getString(2);
                String customerPhone = cursor.getString(3);
                String customerAddress = cursor.getString(4);
                boolean customerActive = cursor.getInt(5) == 1;

                CustomerModel newCustomer = new CustomerModel(customerName, customerEmail, customerPhone, customerAddress, customerActive);
                newCustomer.setId(customerId);
                returnList.add(newCustomer);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnList;
    }

    public void updateCustomer(long customerId, String name, String email, String phone, String address, boolean isActive) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(EMAIL, email);
        contentValues.put(PHONE, phone);
        contentValues.put(ADDRESS, address);
        contentValues.put(IS_ACTIVE, isActive);

        long result = db.update(TABLE_NAME, contentValues, "id=?", new String[]{String.valueOf(customerId)});
        if (result == -1) {
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Customer updated", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteCustomer(long customerId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(customerId)});
        if (result == -1) {
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Customer deleted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
