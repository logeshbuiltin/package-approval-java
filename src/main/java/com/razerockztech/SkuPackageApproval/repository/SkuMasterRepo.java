package com.razerockztech.SkuPackageApproval.repository;

import com.razerockztech.SkuPackageApproval.model.SkuMasterModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface SkuMasterRepo extends JpaRepository<SkuMasterModel, String> {
    List<SkuMasterModel> findAll();

    @Query(
            "select DISTINCT t from SkuMasterModel t  where t.skuNo=:skuNo")
    SkuMasterModel getOrderByObject(@Param("skuNo") String skuNo);

    @Query(
            "select DISTINCT skuNo from SkuMasterModel t")
    List<String> getAllSkuNo();

    @Query(
            "select DISTINCT buyer from SkuMasterModel t")
    List<String> getBuyerList();

    @Transactional
    @Modifying
    @Query(
            "delete from SkuMasterModel t where t.skuNo=:skuNo")
    void deleteSkuNo(@Param("skuNo") String skuNo);
}
