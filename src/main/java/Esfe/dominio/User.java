package Esfe.dominio;

public class User {
    private int id;
    private String name;
    private String passwordHash;
    private String email;
    private byte status;

    // *** CONSTRUCTOR VACÍO AÑADIDO (PARA RESOLVER EL ERROR EN LoginForm.java) ***
    public User() {
        // Puedes inicializar valores por defecto aquí si lo deseas, por ejemplo:
        // this.id = 0;
        // this.name = "";
        // this.passwordHash = "";
        // this.email = "";
        // this.status = 0; // O un valor por defecto que tenga sentido
    }

    public User(String admin, String number, String mail) {
        // Es importante que este constructor inicialice los atributos de la clase.
        // Actualmente, este constructor no asigna los parámetros a los atributos de la clase.
        // Si estos parámetros corresponden a 'name', 'passwordHash', 'email' respectivamente,
        // deberías hacer algo como:
        // this.name = admin;
        // this.passwordHash = number; // ¿Estás seguro de que 'number' es passwordHash?
        // this.email = mail;
        // También considera inicializar 'id' y 'status' si son relevantes.
    }

    public User(int id, String name, String passwordHash, String email, byte status) {
        this.id = id;
        this.name = name;
        this.passwordHash = passwordHash;
        this.email = email;
        this.status = status;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getStrEstatus(){
        String str="";
        switch (status) {
            case 1:
                str = "ACTIVO";
                break;
            case 2:
                str = "INACTIVO";
                break;
            default:
                str = "";
        }

        return str;
    }
}