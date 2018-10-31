package com.example.edgar.asesoriasinformales;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Student_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_);

        Intent intent = getIntent();
        Bundle accountBundle = intent.getBundleExtra("Account Bundle");
        Toast.makeText(getApplicationContext(), "Student: " + accountBundle.getString("Name"), Toast.LENGTH_SHORT).show();
    }
}
