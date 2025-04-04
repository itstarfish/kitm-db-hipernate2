package com.kitm.kitm_db_hipernate.entity;

import jakarta.persistence.*;

@Entity
@Table(name="realestate")
public class RealEstate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="adresas")
    private String adress;

    @Column(name="plotas_kv_m")
    private Double areaSqrM;

    @Column(name="kaina_eur")
    private Double priceEur;

    @Column(name="ivertinimas")
    private Integer score;

    @Column(name="tipas")
    private String type;

    @Column(name="vip")
    private Boolean vip;

    public RealEstate(){

    }

    public RealEstate(String adress, Double areaSqrM, Double priceEur, Integer score, String type, Boolean vip) {
        this.adress = adress;
        this.areaSqrM = areaSqrM;
        this.priceEur = priceEur;
        this.score = score;
        this.type = type;
        this.vip = vip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Double getAreaSqrM() {
        return areaSqrM;
    }

    public void setAreaSqrM(Double areaSqrM) {
        this.areaSqrM = areaSqrM;
    }

    public Double getPriceEur() {
        return priceEur;
    }

    public void setPriceEur(Double priceEur) {
        this.priceEur = priceEur;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getVip() {
        return vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }

    @Override
    public String toString() {
        return String.format(
                "| %3d | %-25s | %10.2f m² | %10.2f € | Įvertinimas: %2d | %-10s | VIP: %-5s |",
                id, adress, areaSqrM, priceEur, score, type, vip ? "Taip" : "Ne"
        );
    }
}
