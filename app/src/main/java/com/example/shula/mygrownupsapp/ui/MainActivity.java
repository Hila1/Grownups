package com.example.shula.mygrownupsapp.ui;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shula.mygrownupsapp.models.Warning;
import com.example.shula.mygrownupsapp.services.FireApp;
import com.example.shula.mygrownupsapp.R;
import com.example.shula.mygrownupsapp.models.VideoCamera;
import com.example.shula.mygrownupsapp.models.FamilyMember;
import com.example.shula.mygrownupsapp.models.Member;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

import static com.example.shula.mygrownupsapp.ui.LoginActivity.MAIL;
import static com.example.shula.mygrownupsapp.ui.LoginActivity.PASSWORD;
import static com.example.shula.mygrownupsapp.ui.LoginActivity.SHATED_PREFS;

public class MainActivity extends AppCompatActivity {
    private TextView txtHello;
    private FamilyMember familyMember;
    private Member member;
    private VideoCamera videoCamera;
    private Button btnGotWarning;
    private FirebaseAuth firebaseAuth;

    private final String CHANNEL_1_ID = "channel1";
    private NotificationManagerCompat notificationManager;
    private static final String COLLECTION_NAME_FAMILY_MEMBER = "FamilyMember";
    private static final String COLLECTION_NAME_ALERTS = "Alerts";
    private CollectionReference collectionReference;
    private FirebaseFirestore db;
    public static Warning currWarning;



    public void init(){//shula and hila listening to warning if a warning accure alert to the user and write to database.  and add it to the warning list of member.
        db = FirebaseFirestore.getInstance();
        SharedPreferences sharedPreferences=getSharedPreferences(SHATED_PREFS,MODE_PRIVATE);
        String mail=sharedPreferences.getString(MAIL,"");
        String password=sharedPreferences.getString(PASSWORD,"");
        String name;


       try{ if(firebaseAuth.getCurrentUser()==null){
            finish();
            FirebaseAuth.getInstance().signOut();
            Intent log_out=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(log_out);

        }}
        catch (NullPointerException e)
        {
            Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show();
        }
        btnGotWarning= findViewById(R.id.btn_got_warning);
        btnGotWarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent warning=new Intent(MainActivity.this,WarningActivity.class);
                startActivity(warning);
            }
        });
        FireApp f = new FireApp();


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        alertListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.activioty_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.ic_warnings:
                this.finish();
                Intent warnings=new Intent(MainActivity.this,WarningActivity.class);
                startActivity(warnings);
                return true;
            case R.id.ic_log_out:
                this.finish();
                FirebaseAuth.getInstance().signOut();
                Intent log_out=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(log_out);
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }
    public void alertListener(){
        collectionReference = db.collection(COLLECTION_NAME_ALERTS);
        collectionReference.addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null)
                { return;
                }
                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.ADDED)
                    {

                        FamilyMember familyMember = new FamilyMember();
                        fetchFamilyMemberForAlert(familyMember,dc);


                    }
                }
            }
        });

    }


    public void fetchFamilyMemberForAlert(final FamilyMember fm, final DocumentChange dc) {
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

                        DocumentSnapshot documentSnapshot = dc.getDocument();
                        String s;
                        try{
                            s = fm.getMemberId();
                            if(documentSnapshot.get("memberId").equals(s)){
                                String date = dc.getDocument().getData().get("date").toString();
                                String level = dc.getDocument().getData().get("level").toString();
                                String memberId = dc.getDocument().getData().get("memberId").toString();
                                Warning warning = new Warning(fm.getId(), date,level,memberId);
                                deleteAlert(documentSnapshot.getId());
                                /* add */
                                currWarning = warning;
                                /*    */
                                sendOnChannel(warning);
                            }
                        }catch (Exception e){ return;}
                        //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        //Log.d(TAG, "No such document");
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void deleteAlert(String id) {
        db.collection(COLLECTION_NAME_ALERTS).document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }


    public void sendOnChannel(Warning warning) {
        notificationManager = NotificationManagerCompat.from(this);

        String level = warning.getLevel();

        String title = " Alert! your family member is in danger!";
        String message = "please check out ";
        level +=title;
        PendingIntent contentIntent = PendingIntent.getActivity(this, 1, new Intent(this, OptionsActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(level)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(1, notification);

    }

}
