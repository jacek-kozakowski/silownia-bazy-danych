package pwr.bazydanych.silowniadb.uzytkownik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pwr.bazydanych.silowniadb.uzytkownik.model.PracownikRecepcji;

@Repository
public interface PracownikRecepcjiRepository extends JpaRepository<PracownikRecepcji, Long> {
}
