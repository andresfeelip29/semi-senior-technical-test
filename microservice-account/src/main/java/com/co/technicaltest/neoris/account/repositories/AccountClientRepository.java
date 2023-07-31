package com.co.technicaltest.neoris.account.repositories;

import com.co.technicaltest.neoris.account.models.entity.AccountClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountClientRepository extends JpaRepository<AccountClient, Long> {
}
