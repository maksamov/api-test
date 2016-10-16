package hr.keeper.ukcode.mock;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hr.keeper.ukcode.address.AddressUK;
import hr.keeper.util.UtilMethods;

@RestController
@RequestMapping("api/public/ukcode/mock")
public class PostcoderUkcodeMockController {

	@RequestMapping(value="/{ukcode}", method = RequestMethod.GET)
	public ResponseEntity<AddressUK[]> getAddressByPostcode(@PathVariable String ukcode) {
	
		AddressUK[] addresses = createMockAddresses();
		
		if (UtilMethods.checkStringEqualityWithoutSpaces(ukcode, addresses[0].getPostcode()))
			return new ResponseEntity<AddressUK[]>(addresses, HttpStatus.OK);
		else
			return null;
	}
	
	private AddressUK[] createMockAddresses() {
		
		AddressUK[] addresses = new AddressUK[2];
		
		AddressUK address1 = new AddressUK();
		
		address1.setSummaryline("Allies Computing Ltd, Manor Farm Barns, Fox Road, Framingham Pigot, Norwich, Norfolk, NR14 7PZ");
		address1.setOrganisation("Allies Computing Ltd");
		address1.setBuildingname("Manor Farm Barns");
		address1.setPremise("Manor Farm Barns");
		address1.setStreet("Fox Road");
		address1.setDependentlocality("Framingham Pigot");
		address1.setPosttown("Norwich");
		address1.setCounty("Norfolk");
		address1.setPostcode("NR147PZ");
		
		addresses[0] = address1;
		
		AddressUK address2 = new AddressUK();
		
		address2.setSummaryline("B2B Cashflow Solutions Ltd, Manor Farm Barns, Fox Road, Framingham Pigot, Norwich, Norfolk, NR14 7PZ");
		address2.setOrganisation("B2B Cashflow Solutions Ltd");
		address2.setBuildingname("Manor Farm Barns");
		address2.setPremise("Manor Farm Barns");
		address2.setStreet("Fox Road");
		address2.setDependentlocality("Framingham Pigot");
		address2.setPosttown("Norwich");
		address2.setCounty("Norfolk");
		address2.setPostcode("NR147PZ");
		
		addresses[1] = address2;
		
		return addresses;
	}
	
}
