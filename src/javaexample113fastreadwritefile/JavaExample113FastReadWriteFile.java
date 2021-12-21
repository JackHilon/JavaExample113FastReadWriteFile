
package javaexample113fastreadwritefile;

import java.io.File;


public class JavaExample113FastReadWriteFile {

    // you must wait 3 minutes at least
    
    public static void main(String[] args) {
        
        
        File fil = myHelper.CreateFile("myWorkingFolder", "myDataFile.dat");
        
        int[] vector = MyIntArray.CreateMyIntArray();
        
        //======================================================================
        System.out.println("-------------------------------------------------");
        
        FastInput.WriteIntArrayToRandomAccessFile(fil, vector);
        
        FastInput.ReadIntArrayRandomAccessFileAndMeasureTime(fil, vector.length);
        
        FastInput.ReadIntArrayFileByMovingItToMainMemoryAndMeasureTime(fil, vector.length);
        
        //======================================================================
        System.out.println("-------------------------------------------------");
        myHelper.DeleteFileWithException(fil);
        File fil2 = myHelper.CreateFile("myWorkingFolder", "myDataFile2.dat");
        File fil3 = myHelper.CreateFile("myWorkingFolder", "myDataFile3.dat");
        //======================================================================
        System.out.println("-------------------------------------------------");
        
        FastOutput.WriteIntArrayToRandomAccessFileAndMeasureTime(fil2, vector);
        
        //fil3
        FastOutput.WriteIntArrayFileByMovingItToMainMemoryAndMeasureTime(fil3, vector);                
        
        myHelper.DeleteFileWithException(fil2);
        myHelper.DeleteFileWithException(fil3);

    }
    
}
