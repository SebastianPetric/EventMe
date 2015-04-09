package thesis.hfu.eventmy.objects;


public class User {

   private int user_id,status;
   private String name,prename,email;

   public User(int Uid,int status,String name,String prename,String email){
       this.status=status;
       this.user_id=Uid;
       this.name=name;
       this.prename=prename;
       this.email=email;
   }

    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPrename() {
        return prename;
    }
    public void setPrename(String prename) {
        this.prename = prename;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}
