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
            httpConn.setRequestMethod("POST");
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

    public RSSFeed getFuelType() {

        HttpURLConnection httpConn = null;
        try {
            RSSFeed _feed = new RSSFeed();
            URL url = new URL(mainUrl + "fuel.php");
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("POST");
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
                _items.setFuel(jsonObject0.getString("Fuel"));
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

    public RSSFeed getInColorType() {

        HttpURLConnection httpConn = null;
        try {
            RSSFeed _feed = new RSSFeed();
            URL url = new URL(mainUrl + "colored.php");
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("POST");
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
                _items.setInColor(jsonObject0.getString("Icolor"));
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

    public RSSFeed getBodyColorType() {

        HttpURLConnection httpConn = null;
        try {
            RSSFeed _feed = new RSSFeed();
            URL url = new URL(mainUrl + "bodycolor.php");
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("POST");
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
                _items.setBodyColor(jsonObject0.getString("Bcolor"));
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

    public RSSFeed getCarList(String brandID, String modelID, String leastPrice, String maxPrice, String leastManf, String maxManf, String province, String bodyColor, String inColor, String fuel, String status) {

        HttpURLConnection httpConn = null;
        try {
            RSSFeed _feed = new RSSFeed();
            URL url = new URL(mainUrl + "index.php?berand=" + brandID + "&model=" + modelID + "&price=" + leastPrice + "&price1=" + maxPrice + "&tsakht=" + leastManf + "&tsakht1=" + maxManf + "&ostan=" + province + "&body_color=" + bodyColor + "&in_color=" + inColor + "&fuel=" + fuel + "&status=" + status);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("POST");
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
            httpConn.setRequestMethod("POST");
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

    public RSSFeed getProductionYear(String id) {

        HttpURLConnection httpConn = null;
        try {
            RSSFeed _feed = new RSSFeed();
            URL url = new URL(mainUrl + "sakht.php?id=" + id);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("POST");
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
                _items.setManufactured(jsonObject0.getString("Date"));
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

    public RSSFeed getCarDetail(String id) {

        HttpURLConnection httpConn = null;
        try {
            RSSFeed _feed = new RSSFeed();
            URL url = new URL(mainUrl + "viewad.php?id=" + id);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("POST");
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
                _items.setBody(jsonObject0.getString("Body"));
                _items.setBodyColor(jsonObject0.getString("BodyColor"));
                _items.setFuel(jsonObject0.getString("Fuel"));
                _items.setGearbox(jsonObject0.getString("Gearbox"));
                _items.setInColor(jsonObject0.getString("InColor"));
                _items.setBodyInColor(jsonObject0.getString("BodyInColor"));
                _items.setPlate(jsonObject0.getString("Khas"));
                _items.setStatus(jsonObject0.getString("Status"));
                _items.setInsurance(jsonObject0.getString("Bime"));
                _items.setUsed(jsonObject0.getString("Karkard"));
                _items.setDesc(jsonObject0.getString("Tozih"));
                _items.setPhone(jsonObject0.getString("Phone"));
                _items.setId(jsonObject0.getString("Id"));
                _items.setThumb(jsonObject0.getString("Thumb"));
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
