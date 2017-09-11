package repositories;

import org.springframework.data.repository.CrudRepository;


import entities.Change;

public interface ChangeRepository extends CrudRepository<Change, Long>{
		Change findByName(String name);
}
