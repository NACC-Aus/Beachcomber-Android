package com.appiphany.beachcomber.util;

import com.appiphany.beachcomber.BuildConfig;

public class Config {
    public static final String INDEX_PAGE = "index_page";

    public static final String REALM_FILE = "default.realm";
    private static final String ASSETS_PATH = "file:///android_asset/";
    public static final String THUMB_PATH = ASSETS_PATH + "Thumbnails/";

    public static final String[] BEACHCOMBER_HEADERS = new String[]{
            "Introduction", "Chordates - Animals with backbones", "Invertebrates - Animals without backbones", "Seagrasses and algae", "Unusual finds", "Marine Pests"
    };

    public static boolean IS_BEACHCOMBER = BuildConfig.APPLICATION_ID.equalsIgnoreCase("com.appiphany.beachcomber");
    public static boolean IS_CHAPMAN_RIVER = BuildConfig.APPLICATION_ID.equalsIgnoreCase("com.appiphany.chapmanriver");

    public static String PDF_FILE_NAME = "";
}
