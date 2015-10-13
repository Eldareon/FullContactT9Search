package fjodors.com.fullcontactt9search;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Fjodors on 2015.05.10..
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<String> words;
    private String searchQuery = "";
    private Map t9Map;


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
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
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v;

        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());

        v = mInflater.inflate(R.layout.rec_view_word, parent, false);
        return new ViewHolderWords(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ViewHolderWords viewHolderBios = (ViewHolderWords) viewHolder;
        viewHolderBios.word.setText(highlightText(toNumber(words.get(position)), words.get(position), searchQuery));
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public RecyclerAdapter(Map t9Map) {
        this.words = new ArrayList<>();
        this.t9Map = t9Map;
    }

    public String removeItem(int position) {
        final String word = words.remove(position);
        notifyItemRemoved(position);
        return word;
    }

    public void addItem(int position, String word) {
        words.add(position, word);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final String model = words.remove(fromPosition);
        words.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<String> words, String query) {

        searchQuery = query;

        applyAndAnimateRemovals(words);
        applyAndAnimateAdditions(words);
        applyAndAnimateMovedItems(words);
    }

    private void applyAndAnimateRemovals(List<String> newWords) {
        for (int i = words.size() - 1; i >= 0; i--) {
            final String word = words.get(i);
            if (!newWords.contains(word)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<String> newWords) {
        for (int i = 0, count = newWords.size(); i < count; i++) {
            final String word = newWords.get(i);
            if (!words.contains(word)) {
                addItem(i, word);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<String> newWords) {
        for (int toPosition = newWords.size() - 1; toPosition >= 0; toPosition--) {
            final String word = newWords.get(toPosition);
            final int fromPosition = word.indexOf(word);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public static CharSequence highlightText(String numberText, String OriginalText, String search) {

        String normalizedText = Normalizer.normalize(numberText, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();

        int start = normalizedText.indexOf(search);
        if (start < 0) {
            return numberText;
        } else {
            Spannable highlighted = new SpannableString(OriginalText);
            while (start >= 0) {
                int spanStart = Math.min(start, numberText.length());
                int spanEnd = Math.min(start + search.length(), numberText.length());

                highlighted.setSpan(new ForegroundColorSpan(Color.BLUE), spanStart, spanEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                start = normalizedText.indexOf(search, spanEnd);
            }

            return highlighted;
        }
    }

    public String toNumber(String word) {
        String numberWord = "";

        for (int i = 0; i < word.length(); i++) {
            numberWord = numberWord + String.valueOf(t9Map.get(word.charAt(i)));
        }
        return numberWord;
    }
}
