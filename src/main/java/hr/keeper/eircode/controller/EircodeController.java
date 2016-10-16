package hr.keeper.eircode.controller;

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

import hr.keeper.eircode.address.AddressIE;
import hr.keeper.eircode.address.AddressIERepository;

@RestController
@RequestMapping("api/public/eircode")
public class EircodeController {

	@Autowired
	private AddressIERepository addressRepository;

	@Value("${postcoder.search_key}")
	private String SEARCH_KEY;
	
	@Value("${postcoder.method}")
	private String METHOD;
	
	@Value("${postcoder.search_term}")
	private String SEARCH_TERM;

	@Value("${dev-profile.value}")
	private boolean isDevelopment;

	@RequestMapping(value = "/{eircode}", method = RequestMethod.GET)
	public ResponseEntity<AddressIE> getAddressByEircode(@PathVariable String eircode) {

		AddressIE address;
		
		Optional<AddressIE> maybeAddress = addressRepository.findByEircode(eircode);

		if (maybeAddress.isPresent()) {
			address = maybeAddress.get();
		}
		else {
			address = queryAndSaveToDB(eircode);
		}
		
		return new ResponseEntity<AddressIE>(address, HttpStatus.OK);
	}
	
	private AddressIE queryAndSaveToDB(String eircode){
		
		AddressIE address = queryPostcoderAPI(eircode);
		
		address.setEircode(eircode);
		address = addressRepository.save(address);
		
		return address;
	}

	private AddressIE queryPostcoderAPI(String eircode) {
		
		String uri = createUri(eircode);

		RestTemplate restTemplate = new RestTemplate();
		AddressIE[] addresses = restTemplate.getForObject(uri, AddressIE[].class);

		return addresses[0];
	}

	private String createUri(String eircode) {

		if (isDevelopment)
			return String.format("http://localhost:8080/api/public/eircode/mock/%s", eircode);
		else
			return String.format("http://ws.postcoder.com/pcw/%s/%s/ie/%s%s", SEARCH_KEY, METHOD, eircode, SEARCH_TERM);

	}
	
}
