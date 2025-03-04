package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<SaleReportDTO> getReport(String name, String minDate, String maxDate) {
		Pair<LocalDate, LocalDate> dates = instantiateLocalData(minDate, maxDate);
		return repository.searchReport(name, dates.getFirst(), dates.getSecond());
	}

	public List<SaleSummaryDTO> getSummary(String minDate, String maxDate) {
		Pair<LocalDate, LocalDate> dates = instantiateLocalData(minDate, maxDate);
		return repository.searchSummary(dates.getFirst(), dates.getSecond());
	}

	private Pair<LocalDate, LocalDate> instantiateLocalData(String min, String max) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate minDate = (!min.isEmpty()) ? minDate = LocalDate.parse(min) : today.minusYears(1L);
		LocalDate maxDate = (!max.isEmpty()) ? maxDate = LocalDate.parse(max) : today;
		return Pair.of(minDate, maxDate);
	}
}
