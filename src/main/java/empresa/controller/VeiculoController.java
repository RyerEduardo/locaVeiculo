package empresa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import empresa.model.ReservaRepository;
import empresa.model.Veiculo;
import empresa.model.VeiculoRepository;

@Controller
public class VeiculoController {

		@Autowired
		private VeiculoRepository repo;
		
		
		
		@GetMapping("/veiculolista")
		public String exibirVeiculo(Model model) {
			model.addAttribute("lista", repo.findAll());
			return "veiculolista";
		}
		
		
		
		@GetMapping("/veiculo")
		public String exibirFormulario(Model model) {
			model.addAttribute("veiculo", new Veiculo());
			return "veiculoform";
		}
		
		@PostMapping("/veiculo")
		public String salvar(@ModelAttribute Veiculo veiculo) {
			repo.save(veiculo);
			return "redirect:/veiculolista";
		}
		
		@GetMapping("/veiculo/{id}/del")
		public String excluir(@PathVariable Integer id) {
			repo.deleteById(id);
			return "redirect:/veiculolista";
		}
		
		@GetMapping("/veiculo/{id}/edit")
		public String alterar(@PathVariable Integer id, Model model) {
			Veiculo veiculo = repo.findById(id).orElse(null);
			model.addAttribute("veiculo", veiculo);
			model.addAttribute("edit", true);
			return "veiculoform";
		}
		
		
}

