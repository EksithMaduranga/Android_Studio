package com.example.thechefofficial;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thechefofficial.dummy.DummyContent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link imageGalleryDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class imageGalleryListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    FirebaseAuth fAuth;
    FirebaseUser user;
    FirebaseFirestore fStore;
    Uri uri;
    private boolean mTwoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagegallery_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        fStore = FirebaseFirestore.getInstance();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        View recyclerView = findViewById(R.id.imagegallery_list);
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final imageGalleryListActivity mParentActivity;
        private final List<DummyContent.DummyItem> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(imageGalleryDetailFragment.ARG_ITEM_ID, item.id);
                    imageGalleryDetailFragment fragment = new imageGalleryDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.imagegallery_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, imageGalleryDetailActivity.class);
                    intent.putExtra(imageGalleryDetailFragment.ARG_ITEM_ID, item.id);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(imageGalleryListActivity parent,
                                      List<DummyContent.DummyItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.imagegallery_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.recipeName.setText("Not eat");
            holder.recipeUserName.setText("eat me");
            holder.recipeDes.setText("this is poison");
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/thechef-fe8f1.appspot.com/o/userProfile%2FjBZ5GWwPcMhGeDjFf5nZqbRHkIe2?alt=media&token=1c2f508a-d62a-4438-9bc7-b190af87a98e").into(holder.recipeUserimg);
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/thechef-fe8f1.appspot.com/o/userProfile%2FjBZ5GWwPcMhGeDjFf5nZqbRHkIe2?alt=media&token=1c2f508a-d62a-4438-9bc7-b190af87a98e").into(holder.recipeImg);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView recipeName;
            final TextView recipeUserName;
            final TextView recipeDes;
            final ImageView recipeImg;
            final ImageView recipeUserimg;

            ViewHolder(View view) {
                super(view);
                recipeName = (TextView) view.findViewById(R.id.reciname);
                recipeUserName = (TextView) view.findViewById(R.id.id_text);
                recipeDes = (TextView) view.findViewById(R.id.des);
                recipeImg = (ImageView) view.findViewById(R.id.reciPict);
                recipeUserimg = (ImageView) view.findViewById(R.id.UsetPic);
            }
        }
    }
}