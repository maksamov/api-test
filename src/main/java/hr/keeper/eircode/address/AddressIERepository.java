package hr.keeper.eircode.address;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressIERepository extends JpaRepository<AddressIE, String> {

	public Optional<AddressIE> findByEircode(String eircode);
	
}
