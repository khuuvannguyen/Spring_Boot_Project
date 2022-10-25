package com.vannguyen.SpringBootProject.domain.repositories;

import com.vannguyen.SpringBootProject.domain.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {
    List<OrderDetail> findByOrderId(UUID id);

    void deleteByOrderId(UUID id);

//    @Query
//    OrderDetail findByOrderIdAndDetailId(UUID orderId, UUID detailId);
}
