package hr.keeper.eircode.mock;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hr.keeper.eircode.address.AddressIE;

@RestController
@RequestMapping("api/public/eircode/mock")
public class PostcoderEircodeMockController {

	@RequestMapping(value="/{eircode}", method = RequestMethod.GET)
	public ResponseEntity<AddressIE[]> getAddressByEircode(@PathVariable String eircode) {
	
		AddressIE[] addresses = new AddressIE[1];
		
		AddressIE address = createMockAddress();
		
		if (eircode.contentEquals(address.getEircode()))
			addresses[0] = address;
		
		return new ResponseEntity<AddressIE[]>(addresses, HttpStatus.OK);
	}
	
	private AddressIE createMockAddress() {
		
		AddressIE address = new AddressIE();
		
		address.setEircode("1234");
				
		address.setSummaryline("Summary line");
		address.setPosttown("Posstown");
		address.setPostcode("Postcode");
		address.setCountry("Country");

		return address;
	}
	
}
