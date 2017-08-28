package com.carlfolkesson.medicinehandler;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNewMedicineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_medicine);

        final EditText nameInput = (EditText)findViewById(R.id.nameInput);
        final EditText stockInput = (EditText)findViewById(R.id.stockInput);
        final EditText doseInput = (EditText)findViewById(R.id.doseInput);

        // Takes care of what happens when the addButton is clicked
        Button addButton = (Button) findViewById(R.id.addMedicineButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                String nameInputText = nameInput.getText().toString();
                String stockInputText = stockInput.getText().toString();
                String doseInputText = doseInput.getText().toString();
                if(nameInputText.equals("")) {
                    Snackbar.make(view, "Du måste ge medicinen ett namn", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                } else if(stockInputText.equals("")) {
                    Snackbar.make(view, "Du måste ange hur mycket av medicinen du har i lager", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if(doseInputText.equals("")) {
                    Snackbar.make(view, "Du måste ange hur mycket av medicinen du tar varje dag", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    intent.putExtra("name", nameInputText);
                    intent.putExtra("stock", stockInputText);
                    intent.putExtra("dose", doseInputText);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        // Takes care of what happens when the cancelButton is clicked
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }
}
