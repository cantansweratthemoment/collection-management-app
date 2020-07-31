package laba_8.Classes;

import laba_8.Utils.IDGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Organization implements Comparable<Organization>, Serializable {
    private static final long serialVersionUID = 32L;
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Double annualTurnover; //Поле не может быть null, Значение поля должно быть больше 0
    private OrganizationType type; //Поле не может быть null
    private Address officialAddress; //Поле не может быть null
    private String owner;
    private String color;

    public Organization(String name, Coordinates coordinates, Double annualTurnover, OrganizationType type, Address officialAddress) {
        this.id = IDGenerator.generateID();
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now();
        this.annualTurnover = annualTurnover;
        this.type = type;
        this.officialAddress = officialAddress;
    }

    public Organization(int id, String name, Coordinates coordinates, LocalDateTime creationDate, Double annualTurnover, OrganizationType type, Address officialAddress, String owner, String color) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.annualTurnover = annualTurnover;
        this.type = type;
        this.officialAddress = officialAddress;
        this.owner = owner;
        this.color = color;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Double getAnnualTurnover() {
        return annualTurnover;
    }

    public void setAnnualTurnover(Double annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    public OrganizationType getType() {
        return type;
    }

    public void setType(OrganizationType type) {
        this.type = type;
    }

    public Address getOfficialAddress() {
        return officialAddress;
    }

    public void setOfficialAddress(Address officialAddress) {
        this.officialAddress = officialAddress;
    }

    public String getStreet() {
        return this.officialAddress.getStreet();
    }

    public Double getX() {
        return this.coordinates.getX();
    }

    public Double getY() {
        return this.coordinates.getY();
    }

    public String getZipCode() {
        return this.getOfficialAddress().getZipCode();
    }

    public float getLx() {
        return this.getOfficialAddress().getTown().getX();
    }

    public float getLy() {
        return this.getOfficialAddress().getTown().getY();
    }

    public String getTown() {
        return this.getOfficialAddress().getTown().getName();
    }

    public Organization(String name, Coordinates coordinates, Double annualTurnover, OrganizationType type, Address officialAddress, String owner, String color) {
        this.id = IDGenerator.generateID();
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now();
        this.annualTurnover = annualTurnover;
        this.type = type;
        this.officialAddress = officialAddress;
        this.owner = owner;
        this.color = color;
    }

    @Override
    public int compareTo(Organization organization) {
        if (this.annualTurnover - organization.getAnnualTurnover() > 0) {
            return 1;
        } else {
            if (this.annualTurnover - organization.getAnnualTurnover() == 0) {
                return 0;
            } else {
                return -1;
            }
        }
    }
}