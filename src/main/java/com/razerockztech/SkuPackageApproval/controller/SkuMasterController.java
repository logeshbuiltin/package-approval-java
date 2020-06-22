package com.razerockztech.SkuPackageApproval.controller;

import com.razerockztech.SkuPackageApproval.model.SkuItemModel;
import com.razerockztech.SkuPackageApproval.model.SkuMasterModel;
import com.razerockztech.SkuPackageApproval.repository.SkuMasterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class SkuMasterController {
    @Autowired
    private SkuMasterRepo skuMasterRepo;

    HashMap<String, String> returnMap = new HashMap<>();

    @GetMapping("/getallskuNo")
    public List<String> getSkuList() {
        List<String> skuNoList = skuMasterRepo.getAllSkuNo();
        return skuNoList;
    }

    @GetMapping("/getskuData/{skuNo}")
    public SkuMasterModel getSkudata(@PathVariable String skuNo) {
        SkuMasterModel skuMasterModel = skuMasterRepo.getOrderByObject(skuNo);
        return skuMasterModel;
    }

    @GetMapping("/getallskuDetails")
    public List<SkuMasterModel> getSkuDataList() {
        return skuMasterRepo.findAll();
    }

    @PostMapping("/postskumaster")
    public Map<String, String> addMasterBySku(@RequestBody List<SkuMasterModel> skuMasterList){
        skuMasterList.forEach(sku ->  {
            try{
                SkuMasterModel skuMasterModel = skuMasterRepo.getOrderByObject(sku.getSkuNo());
                if(skuMasterModel != null) {
                    sku.setId(skuMasterModel.getId());
                    skuMasterRepo.save(sku);
                } else {
                    skuMasterRepo.save(sku);
                }
                returnMap.put("status", "success");
            } catch (Exception e) {
                returnMap.put("status", "error");
            }
        });
        return returnMap;
    }

    @PutMapping("/updateskumaster/{skuNo}")
    public Map<String, String> updateMasterBySku(@PathVariable String skuNo, @RequestBody Map<String, String> skuMasterStatus){
        try {
            SkuMasterModel skuMasterModel = skuMasterRepo.getOrderByObject(skuNo);
            if(skuMasterModel != null) {
                skuMasterModel.setStatus("Completed");
                skuMasterRepo.save(skuMasterModel);
                returnMap.put("status", "success");
            }
        } catch (Exception e) {
            returnMap.put("status", "error");
        }
        return returnMap;
    }

    @DeleteMapping("/skumasterdelete/{skuNo}")
    public Map<String, String> deleteMasterById(@PathVariable String skuNo) {
        try {
            skuMasterRepo.deleteSkuNo(skuNo);
            returnMap.put("status", "success");
        }catch (Exception e){
            returnMap.put("status", "error");
        }
        return returnMap;
    }
}
