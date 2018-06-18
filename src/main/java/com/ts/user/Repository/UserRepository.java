package com.ts.user.Repository;

import com.ts.user.Model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface UserRepository extends CrudRepository<User, Long>, PagingAndSortingRepository<User, Long> {

}
