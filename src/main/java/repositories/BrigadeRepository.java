package repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import entities.Brigade;

public interface BrigadeRepository extends CrudRepository<Brigade, Long>{
	Brigade findByName(String name);
	Brigade findById(Long id);
	
}
