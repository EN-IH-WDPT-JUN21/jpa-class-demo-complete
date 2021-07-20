package com.ironhack.jpaclassdemo.repository;

import com.ironhack.jpaclassdemo.dao.Coffee;
import com.ironhack.jpaclassdemo.dao.Country;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CoffeeRepositoryTest {

    @Autowired
    CoffeeRepository coffeeRepository;

    @BeforeEach
    void setUp() {
        var arabica = new Coffee(null, "Arabica", BigDecimal.valueOf(13.3), Country.JAVA);
        coffeeRepository.save(arabica);
    }

    @AfterEach
    void tearDown() {
        coffeeRepository.deleteAll();
    }

    @Test
    public void countCoffee(){
        long coffeeCountBeforeSave = coffeeRepository.count();
        assertEquals(1, coffeeCountBeforeSave);
    }

    @Test
    public void createAndSaveCoffee(){
        long coffeeCountBeforeSave = coffeeRepository.count();
        var nescafe = new Coffee(null, "Nescafe Gold", BigDecimal.valueOf(5.50), Country.BRAZIL);
        coffeeRepository.save(nescafe);
        long coffeeCountAfterSave = coffeeRepository.count();
        assertEquals(++coffeeCountBeforeSave, coffeeCountAfterSave);
    }

    @Test
    public void findByNameTest(){
        var coffeeFound = coffeeRepository.findByName("Arabica");
        assertTrue(coffeeFound.isPresent());
    }

    @Test
    void updateCoffeeEntry() {
        var arabica = coffeeRepository.findByName("Arabica");
        var arabicaPrice = arabica.get().getPrice();
        arabica.get().setPrice(BigDecimal.valueOf(3));
        assertNotEquals(arabica.get().getPrice(), arabicaPrice);
    }

    @Test
    void deleteCoffee() {
        var nescafe = new Coffee(null, "Nescafe Gold", BigDecimal.valueOf(5.50), Country.BRAZIL);
        coffeeRepository.save(nescafe);
        long coffeeCountBeforeDelete = coffeeRepository.count();
        coffeeRepository.delete(nescafe);
        long coffeeCountAfterDelete = coffeeRepository.count();
        assertEquals(--coffeeCountBeforeDelete, coffeeCountAfterDelete);
    }

    @Test
    void findByNameLike() {
        var coffeeThatStartsWithAra = coffeeRepository.findByNameLike("Ara%");
        assertFalse(coffeeThatStartsWithAra.isEmpty());
    }

    @Test
    void findByPriceLessThan() {
        var coffeesLessThanPrice = coffeeRepository.findByPriceLessThan(BigDecimal.valueOf(20));
        assertEquals(1, coffeesLessThanPrice.size());
    }
}