package com.example.thechefofficial;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.InputStream;

public class test extends AppCompatActivity {
    ImageView propic;
    FirebaseAuth fAuth;
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        propic = (ImageView) findViewById(R.id.imageView2);
        fAuth = FirebaseAuth.getInstance();

    }
    public void loadWithGlide() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("userProfile/email.jpg");

        // ImageView in your Activity
        ImageView imageView = findViewById(R.id.imageView2);

        Glide.with(this)
                .load(storageReference)
                .into(imageView);
    }
}