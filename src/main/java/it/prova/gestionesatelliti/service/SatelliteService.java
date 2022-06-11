package it.prova.gestionesatelliti.service;

import java.util.List;

import it.prova.gestionesatelliti.model.Satellite;

public interface SatelliteService {
	
	public List<Satellite> listaAll();
	
	public Satellite caricaSingoloElemento(Long id);
	
	public void aggiorna(Satellite satelliteService);
	
	public void inserisci(Satellite satelliteService);
	
	public void rimuovi(Satellite satelliteService);
	
	public List<Satellite> findByExample(Satellite example);
	
	public List<Satellite> trovaSatellitiLanciatiDaAlmeno2Anni();
	
	public List<Satellite> trovaSatellitiDisattivatiMaNonRientrati();
}
