package com.sunny.repository;

import com.sunny.model.Loans;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface LoanRepository extends CrudRepository<Loans, Long> {

	@PreAuthorize("hasRole('ROOT')")
	List<Loans> findByCustomerIdOrderByStartDtDesc(int customerId);

}
/*
@PreAuthorize("hasRole('ROOT')")   <-- any user has a Role='ROOT'  only that user can call this method



 */
