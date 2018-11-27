package com.example.edgar.asesoriasinformales;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.edgar.asesoriasinformales.connector.DatabaseConnector;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int RC_SIGN_IN = 123;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount account;
    ImageButton location_permission_button;
    Drawable location_permission_enabled;
    Drawable location_permission_disabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Intent intent = new Intent();
       // intent.putExtra("account", account);
        //intent.setClass(getApplicationContext(), TeacherStats.class);
        //startActivity(intent);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Establish under which options Google Sign In will be built
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Activity initial state
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        location_permission_button = findViewById(R.id.location_permission_button);
        location_permission_button.setEnabled(false);
        location_permission_enabled = getDrawable(R.drawable.ic_location_on_black_24dp);
        location_permission_enabled.setTint(getColor(R.color.colorEnabledGreen));
        location_permission_disabled = getDrawable(R.drawable.ic_location_off_black_24dp);
        location_permission_disabled.setTint(getColor(R.color.colorDisabledRed));
        checkLocationPermission();
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            location_permission_button.setImageDrawable(location_permission_disabled);
        }
        else{
            location_permission_button.setImageDrawable(location_permission_enabled);
        }
    }

    /**
     * If user prompted to never show again the permission dialog for location, a dialog will be
     * shown prompting the user to grant said permission through the Settings screen.
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Permiso requerido");
        builder.setMessage("Esta aplicación requiere permisos. Puede otorgarlos en la pantalla de configuración");
        builder.setPositiveButton("Ir a configuración", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> {
            dialog.cancel();
            location_permission_button.setImageDrawable(location_permission_disabled);
        });
        builder.show();
    }

    /**
     * Opens intent to user's Settings screen.
     */
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    /**
     * Nearby API requires Coarse location permission, which is tagged as a dangerous permission. In
     * order to obtain it, the user must explicitly grant said permission at runtime. When the user
     * clicks on button, a dialog prompt will ask for this permission from the user. Toast shows
     * result to user. User is reminded that the app needs said permission if user doesn't grant it.
     */
    private void checkLocationPermission() {
        Dexter.withActivity(this).withPermission(Manifest.permission.ACCESS_COARSE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                Toast.makeText(getApplicationContext(), "Permiso aprobado!", Toast.LENGTH_SHORT).show();
                location_permission_button.setImageDrawable(location_permission_enabled);
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                if(response.isPermanentlyDenied()){
                    location_permission_button.setImageDrawable(location_permission_disabled);
                    showSettingsDialog();
                }
                Toast.makeText(getApplicationContext(), "La aplicación requiere utilizar su ubicación para funcionar!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
                location_permission_button.setImageDrawable(location_permission_disabled);
            }
        }).withErrorListener(error -> {
            Log.i("Dexter Error", "Error regarding location permission request");
            Toast.makeText(getApplicationContext(), "Error occurred: " + error.toString(), Toast.LENGTH_SHORT).show();
        }).check();
    }

    /**
     * Method that will create the intent for Google Sign In.
     */
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * Method that will sign out automatically user from application. No need to call for external
     * intent.
     * @param v View that will contain the onClick method associated with this method.
     */
    public void signOut(View v) {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    Toast.makeText(getApplicationContext(), "Sesión terminada", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                });
    }

    /**
     * Verifies on activity start if a user has previously signed in. If previously signed in,
     * account will hold the information, else it will be null.
     * Update UI accordingly.
     */
    @Override
    protected void onStart() {
        super.onStart();
        checkLocationPermission();
        account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    /**
     * Update UI according to data received from Sign in method.
     * @param acct GoogleSignInAccount containing the users account if previously signed in.
     *                If null, user hasn't signed in.
     */
    private void updateUI(GoogleSignInAccount acct) {
        if(acct != null){
            checkUser(acct.getEmail(), "email");
        }
        else{
            Log.i("Initial Sign in", "No account");
            View signInButton = findViewById(R.id.sign_in_button);
            signInButton.setEnabled(true);
        }
    }

    /**
     * Handler for different button clicks.
     * @param v View holding the button clicked on main layout.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
        }

    }

    /**
     * Handle results from calls for "startActivityForResult"
     * @param requestCode The integer request code originally supplied to startActivityForResult(),
     *                    allowing you to identify who this result came from.
     * @param resultCode The integer result code returned by the child activity through its
     *                   setResult().
     * @param data An Intent, which can return result data to the caller (various data can be
     *             attached to Intent "extras").
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    /**
     * Handler for result of the Sign In operation.
     * @param completedTask Task containing the result of the Sign in operation. Always completes.
     */
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Sign in Handler", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void startAdvisory(String role){
        if((ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) && account != null){
            Intent intent = new Intent();
            intent.putExtra("account", account);

            switch (role) {
                case "alumno":
                    intent.setClass(getApplicationContext(), HistorialAlumnoActivity.class);
                    startActivity(intent);
                    break;
                case "asesor":
                    intent.setClass(getApplicationContext(), TeacherStats.class);
                    startActivity(intent);
                    break;
                default:
                    Log.i("Unreachable statement", "Arrived on a dark place in the startAdvisory() method");
                    Toast.makeText(getApplicationContext(), "This shouldn't happen(startAdvisory() error). Please contact a developer", Toast.LENGTH_LONG).show();
                    break;
            }
            finish();
        }
    }

    public void checkUser(String loginCredential, String type){
        RequestQueue queue = Volley.newRequestQueue(this);
        String databaseURLusers = "https://tutorias-220600.firebaseio.com/usuarios";
        String query;
        if(type.equals("email")){
            query = databaseURLusers + ".json" + "?orderBy=\"correo\"&equalTo=\"" + loginCredential + "\"";
        }
        else {
            query = databaseURLusers + "/" + loginCredential + ".json";
        }
        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, query, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Check User", response.toString());
                        try {
                            String role = response.getString("rol");
                            startAdvisory(role);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error requesting user info. Please check Internet connection and try again", Toast.LENGTH_LONG).show();
                        Log.e("Check User Error", error.toString());
                    }
                });
        queue.add(jsonObjectRequest);
    }
}