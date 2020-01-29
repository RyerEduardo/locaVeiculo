
package empresa.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface VeiculoRepository extends JpaRepository<Veiculo, Integer> {
	
	@Query(value = "SELECT * FROM veiculo WHERE veiculo.departamento_id = :id AND veiculo.id != :id", nativeQuery = true)
	List <Veiculo> findByGerente(Integer id);
	
}

