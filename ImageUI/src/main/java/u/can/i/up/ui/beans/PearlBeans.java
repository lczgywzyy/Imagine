package u.can.i.up.ui.beans;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * Created by Pengp on 2015/7/29.
 */
public class PearlBeans implements Parcelable {

    private int SMaterialId;

    private int category;

    private int MerchantCode;

    private int type;

    private String weight;

    private String name;

    private String size;

    public String gettMaterialName() {
        return tMaterialName;
    }

    public void settMaterialName(String tMaterialName) {
        this.tMaterialName = tMaterialName;
    }

    private String tMaterialName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String price;

    private String aperture;

    private String MD5;

    private String path;

    private String material;

    private String description;

    private boolean isSynchronized;

    public boolean isSynchronized() {
        return isSynchronized;
    }

    public void setIsSynchronized(boolean isSynchornized) {
        this.isSynchronized = isSynchornized;
    }

    public int getSMaterialId() {
        return SMaterialId;
    }

    public void setSMaterialId(int SMaterialId) {
        this.SMaterialId = SMaterialId;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getMerchantCode() {
        return MerchantCode;
    }

    public void setMerchantCode(int merchantCode) {
        MerchantCode = merchantCode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }



    public String getMD5() {
        return MD5;
    }

    public void setMD5(String MD5) {
        this.MD5 = MD5;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAperture() {
        return aperture;
    }

    public void setAperture(String aperture) {
        this.aperture = aperture;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(weight);
        dest.writeString(name);
        dest.writeString(size);
        dest.writeString(tMaterialName);
        dest.writeString(price);
        dest.writeString(aperture);
        dest.writeString(MD5);
        dest.writeString(path);
        dest.writeString(description);
        dest.writeInt(SMaterialId);
        dest.writeInt(category);
        dest.writeString(material);
        dest.writeInt(MerchantCode);
        dest.writeInt(type);
    }
    @Override
    public boolean equals(Object obj) {

        if(obj instanceof PearlBeans) {
            if (TextUtils.isEmpty(((PearlBeans)obj).getMD5())) {
                return false;
            } else {
                return ((PearlBeans)obj).getMD5().equals(this.getMD5());
            }

        }else{
            return false;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<PearlBeans> CREATOR=new Creator<PearlBeans>() {
        @Override
        public PearlBeans createFromParcel(Parcel source) {

            PearlBeans pearlBeans=new PearlBeans();
            pearlBeans.setWeight(source.readString());
            pearlBeans.setName(source.readString());
            pearlBeans.setSize(source.readString());
            pearlBeans.settMaterialName(source.readString());
            pearlBeans.setPrice(source.readString());
            pearlBeans.setAperture(source.readString());
            pearlBeans.setMD5(source.readString());
            pearlBeans.setPath(source.readString());
            pearlBeans.setDescription(source.readString());
            pearlBeans.setSMaterialId(source.readInt());
            pearlBeans.setCategory(source.readInt());
            pearlBeans.setMaterial(source.readString());
            pearlBeans.setMerchantCode(source.readInt());

            pearlBeans.setType(source.readInt());

            return pearlBeans;
        }

        @Override
        public PearlBeans[] newArray(int size) {
            return new PearlBeans[size];
        }
    };

}
