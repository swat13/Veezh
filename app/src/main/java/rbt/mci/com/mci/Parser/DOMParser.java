package rbt.mci.com.mci.Parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DOMParser {

    private String mainUrl = "http://veezh.com/mobile/";

    public RSSFeed getAllBrands() {

        HttpURLConnection httpConn = null;
        try {
            RSSFeed _feed = new RSSFeed();
            URL url = new URL(mainUrl + "berand.php");
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.setConnectTimeout(10000);
            httpConn.setReadTimeout(10000);
            httpConn.connect();
            int resCode = httpConn.getResponseCode();

            if (resCode != HttpURLConnection.HTTP_OK) {
                return null;
            }
            InputStream in = httpConn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();

            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("posts");
            for (int i = 0; i < jsonArray.length(); i++) {
                RSSItem _items = new RSSItem();
                JSONObject jsonObject0 = jsonArray.getJSONObject(i);
                _items.setName(jsonObject0.getString("Berand"));
                _items.setId(jsonObject0.getString("Id"));
                _feed.addItem(_items);
            }

            return _feed;

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }

        return null;
    }

    public RSSFeed getCarList(String brandID, String modelID, String leastPrice, String maxPrice, String leastManf, String maxManf, String province) {

        HttpURLConnection httpConn = null;
        try {
            RSSFeed _feed = new RSSFeed();
            URL url = new URL(mainUrl + "index.php?berand=" + brandID + "&model=" + modelID + "&price=" + leastPrice + "&price1=" + maxPrice + "&tsakht=" + leastManf + "&tsakht1=" + maxManf + "&ostan=" + province);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.setConnectTimeout(10000);
            httpConn.setReadTimeout(10000);
            httpConn.connect();
            int resCode = httpConn.getResponseCode();

            if (resCode != HttpURLConnection.HTTP_OK) {
                return null;
            }
            InputStream in = httpConn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();

            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("posts");
            for (int i = 0; i < jsonArray.length(); i++) {
                RSSItem _items = new RSSItem();
                JSONObject jsonObject0 = jsonArray.getJSONObject(i);
                _items.setName(jsonObject0.getString("Berand") + " " + jsonObject0.getString("Model"));
                _items.setCity(jsonObject0.getString("Ostan"));
                _items.setPrice(jsonObject0.getString("Price"));
                _items.setManufactured(jsonObject0.getString("Tsakht"));
                _items.setUsed(jsonObject0.getString("Karkard"));
                _items.setGearbox(jsonObject0.getString("Gearbox"));
                _items.setId(jsonObject0.getString("Id"));
                _items.setThumb(jsonObject0.getString("Thumb"));
                _feed.addItem(_items);
            }

            return _feed;

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }

        return null;
    }

    public RSSFeed getAllModels(String id) {

        HttpURLConnection httpConn = null;
        try {
            RSSFeed _feed = new RSSFeed();
            URL url = new URL(mainUrl + "model.php?id=" + id);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.setConnectTimeout(10000);
            httpConn.setReadTimeout(10000);
            httpConn.connect();
            int resCode = httpConn.getResponseCode();

            if (resCode != HttpURLConnection.HTTP_OK) {
                return null;
            }
            InputStream in = httpConn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();

            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("posts");
            for (int i = 0; i < jsonArray.length(); i++) {
                RSSItem _items = new RSSItem();
                JSONObject jsonObject0 = jsonArray.getJSONObject(i);
                _items.setName(jsonObject0.getString("Model"));
                _items.setId(jsonObject0.getString("Id"));
                _feed.addItem(_items);
            }

            return _feed;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }

        return null;
    }
}