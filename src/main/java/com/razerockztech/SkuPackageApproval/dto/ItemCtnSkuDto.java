package com.razerockztech.SkuPackageApproval.dto;

public class ItemCtnSkuDto {
    public String getSkuNo() {
        return skuNo;
    }

    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    public String getAsnNo() {
        return asnNo;
    }

    public void setAsnNo(String asnNo) {
        this.asnNo = asnNo;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(Double netWeight) {
        this.netWeight = netWeight;
    }

    public Double getTolerance() {
        return tolerance;
    }

    public void setTolerance(Double tolerance) {
        this.tolerance = tolerance;
    }

    public Integer getCtnCount() {
        return ctnCount;
    }

    public void setCtnCount(Integer ctnCount) {
        this.ctnCount = ctnCount;
    }

    public Integer getCtnNo() {
        return ctnNo;
    }

    public void setCtnNo(Integer ctnNo) {
        this.ctnNo = ctnNo;
    }

    private String skuNo;
    private String asnNo;
    private String itemDesc;
    private String color;
    private Integer ctnNo;
    private Double netWeight;
    private Double tolerance;
    private Integer ctnCount;
}
