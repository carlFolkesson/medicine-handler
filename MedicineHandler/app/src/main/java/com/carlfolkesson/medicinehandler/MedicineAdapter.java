package com.carlfolkesson.medicinehandler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kalle on 2017-08-23.
 */

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {

    private List<MedicineItem> medicineItems;

    public MedicineAdapter(List<MedicineItem> medicineItems) {
        this.medicineItems = medicineItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicine_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MedicineItem medicineItem = medicineItems.get(position);

        holder.textViewName.setText(medicineItem.getName());
        String stockText = "Antal tabletter i lager: " + String.valueOf(medicineItem.getStock());
        holder.textViewStock.setText(stockText);
    }

    @Override
    public int getItemCount() {
        return medicineItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public TextView textViewStock;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.name);
            textViewStock = itemView.findViewById(R.id.stock);
        }
    }
}
