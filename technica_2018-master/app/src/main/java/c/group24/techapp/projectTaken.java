package c.group24.techapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class projectTaken extends Activity {

    ExpandableListView expandableListView;
    adaptertaken customExpandableListViewAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        database = FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        final String uidStr = auth.getCurrentUser().getUid();
        myRef = database.getReference().child(uidStr);

        expandableListView = findViewById(R.id.lvExp);
        SetStandardGroups();
        customExpandableListViewAdapter = new adaptertaken(this, listDataHeader, listDataChild);
        expandableListView.setAdapter(customExpandableListViewAdapter);
        Button post = findViewById(R.id.button6);
        Button list = findViewById(R.id.button7);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(projectTaken.this,addProject.class);
                startActivity(i);
            }
        });
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(projectTaken.this,projectLists.class);
                startActivity(i);
            }
        });

    }

    public void SetStandardGroups() {

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        myRef.addChildEventListener(new ChildEventListener() {
            int counter = 0;
            List<String> childItem = new ArrayList<>();



            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                listDataHeader.add(dataSnapshot.getKey());
                Log.e("TAG", listDataHeader.get(counter));
                childItem = new ArrayList<>();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String childNames = (String) ds.getValue();
                    String key = ds.getKey();
                    Log.e("TAG", "childNames :" + childNames);
                    childItem.add(childNames);
                }

                listDataChild.put(listDataHeader.get(counter), childItem);
                counter++;
                Log.e("TAG", "counter :" + counter);

                customExpandableListViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
