package empresa.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
	@Query(value = "SELECT * FROM reserva WHERE reserva.dataInicio :dataInicio OR reserva.dataFim :dataFim", nativeQuery = true)
	List <Reserva> reservaEntre(String dataInicio, String dataFim);
}
