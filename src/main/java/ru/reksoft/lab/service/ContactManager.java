package ru.reksoft.lab.service;

import ru.reksoft.lab.domain.Contact;
import ru.reksoft.lab.dao.ContactDao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mishanin on 19.04.2016.
 */
public class ContactManager {

    private int maxContacts;
    private ContactDao provider;
    private Integer currentSize;

    public void setProvider(ContactDao provider) {
        this.provider = provider;
    }

    public void setMaxContacts(int maxContacts) {
        this.maxContacts = maxContacts;
    }

    public List<Contact> getContacts() throws SQLException {
        return provider.getContacts();
    }

    public void addContact(String name, String surname, String telNumber, String mail, String organization, String position) throws Exception{
        provider.addContact(name, surname, telNumber, mail, organization, position);
        currentSize = null;
    }

    public void deleteContact(int id) throws SQLException, IllegalArgumentException{
        provider.deleteContact(id);
        currentSize = null;
    }

    public int getCurrentSizeOfBook() throws SQLException {
        currentSize = provider.getSizeOfBook();
        return currentSize;
    }

    public int getFreeSpaceOfBook() throws SQLException {
        if (currentSize == null)
            return maxContacts - getCurrentSizeOfBook();
        else
            return maxContacts - currentSize;
    }
}
