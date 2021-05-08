package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity3 extends AppCompatActivity {
    Button btn;
    EditText txtDa,txtHei,txtWei,txtL,txtR,txtChe,txtWai,txtHi;
    Button but3;
    DatabaseReference reff;
    Measurement measurement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        txtDa=findViewById(R.id.txtDate);
        txtHei=findViewById(R.id.txtHeight);
        txtWei=findViewById(R.id.txtWeight);
        txtL=findViewById(R.id.txtLarm);
        txtR=findViewById(R.id.txtRarm);
        txtChe=findViewById(R.id.txtChest);
        txtWai=findViewById(R.id.txtWaist);
        txtHi=findViewById(R.id.txtHip);
        but3=findViewById(R.id.button3);

        Measurement measurement=new Measurement();

        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reff= FirebaseDatabase.getInstance().getReference().child("Measurement");
                try{
                    if (TextUtils.isEmpty(txtDa.getText().toString()))
                        Toast.makeText(getApplicationContext(),"please enter date",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(txtHei.getText().toString()))
                        Toast.makeText(getApplicationContext(),"please enter height",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(txtWei.getText().toString()))
                        Toast.makeText(getApplicationContext(),"please enter weight",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(txtL.getText().toString()))
                        Toast.makeText(getApplicationContext(),"please enter l arm",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(txtR.getText().toString()))
                        Toast.makeText(getApplicationContext(),"please enter R arm",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(txtChe.getText().toString()))
                        Toast.makeText(getApplicationContext(),"please enter chest",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(txtWai.getText().toString()))
                        Toast.makeText(getApplicationContext(),"please enter waist",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(txtHi.getText().toString()))
                        Toast.makeText(getApplicationContext(),"please enter hip",Toast.LENGTH_SHORT).show();
                    else {
                        measurement.setDate(txtDa.getText().toString().trim());
                        measurement.setHeight(Float.parseFloat(txtHei.getText().toString().trim()));
                        measurement.setWeight(Float.parseFloat(txtWei.getText().toString().trim()));
                        measurement.setLarm(Float.parseFloat(txtL.getText().toString().trim()));
                        measurement.setRarm(Float.parseFloat(txtR.getText().toString().trim()));
                        measurement.setChest(Float.parseFloat(txtChe.getText().toString().trim()));
                        measurement.setWaist(Float.parseFloat(txtWai.getText().toString().trim()));
                        measurement.setHip(Float.parseFloat(txtHi.getText().toString().trim()));


                        reff.child("measurement2").setValue(measurement).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                              Toast.makeText(Activity3.this,"Success",Toast.LENGTH_SHORT).show();
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                     Toast.makeText(Activity3.this,"Unsuccess",Toast.LENGTH_SHORT).show();
                                    }
                                });
                        clearControls();

                    }


                }catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(),"invalid",Toast.LENGTH_SHORT).show();
                }
            }
        });



        /*reff= FirebaseDatabase.getInstance().getReference().child("Measurement");
        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Float Height= Float.parseFloat(txtHei.getText().toString().trim());
               Float weight=Float.parseFloat(txtWei.getText().toString().trim());
               Float Larm= Float.parseFloat(txtL.getText().toString().trim());
               Float Rarm= Float.parseFloat(txtR.getText().toString().trim());
               Float Chest=Float.parseFloat(txtChe.getText().toString().trim());
               Float Waist=Float.parseFloat(txtWai.getText().toString().trim());
               Float Hip=Float.parseFloat(txtHi.getText().toString().trim());

               measurement.setDate(txtDa.getText().toString().trim());
               measurement.setHeight(Height);
               measurement.setWeight(weight);
               measurement.setLarm(Larm);
               measurement.setRarm(Rarm);
               measurement.setChest(Chest);
               measurement.setWaist(Waist);
               measurement.setHip(Hip);

               reff.push().setValue(measurement);
                Toast.makeText(Activity3.this,"data inserted successfully",Toast.LENGTH_LONG).show();


            }
        });*/









        btn=findViewById(R.id.button4);
    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder= new AlertDialog.Builder(Activity3.this);
            builder.setMessage("Are you sure you want to cancel?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent= new Intent(Activity3.this,Activity2.class);
                    startActivity(intent);
                }
            })
                    .setNegativeButton("No",null);


            AlertDialog alert =builder.create();
            alert.show();

        }
    });



    }
    private void clearControls(){
        txtDa.setText("");
        txtHei.setText("");
        txtWei.setText("");
        txtL.setText("");
        txtR.setText("");
        txtChe.setText("");
        txtWai.setText("");
        txtHi.setText("");
    }
}