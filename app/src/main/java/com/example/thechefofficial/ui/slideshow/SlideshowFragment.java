package com.example.thechefofficial.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.Slide;

import com.example.thechefofficial.LoginActivity;
import com.example.thechefofficial.R;
import com.example.thechefofficial.RegisterActivity;
import com.example.thechefofficial.imageGalleryDetailActivity;
import com.example.thechefofficial.imageGalleryDetailFragment;
import com.example.thechefofficial.imageGalleryListActivity;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
    public void goImageGallery(View view){
        Intent intent = new Intent(getActivity(), imageGalleryDetailFragment.class);
        startActivity(intent);
    }

}