package com.appiphany.beachcomber.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Environment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.appiphany.beachcomber.R;

public class TocAdapter extends BaseAdapter {
    public final String mExtStoragePath = Environment.getExternalStorageDirectory().getPath();
    private Context mContext;
    private LayoutInflater mInflater;
    private List<String[]> mTocs;

    public TocAdapter(Context context, List<String[]> tocs) {
        mContext = context;
        mTocs = tocs;
        mInflater = LayoutInflater.from(context);
    }

    public void setListToc(List<String[]> tocs) {
        this.mTocs = tocs;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return (mTocs == null) ? 0 : mTocs.size();
    }

    @Override
    public Object getItem(int position) {
        return mTocs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_layout, null);

            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.tvTitle);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Typeface tf1 = Typeface.createFromAsset(mContext.getAssets(), "fonts/BebasNeue.otf");
        Typeface tf2 = Typeface.createFromAsset(mContext.getAssets(), "fonts/RobotoRegular.ttf");

        if (mTocs.get(position)[0].equalsIgnoreCase("")) {
            holder.title.setText(String.valueOf(mTocs.get(position)[1]));
            holder.title.setBackgroundResource(R.drawable.bg_item);
            holder.title.setTypeface(tf2);
            holder.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            holder.title.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.title.setPadding(20, 10, 0, 10);
        } else {
            holder.title.setText(String.valueOf(mTocs.get(position)[0]));
            holder.title.setBackgroundColor(Color.TRANSPARENT);
            holder.title.setTypeface(tf1);
            holder.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
            holder.title.setTextColor(mContext.getResources().getColor(R.color.label_color));
            holder.title.setPadding(20, 15, 0, 15);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView title;
    }

}
