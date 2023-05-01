package com.example.thechefofficial.ui.feedback;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.thechefofficial.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class FeedbackFragment extends Fragment {

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static List<FeedBack> feedList;
    public static FeedbackAdapter adapter;
    public static ListView feedBackList = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        feedBackList = (ListView) view.findViewById(R.id.feedbackListView);

        feedList = new ArrayList<FeedBack>();

        db.collection("feedback")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println("DocumentSnapshot data: " + document.getData());

                                FeedBack feedBack = new FeedBack();

                                feedBack = document.toObject(FeedBack.class);
                                System.out.println("Item" + feedBack.rate);
                                feedList.add(feedBack);

                                System.out.println("POST Data" + feedList);

                                adapter = new FeedbackAdapter(getActivity(), feedList);
                                feedBackList.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            System.out.println("No such document");
                        }
                    }
                });



        return view;
    }
}