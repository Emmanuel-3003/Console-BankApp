package domain;

public class Customer {
    private String id;
    private String customerName;
    private String email;

    public Customer(String id, String customerName, String email) {
        this.id = id;
        this.customerName = customerName;
        this.email = email;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
