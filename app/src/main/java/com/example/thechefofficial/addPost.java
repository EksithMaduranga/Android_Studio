package com.example.thechefofficial;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class addPost extends AppCompatActivity {
    FirebaseAuth fAuth;
    String userID;
    FirebaseFirestore fStore;
    StorageReference storageRef;
    private String downUri;
    Uri uri;
    ImageView reciImg;

    private StorageTask<UploadTask.TaskSnapshot> uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        Button back = findViewById(R.id.backBtn);
        Button add = findViewById(R.id.addReciBtn);
        EditText reciName = findViewById(R.id.addrecinNme);
        EditText reciDes = findViewById(R.id.addrecIDes);
         reciImg= findViewById(R.id.addReciImge);

        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getUid();
        fStore = FirebaseFirestore.getInstance();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addPost.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        reciImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker=new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                startActivityForResult(photoPicker,1);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = reciName.getText().toString().trim();
                final String des = reciDes.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    reciName.setError("Name is Required.");
                    return;
                }

                if (TextUtils.isEmpty(des)) {
                    reciDes.setError("Discription is is Required.");
                    return;
                }

                addRecipe(name, des, downUri);
                //uploadImage(name,des);

            }
        });


    }

    private void addRecipe(String name, String des,String img) {

        Toast.makeText(addPost.this, "Recipe Added.", Toast.LENGTH_SHORT).show();
        userID = fAuth.getCurrentUser().getUid();

        String time = String.valueOf(Timestamp.now().getNanoseconds());
        DocumentReference documentReference = fStore.collection("recipe").document(time);
        documentReference.getId();
        Map<String, Object> post = new HashMap<>();
        post.put("id",userID);
        post.put("imgId" , time);
        post.put("postName", name);
        post.put("postdes", des);
        post.put("postImg",img);

        documentReference.set(post).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "Post Added ! " + userID);
                Intent intent = new Intent(addPost.this,HomeActivity.class);
                startActivity(intent);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "onFailure: " + e.toString());
            }
        });
    }


    public void uploadImage( String name, String des) {

        String time = String.valueOf(Timestamp.now().getNanoseconds());
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Recipe").child(time);

        uploadTask = storageReference.putFile(uri);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return storageReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    Log.w("log", "uri " + downloadUri);
                    downUri = downloadUri.toString();


                    addRecipe(name, des, downUri);

                } else {
                    // Handle failures
                    // ...
                }
            }
        });



    }
    @Override
    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            uri = data.getData();
            reciImg.setImageURI(uri);

        } else Toast.makeText(this, "You Haven't Select Image", Toast.LENGTH_SHORT).show();
    }
}