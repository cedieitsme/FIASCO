package com.example.dell.fiascov2;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
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
    private EditText et_fname;
    private EditText et_lname;
    private EditText et_contact;
    private EditText et_city;
    private EditText et_street;
    private EditText et_postal;
    private EditText et_height;
    private EditText et_weight;
    private EditText et_email;
    private EditText et_password;
    private EditText et_confirm_password;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private String birthdate;

    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etBirthday = findViewById(R.id.register_et_birthday);
        et_fname = findViewById(R.id.register_et_fname);
        et_email = findViewById(R.id.register_et_email);
        et_lname = findViewById(R.id.register_et_lname);
        et_contact = findViewById(R.id.register_et_contact);
        et_city = findViewById(R.id.register_et_city);
        et_street = findViewById(R.id.register_et_street);
        et_postal = findViewById(R.id.register_et_postal);
        et_height = findViewById(R.id.register_et_height);
        et_weight = findViewById(R.id.register_et_weight);
        et_password = findViewById(R.id.register_et_password);
        et_confirm_password = findViewById(R.id.register_et_confpassword);

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
                birthdate = year + "-" + month + "-" + day;
            }
        };

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuperTask.execute(RegisterActivity.this, "register", TaskConfig.REGISTER_URL, "Registering User...");
            }
        });
    }

    @Override
    public void onTaskRespond(String id, String json) {
        switch (id) {
            case "register": {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    if(jsonObject.getString("status").equals("success")) {
                        Intent intent  = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception e) { }
                break;
            }
        }
    }

    @Override
    public ContentValues setRequestValues(String id, ContentValues contentValues) {
        switch (id) {
            case "register":
                contentValues.put("fname", et_fname.getText().toString());
                contentValues.put("lname", et_lname.getText().toString());
                contentValues.put("gender", "Male");
                contentValues.put("birthdate", birthdate);
                contentValues.put("mobile_number", et_contact.getText().toString());
                contentValues.put("city", et_city.getText().toString());
                contentValues.put("street", et_street.getText().toString());
                contentValues.put("postal_code", et_postal.getText().toString());
                contentValues.put("weight", et_weight.getText().toString());
                contentValues.put("height", et_height.getText().toString());
                contentValues.put("email", et_email.getText().toString());
                contentValues.put("password", et_password.getText().toString());
                return contentValues;
        }
        return null;
    }
}
