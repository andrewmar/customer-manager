package com.example.clientsmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.CustomerViewHolder> {
    private List<CustomerModel> customers;
    private Context context;
    Activity activity;

    public CustomerListAdapter(Activity activity, Context context, List<CustomerModel> customers) {
        this.activity = activity;
        this.customers = customers;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_list, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        holder.tvId.setText(String.valueOf(position + 1));
        holder.tvName.setText(String.valueOf(customers.get(position).getName()));
        holder.tvEmail.setText(String.valueOf(customers.get(position).getEmail()));

        holder.listLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = holder.getAdapterPosition();
                if (itemPosition != RecyclerView.NO_POSITION) {
                    CustomerModel clickedCustomer = customers.get(itemPosition);

                    Intent intent = new Intent(context, UpdateActivity.class);
                    intent.putExtra("id", clickedCustomer.getId());
                    intent.putExtra("name", clickedCustomer.getName());
                    intent.putExtra("email", clickedCustomer.getEmail());
                    intent.putExtra("phone", clickedCustomer.getPhone());
                    intent.putExtra("address", clickedCustomer.getAddress());
                    intent.putExtra("isActive", clickedCustomer.isActive());

                    activity.startActivityForResult(intent, 1);
                }
            }
        });
    }

    public void setCustomers(List<CustomerModel> updatedCustomers) {
        this.customers = updatedCustomers;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return customers.size();
    }

    static class CustomerViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvEmail;
        private TextView tvId;
        LinearLayout listLayout;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.customerName);
            tvEmail = itemView.findViewById(R.id.customerEmail);
            tvId = itemView.findViewById(R.id.customerId);
            listLayout = itemView.findViewById(R.id.listLayout);

        }
    }
}
