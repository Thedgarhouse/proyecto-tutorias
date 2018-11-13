package com.example.edgar.asesoriasinformales;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.Optional;

public class Teacher_Activity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Intent intent = getIntent();
        GoogleSignInAccount account = intent.getParcelableExtra("account");
        ImageView profilePicture = findViewById(R.id.user_profile_picture);
        TextView nameField = findViewById(R.id.name_field);
        TextView emailField = findViewById(R.id.email_field);
        Optional<Uri> maybePhotoUri = Optional.ofNullable(account.getPhotoUrl()); // Optional magic
        String photoUriString = maybePhotoUri.map(Uri::toString).orElse("N/A"); // Even more Optional magic
        String nameString = account.getDisplayName();
        String emailString = account.getEmail();
        if(!photoUriString.equals("N/A")){
            Uri photoUri = Uri.parse(photoUriString);
            Glide.with(this).load(photoUri).into(profilePicture);
        }
        nameField.setText(nameString);
        emailField.setText(emailString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sign_out_item:
                signOut();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Method that will sign out automatically user from application. No need to call for external
     * intent.
     */
    public void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    Toast.makeText(getApplicationContext(), "Sesión terminada", Toast.LENGTH_SHORT).show();

                });
    }
}
