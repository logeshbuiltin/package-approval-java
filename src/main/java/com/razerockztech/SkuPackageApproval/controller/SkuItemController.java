package com.razerockztech.SkuPackageApproval.controller;

import com.razerockztech.SkuPackageApproval.exception.ResourceNotFoundException;
import com.razerockztech.SkuPackageApproval.model.SkuItemModel;
import com.razerockztech.SkuPackageApproval.model.SkuMasterModel;
import com.razerockztech.SkuPackageApproval.repository.SkuItemRepo;
import com.razerockztech.SkuPackageApproval.repository.SkuMasterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@CrossOrigin(origins = "https://package-approval.web.app", maxAge = 3600)
@RestController
public class SkuItemController {

    @Autowired
    private SkuItemRepo skuItemRepo;

    @Autowired
    private SkuMasterRepo skuMasterRepo;


    @GetMapping("/getitem/{itemSku}")
    public List<SkuItemModel> getItemBySku(@PathVariable String itemSku) {
        return skuItemRepo.getOrderListByObject(itemSku);
    }

    @GetMapping("/getctnno/{itemSku}")
    public List<Integer> getCtnNoBySku(@PathVariable String itemSku) {
        return skuItemRepo.getAllCtnNo(itemSku);
    }

    @PostMapping("/postitem/{skuNo}")
    public List<SkuItemModel> addItemBySku(@PathVariable String skuNo, @RequestBody List<SkuItemModel> skuItemModelList){
        SkuMasterModel skuMasterModel = skuMasterRepo.getOrderByObject(skuNo);
        skuItemModelList.forEach(skuItem -> {
            skuItem.setSkuNo(skuMasterModel);
        });
        return skuItemRepo.saveAll(skuItemModelList);
        /*return skuMasterRepo.findById(skuNo).map(master -> {
            skuItemModelList.forEach(skuItem -> {
                skuItem.setSkuNo(master);
            });
            return skuItemRepo.saveAll(skuItemModelList);
        }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + skuNo));*/
    }

}
