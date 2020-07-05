package com.razerockztech.SkuPackageApproval.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.razerockztech.SkuPackageApproval.dto.ItemCtnDto;
import com.razerockztech.SkuPackageApproval.dto.ItemCtnSkuDto;
import com.razerockztech.SkuPackageApproval.exception.ResourceNotFoundException;
import com.razerockztech.SkuPackageApproval.model.SkuItemModel;
import com.razerockztech.SkuPackageApproval.model.SkuMasterModel;
import com.razerockztech.SkuPackageApproval.repository.SkuItemRepo;
import com.razerockztech.SkuPackageApproval.repository.SkuMasterRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.*;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@CrossOrigin(origins = "https://package-approval.web.app", maxAge = 3600)
@RestController
public class SkuItemController {

    @Autowired
    private SkuItemRepo skuItemRepo;

    @Autowired
    private SkuMasterRepo skuMasterRepo;

    HashMap<String, String> returnMap = new HashMap<>();


    @GetMapping("/getitem/{itemSku}")
    public List<SkuItemModel> getItemBySku(@PathVariable String itemSku) {
        return skuItemRepo.getOrderListByObject(itemSku);
    }

    @GetMapping("/getallbuyer")
    public List<String> getBuyerList() {
        return skuItemRepo.getBuyerList();
    }

    @GetMapping("/getitemgroup/{itemSku}")
    public List<ItemCtnSkuDto> getItemGroupBySku(@PathVariable String itemSku) {
        List<Object[]> itemObjectList = new ArrayList<>();
        if(!itemSku.equals("null")) {
            itemObjectList = skuItemRepo.getCtnNoByGroup(itemSku);
        } else {
            itemObjectList = skuItemRepo.getAllCtnNoByGroup(itemSku);
        }
        List<ItemCtnSkuDto> itemCtnSkuDtos = new ArrayList<>();
        for(Object[] sku: itemObjectList) {
            ItemCtnSkuDto itemCtnSkuDto = new ItemCtnSkuDto();
            itemCtnSkuDto.setSkuNo(sku[0].toString());
            itemCtnSkuDto.setAsnNo(sku[1].toString());
            itemCtnSkuDto.setItemDesc(sku[2].toString());
            itemCtnSkuDto.setColor(sku[3].toString());
            itemCtnSkuDto.setCtnNo(Integer.parseInt(sku[4].toString()));
            itemCtnSkuDto.setNetWeight(Double.parseDouble(sku[5].toString()));
            itemCtnSkuDto.setTolerance(Double.parseDouble(sku[6].toString()));
            itemCtnSkuDto.setCtnCount(Integer.parseInt(sku[7].toString()));
            itemCtnSkuDtos.add(itemCtnSkuDto);
        }
        return itemCtnSkuDtos;
    }

    @GetMapping("/getitembuyer/{buyer}")
    public List<ItemCtnSkuDto> getItemGroupByBuyer(@PathVariable String buyer) {
        List<Object[]> itemObjectList = new ArrayList<>();
        if(!buyer.equals("null")) {
            itemObjectList = skuItemRepo.getCtnNoByBuyer(buyer);
        } else {
            itemObjectList = skuItemRepo.getCtnNoByBuyer(buyer);
        }
        List<ItemCtnSkuDto> itemCtnSkuDtos = new ArrayList<>();
        for(Object[] sku: itemObjectList) {
            ItemCtnSkuDto itemCtnSkuDto = new ItemCtnSkuDto();
            itemCtnSkuDto.setSkuNo(sku[0].toString());
            itemCtnSkuDto.setAsnNo(sku[1].toString());
            itemCtnSkuDto.setItemDesc(sku[2].toString());
            itemCtnSkuDto.setColor(sku[3].toString());
            itemCtnSkuDto.setCtnNo(Integer.parseInt(sku[4].toString()));
            itemCtnSkuDto.setNetWeight(Double.parseDouble(sku[5].toString()));
            itemCtnSkuDto.setTolerance(Double.parseDouble(sku[6].toString()));
            itemCtnSkuDto.setCtnCount(Integer.parseInt(sku[7].toString()));
            itemCtnSkuDtos.add(itemCtnSkuDto);
        }
        return itemCtnSkuDtos;
    }

    @GetMapping("/getctnno/{itemSku}")
    public List<Integer> getCtnNoBySku(@PathVariable String itemSku) {
        return skuItemRepo.getAllCtnNoSku(itemSku);
    }

    @GetMapping("/getallctn")
    public List<ItemCtnDto> getAllCtn() {
        List<Object[]> itemObjectList = skuItemRepo.getAllCtnNo();
        List<ItemCtnDto> itemCtnDtos = new ArrayList<>();
        for(Object[] sku: itemObjectList) {
            ItemCtnDto itemCtnDto = new ItemCtnDto();
            itemCtnDto.setSkuNo(sku[0].toString());
            itemCtnDto.setCtnNo(sku[1].toString());
            itemCtnDto.setNetWeight(Double.parseDouble(sku[2].toString()));
            itemCtnDto.setTolerance(Double.parseDouble(sku[3].toString()));
            itemCtnDtos.add(itemCtnDto);
        }
        //ModelMapper modelMapper = new ModelMapper();
        //itemObjectList.forEach(sku -> {
        //    itemCtnDtos.add(modelMapper.map(sku, ItemCtnDto.class));
        //});
        return itemCtnDtos;
    }

    @PostMapping("/postitem/{skuNo}")
    public List<SkuItemModel> addItemBySku(@PathVariable String skuNo, @RequestBody List<SkuItemModel> skuItemModelList){
        SkuMasterModel skuMasterModel = skuMasterRepo.getOrderByObject(skuNo);
        skuItemModelList.forEach(skuItem -> {
            skuItem.setSkuNo(skuMasterModel);
        });
        return skuItemRepo.saveAll(skuItemModelList);
    }

    @PutMapping("/putitemsku/{buyer}")
    public Map<String, String> updateShippedSku(@PathVariable String buyer, @RequestBody List<ItemCtnSkuDto> itemCtnSkuDtoList) {
        try{
            for(ItemCtnSkuDto item: itemCtnSkuDtoList) {
                List<SkuItemModel> skuItemModels = skuItemRepo.getItemBySkuCtn(item.getSkuNo(), item.getCtnNo());
                for(SkuItemModel skuItemModel: skuItemModels) {
                    skuItemModel.setStatus("shipped");
                    skuItemModel.setShipDate(new Date());
                    skuItemRepo.save(skuItemModel);
                }
            }
            returnMap.put("status", "success");
        } catch (Exception e) {
            returnMap.put("status", "error");
        }
        return returnMap;
    }

}
