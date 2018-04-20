package com.example.barir.virtualtimecapsule1;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.firebase.database.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class CapsuleListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    public List<Capsule> ITEMS = new ArrayList<Capsule>();
    public static Map<String, Capsule> ITEM_MAP = new HashMap<String, Capsule>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference().child("capsules");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capsule_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }


        });

        if (findViewById(R.id.capsule_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        View recyclerView = findViewById(R.id.capsule_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot capsuleSnapShot: dataSnapshot.getChildren()){
                        Capsule capsule = capsuleSnapShot.getValue(Capsule.class);
                        addItem(capsule);
                        View recyclerView = findViewById(R.id.capsule_list);
                        assert recyclerView != null;
                        setupRecyclerView((RecyclerView) recyclerView);
                        //Log.d("capsule_log", capsule.getTitle()+"\t"+capsule.getContent());
                        //Log.d("Capsule_array",""+ITEMS.isEmpty());
                    }
                    //Log.d("Capsule_array",""+ITEMS.get(0).getTitle());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("err", "The read failed: " + databaseError.getCode());
            }




        });

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this,ITEMS,mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final CapsuleListActivity mParentActivity;
        private final List<Capsule> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Capsule item = (Capsule) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(CapsuleDetailFragment.ARG_ITEM_ID, item.getTitle());
                    CapsuleDetailFragment fragment = new CapsuleDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.capsule_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, CapsuleDetailActivity.class);
                    intent.putExtra(CapsuleDetailFragment.ARG_ITEM_ID, item.getTitle());
                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(CapsuleListActivity parent,
                                      List<Capsule> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.capsule_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).getTitle());
            holder.mContentView.setText(mValues.get(position).getContent());

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
    private void addItem(Capsule item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getTitle(), item);
    }
}
