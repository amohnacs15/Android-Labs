package com.androidhunter.testynotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Our list will only show the Title
 */
public class NoteAdapter extends BaseAdapter {

    private Context context;
    private List<Note> adapterNotes;
    private LayoutInflater layoutInflater = null;

    public NoteAdapter(Context cContext, List<Note> cAdapterNotes) {
        this.context = cContext;
        this.adapterNotes = cAdapterNotes;
        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return adapterNotes.size();
    }

    @Override
    public Note getItem(int position) {
        return adapterNotes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        ListViewHolder holder;

        if(convertView == null) {
            LayoutInflater li = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.item_list, null);
            holder = new ListViewHolder(v);
            v.setTag(holder);

        } else {
            holder = (ListViewHolder) v.getTag();
        }
        holder.tv.setText(adapterNotes.get(position).getTitle());
        return v;
    }

    class ListViewHolder {
        public TextView tv;

        public ListViewHolder(View view) {
            tv = (TextView) view.findViewById(R.id.listViewText);
        }
    }
}


