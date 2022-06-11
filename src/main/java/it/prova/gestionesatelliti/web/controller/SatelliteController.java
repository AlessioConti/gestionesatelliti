package it.prova.gestionesatelliti.web.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.gestionesatelliti.model.Satellite;
import it.prova.gestionesatelliti.model.StatoSatellite;
import it.prova.gestionesatelliti.service.SatelliteService;
import it.prova.gestionesatelliti.validator.SatelliteValidator;

@Controller
@RequestMapping(value = "/satellite")
public class SatelliteController {

	@Autowired
	private SatelliteService satelliteService;

	@Autowired
	private SatelliteValidator satelliteValidator;

	@GetMapping
	public ModelAndView listAll() {
		ModelAndView mv = new ModelAndView();
		List<Satellite> results = satelliteService.listaAll();
		mv.addObject("satellite_list_attribute", results);
		mv.setViewName("satellite/list");
		return mv;
	}

	@GetMapping("/search")
	public String search() {
		return "satellite/search";
	}

	@GetMapping("/insert")
	public String create(Model model) {
		model.addAttribute("insert_satellite_attr", new Satellite());
		return "satellite/insert";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("insert_satellite_attr") Satellite satellite, BindingResult result,
			RedirectAttributes redirectAttrs) {

		satelliteValidator.validate(satellite, result);

		if (result.hasErrors())
			return "satellite/insert";

		satelliteService.inserisci(satellite);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";
	}

	@GetMapping("/show/{idSatellite}")
	public String show(@PathVariable(required = true) Long idSatellite, Model model) {
		model.addAttribute("show_satellite_attr", satelliteService.caricaSingoloElemento(idSatellite));
		return "satellite/show";
	}

	@GetMapping("/delete/{idSatellite}")
	public String delete(@PathVariable(required = true) Long idSatellite, Model model) {
		model.addAttribute("delete_satellite_attr", satelliteService.caricaSingoloElemento(idSatellite));
		return "satellite/delete";
	}

	@GetMapping("/remove/{idSatellite}")
	public String remove(@PathVariable(required = true) Long idSatellite, RedirectAttributes redirectAttrs) {

		Satellite satelliteRemove = satelliteService.caricaSingoloElemento(idSatellite);

		if (satelliteValidator.removeCheck(satelliteRemove)) {

			satelliteService.rimuovi(satelliteRemove);
			redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");

		} else {

			redirectAttrs.addFlashAttribute("failedMessage", "Operazione fallita, il satellite non è selezionabile");

		}

		return "redirect:/satellite";
	}

	@GetMapping("/edit/{idSatellite}")
	public String edit(@PathVariable(required = true) Long idSatellite, Model model) {
		model.addAttribute("update_satellite_attr", satelliteService.caricaSingoloElemento(idSatellite));
		return "satellite/edit";
	}

	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("update_satellite_attr") Satellite satellite, BindingResult result,
			RedirectAttributes redirectAttrs) {

		satelliteValidator.validate(satellite, result);

		if (result.hasErrors())
			return "satellite/edit";

		satelliteService.aggiorna(satellite);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";

	}

	@PostMapping("/list")
	public String listByExample(Satellite example, ModelMap model) {
		
		List<Satellite> result = satelliteService.findByExample(example);
		model.addAttribute("satellite_list_attribute", result);
		return "satellite/list";
	}

	@GetMapping("/launch/{idSatellite}")
	public String launch(@PathVariable(required = true) Long idSatellite, RedirectAttributes redirectAttrs) {

		Satellite satelliteLaunch = satelliteService.caricaSingoloElemento(idSatellite);
		satelliteLaunch.setDataLancio(new Date());
		satelliteLaunch.setStato(StatoSatellite.IN_MOVIMENTO);
		satelliteService.aggiorna(satelliteLaunch);

		redirectAttrs.addFlashAttribute("successMessage", "Satellite lanciato!");
		return "redirect:/satellite";
	}

	@GetMapping("/retreat/{idSatellite}")
	public String retreat(@PathVariable(required = true) Long idSatellite, RedirectAttributes redirectAttrs) {
		
		Satellite satelliteReturn = satelliteService.caricaSingoloElemento(idSatellite);
		satelliteReturn.setDataRientro(new Date());
		satelliteReturn.setStato(StatoSatellite.DISATTIVATO);
		satelliteService.aggiorna(satelliteReturn);

		redirectAttrs.addFlashAttribute("successMessage", "Satellite tornato in orbita con successo!");
		return "redirect:/satellite";
	}

	@GetMapping("/searchTwoYearsOld")
	public ModelAndView listSatellitiLanciatiDaOltre2Anni(RedirectAttributes redirectAttrs) {
		
		ModelAndView mv = new ModelAndView();
		List<Satellite> results = satelliteService.trovaSatellitiLanciatiDaAlmeno2Anni();
		mv.addObject("satellite_list_attribute", results);
		mv.setViewName("satellite/list");
		
		return mv;
	}

	@GetMapping("/disableButNotBack")
	public ModelAndView trovaSatellitiDiasttivatiMaNonRientrati(RedirectAttributes redirectAttrs) {
		
		ModelAndView mv = new ModelAndView();
		List<Satellite> results = satelliteService.trovaSatellitiDisattivatiMaNonRientrati();
		mv.addObject("satellite_list_attribute", results);
		mv.setViewName("satellite/list");
		
		return mv;
	}

	@GetMapping("/tenYearsStill")
	public ModelAndView trovaSatellitiFissiDaAlmeno10Anni(RedirectAttributes redirectAttrs) {
		
		ModelAndView mv = new ModelAndView();
		List<Satellite> results = satelliteService.trovaSatellitiInOrbitaFissaDaAlmeno10Anni();
		mv.addObject("satellite_list_attribute", results);
		mv.setViewName("satellite/list");
		
		return mv;
	}

}
