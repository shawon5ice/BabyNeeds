package com.ssquare.babyneeds.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.ssquare.babyneeds.R;
import com.ssquare.babyneeds.data.DatabaseHandler;
import com.ssquare.babyneeds.models.BabyItem;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final Context context;
    private final List<BabyItem> babyItemList;
    AlertDialog.Builder builder;
    AlertDialog dialog;

    public RecyclerViewAdapter(Context context, List<BabyItem> babyItemList) {
        this.context = context;
        this.babyItemList = babyItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BabyItem babyItem = babyItemList.get(position);
        holder.name.setText(babyItem.getItemName());
        holder.size.setText("Size : "+babyItem.getItemSize());
        holder.color.setText("Color : "+babyItem.getItemColor());
        holder.quantity.setText("Quantity : "+String.valueOf(babyItem.getItemQuantity()));
        holder.date.setText("Item placed in "+babyItem.getItemAddedDate());
    }

    @Override
    public int getItemCount() {
        return babyItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,size,color,quantity,date;
        Button delete,edit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_nameId);
            size = itemView.findViewById(R.id.item_sizeId);
            color = itemView.findViewById(R.id.item_colorID);
            quantity = itemView.findViewById(R.id.item_quantityId);
            date = itemView.findViewById(R.id.item_added_dateID);
            delete = itemView.findViewById(R.id.delete_id);
            edit = itemView.findViewById(R.id.edit_id);

            delete.setOnClickListener(this::onClick);
            edit.setOnClickListener(this::onClick);

        }

        @Override
        public void onClick(View v) {
            DatabaseHandler databaseHandler = new DatabaseHandler(context);
            BabyItem babyItem = babyItemList.get(getAdapterPosition());
            if(v.getId()==R.id.delete_id){
                int id = babyItem.getId();

                builder = new AlertDialog.Builder(context);
                View conf_v = LayoutInflater.from(context).inflate(R.layout.delete_conf_popup,null);
                ImageView no = conf_v.findViewById(R.id.conf_no_icon);
                ImageView yes = conf_v.findViewById(R.id.conf_yes_icon);


                builder.setView(conf_v);
                dialog = builder.create();
                dialog.show();
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        babyItemList.remove(getAdapterPosition());
                                databaseHandler.deleteItem(id);
                                notifyDataSetChanged();
                                dialog.dismiss();
                    }
                });
            }
            if(v.getId()==R.id.edit_id){

            }
        }
    }
}
