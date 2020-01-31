package empresa.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

}
