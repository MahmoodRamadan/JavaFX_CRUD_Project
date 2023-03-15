
package main;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TextField;

    public class Resources {
	private  SimpleStringProperty fName;
        private SimpleStringProperty lName;	
	private Integer age;
       private Integer id;

	public Resources(String Fname, String lastName,int age) {
        this.fName = new SimpleStringProperty(Fname);
        this.lName = new SimpleStringProperty(lastName);
        this.age=age;
    }
        public Resources(String Fname, String lastName,int age,int id) {
        this.fName = new SimpleStringProperty(Fname);
        this.lName = new SimpleStringProperty(lastName);
        this.age=age;
        this.id=id;
    }

    Resources() {
    }

    Resources(TextField fname, TextField lname, TextField age, int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    Resources(String text, String text0, TextField age, int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

   

   
    public String getFName() {
        return fName.get();
    }

    public void setFName(String Fname) {
        this.fName.set(Fname);
    }

    public String getLName() {
        return lName.get();
    }

    public void setLName(String Lname) {
        this.lName.set(Lname);
    }
    public int getAge() {
		return age;
	}

	public void setAge(int a) {
		this.age = a;
	}

   public int getId() {
            return id;    }
    
    public void setId(int b) {
        this.id =b ;
	}
   
}

