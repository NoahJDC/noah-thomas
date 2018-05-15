import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;

public class contactsApplication {
    public static void main(String[] args){
        buildFile("contactList.txt","data");

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
}
