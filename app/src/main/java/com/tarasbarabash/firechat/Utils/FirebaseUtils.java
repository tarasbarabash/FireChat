package com.tarasbarabash.firechat.Utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.tarasbarabash.firechat.Utils.FirebaseUtils.USERS_DB.FIELDS.PHOTO_URL;

/**
 * Created by Taras
 * 30.04.2018, 10:58.
 */

public final class FirebaseUtils {
    public static final class USERS_DB {
        public static final String DB_NAME = "users";
        public static final class FIELDS {
            public static final String META = "meta";
            public static final String PHOTO_URL = "photoUrl";
            public static final String NAME = "name";
            public static final String CONTACTS = "contacts";
            public static final String CONVERSATIONS = "conversation";
            public static final String ONLINE = "online";
            public static final String LAST_SEEN = "lastSeen";
            public static final String USERNAME = "userName";
            public static final String BIO = "bio";
            public static final String DEVICE_ID = "deviceId";
        }
    }

    public static final class CONVERSATIONS_DB {
        public static final String DB_NAME = "conversations";
        public static final class FIELDS {
            public static final String MESSAGES = "messages";
            public static final String SENDER = "sender";
            public static final String TEXT = "text";
            public static final String TIME = "time";
            public static final String USERS = "users";
            public static final String TYPE = "type";
        }
    }

    public static final class USERNAMES_DB {
        public static final String DB_NAME = "usernames";
    }

    public static DatabaseReference getUsersRef() {
        return FirebaseDatabase.getInstance().getReference().child(USERS_DB.DB_NAME);
    }

    public static DatabaseReference getUserRef(String user) {
        return getUsersRef().child(user);
    }

    public static DatabaseReference getCurrentUserRef() {
        return getUserRef(getCurrentUserPhoneNumber());
    }

    public static DatabaseReference getConversationsRef() {
        return FirebaseDatabase.getInstance().getReference().child(CONVERSATIONS_DB.DB_NAME);
    }

    public static DatabaseReference getUserMetaRef(String user) {
        return getUsersRef().child(user).child(USERS_DB.FIELDS.META);
    }

    public static DatabaseReference getCurrentUserMetaRef() {
        return getUserMetaRef(getCurrentUserPhoneNumber());
    }

    public static DatabaseReference getCurrentUserPicRef() {
        return getCurrentUserMetaRef().child(PHOTO_URL);
    }

    public static StorageReference getUserPicsRef() {
        return FirebaseStorage.getInstance().getReference().child("userPics");
    }

    public static StorageReference getCurrentUserPicStorageRef() {
        return getUserPicsRef().child(getCurrentUserPhoneNumber() + ".jpg");
    }

    public static StorageReference getMessagesPicsStorageRef() {
        return FirebaseStorage.getInstance().getReference().child("messages");
    }

    public static DatabaseReference getUsernamesRef() {
        return FirebaseDatabase.getInstance().getReference().child(USERNAMES_DB.DB_NAME);
    }

    public static FirebaseUser getUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static String getCurrentUserPhoneNumber() {
        return getUser().getPhoneNumber();
    }

    public static boolean isCurrentUser(String phoneNumber) {
        return getUser().getPhoneNumber().equals(phoneNumber);
    }
}
