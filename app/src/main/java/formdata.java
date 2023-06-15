public class formdata {
    private String name;
    private String usn;
    private String reason;
    private String parent_contact;
    private String dateoa;


    public formdata(String name, String usn, String reason,String parent_contact,String dateoa) {
        this.name = name;
        this.usn = usn;
        this.reason = reason;
        this.parent_contact = parent_contact;
        this.dateoa = dateoa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsn() {
        return usn;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getParent_contact() {
        return parent_contact;
    }

    public void setParent_contact(String parent_contact) {
        this.parent_contact = parent_contact;
    }

    public String getDateoa() {
        return dateoa;
    }

    public void setDateoa(String dateoa) {
        this.dateoa = dateoa;
    }
}
