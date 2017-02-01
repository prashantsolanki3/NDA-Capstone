package com.prashantsolanki.blackshift.trans.viewholder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    public QuoteVh(View itemView, Context context) {
        super(itemView, context);
        star = (ImageView) itemView.findViewById(R.id.star);
        copy = (ImageView) itemView.findViewById(R.id.copy);
        share = (ImageView) itemView.findViewById(R.id.share);
        quoteTv = (TextView) itemView.findViewById(R.id.quote);
        speakerTv = (TextView) itemView.findViewById(R.id.speaker);

        copy.setOnClickListener(this);
        star.setOnClickListener(this);
        share.setOnClickListener(this);
        quoteTv.setOnClickListener(this);

        star.setImageDrawable(new IconDrawable(getContext(), MaterialIcons.md_star_border)
                .colorRes(android.R.color.black)
                .actionBarSize());

        copy.setImageDrawable(new IconDrawable(getContext(), MaterialIcons.md_content_copy)
                .colorRes(android.R.color.black)
                .actionBarSize());

        share.setImageDrawable(new IconDrawable(getContext(), MaterialIcons.md_share)
                .colorRes(android.R.color.black)
                .actionBarSize());
    }


    @Override
    public void onClick(View v) {
        String quote = quoteTv.getText().toString();
        int id = v.getId();
        if(id == R.id.quote || id == R.id.copy)
            copyOnClick(quote);
        else if(id == R.id.star){

        }else if(id == R.id.share){
            share(quote);
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
        quoteTv.setText(quote.getQuote());
        //speakerTv.setText(String.format(" - %s",quote.getSpeaker()));
    }
}
