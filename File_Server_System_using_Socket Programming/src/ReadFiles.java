import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadFiles extends Server{

    public static void readAllFile() {

        int fileId = 1; //0
        File dFile = new File("Server File/");
        File[] listOfALLFiles = dFile.listFiles();

        for(File file : listOfALLFiles){

            try {

                FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
                String fileName = file.getName();
                byte[] fileContentBytes = new byte[(int) file.length()];
                if((int) file.length() > 0){
                    fileInputStream.read(fileContentBytes);
                }

                MyFile newFile = new MyFile(fileId, fileName, fileContentBytes, getFileExtension(fileName));
                newFile.setData(fileContentBytes);
                allFiles.add(newFile);
                fileId++;

                System.out.println(newFile.getId()+" "+ newFile.getName()+" "+newFile.getName().length()+ " "+ newFile.getFileExtension());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public static String getFileExtension(String fileName) {

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            return fileName.substring(i + 1);
        } else {
            return "No extension found.";
        }
    }

}
