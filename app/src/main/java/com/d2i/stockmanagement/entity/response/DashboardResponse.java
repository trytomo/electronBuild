package com.d2i.stockmanagement.entity.response;

import com.d2i.stockmanagement.entity.Product;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DashboardResponse {
    @SerializedName("product_terjual")
    private int produkTerjual;

    @SerializedName("produk_hilang")
    private int produkHilang;

    @SerializedName("berlian_terjual")
    private int berlianTerjual;

    @SerializedName("emas_terjual")
    private int emasTerjual;

    @SerializedName("produk_terjual_terbaru")
    private ArrayList<Product> produkTerjualTerbaru;

    public int getProdukTerjual() {
        return produkTerjual;
    }

    public void setProdukTerjual(int produkTerjual) {
        this.produkTerjual = produkTerjual;
    }

    public int getProdukHilang() {
        return produkHilang;
    }

    public void setProdukHilang(int produkHilang) {
        this.produkHilang = produkHilang;
    }

    public int getBerlianTerjual() {
        return berlianTerjual;
    }

    public void setBerlianTerjual(int berlianTerjual) {
        this.berlianTerjual = berlianTerjual;
    }

    public int getEmasTerjual() {
        return emasTerjual;
    }

    public void setEmasTerjual(int emasTerjual) {
        this.emasTerjual = emasTerjual;
    }

    public ArrayList<Product> getProdukTerjualTerbaru() {
        return produkTerjualTerbaru;
    }

    public void setProdukTerjualTerbaru(ArrayList<Product> produkTerjualTerbaru) {
        this.produkTerjualTerbaru = produkTerjualTerbaru;
    }
}
