package empresa.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import empresa.model.Reserva;
import empresa.model.ReservaRepository;
import empresa.model.Temp;
import empresa.model.Veiculo;
import empresa.model.VeiculoRepository;

@Controller
public class ReservaController {

		@Autowired
		private ReservaRepository repod;
	
		@Autowired
		private VeiculoRepository repo;
		
		@GetMapping("/")
		public String home() {
			
			return "home";
		}
		
		
		
		@GetMapping("/reservalista")
		public String exibirDepartamento(Model modeld) {
			modeld.addAttribute("lista", repod.findAll());
			
			return "reservalista";
		}
		
		@GetMapping("/reserva")
		public String exibirFormulario(Model modeld) {
			modeld.addAttribute("temp", new Temp());
			
			return "reservaform";
		}
		
		
	    
	    
	    public Date convertData(String data) throws ParseException {
			
	    	DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    	Date d = (Date)formatter.parse(data);
            
            return d;
		}
		
		public boolean validaData(String data1, String data2) {
			
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");//formata a data
            boolean erro = false;
            
            try{
            	Date d1 = (Date)formatter.parse(data1);
            	Date d2 = (Date)formatter.parse(data2);
            }
            catch (Exception e){
            	erro = true;
            }
            return erro;
		}
		
		@PostMapping("/verifica")
		public String verificaDatas(@ModelAttribute Temp temp, Model d) throws ParseException {
			
			
			boolean erro = validaData(temp.getDataInicio(), temp.getDataFim());
			
			if(erro == true) {
				return "erro";
			}else {
				Date d1 = convertData(temp.getDataInicio());
				Date d2 = convertData(temp.getDataFim());
				
				List<Reserva> todasReservas = repod.findAll(); //pega todas reservas
		    	List<Reserva> conflito = new ArrayList<Reserva>(); //recebe as filtradas
		    	
		    	
		    	//separa os conflitos por data
		    	for(int i=0; i<todasReservas.size(); i++) {
		    		
		    		if(d1.before(todasReservas.get(i).getDataFim())) {
		    			conflito.add(todasReservas.get(i)); //nova reserva não pode começar antes do fim de outra reserva
		    		}										
		    		
		    	}
		    	
		    	
		    	
		    	//separa os carros
		    	List<Veiculo> veiculos = repo.findAll();//todos os veiculos
	    		List<Veiculo> disponiveis = new ArrayList();//veiculos disponiveis
	    		
	    		
		    	for(int i=0; i<veiculos.size(); i++) {
		    	
		    		Veiculo v = veiculos.get(i);
		    		boolean liberado = true;
		    		
		    		for(int j=0; j<conflito.size(); j++) {
		    			if(conflito.get(j).equals(v)) {
		    				liberado = false;
		    			}
		    		}
		    		
		    		if(liberado == true) {
		    			disponiveis.add(v);
		    		}
		    		
		    	}
		   
		    	Reserva reserva = new Reserva();
		    	reserva.setDataInicio(d1);//recebe datas formatadas
		    	reserva.setDataFim(d2);
		    	
				d.addAttribute("veiculosdisponiveis", disponiveis);
				d.addAttribute("reserva", reserva);
		    
		    	return "reservaform2";
			}
			
		}
		
		@GetMapping("/reserva/{id}/edit")
		public String alterar(@PathVariable Integer id, Model modeld) {
			Reserva reserva = repod.findById(id).orElse(null);
			modeld.addAttribute("veiculo", repo.findAll());
			modeld.addAttribute("reserva", reserva);
			modeld.addAttribute("edit", true);
			return "reservaform";
		}

		
		
		@PostMapping("/reserva")
		public String salvar(@ModelAttribute Reserva reserva) {
			repod.save(reserva);
			
			return "redirect:/reservalista";
		}
		
		@GetMapping("/reserva/{id}/del")
		public String excluir(@PathVariable Integer id) {
			repod.deleteById(id);
			
			return "redirect:/reservalista";
		}
		
	
		
}

