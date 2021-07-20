package com.ironhack.jpaclassdemo.repository;

import com.ironhack.jpaclassdemo.dao.Coffee;
import com.ironhack.jpaclassdemo.dao.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
    Optional<Coffee> findByName(String name);
    List<Coffee> findByOrigin(Country countryOfOrigin);
    List<Coffee> findByNameLike(String name);
    List<Coffee> findByPriceLessThan(BigDecimal price);
}
