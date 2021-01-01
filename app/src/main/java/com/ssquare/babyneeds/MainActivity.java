package com.ssquare.babyneeds;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ssquare.babyneeds.data.DatabaseHandler;
import com.ssquare.babyneeds.models.BabyItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private View saveButton;
    DatabaseHandler databaseHandler;
    private EditText itemName,itemQuantity,itemColor,itemSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        databaseHandler = new DatabaseHandler(this);

        int counter = databaseHandler.getItemCount();
        if(counter!=0){
            Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
            startActivity(intent);
            finish();
        }else {

            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                    createPopUpDialog();
                }
            });
        }
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
        name = itemName.getText().toString();
        quantity = Integer.parseInt(String.valueOf(itemQuantity.getText()));
        size = itemSize.getText().toString();
        color = itemColor.getText().toString();

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}