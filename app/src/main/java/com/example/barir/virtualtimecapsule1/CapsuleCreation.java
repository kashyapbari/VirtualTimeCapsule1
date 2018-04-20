package com.example.barir.virtualtimecapsule1;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class CapsuleCreation extends AppCompatActivity implements View.OnClickListener {
    Button submit_button;
    EditText title,to,content;
    DatePicker datePicker;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capsule_creation);
        submit_button=findViewById(R.id.submit_button);
        title=findViewById(R.id.title);
        to=findViewById(R.id.to);
        content=findViewById(R.id.content);
        datePicker=findViewById(R.id.date);

        submit_button.setOnClickListener(this);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference().child("capsules");

    }

    @Override
    public void onClick(View v) {
        if (v == submit_button){
            String titleString = title.getText().toString().trim();
            String toString = to.getText().toString().trim();
            String contentString = content.getText().toString().trim();

            writeCapsule(titleString, toString, contentString);
        }
    }

    private void writeCapsule(String titleString, String toString, String contentString) {
        Capsule capsule = new Capsule(titleString, toString, contentString);
        Map<String, Object> capsuleValue = capsule.toMap();
        mRef.push().setValue(capsuleValue).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
//                Dialog dialog = new Dialog(CapsuleCreation.this);
//                dialog
                Intent intent=new Intent(CapsuleCreation.this,successfull.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
