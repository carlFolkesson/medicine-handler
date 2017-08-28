package com.carlfolkesson.medicinehandler;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<MedicineItem> medicineItems;

    private int index = 0;

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
                if(index > 0) {
                    medicineItems.remove(index-1);
                    index--;
                    adapter.notifyDataSetChanged();
                }
            }
        });

        setDate();
        createRecyclerView();
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

    /**
     * Creates a RecyclerView containing a name and a number
     */
    private void createRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        medicineItems = new ArrayList<>();
        adapter = new MedicineAdapter(medicineItems);
        recyclerView.setAdapter(adapter);
    }

    private void setDate() {
        final Calendar t = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("EEEE yyyy-MM-dd");
        String currentDateTimeString = df.format(t.getTime());
        final TextView date = (TextView) findViewById(R.id.date);
        date.setText(currentDateTimeString);
    }

    /**
     * Takes care off the result from an other activity
     * @param requestCode the code the activity was called with
     * @param resultCode if the activity exited as intended
     * @param data the data sent back from the activity
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // If an new medicin was added
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
                index++;
                adapter.notifyDataSetChanged();
            }
        }
    }
}
