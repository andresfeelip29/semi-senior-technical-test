package com.co.technicaltest.neoris.client.repositories;

import com.co.technicaltest.neoris.client.models.entity.ClientAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientAccountRepository extends JpaRepository<ClientAccount, Long> {

}
