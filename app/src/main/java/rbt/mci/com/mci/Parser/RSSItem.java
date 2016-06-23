package rbt.mci.com.mci.Parser;

import java.io.Serializable;

public class RSSItem implements Serializable {

    private String _name = null;
    private String _id = null;
    private String _thumb = null;
    private String _manufactured = null;
    private String _city = null;
    private String _used = null;
    private String _gearbox = null;
    private String _price = null;
    private String _image = null;

    void setName(String name) {
        _name = name;
    }

    void setId(String id) {
        _id = id;
    }

    public String getName() {
        return _name;
    }

    public String getId() {
        return _id;
    }

    public String getCity() {
        return _city;
    }

    public void setCity(String city) {
        _city = city;
    }

    public String getUsed() {
        return _used;
    }

    public void setUsed(String used) {
        _used = used;
    }

    public String getGearbox() {
        return _gearbox;
    }

    public void setGearbox(String gearbox) {
        _gearbox = gearbox;
    }

    public String getPrice() {
        return _price;
    }

    public void setPrice(String price) {
        _price = price;
    }

    public String getImage() {
        return _image;
    }

    public void setImage(String image) {
        _image = image;
    }

    public String getThumb() {
        return _thumb;
    }

    public void setThumb(String thumb) {
        _thumb = thumb;
    }

    public String getManufactured() {
        return _manufactured;
    }

    public void setManufactured(String manufactured) {
        _manufactured = manufactured;
    }
}
