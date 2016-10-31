package ru.reksoft.lab.dao.impl;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;
import ru.reksoft.lab.dao.ContactDao;
import ru.reksoft.lab.domain.Contact;

import java.sql.SQLException;
import java.util.List;

import static java.lang.Math.toIntExact;

/**
 * Created by mishanin on 27.04.2016.
 */
@Transactional
public class HiberDaoImpl implements ContactDao {

    private Session session;

    public HiberDaoImpl(SessionFactory sessionFactory){
        session = sessionFactory.openSession();
    }

    @Override
    public void addContact(String name, String surname, String telNumber, String mail, String organization, String position)
            throws SQLException {
        Contact contact = new Contact();
        contact.setName(name);
        contact.setSurname(surname);
        contact.setTelNumber(telNumber);
        contact.setMail(mail);
        contact.setOrganization(organization);
        contact.setPosition(position);
        session.save(contact);
    }

    @Override
    public void deleteContact(int id) throws SQLException, IllegalArgumentException {
        Transaction tx = session.beginTransaction();
        Contact contact = session.get(Contact.class, id);
        if (contact == null)
          throw new IllegalArgumentException(String.format("Contact not found (id = %d).", id));
        session.delete(contact);
        tx.commit();
    }

    @Override
    public void updateContact(Contact contact) throws SQLException {

    }

    @Override
    public List<Contact> getContacts() throws SQLException {
        return session.createQuery("FROM ru.reksoft.lab.domain.Contact").list();
    }

    @Override
    public int getSizeOfBook() throws SQLException{
        return toIntExact((long)session.createQuery("select count (*) from ru.reksoft.lab.domain.Contact").uniqueResult());
    }
}
