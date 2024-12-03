package com.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.project.entities.Car;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT DISTINCT c.brand FROM Car c")
    List<String> findDistinctBrands();

    @Query("SELECT DISTINCT c.type FROM Car c")
    List<String> findDistinctTypes();

    @Query("SELECT DISTINCT c.color FROM Car c")
    List<String> findDistinctColors();

    @Query("SELECT DISTINCT c.transmission FROM Car c")
    List<String> findDistinctTransmissions();

    // Filtered by Brand
    @Query("SELECT DISTINCT c.type FROM Car c WHERE c.brand = :brand")
    List<String> findDistinctTypesByBrand(@Param("brand") String brand);

    @Query("SELECT DISTINCT c.color FROM Car c WHERE c.brand = :brand")
    List<String> findDistinctColorsByBrand(@Param("brand") String brand);

    @Query("SELECT DISTINCT c.transmission FROM Car c WHERE c.brand = :brand")
    List<String> findDistinctTransmissionsByBrand(@Param("brand") String brand);

    // Filtered by Type
    @Query("SELECT DISTINCT c.color FROM Car c WHERE c.type = :type")
    List<String> findDistinctColorsByType(@Param("type") String type);

    @Query("SELECT DISTINCT c.transmission FROM Car c WHERE c.type = :type")
    List<String> findDistinctTransmissionsByType(@Param("type") String type);

    // Filtered by Color
    @Query("SELECT DISTINCT c.transmission FROM Car c WHERE c.color = :color")
    List<String> findDistinctTransmissionsByColor(@Param("color") String color);

    // Combined Filters
    @Query("SELECT DISTINCT c.color FROM Car c WHERE c.brand = :brand AND c.type = :type")
    List<String> findDistinctColorsByBrandAndType(@Param("brand") String brand, @Param("type") String type);

    @Query("SELECT DISTINCT c.transmission FROM Car c WHERE c.brand = :brand AND c.type = :type")
    List<String> findDistinctTransmissionsByBrandAndType(@Param("brand") String brand, @Param("type") String type);

    @Query("SELECT DISTINCT c.transmission FROM Car c WHERE c.brand = :brand AND c.type = :type AND c.color = :color")
    List<String> findDistinctTransmissionsByBrandAndTypeAndColor(@Param("brand") String brand, @Param("type") String type, @Param("color") String color);

    @Query("SELECT DISTINCT c.transmission FROM Car c WHERE c.type = :type AND c.color = :color")
    List<String> findDistinctTransmissionsByTypeAndColor(@Param("type") String type, @Param("color") String color);
}
