package com.razerockztech.SkuPackageApproval.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "sku_master")
public class SkuMasterModel {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "sku_no")
    private String skuNo;

    @Column(name = "upc_no")
    private String upcNo;

    @Column(name = "asn_no")
    private String asnNo;

    @Column(name = "maxican_design_no")
    private String designNo;

    @Column(name = "item_desc")
    private String itemDesc;

    @Column(name = "size")
    private String size;

    @Column(name = "color")
    private String color;

    @Column(name = "net_weight_item")
    private Double netWeightItem;

    @Column(name = "gr_weight_item")
    private Double grWeightItem;

    @Column(name = "tolerance")
    private Double tolerance;

    @Column(name = "order_qty")
    private Long orderQty;

    @Column(name = "qty_per_ctn")
    private Long qtyPerCtn;

    @Column(name = "fold_size")
    private String foldSize;

    @Column(name = "ctn_length")
    private Double ctnLength;

    @Column(name = "ctn_width")
    private Double ctnWidth;

    @Column(name = "ctn_height")
    private double ctnHeight;

    @Column(name = "cbm_ctn")
    private Double cbmCtn;

    @Column(name = "net_weight_ctn")
    private Double netWeightCtn;

    @Column(name = "gr_weight_ctn")
    private Double grWeightCtn;

    @Column(name = "package_accessories")
    private String packAccess;

    @Column(name = "status")
    private String status;

    @Column(name = "buyer")
    private String buyer;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "picByte", length = 1000)
    private byte[] picByte;

}
