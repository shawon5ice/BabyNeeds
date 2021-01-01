package com.ssquare.babyneeds;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ssquare.babyneeds.adapter.RecyclerViewAdapter;
import com.ssquare.babyneeds.data.DatabaseHandler;
import com.ssquare.babyneeds.models.BabyItem;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    boolean update = false;

    RecyclerView recyclerView;
    List<BabyItem> babyItemList = new ArrayList<>();
    FloatingActionButton floatingActionButton;

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private View saveButton;
    DatabaseHandler databaseHandler;
    RecyclerViewAdapter recyclerViewAdapter;
    private EditText itemName,itemQuantity,itemColor,itemSize;
    AppCompatImageView delete,edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        databaseHandler = new DatabaseHandler(this);

        recyclerView = findViewById(R.id.recyclerView);
        populateData();
        floatingActionButton = findViewById(R.id.floatingActionButton2nd);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopUpDialog();
            }
        });
    }
    private void createPopUpDialog() {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup,null);
        itemName = view.findViewById(R.id.baby_item);
        itemQuantity = view.findViewById(R.id.itemQunatity);
        itemColor = view.findViewById(R.id.item_color);
        itemSize = view.findViewById(R.id.item_size);
        saveButton = view.findViewById(R.id.add_item_button);

        builder.setView(view);
        dialog = builder.create();
//        Window window = dialog.getWindow();
//        WindowManager.LayoutParams wlp = window.getAttributes();
//
//        wlp.gravity = Gravity.BOTTOM;
//        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//        window.setAttributes(wlp);
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!itemName.getText().toString().isEmpty() &&
                        !itemQuantity.getText().toString().isEmpty() &&
                        !itemSize.getText().toString().isEmpty() &&
                        !itemColor.getText().toString().isEmpty() ){
                    saveItem(v);
                }else {
                    Snackbar.make(v,"Please insert information",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveItem(View v) {
        String name,color,size;
        int quantity;
        name = itemName.getText().toString().trim();
        quantity = Integer.parseInt(String.valueOf(itemQuantity.getText()).trim());
        size = itemSize.getText().toString().trim();
        color = itemColor.getText().toString().trim();

        BabyItem item = new BabyItem();
        item.setItemName(name);
        item.setItemColor(color);
        item.setItemQuantity(quantity);
        item.setItemSize(size);

        databaseHandler.addItem(item);
        Log.i("sql", "onClick: saved"+name);
        Snackbar.make(v, "Item saved", Snackbar.LENGTH_LONG)
                .show();
        dialog.dismiss();
        populateData();

    }

    public void populateData(){
        babyItemList.clear();
        babyItemList = databaseHandler.getALlBabyItem();
        recyclerViewAdapter = new RecyclerViewAdapter(getApplicationContext(),babyItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.edit_id){
            update = true;
            createPopUpDialog();
        }
    }
}