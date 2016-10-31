package ru.reksoft.lab.domain;

import org.apache.commons.lang.StringUtils;

import javax.persistence.*;

/**
 * Created by mishanin on 19.04.2016.
 */
@Entity
@Table(name = "contacts_table")
public class Contact {
    private String name;
    private String surname;

    @Column(name = "tel_number")
    private String telNumber;

    private String mail;
    private String organization;
    private String position;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Contact() {}

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public String getMail() {
        return mail;
    }

    public String getOrganization() {
        return organization;
    }

    public String getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj){

        if (obj == null || obj.getClass() != Contact.class) return false;

        Contact anotherContact = (Contact) obj;

        return StringUtils.equals(name, anotherContact.getName()) &&
                StringUtils.equals(surname, anotherContact.getSurname()) &&
                StringUtils.equals(telNumber, anotherContact.getTelNumber()) &&
                StringUtils.equals(mail, anotherContact.getMail()) &&
                StringUtils.equals(organization, anotherContact.getOrganization()) &&
                StringUtils.equals(position, anotherContact.getPosition());
    }
}
