package fjodors.com.fullcontactt9search;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Fjodors on 2015.05.10..
 */

public class RecyclerAdapter extends  RecyclerView.Adapter<ViewHolder> {

    private String searchQuery = "";
    private int lastPosition = -1;
    private  List<String> items;


    public RecyclerAdapter() {
        items = new ArrayList<>();
    }

    public class ViewHolderWords extends ViewHolder {
        @Bind(R.id.word)
        TextView word;

        public ViewHolderWords(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        View v = mInflater.inflate(R.layout.rec_view_word, parent, false);
        return new ViewHolderWords(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ViewHolderWords viewHolderWords = (ViewHolderWords) viewHolder;
        String word = items.get(position);
        viewHolderWords.word.setText(highlightText(toNumber(word), word, searchQuery));
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setWordsAndQuery(List<String> words, String query) {
        searchQuery = query;
        items = words;
    }

    public static CharSequence highlightText(String numberText, String OriginalText, String search) {
        String normalizedText = Normalizer.normalize(numberText, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase();
        int start = normalizedText.indexOf(search);

        if (start < 0) return numberText;

        Spannable highlighted = new SpannableString(OriginalText);
        while (start >= 0) {
            int spanStart = Math.min(start, numberText.length());
            int spanEnd = Math.min(start + search.length(), numberText.length());

            highlighted.setSpan(new ForegroundColorSpan(Color.BLUE), spanStart, spanEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            start = normalizedText.indexOf(search, spanEnd);
        }
        return highlighted;
    }

    public String toNumber(String word) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            builder.append(Trie.T9MAP.get(word.charAt(i)));
        }
        return builder.toString();
    }
}