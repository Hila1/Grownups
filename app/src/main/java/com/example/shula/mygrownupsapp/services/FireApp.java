package com.example.shula.mygrownupsapp.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;


import com.example.shula.mygrownupsapp.R;
import com.example.shula.mygrownupsapp.models.Address;
import com.example.shula.mygrownupsapp.models.FamilyMember;
import com.example.shula.mygrownupsapp.models.HelpCenter;
import com.example.shula.mygrownupsapp.models.Member;
import com.example.shula.mygrownupsapp.models.Neighbor;
import com.example.shula.mygrownupsapp.models.VideoCamera;
import com.example.shula.mygrownupsapp.models.Warning;
import com.example.shula.mygrownupsapp.services.AbstractFireApp;
import com.example.shula.mygrownupsapp.ui.MainActivity;
import com.example.shula.mygrownupsapp.ui.OptionsActivity;
import com.example.shula.mygrownupsapp.ui.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Nullable;

import static android.support.constraint.Constraints.TAG;

public class FireApp  extends AbstractFireApp {
    private static final String COLLECTION_NAME_ALERTS ="Alerts" ;
    private static final String COLLECTION_NAME_VIDEO_CAMERA ="VideoCamera" ;
    private CollectionReference collectionReference;
    private static final String COLLECTION_NAME_WARNINGS = "Warnings" ;
    private static final String COLLECTION_NAME_FAMILY_MEMBER = "FamilyMember";
    private static final String COLLECTION_NAME_HELP_CENTER ="HelpCenter";
    static String COLLECTION_NAME_ADDRESS = "Address";
    static String COLLECTION_NAME_MEMBERS = "Member";
    static String COLLECTION_NAME_NEIGHBORS = "Neighbors";
    private static final String CHANNEL_ID = "com.example.shula.mygrownupsapp.ANDROID";
    private NotificationManagerCompat notificationManager;
    public static final String CHANNEL_1_ID = "channel1";
    private FirebaseFirestore db;
    public static boolean userExist;
    public static boolean complete;
    private FirebaseAuth firebaseAuth;
    private boolean authSucceed;


    public FireApp() {
       this.db = FirebaseFirestore.getInstance();
       firebaseAuth = FirebaseAuth.getInstance();
       authSucceed = true;
    }
    @Override
    public void fetchMember(String id, final Member m){
        final DocumentReference docRefMember = db.collection(COLLECTION_NAME_MEMBERS).document(id);
        docRefMember.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        m.clone(document.getData());

                    } else {

                    }
                } else {

                }
            }
        });
    }





    @Override
    public void fetchMemberAddress(String memberId, final Address address) {

        final DocumentReference docRefMember = db.collection(COLLECTION_NAME_ADDRESS).document(memberId);//check who is the id
        docRefMember.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        address.clone(document.getData());
//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        //Log.d(TAG, "No such document");
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
    @Override
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
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }


            }

        });
    }

    @Override
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
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }


            }

        });
    }

    @Override
    public void createMember(final Member m){
        HashMap<String, Object> map = m.toMap();
        this.db.collection(COLLECTION_NAME_MEMBERS).document(m.getId())
                .set(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) { }
                });
    }
    @Override
    public void updateMember(String id, HashMap<String, Object> data){
        this.db.collection(COLLECTION_NAME_MEMBERS).document(id)
                .update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    @Override
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

                        if(documentSnapshot.get("memberId").equals(fm.getMemberId())){
                            String date = dc.getDocument().getData().get("date").toString();
                            String level = dc.getDocument().getData().get("level").toString();
                            String memberId = dc.getDocument().getData().get("memberId").toString();
                            Warning warning = new Warning(fm.getId(), date,level,memberId);

                        }

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

    @Override
    public void createFamilyMember(FamilyMember fm) {
        HashMap<String, Object> map = fm.toMap();
        this.db.collection(COLLECTION_NAME_FAMILY_MEMBER).document(fm.getId())
                .set(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    @Override
    public void updateFamilyMember(String id, HashMap<String, Object> data) {
        this.db.collection(COLLECTION_NAME_MEMBERS).document(id)
                .update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    @Override
    public void fetchHelpCenter(String id, final HelpCenter helpCenter) {
        final DocumentReference docRefMember = db.collection(COLLECTION_NAME_HELP_CENTER).document(id);
        docRefMember.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        helpCenter.clone(document.getData());
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

    @Override
    public void createMembersNeighbor(Neighbor neighbor) {

        HashMap<String, Object> map = neighbor.toMap();
        this.db.collection(COLLECTION_NAME_NEIGHBORS).document()
                .set(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });      }

    @Override
    public void updateMembersNeighbor(String id, HashMap<String, Object> data) {
        this.db.collection(COLLECTION_NAME_NEIGHBORS).document(id)
                .update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });      }

    @Override
    public void createMembersAddress(Address address) {
        HashMap<String, Object> map = address.toMap();
        this.db.collection(COLLECTION_NAME_ADDRESS).document(address.getMemberId())
                .set(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    @Override
    public void updateMembersAddress(String id, HashMap<String, Object> data) {
        this.db.collection(COLLECTION_NAME_MEMBERS).document(id)
                .update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });      }

    @Override
    public void createMembersWarning(Warning warning) {
        HashMap<String, Object> map = warning.toMap();
        this.db.collection(COLLECTION_NAME_WARNINGS).document()
                .set(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    @Override
    public void updateMembersWarning(String id, HashMap<String, Object> data) {
        this.db.collection(COLLECTION_NAME_WARNINGS).document(id)
                .update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    @Override
    public void fetchVideoCamera(String id, final VideoCamera vd) {
        final DocumentReference docRefMember = db.collection(COLLECTION_NAME_VIDEO_CAMERA).document(id);
        docRefMember.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        vd.clone(document.getData());
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

    @Override
    public void createVideoCamera(VideoCamera videoCamera) {
        HashMap<String, Object> map = videoCamera.toMap();
        this.db.collection(COLLECTION_NAME_VIDEO_CAMERA).document(videoCamera.getMemberId())
                .set(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    @Override
    public void updateVideoCamera(String id, HashMap<String, Object> data) {
        this.db.collection(COLLECTION_NAME_VIDEO_CAMERA).document(id)
                .update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

}
