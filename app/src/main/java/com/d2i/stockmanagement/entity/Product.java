package com.d2i.stockmanagement.entity;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("id")
    private String id;

    @SerializedName("sku")
    private String sku;

    @SerializedName("rfid")
    private String rfid;

    @SerializedName("name")
    private String name;

    @SerializedName("design_number")
    private String designNumber;

    @SerializedName("photo_product")
    private String photoProduct;

    @SerializedName("weight")
    private Double weight;

    @SerializedName("purity")
    private int purity;

    @SerializedName("content")
    private String content;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("type")
    private String type;

    @SerializedName("status_print")
    private boolean statusPrint;

    @SerializedName("condition")
    private String condition;

    @SerializedName("flag")
    private boolean flag;

    @SerializedName("status")
    private String status;

    @SerializedName("price")
    private Double price;

    @SerializedName("note")
    private String note;

    @SerializedName("diamond_weight1")
    private Float diamondWeight1;

    @SerializedName("diamond_quantity1")
    private int diamondQuantity1;

    @SerializedName("diamond_weight2")
    private Float diamondWeight2;

    @SerializedName("diamond_quantity2")
    private int diamondQuantity2;

    @SerializedName("diamond_weight3")
    private Float diamondWeight3;

    @SerializedName("diamond_quantity3")
    private int diamondQuantity3;

    @SerializedName("diamond_weight4")
    private Float diamondWeight4;

    @SerializedName("diamond_quantity4")
    private int diamondQuantity4;

    @SerializedName("diamond_weight5")
    private Float diamondWeight5;

    @SerializedName("diamond_quantity5")
    private int diamondQuantity5;

    @SerializedName("date_order")
    private String dateOrder;

    @SerializedName("branch_id")
    private long branchId;

    @SerializedName("store_id")
    private long storeId;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("branch")
    private Branch branch;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignNumber() {
        return designNumber;
    }

    public void setDesignNumber(String designNumber) {
        this.designNumber = designNumber;
    }

    public String getPhotoProduct() {
        return photoProduct;
    }

    public void setPhotoProduct(String photoProduct) {
        this.photoProduct = photoProduct;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public int getPurity() {
        return purity;
    }

    public void setPurity(int purity) {
        this.purity = purity;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isStatusPrint() {
        return statusPrint;
    }

    public void setStatusPrint(boolean statusPrint) {
        this.statusPrint = statusPrint;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Float getDiamondWeight1() {
        return diamondWeight1;
    }

    public void setDiamondWeight1(Float diamondWeight1) {
        this.diamondWeight1 = diamondWeight1;
    }

    public int getDiamondQuantity1() {
        return diamondQuantity1;
    }

    public void setDiamondQuantity1(int diamondQuantity1) {
        this.diamondQuantity1 = diamondQuantity1;
    }

    public Float getDiamondWeight2() {
        return diamondWeight2;
    }

    public void setDiamondWeight2(Float diamondWeight2) {
        this.diamondWeight2 = diamondWeight2;
    }

    public int getDiamondQuantity2() {
        return diamondQuantity2;
    }

    public void setDiamondQuantity2(int diamondQuantity2) {
        this.diamondQuantity2 = diamondQuantity2;
    }

    public Float getDiamondWeight3() {
        return diamondWeight3;
    }

    public void setDiamondWeight3(Float diamondWeight3) {
        this.diamondWeight3 = diamondWeight3;
    }

    public int getDiamondQuantity3() {
        return diamondQuantity3;
    }

    public void setDiamondQuantity3(int diamondQuantity3) {
        this.diamondQuantity3 = diamondQuantity3;
    }

    public Float getDiamondWeight4() {
        return diamondWeight4;
    }

    public void setDiamondWeight4(Float diamondWeight4) {
        this.diamondWeight4 = diamondWeight4;
    }

    public int getDiamondQuantity4() {
        return diamondQuantity4;
    }

    public void setDiamondQuantity4(int diamondQuantity4) {
        this.diamondQuantity4 = diamondQuantity4;
    }

    public Float getDiamondWeight5() {
        return diamondWeight5;
    }

    public void setDiamondWeight5(Float diamondWeight5) {
        this.diamondWeight5 = diamondWeight5;
    }

    public int getDiamondQuantity5() {
        return diamondQuantity5;
    }

    public void setDiamondQuantity5(int diamondQuantity5) {
        this.diamondQuantity5 = diamondQuantity5;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }

    public long getBranchId() {
        return branchId;
    }

    public void setBranchId(long branchId) {
        this.branchId = branchId;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
