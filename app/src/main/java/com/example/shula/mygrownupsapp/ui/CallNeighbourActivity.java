package com.example.shula.mygrownupsapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.shula.mygrownupsapp.R;
import com.example.shula.mygrownupsapp.RecyclerViewAdapter;
import com.example.shula.mygrownupsapp.models.FamilyMember;
import com.example.shula.mygrownupsapp.models.Member;
import com.example.shula.mygrownupsapp.models.Neighbor;
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

public class CallNeighbourActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    static String COLLECTION_NAME_MEMBERS = "Member";
    static String COLLECTION_NAME_NEIGHBORS = "Neighbors";
    private static final String COLLECTION_NAME_FAMILY_MEMBER = "FamilyMember";
    private  ArrayList<String> names;
    private  ArrayList<String> numbers;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_neighbour);
        init();
        //feigi and tehila finish here


    }

    private void init() {
        db = FirebaseFirestore.getInstance();
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
                        fetchMemberNeighbors(fm.getMemberId(),new ArrayList<Neighbor>());

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


    public void fetchMemberNeighbors(String memberId, final ArrayList<Neighbor> neighbors) {

        db.collection(COLLECTION_NAME_NEIGHBORS).whereEqualTo("memberId", memberId).
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            Neighbor neighbor = new Neighbor();
                            neighbor.clone(document.getData());
                            neighbors.add(neighbor);
                        }

                    }
                    displayNeighbors(neighbors);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }


            }

        });
    }

    private void displayNeighbors(ArrayList<Neighbor> neighbors) {
        names= new ArrayList<>();
        numbers=new ArrayList<>();
        if (neighbors == null)
        {
            Toast.makeText(CallNeighbourActivity.this,"you didn't insert any neighbours" +
                    "",Toast.LENGTH_LONG).show();
            return;
        }

        for (Neighbor neighbor: neighbors) {
            names.add(neighbor.getName());
            numbers.add(neighbor.getPhoneNumber());
        }

        RecyclerView recyclerView= findViewById(R.id.recycleView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(names,numbers,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


}
