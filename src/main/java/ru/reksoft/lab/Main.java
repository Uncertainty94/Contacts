package ru.reksoft.lab;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.reksoft.lab.config.AppConfig;
import ru.reksoft.lab.config.DaoConfig;
import ru.reksoft.lab.dao.ContactDao;
import ru.reksoft.lab.service.ContactManager;
import ru.reksoft.lab.util.InputChecker;
import ru.reksoft.lab.exceptions.InputSpellException;
import ru.reksoft.lab.domain.Contact;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by mishanin on 19.04.2016.
 */
public class Main {
    private static BufferedReader br;
    private static ContactManager cm;

    public static void main(String[] args) throws IOException{

        try {
            br = new BufferedReader(System.console().reader());
        } catch (NullPointerException e){
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        ApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);


        cm = (ContactManager) context.getBean("contactManager");
        String provAns = "";
//        System.out.printf("Choose provider (hiber or jdbc).\n>>> ");
//        boolean chooseProviderFlag = true;
//        while (chooseProviderFlag) {
//            provAns = br.readLine();
//            switch (provAns.toLowerCase()) {
//                case "jdbc":
//                    cm.setProvider((ContactDao) context.getBean("jdbcDao"));
//                    chooseProviderFlag = false;
//                    break;
//                case "hiber":
//                case "hibernate":
//                    chooseProviderFlag = false;
//                    break;
//                default:
//                    System.out.println("No such provider. Please choose hiber or jdbc.\n>>>");
//                    break;
//            }
//        }
        String currentCommand;
        boolean exitFlag = false;
        System.out.println("Contact Book 1.0. ");

        while (!exitFlag) {
            boolean trueCommand = false;
            while (!trueCommand) {
                printCommands();
                currentCommand = br.readLine().toLowerCase().trim();
                String[] commandAsArray = currentCommand.split(" ");
                if (commandAsArray.length == 1) {
                    switch (currentCommand) {
                        case "add":
                            trueCommand = true;
                            addCommand();
                            break;
                        case "show":
                            trueCommand = true;
                            showCommand();
                            break;
                        case "exit":
                            trueCommand = true;
                            exitFlag = true;
                            System.out.println("Good bye!");
                            break;
                        default:
                            System.out.printf("Sorry. Use commands correctly.");
                            break;
                    }
                } else if (commandAsArray.length == 2 && "delete".equals(commandAsArray[0])) {
                    deleteCommand(commandAsArray);
                } else {
                    System.out.println("Sorry. Use command correctly.\n");
                }
            }
        }
    }

    private static void printCommands() {
        System.out.printf("Command list:\nshow - for show all book\nadd - to add a new contact\ndelete id - to delete contact\nexit - to close the application\n>>> ");
    }

    private static int checkAnswerToDelete(String answer) throws  NumberFormatException{
        return Integer.parseInt(answer);
    }

    private static void showContactInfo(int index, Contact contact) {
        System.out.printf(index + ". ");
        System.out.printf("id: " + contact.getId() + ";\t");
        System.out.printf("Name: " + contact.getName() + ";\t");
        System.out.printf("Surname: " + contact.getSurname() + ";\t");
        System.out.printf("Telephone number: " + contact.getTelNumber() + ";\t");
        System.out.printf("Mail: " + contact.getMail() + ";\t");
        System.out.printf("Organization: " + contact.getOrganization() + ";\t");
        System.out.printf("Position: " + contact.getPosition() + ";\t\n");
    }

    private static void addCommand(){
        try {
            if (cm.getFreeSpaceOfBook() != 0) {
                String name, surname, telNumber, mail, organization, position;
                try {
                    boolean goodSpell = false;
                    do {
                        System.out.printf("\tName: ");
                        name = br.readLine().trim();
                        try {
                            goodSpell = InputChecker.checkName(name);
                        } catch (InputSpellException e) {
                            System.out.println("\t\t" + e.getMessage());
                        }

                    } while (!goodSpell);
                    goodSpell = false;
                    do {
                        System.out.printf("\tSurname: ");
                        surname = br.readLine().trim();
                        try {
                            goodSpell = InputChecker.checkSurname(surname);
                        } catch (InputSpellException e) {
                            System.out.println("\t\t" + e.getMessage());
                        }

                    } while (!goodSpell);
                    goodSpell = false;
                    do {
                        System.out.printf("\tTelephone number: ");
                        telNumber = br.readLine().trim();
                        try {
                            goodSpell = InputChecker.checkTelNumber(telNumber);
                        } catch (InputSpellException e) {
                            System.out.println("\t\t" + e.getMessage());
                        }

                    } while (!goodSpell);
                    goodSpell = false;
                    do {
                        System.out.printf("\tMail: ");
                        mail = br.readLine().trim();
                        try {
                            goodSpell = InputChecker.checkMail(mail);
                        } catch (InputSpellException e) {
                            System.out.println("\t\t" + e.getMessage());
                        }

                    } while (!goodSpell);
                    goodSpell = false;
                    do {
                        System.out.printf("\tOrganization: ");
                        organization = br.readLine().trim();
                        try {
                            goodSpell = InputChecker.checkOrganization(organization);
                        } catch (InputSpellException e) {
                            System.out.println("\t\t" + e.getMessage());
                        }

                    } while (!goodSpell);
                    goodSpell = false;
                    do {
                        System.out.printf("\tPosition: ");
                        position = br.readLine().trim();
                        try {
                            goodSpell = InputChecker.checkPosition(position);
                        } catch (InputSpellException e) {
                            System.out.println("\t\t" + e.getMessage());
                        }

                    } while (!goodSpell);

                    try {
                        cm.addContact(name, surname, telNumber, mail, organization, position);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        return;
                    }
                    System.out.println("Now " + cm.getCurrentSizeOfBook() + " contacts in the book. You can add " + cm.getFreeSpaceOfBook() + " more.");

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            } else
                System.out.println("Book is full at " + cm.getCurrentSizeOfBook() + " contacts. Delete someone.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void showCommand(){
        List<Contact> contacts;
        try {
            contacts = cm.getContacts();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }
        if (contacts != null && !contacts.isEmpty()) {
            int index = 1;
            for (Contact contact : contacts) {
                showContactInfo(index, contact);
                index++;
            }
        } else {
            try {
                System.out.println("Book is empty. You can add " + cm.getFreeSpaceOfBook() + " contacts.");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void deleteCommand(String[] commandAsArray) throws IOException{
        try {
            int idDelete = checkAnswerToDelete(commandAsArray[1]);
            cm.deleteContact(idDelete);
            System.out.println("Contact deleted");
            System.out.println("Now " + cm.getCurrentSizeOfBook() + " contacts in the book. You can add " + cm.getFreeSpaceOfBook() + " more.");
        } catch (SQLException | IllegalArgumentException e){
            System.out.println("Error on:" + e.getMessage());
        }
    }
}