package pwr.bazydanych.silowniadb.uzytkownik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pwr.bazydanych.silowniadb.uzytkownik.model.Trener;

import java.util.Optional;

@Repository
public interface TrenerRepository extends JpaRepository<Trener, Long> {
}
