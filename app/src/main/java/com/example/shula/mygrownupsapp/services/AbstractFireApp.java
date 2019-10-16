package com.example.shula.mygrownupsapp.services;


import android.app.Application;

import com.example.shula.mygrownupsapp.models.Address;
import com.example.shula.mygrownupsapp.models.FamilyMember;
import com.example.shula.mygrownupsapp.models.HelpCenter;
import com.example.shula.mygrownupsapp.models.Member;
import com.example.shula.mygrownupsapp.models.Neighbor;
import com.example.shula.mygrownupsapp.models.VideoCamera;
import com.example.shula.mygrownupsapp.models.Warning;
import com.google.firebase.firestore.DocumentChange;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AbstractFireApp extends Application{
    public void fetchMember(String id, final Member m){};
    public void createMember(final Member m){};
    public void updateMember(String id, HashMap<String, Object> data){};
//    public void fetchFamilyMember(final FamilyMember fm){};

    public abstract void fetchFamilyMemberForAlert(FamilyMember fm, DocumentChange dc);

    public void createFamilyMember(FamilyMember fm) {};
    public void updateFamilyMember(String id, HashMap<String, Object> data){};
    public void fetchHelpCenter(String id, final HelpCenter helpCenter){};
    public void fetchMemberNeighbors(String id, final ArrayList<Neighbor> neighbors){};
    public void createMembersNeighbor(Neighbor neighbor){};
    public void updateMembersNeighbor(String id, HashMap<String, Object> data){};
    public void fetchMemberAddress(String memberId, final Address address) {};
    public void createMembersAddress(Address address){};
    public void updateMembersAddress(String id, HashMap<String, Object> data){};
    public void fetchMemberWarnings(String memberId, final ArrayList<Warning> warnings) {};
    public void createMembersWarning(Warning warning){};
    public void updateMembersWarning(String id, HashMap<String, Object> data){};
    public void fetchVideoCamera(String id, final VideoCamera vd){};
    public void createVideoCamera(VideoCamera videoCamera){};
    public void updateVideoCamera(String id, HashMap<String, Object> data){};
    public void alertListener(){};
    }
