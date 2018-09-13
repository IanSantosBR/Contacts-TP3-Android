package iansantos.contacts.model;

public class Contact {
    private String name;
    private String phone;
    private String email;
    private String city;
    private String password;
    private String cellPhone;
    private String cpf;

    public Contact(String name, String phone, String email, String city, String password, String cellPhone, String cpf) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.city = city;
        this.password = password;
        this.cellPhone = cellPhone;
        this.cpf = cpf;
    }

    public Contact() {
    }

    public String getName() {
        return name = (name.substring(0, 1).toUpperCase() + name.substring(1)).trim();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email = email.toLowerCase().trim();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city = (city.substring(0, 1).toUpperCase() + city.substring(1)).trim();
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPassword() {
        return password.trim();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}