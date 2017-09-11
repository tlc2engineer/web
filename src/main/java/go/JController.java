package go;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import entities.Log;
import entities.PObject;
import entities.Place;
import repositories.BrigadeRepository;
import repositories.LogRepository;
import repositories.ObjectRepository;
import repositories.PlaceRepository;

@RestController
public class JController {
	final DateFormat df = new SimpleDateFormat("dd.MM.yyyy");	
	@Autowired
	PlaceRepository placeRepository;
	@Autowired
	ObjectRepository objRepository;
	@Autowired
	LogRepository logRepository;
	@Autowired
	BrigadeRepository brepository;
	
	@GetMapping("/get_places")
	public List<Place> get_places(){
		List<Place> places=new ArrayList<Place>();
    	for(Place place :placeRepository.findAll()) {
    		places.add(place);
    	}
        return places;
	}
	
	@GetMapping("/get_objects")
	public List<PObject> get_objects(@RequestParam(value="place",defaultValue="Черновая клеть") String placeName){
		return placeRepository.findByName(placeName).getPobjectlist();
	}
	
	@GetMapping("/get_logs")
	public List<Log> get_logs(@RequestParam(value="place",required=false) String placeName,
			@RequestParam(value="object",required=false) String objectName,
			@RequestParam(value="brig_num",required=false ) Integer brigNum,
			@RequestParam(value="date",required=false ) String date,
			@RequestParam(value="bg_date",required=false ) String bg_date,
			@RequestParam(value="end_date",required=false ) String end_date
			){
		   Specification<Log> scep=new Specification<Log>() {

				@Override
				public Predicate toPredicate(Root<Log> root, CriteriaQuery<?> arg1, CriteriaBuilder builder) {
					// TODO Auto-generated method stub
				Predicate pred = builder.gt(root.get("id"), 0);
				List<Predicate> predicates=new ArrayList<>();
			
				predicates.add(pred);
				if(placeName!=null) {							
					predicates.add(builder.equal(root.get("place"), placeRepository.findByName(placeName)));
				}
				if(objectName!=null) {				
					predicates.add(builder.equal(root.get("pobject"), objRepository.findByName(objectName)));
				}
				if(brigNum!=null) {			
					predicates.add(builder.equal(root.get("brigade"), brepository.findById((long)brigNum)));
				}
				if(date!=null) {
					    
			        try {
			        	
						 Date result = df.parse(date);
					
						 predicates.add(builder.greaterThanOrEqualTo(root.get("date"), result.getTime()));
						 predicates.add(builder.lessThan(root.get("date"), result.getTime()+24*3600*1000));
					} catch (ParseException e) {
					
					}  
				}
				if(bg_date!=null) {					
			        try {			        	
						 Date result = df.parse(bg_date);
						 predicates.add(builder.greaterThanOrEqualTo(root.get("date"), result.getTime()));
					} catch (ParseException e) {
					
					}  
				}
				if(end_date!=null) {					
			        try {			        	
						 Date result = df.parse(end_date);
						 predicates.add(builder.lessThan(root.get("date"), result.getTime()));
					} catch (ParseException e) {
					
					}  
				}
				Predicate[] arr = predicates.toArray(new Predicate[0]);	
				return builder.and(arr);		
			
				}
	   		
	   	};
	   	return logRepository.findAll(scep);
	}
//	public List<Place> places=new ArrayList<Place>();
//	for(Place place :placeRepository.findAll()) {
//		places.add(place);
//	}
//    return places;
	
	

}
