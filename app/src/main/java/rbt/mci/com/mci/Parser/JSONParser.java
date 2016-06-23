package rbt.mci.com.mci.Parser;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JSONParser {

    private static final String MAIN_URL = "http://www.veezh.com/mobile/";

    private static Response response;

    public static JSONObject getCarList() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(MAIN_URL)
                    .build();
            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (IOException | JSONException e) {
            Log.e("JSONParser", "" + e.getLocalizedMessage());
        }
        return null;
    }
}
