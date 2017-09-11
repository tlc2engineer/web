package repositories;

import org.springframework.data.repository.CrudRepository;

import entities.Brigade;
import entities.Place;

public interface PlaceRepository extends CrudRepository<Place, Long>{
	Place findByName(String name);
}
