package com.example.thechefofficial;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thechefofficial.dummy.DummyContent;
import com.squareup.picasso.Picasso;

/**
 * A fragment representing a single imageGallery detail screen.
 * This fragment is either contained in a {@link imageGalleryListActivity}
 * in two-pane mode (on tablets) or a {@link imageGalleryDetailActivity}
 * on handsets.
 */
public class imageGalleryDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public imageGalleryDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.imagegallery_detail, container, false);

            ((TextView) rootView.findViewById(R.id.imagegallery_detai)).setText(mItem.details);
            ((TextView) rootView.findViewById(R.id.reciName)).setText("hi");
            ((TextView) rootView.findViewById(R.id.reciDes)).setText("Des");
    /*   ImageView reciPic=  rootView.findViewById(R.id.reciPict);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/thechef-fe8f1.appspot.com/o/userProfile%2FjBZ5GWwPcMhGeDjFf5nZqbRHkIe2?alt=media&token=1c2f508a-d62a-4438-9bc7-b190af87a98e").into(reciPic);

*/
        return rootView;
    }
}