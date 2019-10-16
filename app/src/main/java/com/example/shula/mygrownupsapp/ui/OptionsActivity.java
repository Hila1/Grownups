package com.example.shula.mygrownupsapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.shula.mygrownupsapp.R;
import com.example.shula.mygrownupsapp.models.FamilyMember;
import com.example.shula.mygrownupsapp.models.Member;
import com.example.shula.mygrownupsapp.models.Neighbor;
import com.example.shula.mygrownupsapp.models.Warning;
import com.example.shula.mygrownupsapp.services.FireApp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import static android.support.constraint.Constraints.TAG;
import static com.example.shula.mygrownupsapp.ui.MainActivity.currWarning;

public class OptionsActivity extends AppCompatActivity {
    private static final String COLLECTION_NAME_WARNINGS ="Warnings" ;
    private final String VIDEO_CAMERA_URL ="https://www.youtube.com/watch?v=26Q5lDyegiw";
    private Button btnBackPptions;
    private Button btnVCamera;
    private Button btnCallNeighbor;
    private Button btnCallHelp;
    private  FireApp fireApp;
    private FirebaseFirestore db;
    static String COLLECTION_NAME_MEMBERS = "Member";
    static String COLLECTION_NAME_NEIGHBORS = "Neighbors";

    private static final String COLLECTION_NAME_FAMILY_MEMBER = "FamilyMember";
    private void init() {
        /* add */
        db = FirebaseFirestore.getInstance();
        fetchFamilyMember(new FamilyMember());
        fireApp = new FireApp();
        btnCallHelp = findViewById(R.id.btn_call_help);
        btnVCamera = findViewById(R.id.btn_connect_to_camera);
        btnCallNeighbor = findViewById(R.id.btn_call_nighbour);
        btnBackPptions = (Button)findViewById(R.id.btn_back_options);
        btnBackPptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Warning warning = currWarning;
                fireApp.createMembersWarning(warning);
                Intent mainActivity = new Intent(OptionsActivity.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });
        btnCallNeighbor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chose neighbor and call

                Warning warning2 = currWarning;
                warning2.setNeighbor(true);
                fireApp.createMembersWarning(warning2);
                Intent intent = new Intent(OptionsActivity.this,CallNeighbourActivity.class);
                startActivity(intent);
            }
        });
        btnVCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Warning warning3 = currWarning;
                warning3.setVideoCamera(true);
                fireApp.createMembersWarning(warning3);


                Uri uri = Uri.parse(VIDEO_CAMERA_URL);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        btnCallHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chose help and call
                Warning warning4 = currWarning;
                warning4.setHelpCenter(true);
                fireApp.createMembersWarning(warning4);
                setContentView(R.layout.activity_call_help);
                setListeners();
            }
        });
    }

    private void setListeners() {

        Button medical_help = findViewById(R.id.call_medical_help);
        Button police = findViewById(R.id.call_police);
        Button fire_fighter = findViewById(R.id.call_fire_fighter);
        Button open_phone_app = findViewById(R.id.open_phone);

        medical_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "101";
                dialContactPhone(number);
            }
        });
        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "100";
                dialContactPhone(number);

            }
        });
        fire_fighter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "102";
                dialContactPhone(number);
            }
        });
        open_phone_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "";
                dialContactPhone(number);
            }
        });

    }

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        init();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    public void fetchFamilyMember(final FamilyMember fm) {
        String id;
        try {
            id= FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        catch (NullPointerException e)
        {
            return;
        }
        final DocumentReference docRefMember = db.collection(COLLECTION_NAME_FAMILY_MEMBER).document(id);
        docRefMember.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        fm.clone(document.getData());
                        fetchMember(fm.getMemberId(),new Member());

                        }

                        //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    else {
                        //Log.d(TAG, "No such document");
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public void fetchMember(String id, final Member m){
        final DocumentReference docRefMember = db.collection(COLLECTION_NAME_MEMBERS).document(id);
        docRefMember.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        m.clone(document.getData());
                        checkVideoCamera(m);
                    } else {

                    }
                } else {

                }
            }
        });
    }

    private void checkVideoCamera(Member m) {
        if(!m.getVideoCamera()){
            btnVCamera.setVisibility(View.GONE);
        }
    }



}

