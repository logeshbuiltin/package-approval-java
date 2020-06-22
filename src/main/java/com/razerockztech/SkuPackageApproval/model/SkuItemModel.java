package com.razerockztech.SkuPackageApproval.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "skuitem_master")
public class SkuItemModel {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sku_no", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private SkuMasterModel skuNo;

    @Column(name = "asn_no")
    private String asnNo;

    @Column(name = "item_desc")
    private String itemDesc;

    @Column(name = "color")
    private String color;

    @Column(name = "net_weight")
    private Double netWeight;

    @Column(name = "tolerance")
    private Double tolerance;

    @Column(name="entry_date")
    private Date entryDate = new Date();

    @Column(name = "ctn_no")
    private Integer ctnNo;

    @Column(name = "status")
    private String status;

    @Column(name = "item_sku")
    private String itemSku;

}
