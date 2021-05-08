package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Activity4 extends AppCompatActivity {

    Button but6,but9,but12;
    EditText txtDat,txtHeit,txtWeit,txtLt,txtRt,txtChet,txtWait,txtHit;
    DatabaseReference reff;
    Measurement measurement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);


        txtDat=findViewById(R.id.rtD);
        txtHeit=findViewById(R.id.rtHei);
        txtWeit=findViewById(R.id.rtWei);
        txtLt=findViewById(R.id.rtL);
        txtRt=findViewById(R.id.rtR);
        txtChet=findViewById(R.id.rtChe);
        txtWait=findViewById(R.id.rtWai);
        txtHit=findViewById(R.id.rtHip);
        but6=findViewById(R.id.button6);
        but9=findViewById(R.id.button9);

        measurement=new Measurement();

        but6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              reff= FirebaseDatabase.getInstance().getReference().child("Measurement/measurement2");
                reff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren()){
                            txtDat.setText(snapshot.child("date").getValue().toString());
                            txtHeit.setText(snapshot.child("height").getValue().toString());
                            txtWeit.setText(snapshot.child("weight").getValue().toString());
                            txtLt.setText(snapshot.child("larm").getValue().toString());
                            txtRt.setText(snapshot.child("rarm").getValue().toString());
                            txtChet.setText(snapshot.child("chest").getValue().toString());
                            txtWait.setText(snapshot.child("waist").getValue().toString());
                            txtHit.setText(snapshot.child("hip").getValue().toString());
                        }
                        else
                            Toast.makeText(getApplicationContext(),"No source to display",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        but9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reff=FirebaseDatabase.getInstance().getReference().child("Measurement");
                reff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild("measurement2")){
                            reff=FirebaseDatabase.getInstance().getReference().child("Measurement").child("measurement2");
                            reff.removeValue();
                            clearfields();
                            Toast.makeText(getApplicationContext(),"Measurements Deleted successfully",Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(),"Not Deleted",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        but12=findViewById(R.id.button12);
        but12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Activity4.this);
                builder.setMessage("Are you sure you want to exit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent= new Intent(Activity4.this,Activity2.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No",null);

                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });



    }
    private void clearfields(){

        txtDat.setText("");
        txtHeit.setText("");
        txtWeit.setText("");
        txtLt.setText("");
        txtRt.setText("");
        txtChet.setText("");
        txtWait.setText("");
        txtHit.setText("");
    }
}