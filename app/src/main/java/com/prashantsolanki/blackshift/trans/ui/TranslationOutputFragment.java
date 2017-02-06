package com.prashantsolanki.blackshift.trans.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.prashantsolanki.blackshift.trans.R;
import com.prashantsolanki.blackshift.trans.model.Quote;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.grantland.widget.AutofitTextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link TranslationOutputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TranslationOutputFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_INPUT = "input";
    private static final String ARG_SPEECH = "speech";

    private String inputString;
    private String speech;

    //private OnFragmentInteractionListener mListener;

    public TranslationOutputFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param input Parameter 1.
     * @return A new instance of fragment TranslationOutputFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TranslationOutputFragment newInstance(String speech, String input) {
        TranslationOutputFragment fragment = new TranslationOutputFragment();
        Bundle args = new Bundle();
        args.putString(ARG_INPUT, input);
        args.putString(ARG_SPEECH, speech);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            inputString = getArguments().getString(ARG_INPUT);
            speech = getArguments().getString(ARG_SPEECH);
        }
    }

    View rootView;

    @BindView(R.id.output_text)
    AutofitTextView outputTextView;

    @BindView(R.id.output_star)
    ImageView star;
    @BindView(R.id.output_copy)
    ImageView copy;
    @BindView(R.id.output_share)
    ImageView share;
    @BindView(R.id.output_speech)
    TextView speechTextView;

    @BindView(R.id.output_options)
    View outputOptions;

    String key;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_translation_output, container, false);
        ButterKnife.bind(this,rootView);
        key = FirebaseDatabase.getInstance().getReference().child("/starred/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/").push().getKey();
        star.setImageDrawable(new IconDrawable(getContext(), MaterialIcons.md_star_border)
                .colorRes(android.R.color.black)
                .actionBarSize());

        copy.setImageDrawable(new IconDrawable(getContext(), MaterialIcons.md_content_copy)
                .colorRes(android.R.color.black)
                .actionBarSize());

        share.setImageDrawable(new IconDrawable(getContext(), MaterialIcons.md_share)
                .colorRes(android.R.color.black)
                .actionBarSize());

        speechTextView.setText(String.format("%s%s",speech.substring(0, 1).toUpperCase(),speech.substring(1)));

        outputTextView.setText(inputString);

        if (outputTextView.getText().toString().trim().isEmpty()||outputTextView.getText().toString().trim().length()==0){
            outputOptions.setVisibility(View.GONE);
        }else{
            outputOptions.setVisibility(View.VISIBLE);
        }
        return rootView;
    }

    @OnClick({R.id.output_text, R.id.output_copy})
    void copyOnClick(View v){
        String output = ((TextView)v).getText().toString().trim();
        if(!output.isEmpty()&&output.length()!=0){
            if(android.os.Build.VERSION.SDK_INT < 11) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(output);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", output);
                clipboard.setPrimaryClip(clip);
            }
            Toast.makeText(getContext(),"Copied to clipboard.",Toast.LENGTH_SHORT).show();
        }
    }

    public String getOutputText(){
        return outputTextView.getText().toString();
    }

    @OnClick(R.id.output_share)
    void share(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, outputTextView.getText().toString());
        sendIntent.setType("text/plain");
        getContext().startActivity(Intent.createChooser(sendIntent, getContext().getResources().getText(R.string.share_title)));
    }

    @OnClick(R.id.output_star)
    void star(){
        final Quote quote = new Quote(key,speech,getOutputText());
        FirebaseDatabase.getInstance().getReference().child("/starred/"+FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(key)) {
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                    database.child("/starred/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/"+quote.getId()+"/").removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if(databaseError==null)
                                star.setImageDrawable(new IconDrawable(getContext(), MaterialIcons.md_star_border).colorRes(android.R.color.black).actionBarSize());
                        }
                    });
                } else {
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                    database.child("/starred/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/"+quote.getId()+"/").setValue(new Quote(quote.getId(),quote.getSpeech(),quote.getOutput()), new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if(databaseError==null)
                                star.setImageDrawable(new IconDrawable(getContext(), MaterialIcons.md_star).colorRes(android.R.color.black).actionBarSize());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
