package com.example.shula.mygrownupsapp.ui;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.shula.mygrownupsapp.R;
import com.example.shula.mygrownupsapp.models.FamilyMember;
import com.example.shula.mygrownupsapp.models.Warning;
import com.example.shula.mygrownupsapp.services.FireApp;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;


public class LoginActivity extends AppCompatActivity {

    private Button btnOk;
    private EditText txtMail;
    private EditText txtPassword;
    private Switch switchRM;
    public static final String SHATED_PREFS = "sharedPrefs";
    public static final String MAIL = "mail";
    public static final String PASSWORD = "password";
    public static final String SWITCH1 = "switch1";
    private String mail;
    private String password;
    private boolean switchRememberMe;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private Button txtRegister;
    private boolean b;
    private static final String CHANNEL_ID = "com.example.shula.mygrownupsapp.ANDROID";


    public void init() {

        txtRegister = (Button) findViewById(R.id.txt_register);
        txtMail = (EditText) findViewById(R.id.txt_mail);
        txtPassword = (EditText) findViewById(R.id.txt_password);
        btnOk = (Button) findViewById(R.id.btn_ok);
        switchRM = (Switch) findViewById(R.id.switch_remember_me);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //stop here
                Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                boolean isLoggedIn = userLogin();
               if(isLoggedIn) {
                   Intent log_in = new Intent(LoginActivity.this, MainActivity.class);
                   startActivity(log_in); //start the profile activty
               }
            }
        });
    }


    public boolean userLogin() {
        String mail = txtMail.getText().toString().trim();
        String password2 = txtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(mail)) {
            Toast.makeText(this, "please enter email", Toast.LENGTH_LONG).show();
            return false;        
        } else if (TextUtils.isEmpty(password2)) {
            Toast.makeText(this, "please enter password", Toast.LENGTH_LONG).show();
            return false;
        } else {
            progressDialog.setMessage("registering please wait...");
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(mail, password2)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                Intent log_in = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(log_in); //start the profile activty
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                b=false;
                            }


                        }
                    });
            if(!b)
            {
                return false;
            }

        }
        return true;
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHATED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (switchRM.isChecked()) {
            editor.putString(MAIL, txtMail.getText().toString());
            editor.putString(PASSWORD, txtPassword.getText().toString());
            Toast.makeText(this, "Data Saves", Toast.LENGTH_SHORT).show();

        } else {
            editor.putString(MAIL, "");
            editor.putString(PASSWORD, "");
        }
        editor.putBoolean(SWITCH1, switchRM.isChecked());
        editor.apply();
    }

    /**
     * the function load the data fron the device
     */
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHATED_PREFS, MODE_PRIVATE);
        mail = sharedPreferences.getString(MAIL, "");
        password = sharedPreferences.getString(PASSWORD, "");
        switchRememberMe = sharedPreferences.getBoolean(SWITCH1, false);
    }
    /*
     * the function input the mail ro the mail_txt ...
     * */

    public void upDateViews() {
        txtMail.setText(mail);
        txtPassword.setText(password);
        switchRM.setChecked(switchRememberMe);
    }




        @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            init();
            loadData();
            upDateViews();

//            FireApp fireApp = new FireApp();
//            fireApp.alertListener();
        }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

    }
}
