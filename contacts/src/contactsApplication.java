import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class contactsApplication {
    public static ArrayList<contacts> contactList = new ArrayList<>();

    public static void main(String[] args){
        buildFile("contactList.txt","data");
        importFile("contactList.txt", "data");
        userInterface();

    }

    public static void buildFile(String file, String directory){
        Path directoryName = Paths.get(directory);
        Path fileName = Paths.get(directory, file);
        try{
            if (Files.notExists(directoryName)){
                Files.createDirectories(directoryName);
            }
            if (!Files.exists(fileName)){
                Files.createFile(fileName);
            }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void userInterface(){
        System.out.println("1. View contacts");
        System.out.println("2. Add a new contact");
        System.out.println("3. Search a contact by name");
        System.out.println("4. Delete an existing contact");
        System.out.println("5. Exit");
        System.out.println("Enter an option (1, 2, 3, 4 or 5):");
        int selection = Input.getInt();
        switch(selection){
            case 1:
                viewContacts();
                break;
            case 2:
                addContact("contactList.txt", "data");
                break;
            case 3:
                searchContacts();
                break;
            case 4:
                deleteContacts();
                break;
            case 5:
                updateInfo("contactList.txt", "data");
                break;
        }
    }

    public static void importFile(String file, String directory){
        Path directoryName = Paths.get(directory);
        Path fileName = Paths.get(directory, file);
        try {
            List<String> info = Files.readAllLines(Paths.get(directory, file));
            for (String line: info){
                String[] divide = line.split(" ");
                String name = divide[0] + " " + divide[1];
                String number = divide[2];
                contacts newContact = new contacts(name, number);
                contactList.add(newContact);
            }


        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void addContact(String file, String directory){
        System.out.println("Enter a contact name: ");
        String name = Input.getString();
        System.out.println("Enter their number: ");
        String number = Input.getString();
        contacts thisPunk = new contacts(name, number);
        contactList.add(thisPunk);
        String contact = name + " " + number;

        List<String> newContact = new ArrayList<>();
        newContact.add(contact);
        try {
            Files.write(Paths.get(directory, file), newContact, StandardOpenOption.APPEND);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        continueProgram();

    }

    public static void viewContacts(){
        for(contacts index: contactList){
            System.out.println(index.getName() + " " + index.getNumber());
        }
        continueProgram();
    }

    public static void searchContacts(){
        System.out.println("Enter the name you are looking for");
        String name = Input.getString();
        int trueName = 0;
        for(contacts index: contactList){
            if(index.getName().equalsIgnoreCase(name)){
                System.out.println(index.getName() + " " + index.getNumber());
                trueName = 1;
            } else if(index.getName().contains(name)){
                System.out.println("Is this the one you were looking for?");
                System.out.println(index.getName() + " " + index.getNumber());
                trueName = 1;
            }
        }
        if(trueName == 0){
            System.out.println("No one has that name!");
        }
        continueProgram();

    }

    public static void deleteContacts(){
        System.out.println("Who would you like to delete?");
        String name = Input.getString();
        for(contacts index: contactList){
            if(index.getName().equalsIgnoreCase(name)){
                contactList.remove(index);
                break;
            }
        }
        continueProgram();
    }

    public static void continueProgram(){
        System.out.println("Continue? Y/N");
        String choice = Input.getString();
        if(choice.equalsIgnoreCase("y")||choice.equalsIgnoreCase("yes")){
            userInterface();
        } else {
            updateInfo("contactList.txt", "data");
        }
    }

    public static void updateInfo(String file, String directory){
        try {
            Files.delete(Paths.get(directory, file));
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        buildFile(file, directory);
        for(contacts index: contactList){
            String newLine = index.getName() + " " + index.getNumber();
            List<String> newContact = new ArrayList<>();
            newContact.add(newLine);
            try {
                Files.write(Paths.get(directory, file), newContact, StandardOpenOption.APPEND);
            }
            catch(IOException e){
                System.out.println(e.getMessage());
            }
        }

    }
}
