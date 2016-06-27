package rbt.mci.com.mci;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;

import rbt.mci.com.mci.Utilities.FontsOverride;

public final class Application extends android.app.Application {

    private static Application mInstance;
    public static Typeface myFont;
    public static LayoutInflater inflater;
    public static Context context;
    public static AssetManager assets;
    public static ConnectivityManager connectivityManager;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        assets = getAssets();
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        myFont = Typeface.createFromAsset(assets, "IRANSansMobile(FaNum).ttf");
        FontsOverride.setDefaultFont(context, "MONOSPACE", "IRANSansMobile(FaNum).ttf");
        mInstance = this;
    }

    public static synchronized Application getInstance() {
        return mInstance;
    }
}