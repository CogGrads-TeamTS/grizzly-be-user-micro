package com.ts.user.Repository;

import com.ts.user.Model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, Long>, JpaRepository<ApplicationUser, Long>, PagingAndSortingRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);
}
