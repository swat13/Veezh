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
    private String _body = null;
    private String _bodyColor = null;
    private String _fuel = null;
    private String _inColor = null;
    private String _bodyInColor = null;
    private String _plate = null;
    private String _status = null;
    private String _insurance = null;
    private String _desc = null;
    private String _phone = null;
    private String _title = null;
    private String _province = null;
    private String _category = null;
    private String _region = null;

    public void setName(String name) {
        _name = name;
    }

    public void setId(String id) {
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

    public String getBody() {
        return _body;
    }

    public void setBody(String body) {
        _body = body;
    }

    public String getBodyColor() {
        return _bodyColor;
    }

    public void setBodyColor(String bodyColor) {
        _bodyColor = bodyColor;
    }

    public String getFuel() {
        return _fuel;
    }

    public void setFuel(String fuel) {
        _fuel = fuel;
    }

    public String getInColor() {
        return _inColor;
    }

    public void setInColor(String inColor) {
        _inColor = inColor;
    }

    public String getBodyInColor() {
        return _bodyInColor;
    }

    public void setBodyInColor(String bodyInColor) {
        _bodyInColor = bodyInColor;
    }

    public String getPlate() {
        return _plate;
    }

    public void setPlate(String plate) {
        _plate = plate;
    }

    public String getStatus() {
        return _status;
    }

    public void setStatus(String status) {
        _status = status;
    }

    public String getInsurance() {
        return _insurance;
    }

    public void setInsurance(String insurance) {
        _insurance = insurance;
    }

    public String getDesc() {
        return _desc;
    }

    public void setDesc(String desc) {
        _desc = desc;
    }

    public String getPhone() {
        return _phone;
    }

    public void setPhone(String phone) {
        _phone = phone;
    }

    public String getCategory() {
        return _category;
    }

    public void setCategory(String category) {
        _category = category;
    }

    public String getProvince() {
        return _province;
    }

    public void setProvince(String province) {
        _province = province;
    }

    public String getRegion() {
        return _region;
    }

    public void setRegion(String region) {
        _region = region;
    }

    public String getTitle() {
        return _title;
    }

    public void setTitle(String title) {
        _title = title;
    }
}