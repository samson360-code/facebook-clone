package facebook.facebookwebpage;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    private String username;
    private String email;
    private String password;
    private String image;

    public void setusername(String username) {
        this.username = username;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public String getusername() {
        return username;
    }

    public String getpassword() {
        return password;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }
    public void setimage(String image) {
        String directoryPath = "/images/";
        this.image = directoryPath + image;
    }
    
     
    public String getimage() {
        return image;
    }

}