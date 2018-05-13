package com.appiphany.beachcomber;

import android.app.Application;
import android.support.annotation.NonNull;

import com.appiphany.beachcomber.util.Config;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;

import io.fabric.sdk.android.Fabric;
import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

@SuppressWarnings("ConstantConditions")
public class GlobalApplication extends Application {
    private static GlobalApplication instance;
    private RealmConfiguration realmConfiguration;

    @Override
    public void onCreate() {
        super.onCreate();
        Crashlytics crashlytics = new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build();
        Fabric.with(this, crashlytics);
        instance = this;
        initFile();

        Realm.init(this);
        realmConfiguration = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .assetFile(Config.REALM_FILE)
                .schemaVersion(2)
                .migration(new RealmMigration() {
                    @Override
                    public void migrate(@NonNull DynamicRealm realm, long oldVersion, long newVersion) {
                        if (oldVersion < 1) {
                            migrateFrom0(realm.getSchema());
                            migrateFrom1(realm.getSchema());
                        } else if (oldVersion < 2) {
                            migrateFrom1(realm.getSchema());
                        }
                    }
                })
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);
    }

    private void migrateFrom1(RealmSchema schema) {
        RealmObjectSchema tocSchema = schema.get("TOC");
        String[] fields = new String[] { "growthForm", "floweringTime", "flowerColour", "family"};
        for (String field: fields) {
            addStringNullableField(tocSchema, field);
        }
    }
    private void migrateFrom0(RealmSchema schema) {
        RealmObjectSchema tocSchema = schema.get("TOC");
        String[] fields = new String[] { "pageName", "thumb", "type", "growth"
                , "aboriginal", "location", "colour", "header"};
        for (String field: fields) {
            addStringNullableField(tocSchema, field);
        }
    }

    private void addStringNullableField(RealmObjectSchema schema, String name){
        if (schema.hasField(name)) {
            if(!schema.isNullable(name)) {
                schema.setNullable(name, true);
            }
        } else {
            schema.addField(name, String.class);
        }
    }
    private void initFile() {
        if (Config.IS_BEACHCOMBER) {
            boolean tabletSize = getResources().getBoolean(R.bool.is_tablet);
            Config.PDF_FILE_NAME = tabletSize ? "Guide Tablet.pdf" : "Guide Phone.pdf";
        } else if (Config.IS_CHAPMAN_RIVER) {
            boolean tabletSize = getResources().getBoolean(R.bool.is_tablet);
            Config.PDF_FILE_NAME = tabletSize ? "CRFtablet.pdf" : "CRFmobile.pdf";
        } else {
            Config.PDF_FILE_NAME = "Guide.pdf";
        }
    }

    public static GlobalApplication getInstance() {
        return instance;
    }

    public RealmConfiguration getRealmConfiguration() {
        return realmConfiguration;
    }
}
