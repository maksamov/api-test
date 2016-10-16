package hr.keeper.ukcode.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import hr.keeper.ukcode.address.AddressUK;
import hr.keeper.ukcode.address.AddressUKRepository;
import hr.keeper.util.UtilMethods;

@RestController
@RequestMapping("api/public/ukcode")
public class PostcodeController {
	
	@Autowired
	private AddressUKRepository addressRepository;

	@Value("${postcoder.search_key}")
	private String SEARCH_KEY;
	
	@Value("${postcoder.method}")
	private String METHOD;
	
	@Value("${postcoder.search_term}")
	private String SEARCH_TERM;

	@Value("${dev-profile.value}")
	private boolean isDevelopment;
	
	@RequestMapping(value = "/{ukcode}", method = RequestMethod.GET)
	public ResponseEntity<AddressUK[]> getAddressByPostcode(@PathVariable String ukcode) {

		AddressUK[] addresses;
		
		List<AddressUK> maybeAddresses = addressRepository.findByPostcode(ukcode);

		if (!maybeAddresses.isEmpty()) {
			addresses = maybeAddresses.toArray(new AddressUK[maybeAddresses.size()]);
		}
		else {
			addresses = queryAndSaveToDB(ukcode);
		}
		
		return new ResponseEntity<AddressUK[]>(addresses, HttpStatus.OK);
	}
	
	private AddressUK[] queryAndSaveToDB(String ukcode){
		
		AddressUK[] addresses = queryPostcoderAPI(ukcode);
		
		for (AddressUK addressUK : addresses) {
			addressUK.setPostcode(UtilMethods.removeSpaces(addressUK.getPostcode()));
			addressRepository.save(addressUK);
		}
		
		return addresses;
	}

	private AddressUK[] queryPostcoderAPI(String ukcode) {
		
		String uri = createUri(ukcode);

		RestTemplate restTemplate = new RestTemplate();
		AddressUK[] addresses = restTemplate.getForObject(uri, AddressUK[].class);

		return addresses;
	}

	private String createUri(String ukcode) {

		if (isDevelopment)
			return String.format("http://localhost:8080/api/public/ukcode/mock/%s", ukcode);
		else
			return String.format("http://ws.postcoder.com/pcw/%s/%s/uk/%s%s", SEARCH_KEY, METHOD, ukcode, SEARCH_TERM);

	}

}
