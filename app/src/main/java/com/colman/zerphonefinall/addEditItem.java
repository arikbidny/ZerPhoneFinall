package com.colman.zerphonefinall;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.colman.zerphonefinall.Model.Item;
import com.colman.zerphonefinall.Model.Model;

import java.util.List;

public class addEditItem extends AppCompatActivity {

    List<Item> data;
    String key;
    String id;
    int position, positionTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        position = intent.getIntExtra("position", 0);
        positionTab = intent.getIntExtra("positionTab",0);
        id = intent.getStringExtra("id");

        if (key.equals("")) {
            showAddEditDialog(null, 0);

        } else {
            Model.getInstance(positionTab).getAllItemsASynch(positionTab, new Model.getItemListener() {
                @Override
                public void onResult(List<Item> items) {
                    data = items;
                    Item item = data.get(position);
                    showAddEditDialog(item, position);
                }

                @Override
                public void onCancel() {

                }
            });

        }
    }


    private void showAddEditDialog(final Item item, final int position){
        DialogFragment df = new DialogFragment(){
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                if (item == null){
                    builder.setTitle("Add new item");
                }
                else {
                    builder.setTitle("Edit item");
                }

                View view = getActivity().getLayoutInflater().inflate(R.layout.add_item,null,false);
                builder.setView(view);

                final EditText newTitle = (EditText) view.findViewById(R.id.item_list_row_edit_title);
                final EditText newPrice = (EditText) view.findViewById(R.id.item_list_row_edit_price);
                final EditText newDetails = (EditText) view.findViewById(R.id.item_list_row_edit_details);

                final Spinner dropDown = (Spinner) view.findViewById(R.id.item_list_row_edit_category);
                String[] categories = {
                        "Single flower", "Gift", "Planet", "Vase"
                };
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),R.layout.spinner_item,categories);
                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                dropDown.setAdapter(adapter);

                if (item != null) {
                    newTitle.setText(item.getTitle());
                    newPrice.setText(item.getPrice());
                    newDetails.setText(item.getdetails());
                    dropDown.post(new Runnable() {
                        @Override
                        public void run() {
                            String cat = item.getCategory().trim().toLowerCase();
                            if (cat.equals("single flower"))
                                dropDown.setSelection(0);
                            else if (cat.equals("gift"))
                                dropDown.setSelection(1);
                            else if (cat.equals("planet"))
                                dropDown.setSelection(2);
                            else if (cat.equals("vase"))
                                dropDown.setSelection(3);
                        }
                    });
                }

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (item == null) {
                            if (newTitle.getText().toString().length() == 0 ||
                                    newPrice.getText().toString().length() == 0 ||
                                    newDetails.getText().toString().length() == 0)
                                Toast.makeText(getActivity().getApplicationContext(), "All fields are required", Toast.LENGTH_LONG).show();
                            else {
                                Item item = new Item(newTitle.getText().toString(),R.drawable.lailot_levanim,newPrice.getText().toString(),
                                        newDetails.getText().toString(),dropDown.getSelectedItem().toString());
                                Model.getInstance(position).add(item);
                            }
                        }
                        else {
                            String title = newTitle.getText().toString();
                            String price = newPrice.getText().toString();
                            String details = newDetails.getText().toString();
                            String category = dropDown.getSelectedItem().toString();
                            Model.getInstance(position).update(item, title, price, details, category);
                        }

                        finish();
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                return builder.create();
            }
        };
        df.show(getFragmentManager(), "add");
    }
    }


