package com.appiphany.beachcomber.adapter;

import com.appiphany.beachcomber.models.TOC;
import com.appiphany.beachcomber.models.TOCHeader;

public interface ITocClickedListener {
    void onHeaderClicked(TOCHeader header);
    void onItemClicked(TOCHeader header, TOC item);
}
