package com.example.dell.fiascov2;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dell.fiascov2.config.TaskConfig;
import com.example.dell.fiascov2.utils.SuperTask;

import org.json.JSONObject;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity implements SuperTask.TaskListener {

    private EditText etBirthday;
    private Button btnRegister;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etBirthday = (EditText) findViewById(R.id.register_et_birthday);
        btnRegister = findViewById(R.id.register_btn_register);
        etBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(RegisterActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + year + "/" + month + "/" + day);

                String date = month + "/" + day + "/" + year;
                etBirthday.setText(date);
            }
        };

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuperTask.execute(RegisterActivity.this, "register", TaskConfig.REGISTER_URL);
            }
        });
    }

    @Override
    public void onTaskRespond(String id, String json) {
        Toast.makeText(this, ""+json, Toast.LENGTH_SHORT).show();
        switch (id) {
            case "register": {
                Log.d(TAG, TaskConfig.REGISTER_URL);
                Log.d(TAG, json);
                try {
                    JSONObject jsonObject = new JSONObject(json);
                } catch (Exception e) { }
                break;
            }
        }
    }

    @Override
    public ContentValues setRequestValues(String id, ContentValues contentValues) {
        switch (id) {
            case "register":
                contentValues.put("fname", "Kendrick Andrew");
                contentValues.put("lname", "Cosca");
                contentValues.put("gender", "Male");
                contentValues.put("birthdate", "1999-02-03");
                contentValues.put("mobile_number", "09167983610");
                contentValues.put("city", "Quezon City");
                contentValues.put("street", "Gold Street");
                contentValues.put("postal_code", "1116");
                contentValues.put("weight", "80");
                contentValues.put("email",
                        "kendrickjaviercosca@gmail.com");
                contentValues.put("password", "123456");
                break;
        }
        return null;
    }
}
