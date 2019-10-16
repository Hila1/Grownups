package com.example.shula.mygrownupsapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shula.mygrownupsapp.services.FireApp;
import com.example.shula.mygrownupsapp.R;
import com.example.shula.mygrownupsapp.models.Address;
import com.example.shula.mygrownupsapp.models.FamilyMember;
import com.example.shula.mygrownupsapp.models.Member;
import com.example.shula.mygrownupsapp.models.Neighbor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class RegisterActivity extends AppCompatActivity {
    private static final String COLLECTION_NAME_MEMBERS ="Members" ;
    private FirebaseAuth firebaseAuth;
    private ArrayList<Neighbor> neighbors;
    private ArrayAdapter<Neighbor> arrayAdapter;
    private EditText etxtRegisterId;
    private EditText etxtRegisteName;
    private EditText etxtRegisterStreetName;
    private EditText etxtRegisterBuilding;
    private EditText etxtRegisterApartment;
    private EditText etxtRegisterNumber;
    private EditText etxtFamilyName;
    private EditText etxtFamilyNumber;
    private EditText etxtEmailRegister;
    private EditText etxtPasswordRegister;
    private EditText etxtSecondPassword;
    private String txtRegisterId;
    private String txtRegisteName;
    private String txtRegisterStreetName;
    private String txtRegisterBuilding;
    private String txtRegisterApartment;
    private String txtRegisterNumber;
    private String txtFamilyName;
    private String txtFamilyNumber;
    private String txtEmailRegister;
    private String txtPasswordRegister;
    private String txtSecondPassword;
    private Button btnRegisterOk;
    private Button btnAddNeighbor;
    private Button btnLogin;
    private CheckBox checkBoxVideoCameras;
    private  boolean authSucceed;
    public static boolean userExist;
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;
    private EditText neighborName;
    private EditText neighborNumber;

    //get all the components.


    public void init(){
        this.db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        authSucceed = true;
        neighbors = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        btnAddNeighbor = findViewById(R.id.btn_add_neighbor);
        btnLogin = findViewById(R.id.btn_login);
        btnRegisterOk = findViewById(R.id.btn_register_ok);
        etxtRegisterId = findViewById(R.id.txt_register_id);
        etxtRegisteName = findViewById(R.id.txt_register_name);
        etxtRegisterStreetName = findViewById(R.id.txt_register_street_name);
        etxtRegisterBuilding = findViewById(R.id.txt_register_building);
        etxtRegisterApartment = findViewById(R.id.txt_register_apartment);
        etxtRegisterNumber = findViewById(R.id.txt_register_number);
        etxtFamilyNumber = findViewById(R.id.txt_family_number);
        etxtFamilyName = findViewById(R.id.txt_family_name);
        etxtEmailRegister = findViewById(R.id.txt_email_register);
        etxtPasswordRegister = findViewById(R.id.txt_password_register);
        etxtSecondPassword = findViewById(R.id.txt_second_password);
        checkBoxVideoCameras = findViewById(R.id.check_box_video_cameras);
        neighborName = findViewById(R.id.txt_nei_name);
        neighborNumber = findViewById(R.id.txt_nei_number);
        btnAddNeighbor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
            try {
                txtRegisterId = etxtRegisterId.getText().toString();
                if(TextUtils.isEmpty(txtRegisterId))
                {
                    Toast.makeText(RegisterActivity.this, "enter member id first",Toast.LENGTH_LONG).show();
                    return;
                }

                Neighbor neighbor = new Neighbor(neighborName.getText().toString(), neighborNumber.getText().toString(), txtRegisterId);
                FireApp fireApp = new FireApp();
                Toast.makeText(RegisterActivity.this, "neighbor added",Toast.LENGTH_LONG).show();
                neighborName.setText("");
                neighborNumber.setText("");
                fireApp.createMembersNeighbor(neighbor);

            }
            catch(Exception e){return;}
        }
            });
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(login);
                }
            });
            btnRegisterOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initialize();
                if(!checkEditText())
                    return;
                FireApp fireApp = new FireApp();
                Member member = new Member();
                fetchMember(txtRegisterId.trim(),member);

            }
            });
    }

    public void userNotExistFunc() {

            createAccount(txtEmailRegister.trim(), txtPasswordRegister.trim());

    }

    public void fetchMember(String id, final Member m){
        progressDialog.setMessage("registering please wait...");
        progressDialog.show();
        final DocumentReference docRefMember = db.collection(COLLECTION_NAME_MEMBERS).document(id);
        docRefMember.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                progressDialog.dismiss();

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        m.clone(document.getData());
                        userExistFunc();

                    } else {
                        userNotExistFunc();
                    }
                } else {
                    userNotExistFunc();

                }
            }
        });
    }

    private void userExistFunc() {
        Toast.makeText(RegisterActivity.this,"user already exist!",Toast.LENGTH_LONG).show();

    }

    private boolean checkEditText() {
        if(TextUtils.isEmpty(txtRegisterId)||TextUtils.isEmpty(txtRegisteName)||TextUtils.isEmpty(txtRegisterNumber)||TextUtils.isEmpty(txtRegisterBuilding)||TextUtils.isEmpty(txtRegisterApartment)||TextUtils.isEmpty(txtRegisterStreetName)||TextUtils.isEmpty(txtFamilyName)||TextUtils.isEmpty(txtFamilyNumber)||TextUtils.isEmpty(txtEmailRegister)||TextUtils.isEmpty(txtPasswordRegister)||TextUtils.isEmpty(txtSecondPassword))
        {
            Toast.makeText(RegisterActivity.this, "some details not entered", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!txtPasswordRegister.equals(txtSecondPassword))
        {
            Toast.makeText(RegisterActivity.this, "problem with password validation", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void register() {


        String familyMemberId;
        try {
            familyMemberId= firebaseAuth.getCurrentUser().getUid();
        }
        catch (NullPointerException e)
        {
            familyMemberId = "111";
        }
        FireApp fireApp=new FireApp();
        Address address = new Address(txtRegisterId, txtRegisterStreetName, txtRegisterBuilding, txtRegisterApartment);
        Member member = new Member(txtRegisterId, txtRegisteName, address, txtRegisterNumber, checkBoxVideoCameras.isChecked());
        FamilyMember familyMember= new FamilyMember(familyMemberId,txtFamilyName, txtFamilyNumber, txtEmailRegister, txtPasswordRegister, member.getId());
        fireApp.createFamilyMember(familyMember);
        fireApp.createMember(member);
     }

    private void initialize(){

        txtRegisterId = etxtRegisterId.getText().toString();
        txtRegisteName = etxtRegisteName.getText().toString();
        txtRegisterStreetName = etxtRegisterStreetName.getText().toString();
        txtRegisterBuilding = etxtRegisterBuilding.getText().toString();
        txtRegisterApartment = etxtRegisterApartment.getText().toString();
        txtRegisterNumber = etxtRegisterNumber.getText().toString();
        txtFamilyName = etxtFamilyName.getText().toString();
        txtFamilyNumber = etxtFamilyNumber.getText().toString();
        txtEmailRegister = etxtEmailRegister.getText().toString();
        txtPasswordRegister = etxtPasswordRegister.getText().toString();
        txtSecondPassword = etxtSecondPassword.getText().toString();
    }
    private boolean createAccount(String txtEmail, String txtPassword) {
        firebaseAuth.createUserWithEmailAndPassword(txtEmail, txtPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                           // Log.d(TAG, "createUserWithEmail:success");
                            Intent log_in = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(log_in); //start the profile activty
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            register();

                        }
                            else {

                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, func(task.getException().toString()), Toast.LENGTH_LONG).show();
                            authSucceed= false;
                        }

                    }
                });
        return authSucceed;
    }

    private String func(String s) {
//        if(s.contains("[") && s.contains("]")) {
//            return s.substring(s.indexOf("["), s.lastIndexOf("]"));
//        }
            return  s;
    }

    @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (firebaseAuth.getCurrentUser() != null) {
            //handle the already login user
        }
    }


}
