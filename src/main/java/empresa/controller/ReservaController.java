package empresa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import empresa.model.Reserva;
import empresa.model.ReservaRepository;
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
			modeld.addAttribute("reserva", new Reserva());
			modeld.addAttribute("veiculo", repo.findAll());
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
		
		@GetMapping("/reserva/{id}/edit")
		public String alterar(@PathVariable Integer id, Model modeld) {
			Reserva reserva = repod.findById(id).orElse(null);
			modeld.addAttribute("veiculo", repo.findAll());
			modeld.addAttribute("reserva", reserva);
			modeld.addAttribute("edit", true);
			return "reservaform";
		}
		
		
}

