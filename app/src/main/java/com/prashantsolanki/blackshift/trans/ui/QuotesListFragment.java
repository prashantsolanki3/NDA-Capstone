package com.prashantsolanki.blackshift.trans.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prashantsolanki.blackshift.trans.R;
import com.prashantsolanki.blackshift.trans.model.Quote;
import com.prashantsolanki.blackshift.trans.viewholder.QuoteVh;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.prashantsolanki3.snaplibrary.snap.adapter.SnapAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuotesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuotesListFragment extends Fragment {

    private static final String ARG_SPEECH = "speech";

    private String speech;

    public QuotesListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment QuotesListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuotesListFragment newInstance(String speech) {
        QuotesListFragment fragment = new QuotesListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SPEECH, speech);
        fragment.setArguments(args);
        return fragment;
    }


    SnapAdapter<Quote> snapAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            speech = getArguments().getString(ARG_SPEECH);
        }
    }

    //TODO: Loading background

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.recycler_view, container, false);
        ButterKnife.bind(this,rootView);
        snapAdapter = new SnapAdapter<>(getContext(), Quote.class,R.layout.item_quote,QuoteVh.class,recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(snapAdapter);


        FirebaseDatabase.getInstance()
                .getReference("/quotes/")
                .orderByChild("speech")
                .startAt(speech)
                .endAt(speech)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                snapAdapter.clear();

                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                    snapAdapter.add(snapshot.getValue(Quote.class));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rootView;
    }
}
