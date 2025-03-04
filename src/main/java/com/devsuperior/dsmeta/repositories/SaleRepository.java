package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;


public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleReportDTO(obj.id, obj.date, obj.amount, obj.seller.name) " +
            "FROM Sale obj " +
            "JOIN obj.seller " +
            "WHERE UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%')) AND obj.date BETWEEN :minDate AND :maxDate")
    List<SaleReportDTO> searchReport(String name, LocalDate minDate, LocalDate maxDate);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(obj.name, SUM(s.amount)) " +
            "FROM Seller obj " +
            "JOIN obj.sales s " +
            "WHERE s.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY obj.id, obj.name")
    List<SaleSummaryDTO> searchSummary(LocalDate minDate, LocalDate maxDate);
}
