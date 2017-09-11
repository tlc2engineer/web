package repositories;

import org.springframework.data.repository.CrudRepository;

import entities.PObject;

public interface ObjectRepository extends CrudRepository<PObject, Long>{
	PObject findByName(String name);

}
