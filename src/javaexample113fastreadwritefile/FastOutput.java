
package javaexample113fastreadwritefile;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;


public class FastOutput {
    
    public static void WriteIntArrayToRandomAccessFileAndMeasureTime(File fil, int[] array) {
        try{
            long starTime = System.currentTimeMillis();
            
            RandomAccessFile raf = new RandomAccessFile(fil, "rw");
            
            raf.seek(0);
            
            for (int i = 0; i < array.length; i++) {
                raf.writeInt(array[i]);
            }
            
            // close
            raf.close();
            
            long endTime = System.currentTimeMillis();
            
            long writingTime = endTime - starTime;
            
            System.out.println("Writing time to random-access-file is: " + writingTime + " [ms]");
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
    //==========================================================================
    
    public static void WriteIntArrayFileByMovingItToMainMemoryAndMeasureTime(File fil, int[] array) {
        
        
        
        try(
            
            
            RandomAccessFile raf = new RandomAccessFile(fil, "rw");
            
            FileChannel kanal = raf.getChannel();
            
                )
            {
                
                long startTime = System.currentTimeMillis();
                
                MappedByteBuffer buff = kanal.map(FileChannel.MapMode.READ_WRITE, 0, MyIntArray.arrayLen * 4);
                // int uses 4 bytes
                                                
                raf.seek(0);
                                
                for (int i = 0; i < array.length; i++) {
                buff.putInt(array[i]);
            }
            
            buff.force();
            
            //close
            kanal.close();
            raf.close();
            
            long endTime = System.currentTimeMillis();
            
            long writingTime = endTime - startTime;
            
            System.out.println("Writing time using main-memory is: " + writingTime + " [ms]");
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            //myHelper.ExceptionHandle(e);
        }
    }
    
    //==========================================================================
    
    
    
    
}
