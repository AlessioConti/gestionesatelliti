package it.prova.gestionesatelliti.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.prova.gestionesatelliti.model.Satellite;
import it.prova.gestionesatelliti.model.StatoSatellite;


@Component
public class SatelliteValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Satellite.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Satellite satellite = (Satellite) target;

		if (satellite.getDataLancio() != null && satellite.getDataRientro() != null
				&& satellite.getDataLancio().after(satellite.getDataRientro())) {

			errors.rejectValue("dataLancio", null,
					"ERRORE! Correggi le date affinchè la data di lancio sia precedente a quella di rientro");

		}
		
		if(satellite.getDataLancio() == null && satellite.getDataRientro() != null) {
			
			errors.rejectValue("dataLancio", null, "ERRORE! Inserisci anche la data di lancio O elimina la data di rientro");
			
		}

		if ((satellite.getStato() == StatoSatellite.FISSO || satellite.getStato() == StatoSatellite.IN_MOVIMENTO)
				&& satellite.getDataRientro() != null) {

			errors.rejectValue("stato", null, "ERRORE! Un satellite non-disattivato non può avere una data di rientro");

		}

		if (satellite.getStato() == StatoSatellite.DISATTIVATO && satellite.getDataRientro() == null) {

			errors.rejectValue("stato", null, "ERRORE! Un satellite disattivato deve avere una data di rientro");

		}

	}
	
	public boolean removeCheck(Object target) {
		
		Satellite satellite = (Satellite) target;
		
		if (satellite.getDataLancio() == null) {
			return true;
		}
		
		if (satellite.getDataRientro() != null && satellite.getStato() == StatoSatellite.DISATTIVATO) {
			return true;
		}
		
		return false;
		
	}

}
