package u.can.i.up.ui.beans;

/**
 * Created by Pengp on 2015/7/29.
 */
public class Pearl {

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
}
