package com.appiphany.beachcomber.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appiphany.beachcomber.R;
import com.appiphany.beachcomber.util.Config;
import com.bumptech.glide.Glide;

public class TocAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<String[]> mTocs;
    private Typeface tf1;
    private Typeface tf2;

    public TocAdapter(Context context, List<String[]> tocs) {
        mContext = context;
        mTocs = tocs;
        mInflater = LayoutInflater.from(context);
        tf1 = Typeface.createFromAsset(mContext.getAssets(), "fonts/BebasNeue.otf");
        tf2 = Typeface.createFromAsset(mContext.getAssets(), "fonts/RobotoRegular.ttf");
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
            convertView = mInflater.inflate(R.layout.item_layout, parent, false);

            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.tvTitle);
            holder.imgThumb = (ImageView) convertView.findViewById(R.id.imgThumb);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mTocs.get(position)[0].equalsIgnoreCase("")) {
            holder.title.setText(String.valueOf(mTocs.get(position)[1]));
            holder.title.setBackgroundResource(R.drawable.bg_item);
            holder.title.setTypeface(tf2);
            holder.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            holder.title.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            holder.title.setPadding(20, 10, 0, 10);
        } else {
            holder.title.setText(String.valueOf(mTocs.get(position)[0]));
            holder.title.setBackgroundColor(Color.TRANSPARENT);
            holder.title.setTypeface(tf1);
            holder.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
            holder.title.setTextColor(ContextCompat.getColor(mContext, R.color.label_color));
            holder.title.setPadding(20, 15, 0, 15);
        }

        String imagePath = Config.THUMB_PATH + "bryzoan.jpg";
        Glide.with(holder.imgThumb.getContext()).load(Uri.parse(imagePath)).into(holder.imgThumb);
        return convertView;
    }

    private static class ViewHolder {
        TextView title;
        ImageView imgThumb;
    }

}
