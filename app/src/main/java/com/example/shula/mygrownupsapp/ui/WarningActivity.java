package com.example.shula.mygrownupsapp.ui;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;

        import com.example.shula.mygrownupsapp.R;
        import com.example.shula.mygrownupsapp.RecyclerViewAdapter;
        import com.example.shula.mygrownupsapp.WarningHistoryAdapter;
        import com.example.shula.mygrownupsapp.models.FamilyMember;
        import com.example.shula.mygrownupsapp.models.Member;
        import com.example.shula.mygrownupsapp.models.Neighbor;
        import com.example.shula.mygrownupsapp.models.Warning;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.firestore.DocumentChange;
        import com.google.firebase.firestore.DocumentReference;
        import com.google.firebase.firestore.DocumentSnapshot;
        import com.google.firebase.firestore.FirebaseFirestore;
        import com.google.firebase.firestore.QuerySnapshot;

        import java.util.ArrayList;

        import static android.support.constraint.Constraints.TAG;

public class WarningActivity extends AppCompatActivity {
    private static final String COLLECTION_NAME_WARNINGS ="Warnings" ;
    private FirebaseFirestore db;
    static String COLLECTION_NAME_MEMBERS = "Member";
    static String COLLECTION_NAME_NEIGHBORS = "Neighbors";
    private static final String COLLECTION_NAME_FAMILY_MEMBER = "FamilyMember";
    private  ArrayList<String> levels;
    private  ArrayList<String> dates;
    private  ArrayList<String> chosenHelps;
    private Button btnBackWarnings;
    private ArrayList<Warning> warnings;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warnings);
        init();


    }

    private void init() {
        db = FirebaseFirestore.getInstance();


            btnBackWarnings = (Button)findViewById(R.id.btn_back_warnings);
            btnBackWarnings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mainActivity = new Intent(WarningActivity.this, MainActivity.class);
                    startActivity(mainActivity);
                }
            });

        fetchFamilyMember(new FamilyMember());


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
                        fetchMemberWarnings(fm.getMemberId(), new ArrayList<Warning>());
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


    public void fetchMemberWarnings(String memberId, final ArrayList<Warning> warnings) {

        db.collection(COLLECTION_NAME_WARNINGS).whereEqualTo("memberId", memberId).
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                           Warning warning = new Warning();
                           warning.clone(document.getData());
                           warnings.add(warning);
                        }

                    }
                    displayWarnings(warnings);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }


            }

        });
    }

    private void displayWarnings(ArrayList<Warning> warnings) {
        if (warnings == null)
        {
            Toast.makeText(WarningActivity.this,"no warnings history for your family member :)",Toast.LENGTH_LONG).show();
            return;
        }
        dates= new ArrayList<>();
        levels=new ArrayList<>();
        chosenHelps=new ArrayList<>();


        for (Warning warning: warnings) {
            dates.add(warning.getDate());
            levels.add(warning.getLevel());
            if(warning.isHelpCenter())
                chosenHelps.add("call help center");
            else if(warning.isNeighbor())
                chosenHelps.add("call neighbor");
            else if(warning.isVideoCamera())
                chosenHelps.add("connect video camera");
        }

        RecyclerView recyclerView= findViewById(R.id.recycle_warnings);
        WarningHistoryAdapter adapter = new WarningHistoryAdapter(dates,levels,chosenHelps,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


}

