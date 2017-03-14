package com.appiphany.beachcomber.models;

public class TOCHeader {
    private String header;
    private TOC toc;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public TOC getToc() {
        return toc;
    }

    public void setToc(TOC toc) {
        this.toc = toc;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TOCHeader){
            TOCHeader other = (TOCHeader) obj;
            return getHeader() != null && getHeader().equalsIgnoreCase(other.getHeader());
        }
        return false;
    }
}
