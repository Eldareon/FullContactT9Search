package fjodors.com.fullcontactt9search;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;

import java.util.List;

import lombok.Getter;


public abstract class AnimatedRecyclerAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    @Getter private final List<T> items;

    public AnimatedRecyclerAdapter(List<T> items) {
        this.items = items;
    }

    public T removeItem(int position) {
        final T item = items.remove(position);
        notifyItemRemoved(position);
        return item;
    }

    public void addItem(int position, T item) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final T model = items.remove(fromPosition);
        items.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<T> items) {
        applyAndAnimateRemovals(items);
        applyAndAnimateAdditions(items);
        applyAndAnimateMovedItems(items);
    }

    private void applyAndAnimateRemovals(List<T> newItems) {
        for (int i = items.size() - 1; i >= 0; i--) {
            final T item = items.get(i);
            if (!newItems.contains(item)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<T> newItems) {
        for (int i = 0, count = newItems.size(); i < count; i++) {
            final T item = newItems.get(i);
            if (!items.contains(item)) {
                addItem(i, item);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<T> newItems) {
        for (int toPosition = newItems.size() - 1; toPosition >= 0; toPosition--) {
            final T item = newItems.get(toPosition);
            final int fromPosition = items.indexOf(item);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }
}