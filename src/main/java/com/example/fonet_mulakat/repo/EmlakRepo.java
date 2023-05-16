package com.example.fonet_mulakat.repo;

import com.example.fonet_mulakat.model.Emlak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmlakRepo extends JpaRepository<Emlak,Long> {
    @Query(value = "SELECT * FROM emlak WHERE isinma = ?1 AND kiralik = ?2 AND tur = ?3", nativeQuery = true)
    List<Emlak> filtreleEmlak(String isinma, String kiralik , String tur);
}
