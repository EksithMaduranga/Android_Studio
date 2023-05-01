package com.example.thechefofficial;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thechefofficial.data.model.User;
import com.example.thechefofficial.ui.feedback.FeedBack;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    MenuItem logout;
    FirebaseUser user;
    FirebaseFirestore fStore;
    TextView navMail,navName;
    Uri uri;
    private AppBarConfiguration mAppBarConfiguration;
    User cus;
    User appUser;
    public Dialog feedbackDialog;
    public float rate = 0;
    public String comment;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public HomeActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        fStore = FirebaseFirestore.getInstance();


        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView userMail = (TextView) header.findViewById(R.id.nawName);
        Button logOut = (Button) header.findViewById(R.id.logoutbtn);
        TextView userName = (TextView) header.findViewById(R.id.nawMail);
        userMail.setText(user.getEmail());
        ImageView pic = (ImageView) header.findViewById(R.id.nawPic);



       /* Gson gson = new Gson();
        cus = gson.fromJson(getIntent().getStringExtra("cus"), User.class);*/


        String UserID = fAuth.getUid().toString();

        System.out.println("User Id" + UserID);



        DocumentReference documentReference= fStore.collection("user").document(UserID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                userMail.setText(value.getString("fName"));
                userName.setText(value.getString("email"));
                Picasso.get().load(value.getString("proPic")).into(pic);
            }
        });



        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,addPost.class);
                startActivity(intent);

            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.logoutbtn)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_feedback:
                System.out.println("Action Feedback");
                ShowCashInHandDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void ShowCashInHandDialog() {

        feedbackDialog = new Dialog(HomeActivity.this);
        feedbackDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        feedbackDialog.setCancelable(true);
        feedbackDialog.setContentView(R.layout.dialog_feedback);
        feedbackDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ImageView ivDialogClose = (ImageView) feedbackDialog.findViewById(R.id.iv_dialogCloseCIH);
        final EditText etCashAmount = (EditText) feedbackDialog.findViewById(R.id.et_cashAmount);
        Button btnCashInHandSubmit = (Button) feedbackDialog.findViewById(R.id.btn_cashInHandSubmit);
        //Rating bar
        RatingBar rbRateCustomer = (RatingBar) feedbackDialog.findViewById(R.id.rb_customerRate);


        ivDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedbackDialog.dismiss();
            }
        });

        rbRateCustomer.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                rate = rating;
                rbRateCustomer.setRating(rate);

            }
        });

        btnCashInHandSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!etCashAmount.getText().toString().isEmpty()) {

                    comment = etCashAmount.getText().toString();

                    etCashAmount.setError(null);

                    feedbackDialog.dismiss();

                    InputMethodManager imm = (InputMethodManager) HomeActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etCashAmount.getWindowToken(), 0);

                    FeedBack feedBack = new FeedBack();

                    feedBack.comment = comment;
                    feedBack.rate = rate;

                    String id = db.collection("feedback").document().getId();

                    feedBack.id = id;
                    db.collection("feedback").document(id).set(feedBack)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(HomeActivity.this, "Feedback Added Successfully.",Toast.LENGTH_SHORT).show();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });


                } else {

                    etCashAmount.setError("Cannot empty...");

                }

            }
        });


        Point size = new Point();
        HomeActivity.this.getWindowManager().getDefaultDisplay().getSize(size);
        int w = (int) (size.x * .9);
        feedbackDialog.getWindow().setLayout(w, ViewGroup.LayoutParams.WRAP_CONTENT);
        feedbackDialog.show();
    }

}