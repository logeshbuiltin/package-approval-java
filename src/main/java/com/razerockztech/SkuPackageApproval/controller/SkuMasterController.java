package com.razerockztech.SkuPackageApproval.controller;

import com.razerockztech.SkuPackageApproval.dto.ImageModelDto;
import com.razerockztech.SkuPackageApproval.model.SkuMasterModel;
import com.razerockztech.SkuPackageApproval.repository.SkuMasterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
//@CrossOrigin(origins = "https://package-approval.web.app", maxAge = 3600)
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

    @GetMapping("/getPagedskuNo/{pageNo}/{pageSize}")
    public List<SkuMasterModel> getSkuPagedList(@PathVariable int pageNo, @PathVariable int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<SkuMasterModel> pagedResult =  skuMasterRepo.findAll(paging);
        return pagedResult.toList();
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

    @PostMapping("/uploadimage/{skuNo}")
    public Map<String, String> uplaodImage(@PathVariable String skuNo, @RequestParam("imageFile") MultipartFile file) throws IOException {
        SkuMasterModel skuMasterModel = skuMasterRepo.getOrderByObject(skuNo);
        if(skuMasterModel != null) {
            skuMasterModel.setName(file.getOriginalFilename());
            skuMasterModel.setType(file.getContentType());
            skuMasterModel.setPicByte(compressBytes(file.getBytes()));
            try {
                skuMasterRepo.save(skuMasterModel);
                returnMap.put("status", "success");
            } catch (Exception e) {
                returnMap.put("status", "error");
            }
        } else {
            returnMap.put("status", "error");
        }
        return returnMap;
    }

    @GetMapping("/downloadimage/{skuNo}")
    public ImageModelDto getImage(@PathVariable String skuNo) {
        ImageModelDto imageModelDto = new ImageModelDto();
        SkuMasterModel skuMasterModel = skuMasterRepo.getOrderByObject(skuNo);
        imageModelDto.setName(skuMasterModel.getName());
        imageModelDto.setType(skuMasterModel.getType());
        if(skuMasterModel.getName() != null) {
            imageModelDto.setPicByte(decompressBytes(skuMasterModel.getPicByte()));
        }
        return imageModelDto;
    }

    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }
}
