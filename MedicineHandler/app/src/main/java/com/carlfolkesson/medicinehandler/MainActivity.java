package com.carlfolkesson.medicinehandler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<MedicineItem> medicineItems;

    private int medicineListIndex = 0;

    // SharedPreferences keys
    final private String medicineListIndexKey = "com.carlfolkesson.medicinehandler.medicineListIndex";
    final private String medicineItemsKey = "com.carlfolkesson.medicinehandler.medicineItems";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fab);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent("com.carlfolkesson.medicinehandler.AddNewMedicineActivity");
                startActivityForResult(intent, 1);
            }
        });

        FloatingActionButton fabRemove = (FloatingActionButton) findViewById(R.id.fabRemove);
        fabRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(medicineListIndex > 0) {
                    medicineItems.remove(medicineListIndex -1);
                    medicineListIndex--;
                    adapter.notifyDataSetChanged();
                }
            }
        });

        setDate();
        medicineItems = new ArrayList<>();
        createRecyclerView();
        loadSavedData();
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

    private void setDate() {
        final Calendar t = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("EEEE yyyy-MM-dd");
        String currentDateTimeString = df.format(t.getTime());
        TextView date = (TextView) findViewById(R.id.date);
        date.setText(currentDateTimeString);
    }

    /**
     * Creates a RecyclerView containing name, number of items in stock and daily dose of a medicine
     */
    private void createRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadSavedData() {

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        // Loads in the saved value of medicineListIndex
        medicineListIndex = sharedPref.getInt(medicineListIndexKey, 0);

        // Loads in the saved value of medicineItems
        Gson gson = new Gson();
        String jsonMedicineItems = sharedPref.getString(medicineItemsKey, null);
        if(jsonMedicineItems != null && !jsonMedicineItems.equals("null")) {
            Log.i("Inte Null", jsonMedicineItems);
            medicineItems = gson.fromJson(jsonMedicineItems, new TypeToken<List<MedicineItem>>() {}.getType());
        }
        adapter = new MedicineAdapter(medicineItems);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Takes care off the result from an other activity
     * @param requestCode the code the activity was called with
     * @param resultCode if the activity exited as intended
     * @param data the data sent back from the activity
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // If an new medicine was added
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String medicineName = data.getStringExtra("name");
                int medicineStock = Integer.parseInt(data.getStringExtra("stock"));
                int medicineDose = Integer.parseInt(data.getStringExtra("dose"));
                MedicineItem medicineItem = new MedicineItem(
                        medicineName,
                        medicineStock,
                        medicineDose);
                medicineItems.add(medicineItem);
                medicineListIndex++;
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        // Saves the current medicineListIndex to the phone
        editor.putInt(medicineListIndexKey, medicineListIndex);

        // Saves the current medicineItems to the phone
        Gson gson = new Gson();
        String jsonMedicineItems = gson.toJson(medicineItems);
        editor.putString(medicineItemsKey, jsonMedicineItems);

        editor.apply();
    }
}
