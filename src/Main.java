import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(100, 3, 1, 124.10);
        GameProgress gameProgress2 = new GameProgress(58, 5, 3, 240.57);
        GameProgress gameProgress3 = new GameProgress(28, 2, 10, 160.89);

        saveGame("D://Games/savegames/save1.dat", gameProgress1);
        saveGame("D://Games/savegames/save2.dat", gameProgress2);
        saveGame("D://Games/savegames/save3.dat", gameProgress3);

        File save1 = new File("D://Games/savegames/save1.dat");
        File save2 = new File("D://Games/savegames/save2.dat");
        File save3 = new File("D://Games/savegames/save3.dat");
        ArrayList<String> listPath = new ArrayList<>();
        listPath.add(save1.getPath());
        listPath.add(save2.getPath());
        listPath.add(save3.getPath());

        zipFiles("D://Games/savegames/zip.zip", listPath);

        deleteFilesAfterZip(listPath);


    }
    private static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    private static void zipFiles(String path, ArrayList<String> listPath) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for (String filePath : listPath) {
                FileInputStream fis = new FileInputStream(filePath);
                File fileToZip = new File(filePath);
                ZipEntry entry = new ZipEntry(fileToZip.getName());
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    private static void deleteFilesAfterZip(ArrayList<String> listPath) {
        for (String filepath : listPath) {
            File delFile = new File(filepath);
            try {
                if (delFile.delete()) {
                    System.out.println("Файл " + delFile.getName() + " успешно удалён");
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}