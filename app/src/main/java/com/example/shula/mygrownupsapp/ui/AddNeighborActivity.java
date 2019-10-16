package com.example.shula.mygrownupsapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shula.mygrownupsapp.R;
import com.example.shula.mygrownupsapp.models.FamilyMember;
import com.example.shula.mygrownupsapp.models.Neighbor;
import com.example.shula.mygrownupsapp.services.FireApp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddNeighborActivity extends AppCompatActivity {
    private static final String COLLECTION_NAME_FAMILY_MEMBER ="FamilyMembers" ;
    private Button btnAddNeighbors;
    private EditText txtName;
    private EditText txtNumber;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_neighbour);
        init();

    }

    private void init() {
        db = FirebaseFirestore.getInstance();

        btnAddNeighbors = (Button) findViewById(R.id.btn_add_neighbors);
        txtName = findViewById(R.id.txt_nei_name);
        txtNumber = findViewById(R.id.txt_nei_number);
        btnAddNeighbors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EditText id = findViewById(R.id.txt_register_id);
                    String memberId = id.getText().toString();

                if(TextUtils.isEmpty(memberId))
                {
                    Toast.makeText(AddNeighborActivity.this, "enter member id first",Toast.LENGTH_LONG).show();
                    return;
                }

                Neighbor neighbor = new Neighbor(txtName.getText().toString(), txtNumber.getText().toString(), memberId);
                FireApp fireApp = new FireApp();
                fireApp.createMembersNeighbor(neighbor);
                }
                catch(Exception e){return;}
            }
        });
    }



}
