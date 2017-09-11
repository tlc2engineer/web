package repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import entities.Log;

public interface LogRepository extends PagingAndSortingRepository<Log, Long>,JpaSpecificationExecutor<Log>{

}
