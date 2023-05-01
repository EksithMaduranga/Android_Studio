package com.example.thechefofficial.ui.feedback;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.thechefofficial.R;
import com.example.thechefofficial.ui.home.Recipe;
import com.example.thechefofficial.ui.slideshow.postViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.List;

import static com.example.thechefofficial.ui.feedback.FeedbackFragment.adapter;
import static com.example.thechefofficial.ui.feedback.FeedbackFragment.db;
import static com.example.thechefofficial.ui.feedback.FeedbackFragment.feedList;
import static com.example.thechefofficial.ui.feedback.FeedbackFragment.feedBackList;

public class FeedbackAdapter extends ArrayAdapter<FeedBack> {


    private List<FeedBack> itemList;
    private Activity activity;


    public FeedbackAdapter(Activity activity, List<FeedBack> itemList)
    {
        super(activity, R.layout.feedback_list_item, itemList);
        this.itemList = itemList;
        this.activity = activity;

    }


    @Override
    public View getView(final int position, View view, ViewGroup parent)

    {

        final FeedBack items = itemList.get(position);
        LayoutInflater inflater = activity.getLayoutInflater();

        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.feedback_list_item, null, true);


        TextView comment = (TextView) rowView.findViewById(R.id.comment);
        TextView rate = (TextView) rowView.findViewById(R.id.rate);
        ImageView remove = (ImageView) rowView.findViewById(R.id.feedbackRemoveBtn);


        comment.setText(items.getComment());
        rate.setText(items.getRate()+"");


        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.collection("feedback").document(items.getId())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                db.collection("feedback")
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {

                                                    feedList.clear();
                                                    adapter.clear();

                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        System.out.println("DocumentSnapshot data: " + document.getData());

                                                        FeedBack item = new FeedBack();

                                                        item = document.toObject(FeedBack.class);

                                                        feedList.add(item);

                                                        System.out.println("POST Data" + feedList);

                                                        adapter = new FeedbackAdapter(activity, feedList);
                                                        feedBackList.setAdapter(adapter);
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                } else {
                                                    System.out.println("No such document");
                                                }
                                            }
                                        });


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

            }
        });

        return rowView;
    }


}
