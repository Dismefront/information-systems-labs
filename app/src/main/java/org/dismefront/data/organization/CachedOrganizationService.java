package org.dismefront.data.organization;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CachedOrganizationService {
    
    private final OrganizationRepository organizationRepository;
    
    @Cacheable(value = "organizations", key = "#id")
    public Optional<Organization> findById(Long id) {
        return organizationRepository.findById(id);
    }
    
    @CacheEvict(value = "organizations", key = "#organization.id")
    public Organization save(Organization organization) {
        return organizationRepository.save(organization);
    }
    
    @CacheEvict(value = "organizations", key = "#id")
    public void deleteById(Long id) {
        organizationRepository.deleteById(id);
    }
}