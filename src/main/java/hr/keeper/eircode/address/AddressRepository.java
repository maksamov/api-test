package hr.keeper.eircode.address;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, String> {

	public Optional<Address> findByEircode(String eircode);
	
}
