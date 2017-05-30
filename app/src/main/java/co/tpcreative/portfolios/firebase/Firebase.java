package co.tpcreative.portfolios.firebase;

import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import co.tpcreative.portfolios.model.CData;
import co.tpcreative.portfolios.model.CObject;

public class Firebase {

    public static final String TAG = "Firebase" ;
    private ListenerFirebase listenerFirebase ;

    public static Firebase getInstance(ListenerFirebase listenerFirebase) {
        Firebase firebase = new Firebase();
        firebase.listenerFirebase = listenerFirebase;
        return firebase;
    }

    public static void saveToServer(CData cData){
        DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference(); //Getting root reference
        myRef1.setValue(cData, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null){
                    Log.d(TAG,"Error occurred :" + databaseError.getMessage());
                }
                else{
                    Log.d(TAG,"Sent data to Firebase successfully");
                }
            }
        });
    }

    public void getJsonFromServer(){
        DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference(); //Getting root reference
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CData cData = dataSnapshot.getValue(CData.class);
                listenerFirebase.onSuccess(cData);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                listenerFirebase.onError(databaseError.getMessage());
            }
        });
    }

    public interface ListenerFirebase {
        void onSuccess(CData cData);
        void onError(String error);
    }

}
