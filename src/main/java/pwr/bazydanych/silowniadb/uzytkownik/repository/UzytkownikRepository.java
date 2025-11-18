package pwr.bazydanych.silowniadb.uzytkownik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pwr.bazydanych.silowniadb.uzytkownik.model.Uzytkownik;

import java.util.Optional;

@Repository
public interface UzytkownikRepository extends JpaRepository<Uzytkownik, Long> {

    Optional<Uzytkownik> findByEmail(String email);
    boolean existsByEmail(String email);
}
