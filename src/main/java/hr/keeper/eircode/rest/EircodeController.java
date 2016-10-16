package hr.keeper.eircode.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import hr.keeper.eircode.address.Address;
import hr.keeper.eircode.address.AddressRepository;

@RestController
@RequestMapping("api/public/eircode")
public class EircodeController {

	@Autowired
	private AddressRepository addressRepository;

	private final String SEARCH_KEY = "PCW3Y-V28MF-PHNS7-FWCVH";
	private final String METHOD = "address";
	private final String SEARCH_TERM = "?format=json";

	@Value("${dev-profile.value}")
	private boolean isDevelopment;

	@RequestMapping(value = "/{eircode}", method = RequestMethod.GET)
	public ResponseEntity<Address> getAddressByEircode(@PathVariable String eircode) {

		Address address;
		
		Optional<Address> maybeAddress = addressRepository.findByEircode(eircode);

		if (maybeAddress.isPresent()) {
			address = maybeAddress.get();
		}
		else {
			address = queryAndSaveToDB(eircode);
		}
		
		return new ResponseEntity<Address>(address, HttpStatus.OK);
	}
	
	private Address queryAndSaveToDB(String eircode){
		
		Address address = queryPostcoderAPI(eircode);
		
		address.setEircode(eircode);
		address = addressRepository.save(address);
		
		return address;
	}

	private Address queryPostcoderAPI(String eircode) {
		
		String uri = createUri(eircode);

		RestTemplate restTemplate = new RestTemplate();
		Address[] addresses = restTemplate.getForObject(uri, Address[].class);

		return addresses[0];
	}

	private String createUri(String eircode) {

		if (isDevelopment)
			return String.format("http://localhost:8080/api/public/eircode/mock/%s", eircode);
		else
			return String.format("http://ws.postcoder.com/pcw/%s/%s/ie/%s%s", SEARCH_KEY, METHOD, eircode, SEARCH_TERM);

	}
	
}
