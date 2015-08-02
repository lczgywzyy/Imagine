package u.can.i.up.ui.beans;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by Pengp on 2015/7/29.
 */
public class PearlBeans {

    private int SMaterialId;

    private int TMaterialId;

    private int MerchantCode;

    private int type;

    private int Weight;

    private float Size;

    private float Price;

    private float Aperture;

    private String Md5;

    private String PicDirectory;

    private String Material;

    private String Description;

    public int getSMaterialId() {
        return SMaterialId;
    }

    public void setSMaterialId(int SMaterialId) {
        this.SMaterialId = SMaterialId;
    }

    public int getTMaterialId() {
        return TMaterialId;
    }

    public void setTMaterialId(int TMaterialId) {
        this.TMaterialId = TMaterialId;
    }

    public int getMerchantCode() {
        return MerchantCode;
    }

    public void setMerchantCode(int merchantCode) {
        MerchantCode = merchantCode;
    }

    public int getWeight() {
        return Weight;
    }

    public void setWeight(int weight) {
        Weight = weight;
    }

    public float getSize() {
        return Size;
    }

    public void setSize(float size) {
        Size = size;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public float getAperture() {
        return Aperture;
    }

    public void setAperture(float aperture) {
        Aperture = aperture;
    }

    public String getMd5() {
        return Md5;
    }

    public void setMd5(String md5) {
        Md5 = md5;
    }

    public String getPicDirectory() {
        return PicDirectory;
    }

    public void setPicDirectory(String picDirectory) {
        PicDirectory = picDirectory;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
