package com.project.elaajonclick.model.fireStoreApi;

import com.project.elaajonclick.model.User;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserHelper {
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static CollectionReference usersRef = db.collection("User");

    public static void addUser(String name, String address, String tel, String type) {
        User user = new User(name, address, tel, FirebaseAuth.getInstance().getCurrentUser().getEmail(), type);
        usersRef.document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).set(user);

    }
}
