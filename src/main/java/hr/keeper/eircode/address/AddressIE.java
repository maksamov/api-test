package hr.keeper.eircode.address;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter @ToString
@NoArgsConstructor
@Entity
public class AddressIE {
	
	@Id
	@JsonIgnore
	private String eircode;
	
	private String summaryline;
	private String posttown;
	private String postcode;
	private String country;
	
}

