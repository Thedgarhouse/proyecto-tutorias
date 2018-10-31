package com.example.edgar.asesoriasinformales;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Teacher_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_);

        Intent intent = getIntent();
        Bundle accountBundle = intent.getBundleExtra("Account Bundle");
        Toast.makeText(getApplicationContext(), "Teacher: " + accountBundle.getString("Name"), Toast.LENGTH_SHORT).show();
    }
}
