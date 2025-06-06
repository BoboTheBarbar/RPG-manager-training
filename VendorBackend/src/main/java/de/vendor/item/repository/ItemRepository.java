package de.vendor.item.repository;

import de.vendor.item.domain.DomainItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<DomainItem, Long> {
} 