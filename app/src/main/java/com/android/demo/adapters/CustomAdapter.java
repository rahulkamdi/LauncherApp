package com.android.demo.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.applib.launcher.model.AppData;
import com.android.demo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// custom adapter.
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private static final String TAG= "CustomAdapter";
    private final List<AppData> tempList;
    private List<AppData> list;

    public CustomAdapter(List<AppData> list) {
        this.list = list;
        tempList = new ArrayList<>();
        tempList.addAll(list);
        Log.i(TAG,"list size found:"+tempList.size());
    }

    /**
     * Reset list items.
     */
    public void resetListItems(){
        tempList.clear();
        tempList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(tempList.get(position).getAppName());
        holder.textView.setTag(position);
        if(tempList.get(position).getIcon()!=null) {
            holder.imageView.setImageDrawable(tempList.get(position).getIcon());
        }
    }

    @Override
    public int getItemCount() {
        return tempList.size();
    }

    // Create new list by query and refresh the list.
    public void getFilter(String query) {
        if (query.isEmpty()) {
            resetListItems();
            return;
        } else {
            tempList.clear();
            for (AppData appData : list) {
                if (appData.getAppName().toLowerCase().contains(query.toLowerCase())) {
                    tempList.add(appData);
                }
            }
            notifyDataSetChanged();
        }
    }

    /**
     * My view holder pattern.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView textView;
        public final ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.text_view);
            imageView = view.findViewById(R.id.icon);
            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = (int) view.getTag();
            // NA
        }
    }


}
