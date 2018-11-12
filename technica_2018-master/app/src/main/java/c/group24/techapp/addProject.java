package c.group24.techapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class addProject extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addproject);
        Button post_pj = findViewById(R.id.post_pj);
        Button post_and_join = findViewById(R.id.post_and_join);


        post_pj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView pjName = findViewById(R.id.pj_name);
                TextView pjseats = findViewById(R.id.pj_seats);
                TextView pjDes = findViewById(R.id.pj_des);

                final String name = pjName.getText().toString();
                final String des = pjDes.getText().toString();
                final String seats = pjseats.getText().toString();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Projects");
                // add project to Projects list
                ref.child(name);
                Log.i("name",name);
                ref.child(name).child("Description").setValue(des);
                ref.child(name).child("Seats").setValue(seats);

            }
        });
        post_and_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView pjName = findViewById(R.id.pj_name);
                TextView pjseats = findViewById(R.id.pj_seats);
                TextView pjDes = findViewById(R.id.pj_des);

                final String name = pjName.getText().toString();
                final String des = pjDes.getText().toString();
                final String seats = pjseats.getText().toString();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Projects");
                // add project to Projects list
                ref.child(name);
                ref.child(name).child("Description").setValue(des);
                ref.child(name).child("Seats").setValue(Integer.toString(Integer.parseInt(seats)-1));
                FirebaseAuth auth = FirebaseAuth.getInstance();
                final String uidStr = auth.getCurrentUser().getUid();
                String emailStr = auth.getCurrentUser().getEmail();
                final String proj_name = name;
                DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Taken projects and members").child(proj_name);
                ref2.child(uidStr).setValue(emailStr);
                DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference();
                Log.e("what","ha");
                ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String,Object> add = new HashMap<String, Object>();
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            String key = ds.getKey();
                            Object value =  ds.getValue();
                            add.put(key,value);
                        }
                        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference();

                        ref2.child(uidStr).child(proj_name).updateChildren(add);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        Button take = findViewById(R.id.button9);
        Button list = findViewById(R.id.button10);
        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(addProject.this,projectTaken.class);
                startActivity(i);
            }
        });
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(addProject.this,projectLists.class);
                startActivity(i);
            }
        });
    }
}
