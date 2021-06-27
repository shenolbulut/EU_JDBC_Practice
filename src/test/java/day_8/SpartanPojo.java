package day_8;

public class SpartanPojo {

    private String name;
    private String gender;
    private Object phone;

    public SpartanPojo(String name, String gender, Object phone){
        this.name=name;
        this.gender=gender;
        this.phone=phone;
    }
    public SpartanPojo(){
    }

    public void setName(String name){
        this.name=name;
    }
    public void setGender(String gender){
        this.gender=gender;
    }
    public void setPhone(Object phone){
        this.phone=phone;
    }

    public String getName(){
        return name;
    }
    public String getGender(){
        return gender;
    }
    public Object getPhone(){
        return phone;
    }

    @Override
    public String toString() {
        return "SpartanPojo{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", phone=" + phone +
                '}';
    }
}
