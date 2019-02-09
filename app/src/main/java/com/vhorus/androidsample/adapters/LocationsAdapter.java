package com.vhorus.androidsample.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vhorus.androidsample.R;
import com.vhorus.androidsample.Utils;
import com.vhorus.androidsample.entities.Location;

import java.util.List;

public class LocationsAdapter extends ArrayAdapter {
    Activity activity ;
    List<Location> locations;
    ViewHolder vHolder = null;

    public LocationsAdapter(Activity activity, List locations){
        super(activity, R.layout.list_item_location, locations);
        this.activity = activity;
        this.locations = locations;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return locations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return locations.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if(rowView == null){
            LayoutInflater inflater = activity.getLayoutInflater();
            rowView = inflater.inflate(R.layout.list_item_location, null);
            vHolder = new ViewHolder();
            vHolder.nameTextView = rowView.findViewById(R.id.location_title_textview);
            vHolder.timestmapTextView = rowView.findViewById(R.id.location_timestamp_textview);
            rowView.setTag(vHolder);
        }
        else{
            vHolder = (ViewHolder) rowView.getTag();
        }

        Location item = locations.get(position);
        vHolder.nameTextView.setText(""+item.getTitle());
        vHolder.timestmapTextView.setText(""+ Utils.getTimeStringDateHoursMinutes(item.getTimestamp()));

        return rowView;
    }

    public static class ViewHolder{
        TextView nameTextView, timestmapTextView;
    }
}
