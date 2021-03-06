package com.d2i.stockmanagement.screen.productcheck;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.d2i.stockmanagement.R;
import com.d2i.stockmanagement.entity.InventoryTag;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {
    private final ArrayList<InventoryTag> inventoryTags;

    public TableAdapter(ArrayList<InventoryTag> inventoryTags) {
        this.inventoryTags = inventoryTags;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.scan_product_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String no = String.valueOf(inventoryTags.get(position).getNo());
        holder.no.setText(no);

        holder.rfid.setText(inventoryTags.get(position).getRfid());
        holder.productName.setText(inventoryTags.get(position).getProductName());
        holder.status.setText(inventoryTags.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return inventoryTags.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setInventoryTags(ArrayList<InventoryTag> data) {
        inventoryTags.clear();
        inventoryTags.addAll(data);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView no;
        private final TextView rfid;
        private final TextView productName;
        private final TextView status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            no = itemView.findViewById(R.id.no);
            rfid = itemView.findViewById(R.id.rfid);
            productName = itemView.findViewById(R.id.product_name);
            status = itemView.findViewById(R.id.status);
        }

        public TextView getStatus() {
            return status;
        }

        public TextView getProductName() {
            return productName;
        }

        public TextView getRfid() {
            return rfid;
        }

        public TextView getNo() {
            return no;
        }
    }
}
