package com.example.thechefofficial;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thechefofficial.data.model.User;
import com.example.thechefofficial.ui.login.LoginViewModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    private LoginViewModel loginViewModel;
    EditText mFullName,mEmail,mPassword,mPhone;
    Button mRegisterBtn;
    Button mpropic;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    String userID;
    FirebaseFirestore fStore;
    StorageReference storageRef;
    ImageView profilePic;
    private Uri imageUrl;
    private String downUri;
    Uri uri;
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;
   public static User cus =new User();
    Gson gson = new Gson();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        final EditText mEmail = findViewById(R.id.mail);
        final EditText mPassword = findViewById(R.id.password);
        final EditText mFullName = findViewById(R.id.name);
        final Button  RegisterBtn = findViewById(R.id.registerbtn);
        final EditText mPhone = findViewById(R.id.mobile);
        profilePic =(ImageView)findViewById(R.id.proPic);



        storageRef = FirebaseStorage.getInstance().getReference("user/proPic/");
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker=new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                startActivityForResult(photoPicker,1);
            }
        });



                RegisterBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final String email = mEmail.getText().toString().trim();
                        String password = mPassword.getText().toString().trim();
                        final String fullName = mFullName.getText().toString();
                        final String phone = mPhone.getText().toString();

                        if (TextUtils.isEmpty(fullName)) {
                            mFullName.setError("Name is Required.");
                            return;
                        }

                        if (TextUtils.isEmpty(email)) {
                            mEmail.setError("Email is Required.");
                            return;
                        }
                        if (TextUtils.isEmpty(phone)) {
                            mPhone.setError("mobile number is Required.");
                            return;
                        }
                        if (phone.length() < 10 || phone.length() > 15) {
                            mPhone.setError("Enter valid phone number");
                            return;
                        }

                        if (TextUtils.isEmpty(password)) {
                            mPassword.setError("Password is Required.");
                            return;
                        }

                        if (password.length() < 8) {
                            mPassword.setError("Password 8 cheractar length");
                            return;
                        }


                        // register the user in firebase

                        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    // send verification link

                                    FirebaseUser fuser = fAuth.getCurrentUser();
                                    fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(RegisterActivity.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                                        }
                                    });

                                    uploadImage(email,password,fullName,phone);


                                } else {
                                    Toast.makeText(RegisterActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    }
                });

    }

    private void userRegister(String email, String password, String fullName, String phone, String proPic) {

        Toast.makeText(RegisterActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
        userID = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("user").document(fAuth.getUid().toString());
        documentReference.getId();
        Map<String, Object> user = new HashMap<>();
        user.put("id",userID);
        user.put("fName", fullName);
        user.put("email", email);
        user.put("phone", phone);
        user.put("proPic", proPic);
        user.put("password",password);


        cus.setUid(userID);
        cus.setName(fullName);
        cus.setUrl(proPic);
        cus.setMail(email);
        cus.setTp(Integer.parseInt(phone));
        cus.setPass(password);

        Intent intent = new Intent(RegisterActivity.this,HomeActivity.class);
        String cusJson = gson.toJson(cus);
        intent.putExtra("cus", cusJson);
        startActivity(intent);

        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "User Profile is created for " + userID);



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK)
        {
            uri = data.getData();
            profilePic.setImageURI(uri);

        }
        else Toast.makeText(this, "You Haven't Select Image", Toast.LENGTH_SHORT).show();
    }

    public void uploadImage(String email, String password, String fullName, String phone){
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
                    downUri = downloadUri.toString();
                    userRegister(email,password,fullName,phone,downUri);

                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }





    public void signIn(View view){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }


}