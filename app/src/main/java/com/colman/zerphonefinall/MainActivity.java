package com.colman.zerphonefinall;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.colman.zerphonefinall.Model.Item;
import com.colman.zerphonefinall.Model.Model;

import java.util.LinkedList;
import java.util.List;


// First Activity we Loged in to application
public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    static Context context;
    SectionsPagerAdapter sectionsPagerAdapter;
    int page;
    ViewPager viewPager;
    String id;

    public static Context getAppContext() {
        return context;
    }


    @Override
    protected void onResume() {
        super.onResume();
        context = getApplicationContext();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        id = intent.getStringExtra("LoggedInEmail");

        context = getApplicationContext();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i=0 ; i<sectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                    .setText(sectionsPagerAdapter.getPageTitle(i))
                    .setTabListener(this));
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
                        onResume();
                    }
                });
                builder.setNegativeButton("cancel",null);

                return builder.create();
            }
        };
        df.show(getFragmentManager(), "add");
    }

    public boolean editItem(Item item,int i){
        showAddEditDialog(item,i);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.addNewItem);
        MenuItem maps = menu.findItem(R.id.maps);
        item.setIcon(R.drawable.business_users_add);
        maps.setIcon(R.drawable.mapsicon);
        if (id.equals("admin@admin.com")){
            item.setVisible(true);
        }
        else item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addNewItem: {
                //showAddEditDialog(null,0);
                Intent myIntent = new Intent(MainActivity.this, addEditItem.class);
                myIntent.putExtra("key", ""); //Optional parameters
                myIntent.putExtra("id",id);
                MainActivity.this.startActivity(myIntent);
                break;
            }
            case R.id.maps: {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
                break;
            }
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        GridView grid;
        List<Item> data;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new PlaceholderFragment(position);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase();
                case 1:
                    return getString(R.string.title_section2).toUpperCase();
                case 2:
                    return getString(R.string.title_section3).toUpperCase();
                case 3:
                    return getString(R.string.title_section4).toUpperCase();
            }
            return null;
        }
    }

    public class PlaceholderFragment extends Fragment {

        int positionTab;
        List<Item> data = new LinkedList<Item>();
        ProgressBar progressBar;
        final MyAdapter adapter = new MyAdapter(MainActivity.this.getApplicationContext());


        public PlaceholderFragment(int i) {
            this.positionTab = i;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_main, container, false);

            GridView gridView = (GridView) view.findViewById(R.id.gridView);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

            gridView.setAdapter(adapter);
            loadItemsData();


            if (id.equals("admin@admin.com")) {
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Item item = data.get(position);
                        //editItem(item, position);
                        Intent myIntent = new Intent(MainActivity.this, addEditItem.class);
                        myIntent.putExtra("key", item.getKey()); //Optional parameters
                        myIntent.putExtra("positionTab",positionTab);
                        myIntent.putExtra("position",position);
                        MainActivity.this.startActivity(myIntent);
                    }
                });
                gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Item item = data.get(position);
                        Model.remove(item);
                        loadItemsData();
                        return true;
                    }
                });
            }
            return view;
        }

        private void loadItemsData() {
            progressBar.setVisibility(View.VISIBLE);
            Model.getInstance(positionTab).getAllItemsASynch(positionTab, new Model.getItemListener() {
                @Override
                public void onResult(List<Item> items) {
                    data = items;
                    adapter.setData(data);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onCancel() {

                }
            });
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            loadItemsData();
        }
    }
}
