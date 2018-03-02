package com.example.dell.fiascov2;

import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.fiascov2.config.TaskConfig;
import com.example.dell.fiascov2.utils.SuperTask;

import org.json.JSONArray;
import org.json.JSONObject;

import me.grantland.widget.AutofitHelper;

public class UserActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, SuperTask.TaskListener {

    private DrawerLayout myDrawer;
    private ActionBarDrawerToggle myToggle;

    private String name;
    private String email;
    private TextView nametxtvw;
    private TextView emailtxtvw;

    private int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        uid = getIntent().getIntExtra("uid", -1);

        SuperTask.execute(this, "get_details", TaskConfig.GET_USER, "Fetching Data...");

        myDrawer = (DrawerLayout) findViewById(R.id.myDrawer);
        myToggle = new ActionBarDrawerToggle(this, myDrawer, R.string.hint_open, R.string.hint_close);

        myDrawer.addDrawerListener(myToggle);
        myToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        View v = navigationView.getHeaderView(0);
        nametxtvw = v.findViewById(R.id.name_txt_vw);
        emailtxtvw = v.findViewById(R.id.email_txt_vw);
        AutofitHelper.create(nametxtvw);
        AutofitHelper.create(emailtxtvw);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (myToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onTaskRespond(String id, String json) {
        switch (id) {
            case "get_details":
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray jsonArray = jsonObject.getJSONArray("user");
                    for(int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        Log.d("USER_ACTIVITY_FIAS", jsonObject+"");
                        nametxtvw.setText(jsonObject.getString("user_fname") + " " + jsonObject.getString("user_lname"));
                        emailtxtvw.setText(jsonObject.getString("email"));
                    }
                } catch (Exception e) { }
                break;
        }
    }

    @Override
    public ContentValues setRequestValues(String id, ContentValues contentValues) {
        switch (id) {
            case "get_details":
                contentValues.put("id", uid);
                return contentValues;
        }
        return null;
    }
}
