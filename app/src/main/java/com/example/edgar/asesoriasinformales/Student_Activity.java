package com.example.edgar.asesoriasinformales;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class Student_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_);

        Intent intent = getIntent();
        Bundle accountBundle = intent.getBundleExtra("Account Bundle");
        ImageView profilePicture = findViewById(R.id.user_profile_picture);
        TextView nameField = findViewById(R.id.name_field);
        TextView emailField = findViewById(R.id.email_field);
        String photoUriString = accountBundle.getString("Photo");
        String nameString = accountBundle.getString("Name");
        String emailString = accountBundle.getString("Email");
        if(!photoUriString.equals("N/A")){
            Uri photoUri = Uri.parse(photoUriString);
            Glide.with(this).load(photoUri).into(profilePicture);
        }
        nameField.setText(nameString);
        emailField.setText(emailString);
    }
}
