package com.appiphany.beachcomber.adapter;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appiphany.beachcomber.R;
import com.appiphany.beachcomber.models.TOC;
import com.appiphany.beachcomber.models.TOCHeader;
import com.appiphany.beachcomber.util.Config;
import com.bumptech.glide.Glide;

import org.zakariya.stickyheaders.SectioningAdapter;

import java.util.List;

@SuppressWarnings("WeakerAccess")
public class TocItemAdapter extends SectioningAdapter {
    private List<TOCHeader> data;
    private ITocClickedListener listener;

    public TocItemAdapter(List<TOCHeader> data, ITocClickedListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @Override
    public int getNumberOfSections() {
        return data.size();
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        return data.get(sectionIndex).getTocList().size();
    }

    @Override
    public boolean doesSectionHaveHeader(int sectionIndex) {
        return true;
    }

    @Override
    public boolean doesSectionHaveFooter(int sectionIndex) {
        return false;
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.header_layout, parent, false);
        return new HeaderViewHolder(v);
    }

    @Override
    public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder viewHolder, int sectionIndex, int itemIndex, int itemUserType) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        final TOC item = getItem(sectionIndex, itemIndex);
        final TOCHeader header = getSection(sectionIndex);
        itemViewHolder.tvTitle.setText(item.getPageName());
        itemViewHolder.tvTitle.setBackgroundColor(ContextCompat.getColor(itemViewHolder.tvTitle.getContext(),
                R.color.item_bg));
        String imagePath = Config.THUMB_PATH + item.getThumb();
        Glide.with(itemViewHolder.imgThumb.getContext()).load(imagePath).into(itemViewHolder.imgThumb);

        ((ItemViewHolder) viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClicked(header, item);
                }
            }
        });
    }

    @Override
    public void onBindHeaderViewHolder(SectioningAdapter.HeaderViewHolder viewHolder, int sectionIndex, int headerUserType) {
        HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
        final TOCHeader header = getSection(sectionIndex);
        headerViewHolder.tvTitle.setBackgroundColor(ContextCompat.getColor(headerViewHolder.tvTitle.getContext()
                , R.color.header_bg));
        headerViewHolder.tvTitle.setTextColor(ContextCompat.getColor(headerViewHolder.tvTitle.getContext()
                , R.color.header_text));
        headerViewHolder.tvTitle.setText(header.getHeader());

        ((HeaderViewHolder) viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onHeaderClicked(header);
                }
            }
        });
    }

    public TOCHeader getSection(int sectionIndex){
        return data.get(sectionIndex);
    }

    public TOC getItem(int sectionIndex, int itemIndex){
        return data.get(sectionIndex).getTocList().get(itemIndex);
    }

    private static class ItemViewHolder extends SectioningAdapter.ItemViewHolder {
        TextView tvTitle;
        ImageView imgThumb;
        ItemViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            imgThumb = (ImageView) itemView.findViewById(R.id.imgThumb);
        }
    }

    private static class HeaderViewHolder extends SectioningAdapter.HeaderViewHolder {
        TextView tvTitle;
        HeaderViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }
}
