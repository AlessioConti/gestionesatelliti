package it.prova.gestionesatelliti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionesatelliti.model.Satellite;
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
		// TODO Auto-generated method stub
		return null;
	}

}
