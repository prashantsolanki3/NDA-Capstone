package com.prashantsolanki.blackshift.trans.viewholder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
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

import io.github.prashantsolanki3.snaplibrary.snap.layout.viewholder.SnapViewHolder;

/**
 * Created by prsso on 30-01-2017.
 */

public class QuoteVh extends SnapViewHolder<Quote> implements View.OnClickListener{

    final private ImageView star;
    final private ImageView copy;
    final private ImageView share;
    final private TextView quoteTv;
    final private TextView speakerTv;
    Quote quote;

    public QuoteVh(View itemView, Context context) {
        super(itemView, context);
        star = (ImageView) itemView.findViewById(R.id.star);
        copy = (ImageView) itemView.findViewById(R.id.copy);
        share = (ImageView) itemView.findViewById(R.id.share);
        quoteTv = (TextView) itemView.findViewById(R.id.output);
        speakerTv = (TextView) itemView.findViewById(R.id.speaker);

        copy.setOnClickListener(this);
        star.setOnClickListener(this);
        share.setOnClickListener(this);
        quoteTv.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String quoteString = quoteTv.getText().toString();
        int id = v.getId();
        if(id == R.id.output || id == R.id.copy)
            copyOnClick(quoteString);
        else if(id == R.id.star){

            FirebaseDatabase.getInstance().getReference().child("/starred/"+FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(quote.getId())) {
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
                        database.child("/starred/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/"+quote.getId()+"/").setValue(quote, new DatabaseReference.CompletionListener() {
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
        }else if(id == R.id.share){
            share(quoteString);
        }
    }

    void share(String quote){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, quote);
        sendIntent.setType("text/plain");
        getContext().startActivity(Intent.createChooser(sendIntent, getContext().getResources().getText(R.string.share_title)));
    }

    void copyOnClick(String output){

        if(!output.isEmpty()&&output.length()!=0){
            if(android.os.Build.VERSION.SDK_INT < 11) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(output);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", output);
                clipboard.setPrimaryClip(clip);
            }
            Toast.makeText(getContext(),"Copied to clipboard.",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void populateViewHolder(Quote quote, int i) {
        this.quote = quote;
        quoteTv.setText(quote.getOutput());

        toggleStar(star,quote.getId());

        copy.setImageDrawable(new IconDrawable(getContext(), MaterialIcons.md_content_copy)
                .colorRes(android.R.color.black)
                .actionBarSize());

        share.setImageDrawable(new IconDrawable(getContext(), MaterialIcons.md_share)
                .colorRes(android.R.color.black)
                .actionBarSize());
    }

    void toggleStar(final ImageView star,final String id){

        FirebaseDatabase.getInstance().getReference().child("/starred/"+FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(id))
                    star.setImageDrawable(new IconDrawable(getContext(), MaterialIcons.md_star)
                            .colorRes(android.R.color.black)
                            .actionBarSize());
                else
                    star.setImageDrawable(new IconDrawable(getContext(), MaterialIcons.md_star_border)
                            .colorRes(android.R.color.black)
                            .actionBarSize());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
