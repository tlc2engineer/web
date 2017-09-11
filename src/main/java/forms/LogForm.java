package forms;

/**
 * Created by Popov on 22.08.2017.
 */
public class LogForm {
    int id;
    String brig;
    public LogForm(){

    }
    public String getBrig() {
        return brig;
    }

    public void setBrig(String brig) {
        this.brig = brig;
    }

    String text;
    String place;
    String object;
    String change;
    String date;
    
    

    public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getChange() {
		return change;
	}
	public void setChange(String change) {
		this.change = change;
	}
	public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LogForm(int id, String text,String brig,String place,String object) {
        this();
        this.id = id;
        this.text = text;
        this.brig=brig;
        this.place=place;
        this.object=object;
    }
}
