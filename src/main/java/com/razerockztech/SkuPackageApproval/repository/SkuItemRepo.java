package com.razerockztech.SkuPackageApproval.repository;

import com.razerockztech.SkuPackageApproval.model.SkuItemModel;
import com.razerockztech.SkuPackageApproval.model.SkuMasterModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkuItemRepo extends JpaRepository<SkuItemModel, String> {
    List<SkuItemModel> findAll();

    List<SkuItemModel> findAllById(String skuNo);

    @Query(
            "select DISTINCT t from SkuItemModel t  where t.itemSku=:itemSku")
    List<SkuItemModel> getOrderListByObject(@Param("itemSku") String itemSku);

    @Query(
            "select DISTINCT ctnNo from SkuItemModel t where t.itemSku=:itemSku")
    List<Integer> getAllCtnNo(@Param("itemSku") String itemSku);
}
