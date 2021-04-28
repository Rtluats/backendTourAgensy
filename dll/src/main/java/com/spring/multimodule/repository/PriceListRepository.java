package com.spring.multimodule.repository;

import com.spring.multimodule.entity.PriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Long> {
	List<PriceList> getPriceListsByHotelCityName(String name);
}
