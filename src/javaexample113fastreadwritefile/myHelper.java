
package javaexample113fastreadwritefile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class myHelper {
    private static File CreateWorkingFolder(String folderName) {

        File folder = new File("." + File.separatorChar + folderName);

        folder.mkdir();

        return folder;
    }

    private static File CreateWorkingFile(File folder, String fileName) {

        File fil = new File(folder, fileName);
        try {
            fil.createNewFile();
            return fil;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void FolderCheck(File folder) {

        if (folder.isDirectory()) {
            System.out.println("A folder, named (" + folder.getName() + "), is created....");
        } else {
            System.out.println("Error....");
        }
    }

    private static void FileCheck(File fil) {

        if (fil.isFile()) {
            System.out.println("A file, named (" + fil.getPath() + "), is created....");
        } else {
            System.out.println("Error....");
        }
    }

    //==========================================================================
    
    public static void ExceptionHandle(Exception e) {

        System.out.println(e.toString());
        System.out.println(e.getMessage());
        e.printStackTrace();
    }

    public static File CreateFile(String folderName, String fileName) {

        File folder = myHelper.CreateWorkingFolder(folderName);

        myHelper.FolderCheck(folder);

        File fil = myHelper.CreateWorkingFile(folder, fileName);

        myHelper.FileCheck(fil);

        return fil;
    }

    //==========================================================================
    
    public static void DeleteFile(File fil) {
        try {
            if (fil.isFile()) {
                fil.delete();
                fil.delete();
            }
        }//end-try
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    //==========================================================================
     
    public static void DeleteFileWithException(File fil) {
        try {
            if (fil.isFile()) {
                Files.delete(fil.toPath()); // this throw an exception 
                                            // if delete-operation fails
            }
        }//end-try
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    //==========================================================================
    
    
    
}
