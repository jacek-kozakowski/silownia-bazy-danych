package pwr.bazydanych.silowniadb.uzytkownik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pwr.bazydanych.silowniadb.uzytkownik.model.Kierownik;

@Repository
public interface KierownikRepository extends JpaRepository<Kierownik, Long> {
}
