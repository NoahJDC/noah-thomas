import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
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
        contactList.clear();
        try {
            List<String> info = Files.readAllLines(Paths.get(directory, file));
            String number = "";
            String name = "";

            for (String line: info){
                String[] divide = line.split(" ");
                name = divide[0] + " " + divide[1];
                number = divide[2];
                String[] numberArray = divide[2].split("");

                if(numberArray.length == 10){
                    number = tenDigits(numberArray);

                } else if(numberArray.length == 7){
                    number = sevenDigits(numberArray);
                }

                contacts newContact = new contacts(name, number);
                contactList.add(newContact);
            }


        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static String sevenDigits(String [] list) {
        String number = "";
        String[] firstHalf = new String[4];
        String[] secondHalf = new String[4];
        for(int i = 0; i < 3; i++){
            firstHalf[i] = list[i];
        }
        firstHalf[3] = "-";
        for(int i = 0; i < 4; i++){
            secondHalf[i] = list[i+3];
        }

        for(int i = 0; i < 4; i++){
            number += firstHalf[i];
        }
        for(int i = 0; i < 4; i++){
            number += secondHalf[i];
        }
        return number;
    }

    public static String tenDigits(String [] list){
        String number = "";
        String[] firstHalf = new String[4];
        String[] secondHalf = new String[4];
        String[] thirdHalf = new String[4];
        int i;

        for(i = 0; i < 3; i++){
            firstHalf[i] = list[i];
        }
        firstHalf[3] = "-";
        for(i = 0; i<4; i++){
            secondHalf[i] = list[i+2];
        }
        secondHalf[3]="-";
        for(i=0;i<4; i++){
            thirdHalf[i] = list[i+3];
        }
        for(i = 0; i<4; i++){
            number += firstHalf[i];
        }
        for(i = 0; i<4; i++){
            number += secondHalf[i];
        }
        for(i = 0;i<4;i++){
            number += thirdHalf[i];
        }
        return number;
    }



    public static void addContact(String file, String directory){
        System.out.println("Enter a contact name: ");
        String name = Input.getString();

        if(checkName(name) == true){
            System.out.println("That contact already exists!");
            addContact("contactList.txt", "data");
        }
        System.out.println("Enter their number: ");
        String number = Input.getString();
        if(number.contains("-")){
            System.out.println("Please enter a number with only integers!");
            addContact("contactList.txt", "data");
        } else if(number.length() < 7 || number.length() > 10 || number.length() == 9 || number.length() == 8 ){
            System.out.println("That is not an acceptable length!");
            addContact("contactList.txt", "data");
        }
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
        System.out.println();
        updateInfo("contactList.txt", "data");
        importFile("contactList.txt", "data");
        continueProgram();

    }

    public static Boolean checkName(String name){
        Boolean check = false;
        for(contacts index: contactList){
            if(index.getName().equalsIgnoreCase(name)){
                check = true;
            }

        }
        return check;

    }

    public static void viewContacts(){
        String headerName = String.format("%-20s|", "Name");
        System.out.println(headerName + "  Phone Number");
        System.out.println("-----------------------------------");
        for(contacts index: contactList){
            String output = String.format("%-20s|  ",index.getName());
            System.out.println(output + index.getNumber());
        }
        System.out.println();
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
                System.out.println(index.getName() + ": " + index.getNumber());
                trueName = 1;
            }
        }
        if(trueName == 0){
            System.out.println("No one has that name!");
        }
        System.out.println();
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
        System.out.println();
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
