package com.razerockztech.SkuPackageApproval.repository;

import com.razerockztech.SkuPackageApproval.dto.ItemCtnDto;
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
            "select DISTINCT itemSku, asnNo, itemDesc, color, ctnNo, sum(netWeight), sum(tolerance), COUNT(id) from SkuItemModel t where t.itemSku=:itemSku and t.status!='shipped'" +
                    "group by t.itemSku, t.asnNo, t.itemDesc, t.color, t.ctnNo")
    List<Object[]> getCtnNoByGroup(@Param("itemSku") String itemSku);

    @Query(
            "select DISTINCT itemSku, asnNo, itemDesc, color, ctnNo, sum(netWeight), sum(tolerance), COUNT(id) from SkuItemModel t where t.status!='shipped'" +
                    "group by t.itemSku, t.asnNo, t.itemDesc, t.color, t.ctnNo")
    List<Object[]> getAllCtnNoByGroup(@Param("itemSku") String itemSku);

    @Query(
            "select DISTINCT t from SkuItemModel t where t.itemSku=:itemSku and t.ctnNo=:ctnNo")
    List<SkuItemModel> getItemBySkuCtn(@Param("itemSku") String itemSku, @Param("ctnNo") Integer ctnNo);

    @Query(
            "select DISTINCT ctnNo from SkuItemModel t where t.itemSku=:itemSku")
    List<Integer> getAllCtnNoSku(@Param("itemSku") String itemSku);

    @Query(
            "select DISTINCT itemSku, ctnNo, sum(netWeight), sum(tolerance) from SkuItemModel t where t.status='shipped' group by t.itemSku, t.ctnNo")
    List<Object[]> getAllCtnNo();
}
