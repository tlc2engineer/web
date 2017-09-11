package go;

import dao.LiteConnect;
import entities.Brigade;
import entities.Change;
import entities.Log;
import forms.DataForm;
import forms.DelForm;
import forms.EditForm;

import forms.LogForm;
import repositories.BrigadeRepository;
import repositories.ChangeRepository;
import repositories.LogRepository;
import repositories.ObjectRepository;
import repositories.PlaceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import entities.PObject;
import entities.Place;

@Controller
public class DataController {
	DataForm saveForm=null;
	String message;
   
    @Autowired
    BrigadeRepository brepository;
    
    @Autowired
    LogRepository logRepository;
    
    @Autowired
    PlaceRepository placeRepository;
    @Autowired
    ObjectRepository objRepository;
    @Autowired
    ChangeRepository changeRepository;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
    
  
   //----------------------------Редактирование записи-------------------------- 
    @GetMapping("/edit")
    public String edit(@ModelAttribute EditForm edit,Model model) {
    	int id=Integer.valueOf(edit.getEditBtn());
    	Log log=null;
    	if(id!=-1) {
    		 log=logRepository.findOne((long)id);
    		
    	}
    	
    	LogForm lform=new LogForm();
    	if(log!=null) {
    		lform.setText(log.getText());
    		lform.setId(id);
    		lform.setPlace(log.getPlace().getName());
    		lform.setObject(log.getPobject().getName());
    		lform.setBrig(log.getBrigade().getName());
    		lform.setDate(log.getOnlyDate());
    		lform.setId(id);
    		
    		Change change=getChange(log.getDate());
    		lform.setChange(change.getName());
    		
    	}
    	
        model.addAttribute("log", lform);
        model.addAttribute("places",placeRepository.findAll());
        model.addAttribute("objects",objRepository.findAll());
        model.addAttribute("changes", changeRepository.findAll());
        model.addAttribute("brigades", brepository.findAll());
        model.addAttribute("add_header", "Редактирование записи номер "+id);
        return "edit";
    }
    
    @PostMapping("/edit")
    public String editPost(@ModelAttribute LogForm log,Model model) {
    	System.out.println("Post edit");
    	int id=log.getId();
    	
    	Brigade brigade=brepository.findByName(log.getBrig());
        PObject object = objRepository.findByName(log.getObject());
        Place place=placeRepository.findByName(log.getPlace());
        Change change=changeRepository.findByName(log.getChange());
        String text=log.getText();
        
       
    	
        model.addAttribute("log", log);
        model.addAttribute("places",placeRepository.findAll());
        model.addAttribute("objects",objRepository.findAll());
        model.addAttribute("changes", changeRepository.findAll());
        model.addAttribute("brigades", brepository.findAll());
        model.addAttribute("add_header", "Редактирование записи номер "+id);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date result=null;
        try {
        	System.out.println(log.getDate());
			 result =  df.parse(log.getDate());
			 System.out.println(result);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			model.addAttribute("messClass", "alarm");
			model.addAttribute("message", "Неправильно введена дата!");
	        return "edit";
			//e.printStackTrace();
		}  
        if(text.isEmpty()) {
        	model.addAttribute("messClass", "alarm");
        	model.addAttribute("message", "Не введен текст сообщения!");
	        return "edit";
        }
        Log elog=logRepository.findOne((long)id); 
        elog.setText(text);
     
        elog.setBrigade(brigade); 
        elog.setPlace(place);
        elog.setPobject(object);
        elog.setDate(result.getTime());
        logRepository.save(elog);
        model.addAttribute("messClass", "info");
        model.addAttribute("message", "Сообщение отредактировано");
       
        return "edit";
    }
//------------------------Добавление записи-----------------------------------------
    @GetMapping("/add")
    public String add(Model model) {
    	System.out.println("Get");
    	LogForm lform=new LogForm();
    	lform.setDate("17.10.2017");
        model.addAttribute("log", lform);
        model.addAttribute("places",placeRepository.findAll());
        model.addAttribute("objects",objRepository.findAll());
        model.addAttribute("changes", changeRepository.findAll());
        model.addAttribute("brigades", brepository.findAll());
        
        return "add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute LogForm log,Model model) {
    	System.out.println("Post");    
        
        Brigade brigade=brepository.findByName(log.getBrig());
        PObject object = objRepository.findByName(log.getObject());
        Place place=placeRepository.findByName(log.getPlace());
        Change change=changeRepository.findByName(log.getChange());
     
        String text=log.getText();
      
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date result=null;
        try {
        	System.out.println(log.getDate());
			 result =  df.parse(log.getDate());
			 System.out.println(result);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			model.addAttribute("message", "Неправильно введена дата!");
			model.addAttribute("messClass", "alarm");
	        return "add";
			//e.printStackTrace();
		}  
        
        model.addAttribute("places",placeRepository.findAll());
        model.addAttribute("objects",objRepository.findAll());
        model.addAttribute("changes", changeRepository.findAll());
        model.addAttribute("brigades", brepository.findAll());
        model.addAttribute("log", log);
       
        if(text.isEmpty()) {
        	model.addAttribute("message", "Не введен текст сообщения!");
        	model.addAttribute("messClass", "alarm");
	        return "add";
        }
        Log addLog = new Log(text,brigade,result.getTime()+change.getBgHour()*3600*1000,place,object);
        logRepository.save(addLog);
        this.message= "Сообщение добавлено";
        model.addAttribute("message", this.message);
        model.addAttribute("messClass", "info");
        return "add";
    }

    @RequestMapping(value = "/places", method = RequestMethod.GET)
    @ResponseBody
    public List<Place> getPlaces (){
    	List<Place> places=new ArrayList<Place>();
    	for(Place place :placeRepository.findAll()) {
    		places.add(place);
    	}
        return places;
    }

    @RequestMapping(value = "/objects", method = RequestMethod.GET)
    @ResponseBody
    public List<PObject> getObjects (){
    	List<PObject> objects=new ArrayList<>();
    	for(PObject po: objects) {
    		objects.add(po);
    	}
        return objects;
    }
    
    @GetMapping("/view_brigades")
    public String view_brigades(Model model) {
//    	brepository.save(new Brigade(1l,"Первая"));
//    	brepository.save(new Brigade(2l,"Вторая"));
//    	brepository.save(new Brigade(3l,"Третья"));
//    	brepository.save(new Brigade(4l,"Четвертая"));
    	Iterable<entities.Place> places = placeRepository.findAll();
    
    	Iterable<Brigade> brigades = brepository.findAll();
        model.addAttribute("brigades",brigades);
        model.addAttribute("places",places);
//        List<Brigade> bone = brepository.findByName("Первая");
//        System.out.println(bone.get(0).getName());

        return "brigades";
    }
    
    @GetMapping("/filter")
    public String filter(Model model){
    	DataForm dform;
    	if(saveForm==null) {
	    	dform=new DataForm();
	    	dform.setCurrPage(1);
	    	dform.setMessPageCount(20);
	    	dform.setMessDate("22.05.2017");
    	}
    	else
    		dform=saveForm;
    	Specification<Log> scep=getSpec( dform);
    	Sort sort=getSort(dform);
    	List<Log> logdata = logRepository.findAll(scep,sort);
    	int currPage=Integer.valueOf(dform.getCurrPage());
        int messCountOfPage=Integer.valueOf(dform.getMessPageCount());
        int bg=(currPage-1)*messCountOfPage;
        int end=((currPage)*messCountOfPage);
        if(end>=logdata.size()) {
     	   end=logdata.size()-1;
        }
           
        List<Log> pdata = logdata.subList(bg, end);
    	model.addAttribute("dform", dform);
    	model.addAttribute("name", "");
    	PageRequest page = new PageRequest(0, 20);
        model.addAttribute("brigades", brepository.findAll());
        model.addAttribute("logs", pdata);
        model.addAttribute("pageCount",logdata.size()/messCountOfPage+1);
        Iterable<entities.Place> places = placeRepository.findAll();
        model.addAttribute("places",places);
        model.addAttribute("objects",objRepository.findAll());
        model.addAttribute("changes", changeRepository.findAll());
        model.addAttribute("message", this.message);
        model.addAttribute("messClass", "info");
		return "data";
    	
    }
    
    @PostMapping("/filter")
    public String postFilter(@ModelAttribute DataForm dform,Model model){    	
    	
    	Specification<Log> scep=getSpec( dform);
    	Sort sort=getSort(dform);
    	List<Log> logdata = logRepository.findAll(scep,sort);
    	boolean firstMessage=dform.getFirstMessage()!=null;
    	boolean lastMessage=dform.getLastMessage()!=null;
    	boolean prev=dform.getPrevMessage()!=null;
    	boolean next=dform.getNextMessage()!=null;
    	
    	int currPage=Integer.valueOf(dform.getCurrPage());
        int messCountOfPage=Integer.valueOf(dform.getMessPageCount());
        if(prev) {
        	currPage-=1;
        	if(currPage<1) currPage=1;
        }
        if(next && currPage<logdata.size()/messCountOfPage) currPage+=1;
        if(firstMessage) currPage=1;
        if(lastMessage) {
        	currPage=logdata.size()/messCountOfPage+1;
        }
        if(currPage>logdata.size()/messCountOfPage+1) {
     	   currPage=logdata.size()/messCountOfPage+1;
     	   
        }
       int bg=(currPage-1)*messCountOfPage;
       int end=((currPage)*messCountOfPage);
       if(end>=logdata.size()) {
    	   end=logdata.size();
       }
            
        List<Log> pdata = logdata.subList(bg, end);
        dform.setCurrPage(currPage);
    	model.addAttribute("dform", dform);
    	this.saveForm=dform;
    	model.addAttribute("name", "");
        model.addAttribute("brigades", brepository.findAll());
        model.addAttribute("logs", pdata);
        model.addAttribute("pageCount", logdata.size()/messCountOfPage+1);
        Iterable<entities.Place> places = placeRepository.findAll();
        model.addAttribute("places",places);
        model.addAttribute("objects",objRepository.findAll());
        model.addAttribute("changes", changeRepository.findAll());
        model.addAttribute("message", this.message);
        model.addAttribute("messClass", "info");
		return "data";
    	
    }
    
    public Change  getChange(long time) {
    	Calendar cl=Calendar.getInstance();
    	cl.setTimeInMillis(time);
    	int h=cl.get(Calendar.HOUR_OF_DAY);
		Change change;
		if(h>=8 && h<=15)
			change=changeRepository.findOne(1l);
		else if(h>=15 && h<23)
			change=changeRepository.findOne(2l);
		else
			change=changeRepository.findOne(3l);
		return change;
    }
    
    
    @PostMapping("/delete")
    public String delete(@ModelAttribute DelForm dform,Model model){
    	long id = dform.getDelId();
    	System.out.println("ID:"+id);
    	logRepository.delete(id);
    	this.message= "Сообщение номер "+id+" удалено";
    	/*
    	DataForm dataForm;
    	if(saveForm==null) {
    	 dataForm=new DataForm();
    	dataForm.setCurrPage(1);
    	dataForm.setMessPageCount(20);
    	dataForm.setMessDate("22.05.2017");
    	}
    	dataForm=saveForm;
    	model.addAttribute("dform", dataForm);
    	model.addAttribute("name", "");
        model.addAttribute("brigades", brepository.findAll());
        model.addAttribute("logs", logRepository.findAll());
        Iterable<entities.Place> places = placeRepository.findAll();
        model.addAttribute("places",places);
        model.addAttribute("objects",objRepository.findAll());
        model.addAttribute("changes", changeRepository.findAll());
        */
		return "redirect:/filter";
    	
    }
    
   private Specification<Log> getSpec(DataForm dform){
	   Specification<Log> scep=new Specification<Log>() {

			@Override
			public Predicate toPredicate(Root<Log> root, CriteriaQuery<?> arg1, CriteriaBuilder builder) {
				// TODO Auto-generated method stub
			Predicate pred = builder.gt(root.get("id"), 0);
			List<Predicate> predicates=new ArrayList<>();
		
			predicates.add(pred);
			if(dform.isFilter()) {
				if(!dform.getBrigade().equals("Все")) {			
					predicates.add(builder.equal(root.get("brigade"), brepository.findByName(dform.getBrigade())));
				}
				if(!dform.getPlace().equals("Все")) {				
					predicates.add(builder.equal(root.get("place"), placeRepository.findByName(dform.getPlace())));
				}
				if(!dform.getObject().equals("Все")) {				
					predicates.add(builder.equal(root.get("pobject"), objRepository.findByName(dform.getObject())));
				}
				if(dform.isDateFilter()) {
					DateFormat df = new SimpleDateFormat("dd.MM.yyyy");	        
			        try {
			        	
						 Date result = df.parse(dform.getDate());
					
						 predicates.add(builder.greaterThanOrEqualTo(root.get("date"), result.getTime()));
						 predicates.add(builder.lessThan(root.get("date"), result.getTime()+24*3600*1000));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						DataController.this.message="Неправильно введена дата";
				       
						//e.printStackTrace();
					}  
					
				}
						
			}
			Predicate[] arr = predicates.toArray(new Predicate[0]);	
			return builder.and(arr);		
		
			}
   		
   	};
	   return scep;
   }
	private Sort getSort(DataForm dform) {
		Sort sort;
		 if(dform.getSort()==null || dform.getSort().equals("Дата")) {
         	 sort=new Sort("date");
        }
        else sort=new Sort("id");
		return sort;
	}
	 @RequestMapping(value = "/placeView")
	 String placeView() {
		 return "placeView";
	 }

}