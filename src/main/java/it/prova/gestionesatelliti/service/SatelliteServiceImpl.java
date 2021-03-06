package it.prova.gestionesatelliti.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionesatelliti.model.Satellite;
import it.prova.gestionesatelliti.model.StatoSatellite;
import it.prova.gestionesatelliti.repository.SatelliteRepository;

@Service
public class SatelliteServiceImpl implements SatelliteService {

	@Autowired
	private SatelliteRepository repository;

	@Transactional(readOnly = true)
	public List<Satellite> listaAll() {
		return (List<Satellite>) repository.findAll();
	}

	@Transactional(readOnly = true)
	public Satellite caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Transactional
	public void aggiorna(Satellite satelliteService) {
		repository.save(satelliteService);
	}

	@Transactional
	public void inserisci(Satellite satelliteService) {
		repository.save(satelliteService);
	}

	@Transactional
	public void rimuovi(Satellite satelliteService) {
		repository.delete(satelliteService);
	}

	@Transactional(readOnly = true)
	public List<Satellite> findByExample(Satellite example) {
		Specification<Satellite> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (StringUtils.isNotEmpty(example.getDenominazione()))
				predicates.add(cb.like(cb.upper(root.get("denominazione")),
						"%" + example.getDenominazione().toUpperCase() + "%"));

			if (StringUtils.isNotEmpty(example.getCodice()))
				predicates.add(cb.like(cb.upper(root.get("codice")), "%" + example.getCodice().toUpperCase() + "%"));

			if (example.getDataLancio() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataLancio"), example.getDataLancio()));

			if (example.getDataRientro() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataRientro"), example.getDataRientro()));

			if (example.getStato() != null)
				predicates.add(cb.equal(root.get("stato"), example.getStato()));

			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		return repository.findAll(specificationCriteria);
	}

	@Transactional(readOnly = true)
	public List<Satellite> trovaSatellitiLanciatiDaAlmeno2Anni() {
		Calendar annoMenoDue = Calendar.getInstance();

		annoMenoDue.add(Calendar.YEAR, -2);

		return repository.findAllByDataLancioBeforeAndStatoNot(annoMenoDue.getTime(), StatoSatellite.DISATTIVATO);
	}

	@Transactional(readOnly = true)
	public List<Satellite> trovaSatellitiDisattivatiMaNonRientrati() {
		return repository.findAllByStatoAndDataRientroIsNull(StatoSatellite.DISATTIVATO);
	}

	@Transactional(readOnly = true)
	public List<Satellite> trovaSatellitiInOrbitaFissaDaAlmeno10Anni() {

		Calendar annoMenoDieci = Calendar.getInstance();
		annoMenoDieci.add(Calendar.YEAR, -10);

		return repository.findAllByDataLancioBeforeAndStato(annoMenoDieci.getTime(), StatoSatellite.FISSO);
	}

}
