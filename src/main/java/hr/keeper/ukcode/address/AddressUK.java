package hr.keeper.ukcode.address;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(value={"id", "new"},ignoreUnknown = true)
@Getter @Setter @ToString
@NoArgsConstructor
@Entity
@NamedQuery(name = "AddressUK.findByPostcode", 
			query = "SELECT a FROM AddressUK a WHERE REPLACE(a.postcode,' ','') = REPLACE(?1,' ','')")
public class AddressUK extends AbstractPersistable<Long> {
	
	private static final long serialVersionUID = 1L;
	
	private String summaryline;
	private String pobox;
	private String organisation;
	private String departmentname;
	private String buildingname;
	private String subbuildingname;
	private String number;
	private String premise;
	private String dependentstreet;
	private String street;
	private String doubledependentlocality;
	private String dependentlocality;
	private String posttown;
	private String county;
	private String postcode;
	private String recodes;
	
}
