package hr.keeper.ukcode.address;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressUKRepository extends JpaRepository<AddressUK, Long> {

	List<AddressUK> findByPostcode(String postcode);
	
}
