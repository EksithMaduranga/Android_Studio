package com.example.thechefofficial.ui.gallery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.thechefofficial.HomeActivity;
import com.example.thechefofficial.R;
import com.example.thechefofficial.RegisterActivity;
import com.example.thechefofficial.data.model.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    FirebaseAuth fAuth;
    FirebaseUser user;
    FirebaseFirestore fStore;
    Uri uri;
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;
    FirebaseFirestore db;
    ImageView pic,navProPic;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        fStore = FirebaseFirestore.getInstance();

        galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        final EditText editName = root.findViewById(R.id.editName);
        final EditText editPhone = root.findViewById(R.id.editPhone);
        final EditText editMail = root.findViewById(R.id.editMail);
        final Button editBtn = root.findViewById(R.id.editProfile);
        pic = root.findViewById(R.id.editPropic);
        navProPic = root.findViewById(R.id.nawPic);

        String UserID = fAuth.getUid().toString();



        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                DocumentReference documentReference= fStore.collection("user").document(UserID);
                documentReference.addSnapshotListener( new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        editName.setText(value.getString("fName"));
                        editMail.setText(value.getString("email"));
                        editPhone.setText(value.getString("phone"));
                        Picasso.get().load(value.getString("proPic")).into(pic);
                    }
                });
            pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent photoPicker=new Intent(Intent.ACTION_PICK);
                    photoPicker.setType("image/*");
                    startActivityForResult(photoPicker,1);



                }
            });


            }

        });


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = editMail.getText().toString().trim();
                final String fullName = editName.getText().toString();
                final String phone = editPhone.getText().toString();

                if (TextUtils.isEmpty(fullName)) {
                    editName.setError("Name is Required.");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    editMail.setError("Email is Required.");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    editPhone.setError("mobile number is Required.");
                    return;
                }
                if (phone.length() < 10 || phone.length() > 15) {
                    editPhone.setError("Enter valid phone number");
                    return;
                }


                // update Data
                UpdateUser(email,fullName,phone);

            }
        });


        return root;


    }
    private void UpdateUser(String email , String fullName, String phone) {

        String userID = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("user").document(fAuth.getUid().toString());
        documentReference.getId();
        Map<String, Object> user = new HashMap<>();
        user.put("fName", fullName);
        user.put("email", email);
        user.put("phone", phone);

        documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "User Profile is Updated for " + userID);

                Toast.makeText(getActivity(), "Profile Updated!", Toast.LENGTH_SHORT).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "onFailure: " + e.toString());
            }
        });


    }


    public void uploadImage(){
        StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("userProfile").child(fAuth.getUid());
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
                    Log.w("log","uri " +downloadUri);
                    Picasso.get().load(downloadUri).into(pic);

                    String userID = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("user").document(fAuth.getUid().toString());
                    documentReference.getId();
                    Map<String, Object> user = new HashMap<>();
                    user.put("proPic", downloadUri.toString());



                    documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG", "User Profile is Updated for " + userID);

                            Toast.makeText(getActivity(), "Profile Updated!", Toast.LENGTH_SHORT).show();


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG", "onFailure: " + e.toString());
                        }
                    });
                } else {

                }
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == HomeActivity.RESULT_OK) {
                uri = data.getData();
                Toast.makeText(getActivity(), "Profile Pic Updated!", Toast.LENGTH_SHORT).show();
                uploadImage();
        }


    }

}