/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Popov
 */
@Entity
@Table(name="logs")
@NamedQuery(
query = "Select log from Log log where log.text = :text", name = "find log by text")
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Transient
    long brig_num;
    
    @Transient 
    long bnum;
    
    
    
    public String getBnum() {
    	if(brigade==null) return "";
        return ""+(brigade.getId());
	}

	@Transient
    String textDate;

    public String getTextDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.YYYY  hh:mm");
        return sdf.format(date);
    }
    public String getOnlyDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.YYYY");
        return sdf.format(date);
    }

    public long getBrig_num() {
        if(brigade==null) return 1l;
        return brigade.getId();
    }
    
    String text;
    long date;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Brigade getBrigade() {
        return brigade;
    }

    public void setBrigade(Brigade brigade) {
        this.brigade = brigade;
    }
    
    @ManyToOne
    private Brigade brigade;
    
 
    
    @ManyToOne
    private Place place;
    
    @ManyToOne
    private PObject pobject;

    public PObject getPobject() {
        return pobject;
    }

    public void setPobject(PObject pobject) {
        this.pobject = pobject;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

  
    
    public Log(){
    
    }
    private Log(String text){
        super();
     
        this.text=text;
    }
    private Log(String text,Brigade brigade){
      this(text);
      this.brigade=brigade;
      this.date=System.currentTimeMillis();    
    }
    public Log(String text,Brigade brigade,long date,Place place,PObject pobject){
        this(text,brigade);
        this.date=date;
        this.place=place;
        this.pobject=pobject;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
    
   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Log)) {
            return false;
        }
        Log other = (Log) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "testjpa.Log[ id=" + id + " ]";
    }
    
}
