package com.co.technicaltest.neoris.client.repositories;

import com.co.technicaltest.neoris.client.models.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
