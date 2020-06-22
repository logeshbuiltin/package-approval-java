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
    private Double size;

    @Column(name = "gsm")
    private String gsm;

    @Column(name = "gsf")
    private String gsf;

    @Column(name = "item_type")
    private String itemType;

    @Column(name = "color")
    private String color;

    @Column(name = "net_weight")
    private Double netWeight;

    @Column(name = "tolerance")
    private Double tolerance;

    @Column(name = "gr_weight")
    private String grWeight;

    @Column(name = "usa")
    private String usa;

    @Column(name = "europe")
    private String europe;

    @Column(name = "ip")
    private String ip;

    @Column(name = "mp")
    private String mp;

    @Column(name = "package_accessories")
    private String packAccess;

    @Column(name = "status")
    private String status;
}
