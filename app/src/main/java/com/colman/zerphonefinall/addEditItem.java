package com.colman.zerphonefinall;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.colman.zerphonefinall.Model.Item;
import com.colman.zerphonefinall.Model.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class addEditItem extends AppCompatActivity {

    List<Item> data;
    String key;
    String id;
    int position, positionTab;
    Bitmap imageBitmap = null;
    ImageView image;
    Item newItem = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        position = intent.getIntExtra("position", 0);
        positionTab = intent.getIntExtra("positionTab", 0);
        id = intent.getStringExtra("id");


        final ImageView photo = (ImageView) findViewById(R.id.student_list_row_camera);
        image = (ImageView) findViewById(R.id.student_list_row_image);
        final EditText title = (EditText) findViewById(R.id.item_list_row_edit_title);
        final EditText price = (EditText) findViewById(R.id.item_list_row_edit_price);
        final EditText detials = (EditText) findViewById(R.id.item_list_row_edit_details);
        final Button ok = (Button) findViewById(R.id.ok_button);
        final Button cancel = (Button) findViewById(R.id.cancel_btn);

        final Spinner dropDown = (Spinner) findViewById(R.id.item_list_row_edit_category);
        String[] categories = {
                "Single flower", "Gift", "Planet", "Vase"
        };
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_item,categories);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        dropDown.setAdapter(adapter);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = String.valueOf(System.currentTimeMillis());
                if (title.getText().toString().length() == 0 || //check if all fields have been field
                        price.getText().toString().length() == 0 ||
                        detials.getText().toString().length() == 0)
                    Toast.makeText(getApplicationContext(), "All field are required", Toast.LENGTH_LONG).show();
                else {
                    String newTitle = title.getText().toString();
                    String newPrice = price.getText().toString();
                    String newDetials = detials.getText().toString();
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String imageName = newTitle + timeStamp +  ".jpg";
                    String newCat = dropDown.getSelectedItem().toString();
                    if (newItem == null && imageBitmap!=null) { //check if a photo has been given
                        newItem = new Item(newTitle, imageName , newPrice, newDetials, newCat,date);

                        Model.getInstance(positionTab).add(newItem);
                        Model.getInstance(positionTab).saveImage(imageBitmap, imageName);
                        finish();
                    } else if (newItem!=null){
                        Model.getInstance(positionTab).update(newItem, newTitle, imageName, newPrice, newDetials, newCat,date);
                        if (imageBitmap!=null) {
                            Model.getInstance(positionTab).saveImage(imageBitmap, imageName);
                        } else if (imageBitmap==null){
                            Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
                            Model.getInstance(positionTab).saveImage(bitmap,imageName);
                        }
                        finish();
                    }
                    else  Toast.makeText(getApplicationContext(), "All field are required", Toast.LENGTH_LONG).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 0);
                }
            }
        });


        if (key.equals("")) {
            //showAddEditDialog(null, 0);

        } else {
            Model.getInstance(positionTab).getAllItemsASynch(positionTab, new Model.getItemListener() {
                @Override
                public void onResult(List<Item> items) {
                    data = items;
                    final Item item = data.get(position);
                    newItem = item;
                    //showAddEditDialog(item, position);
                    title.setText(item.getTitle());
                    price.setText(item.getPrice());
                    detials.setText(item.getdetails());
                    final ProgressBar progressBar = (ProgressBar) findViewById(R.id.add_progressBar);
                    progressBar.setVisibility(View.VISIBLE);
                    Model.getInstance(position).loadImage(item.getImage(), new Model.LoadImageListener() {
                        @Override
                        public void onResult(Bitmap imageBitmap) {
                            if (imageBitmap != null) {
                                image.setImageBitmap(imageBitmap);
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
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
                @Override
                public void onCancel() {
                }
            });



        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intentData) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Bundle extras = intentData.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);
        }
    }
    }


