package com.example.buoi6contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DanhBaAdapter extends ArrayAdapter<DanhBa> {
    int resource;
    List<DanhBa> danhbaitem;

    public DanhBaAdapter(@NonNull Context context, int resource, @NonNull List<DanhBa> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.danhbaitem = objects;
    }
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent)
    {
        View v = convertView;
        if(v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(this.getContext());
            v = vi.inflate(this.resource, null);
        }
        DanhBa d = getItem(position);
        if (d!=null)
        {
            TextView nameTV = (TextView) v.findViewById(R.id.name);
            TextView phoneTV = (TextView) v.findViewById(R.id.phone);
            if (nameTV != null)
                nameTV.setText(d.getName());
            if (phoneTV != null)
                phoneTV.setText(d.getNumberPhone());
        }
        return v;
    }
}
