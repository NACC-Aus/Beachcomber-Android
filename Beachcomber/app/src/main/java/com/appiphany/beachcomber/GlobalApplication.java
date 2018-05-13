package com.appiphany.beachcomber;

import android.app.Application;

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
                .schemaVersion(1)
                .migration(new RealmMigration() {
                    @Override
                    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
                        if (oldVersion <= 1) {
                            RealmSchema schema = realm.getSchema();
                            RealmObjectSchema tocSchema = schema.get("TOC");
                            if (tocSchema.hasField("pageName")) {
                                tocSchema.setNullable("pageName", true);
                            } else {
                                tocSchema.addField("pageName", String.class);
                            }

                            if (tocSchema.hasField("thumb")) {
                                tocSchema.setNullable("thumb", true);
                            } else {
                                tocSchema.addField("thumb", String.class);
                            }

                            if (tocSchema.hasField("type")) {
                                tocSchema.setNullable("type", true);
                            } else {
                                tocSchema.addField("type", String.class);
                            }

                            if (tocSchema.hasField("growth")) {
                                tocSchema.setNullable("growth", true);
                            } else {
                                tocSchema.addField("growth", String.class);
                            }

                            if (tocSchema.hasField("aboriginal")) {
                                tocSchema.setNullable("aboriginal", true);
                            } else {
                                tocSchema.addField("aboriginal", String.class);
                            }

                            if (tocSchema.hasField("location")) {
                                tocSchema.setNullable("location", true);
                            } else {
                                tocSchema.addField("location", String.class);
                            }

                            if (tocSchema.hasField("colour")) {
                                tocSchema.setNullable("colour", true);
                            } else {
                                tocSchema.addField("colour", String.class);
                            }

                            if (tocSchema.hasField("header")) {
                                tocSchema.setNullable("header", true);
                            } else {
                                tocSchema.addField("header", String.class);
                            }
                        }
                    }
                })
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);
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
