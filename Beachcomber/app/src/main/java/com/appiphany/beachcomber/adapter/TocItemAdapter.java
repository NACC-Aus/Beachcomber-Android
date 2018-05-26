package com.appiphany.beachcomber.adapter;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Html;
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
import java.util.Locale;

@SuppressWarnings("WeakerAccess")
public class TocItemAdapter extends SectioningAdapter {
    private List<TOCHeader> data;
    private ITocClickedListener listener;
    private int itemSelectedIndex;
    private int headerSelectedIndex;

    public TocItemAdapter(List<TOCHeader> data, ITocClickedListener listener) {
        this.data = data;
        this.listener = listener;
        itemSelectedIndex = -1;
        headerSelectedIndex = -1;
    }

    public int getItemSelectedIndex() {
        return itemSelectedIndex;
    }

    public void setItemSelectedIndex(int itemSelectedIndex) {
        this.itemSelectedIndex = itemSelectedIndex;
    }

    public int getHeaderSelectedIndex() {
        return headerSelectedIndex;
    }

    public void setHeaderSelectedIndex(int headerSelectedIndex) {
        this.headerSelectedIndex = headerSelectedIndex;
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
        View v = View.inflate(parent.getContext(), R.layout.item_layout, null);
        return new ItemViewHolder(v);
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        View v = View.inflate(parent.getContext(), R.layout.header_layout, null);
        return new HeaderViewHolder(v);
    }

    @Override
    public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder viewHolder, final int sectionIndex, final int itemIndex, int itemUserType) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        final TOC item = getItem(sectionIndex, itemIndex);
        final TOCHeader header = getSection(sectionIndex);
        itemViewHolder.tvTitle.setText(item.getPageName());

        String imagePath = Config.THUMB_PATH + item.getThumb();
        Glide.with(itemViewHolder.imgThumb.getContext()).load(imagePath).into(itemViewHolder.imgThumb);

        ((ItemViewHolder) viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClicked(header, item, sectionIndex, itemIndex);
                }
            }
        });

        String title = item.getPageName();

        if(item.getStartPageNumber() >= 7) {
            if ("Leucopogon sp. Midwest".equals(title)) {
                title = "<i>Leucopogon </i>sp. Midwest";
            } else if ("Verticordia densiflora var. roseostella".equals(title)) {
                title = "<i>Verticordia densiflora </i>var.<i> roseostella</i>";
            } else {
                String[] components = title.split("subsp.");
                if (components.length > 1) {
                    title = String.format(Locale.US, "<i>%s</i>subsp.<i>%s</i>", components[0], components[1]);
                } else {
                    title = String.format(Locale.US, "<i>%s</i>", title);
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            itemViewHolder.tvTitle.setText(Html.fromHtml(title,
                    Html.FROM_HTML_MODE_LEGACY));
        } else {
            itemViewHolder.tvTitle.setText(Html.fromHtml(title));
        }

        if (getItemSelectedIndex() == itemIndex && getHeaderSelectedIndex() == sectionIndex) {
            itemViewHolder.tvTitle.setBackgroundColor(ContextCompat.getColor(itemViewHolder.tvTitle.getContext(),
                    R.color.item_bg_selected));
        } else {
            itemViewHolder.tvTitle.setBackgroundColor(ContextCompat.getColor(itemViewHolder.tvTitle.getContext(),
                    R.color.item_bg));
        }
    }

    @Override
    public void onBindHeaderViewHolder(SectioningAdapter.HeaderViewHolder viewHolder, final int sectionIndex, int headerUserType) {
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
                if (listener != null) {
                    listener.onHeaderClicked(header, sectionIndex);
                }
            }
        });
    }

    public TOCHeader getSection(int sectionIndex) {
        return data.get(sectionIndex);
    }

    public TOC getItem(int sectionIndex, int itemIndex) {
        return data.get(sectionIndex).getTocList().get(itemIndex);
    }

    private static class ItemViewHolder extends SectioningAdapter.ItemViewHolder {
        TextView tvTitle;
        ImageView imgThumb;

        ItemViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            imgThumb = itemView.findViewById(R.id.imgThumb);
        }
    }

    private static class HeaderViewHolder extends SectioningAdapter.HeaderViewHolder {
        TextView tvTitle;

        HeaderViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }

    @Override
    public GhostHeaderViewHolder onCreateGhostHeaderViewHolder(ViewGroup parent) {
        final View ghostView = new View(parent.getContext());
        ghostView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return new GhostHeaderViewHolder(ghostView);
    }
}
