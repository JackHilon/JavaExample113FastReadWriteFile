
package javaexample113fastreadwritefile;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;



public class FastInput {
    
    
    public static void WriteIntArrayToRandomAccessFile(File fil, int[] array) {
        try{
            RandomAccessFile raf = new RandomAccessFile(fil, "rw");
            
            for (int i = 0; i < array.length; i++) {
                raf.writeInt(array[i]);
            }
            
            // close
            raf.close();
            
            System.out.println("Writing completed....");
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
        
    }
    
    //==========================================================================
    
    public static void ReadIntArrayRandomAccessFileAndMeasureTime(File fil, int arrayLength) {
        try{
            RandomAccessFile raf = new RandomAccessFile(fil, "r");
            
            raf.seek(0);
            
            long startTime = System.currentTimeMillis();
            
            int number = 0;
            
            for (int i = 0; i < arrayLength; i++) {
                number = raf.readInt();
            }
            
            System.out.println("The last int number in the file: " + number);
            
            long endTime = System.currentTimeMillis();
            
            long readingTime = endTime - startTime;
            
            System.out.println("Reading time is: " + readingTime + " [ms]");
           
            // close
            raf.close();
            
            System.out.println("Writing completed....");
        }// end-try
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
    //==========================================================================
    
    public static void ReadIntArrayFileByMovingItToMainMemoryAndMeasureTime(File fil, int arrayLength) {
        
        try{
            
            long startTime = System.currentTimeMillis();
            
        RandomAccessFile raf = new RandomAccessFile(fil, "r");
        
        /*
        FileChannel:
        ------------------------------------------------------------------------
        A channel for reading, writing, mapping, and manipulating a file.
        
        A file channel is a SeekableByteChannel that is connected to a file.
        It has a current position within its file which can be both queried 
        and modified. The file itself contains a variable-length sequence of 
        bytes that can be read and written and whose current size can 
        be queried. The size of the file increases when bytes are written 
        beyond its current size; the size of the file decreases when it is 
        truncated. The file may also have some associated metadata such as 
        access permissions, content type, and last-modification time; 
        this class does not define methods for metadata access.
        
        In addition to the familiar read, write, and close operations of 
        byte channels, this class defines the following file-specific operations:

        (*) Bytes may be read or written at an absolute position in a file
        in a way that does not affect the channel's current position.
        (*) A region of a file may be mapped directly into memory; 
        for large files this is often much more efficient than 
        invoking the usual read or write methods.
        (*) Updates made to a file may be forced out to the underlying 
        storage device, ensuring that data are not lost in the event of a system crash.
        (*) Bytes can be transferred from a file to some other channel, 
        and vice versa, in a way that can be optimized by many operating 
        systems into a very fast transfer directly to or from the filesystem cache.
        (*)A region of a file may be locked against access by other programs.
        
        File channels are safe for use by multiple concurrent threads. 
        The close method may be invoked at any time, as specified by the 
        Channel interface. Only one operation that involves the channel's 
        position or can change its file's size may be in progress at any 
        given time; attempts to initiate a second such operation while the 
        first is still in progress will block until the first operation 
        completes. Other operations, in particular those that take 
        an explicit position, may proceed concurrently; whether they in 
        fact do so is dependent upon the underlying implementation and is 
        therefore unspecified.
        
        The view of a file provided by an instance of this class is 
        guaranteed to be consistent with other views of the same file 
        provided by other instances in the same program. The view provided 
        by an instance of this class may or may not, however, be consistent 
        with the views seen by other concurrently-running programs due to 
        caching performed by the underlying operating system and delays 
        induced by network-filesystem protocols. This is true regardless 
        of the language in which these other programs are written, and 
        whether they are running on the same machine or on some other 
        machine. The exact nature of any such inconsistencies are 
        system-dependent and are therefore unspecified.
        
        A file channel is created by invoking one of the open methods 
        defined by this class. A file channel can also be obtained from 
        an existing FileInputStream, FileOutputStream, or 
        RandomAccessFile object by invoking that object's getChannel 
        method, which returns a file channel that is connected to 
        the same underlying file. Where the file channel is obtained from 
        an existing stream or random access file then the state of the 
        file channel is intimately connected to that of the object whose 
        getChannel method returned the channel. Changing the 
        channel's position, whether explicitly or by reading or 
        writing bytes, will change the file position of the originating 
        object, and vice versa. Changing the file's length via the file 
        channel will change the length seen via the originating object, 
        and vice versa. Changing the file's content by writing bytes will 
        change the content seen by the originating object, and vice versa. 
        
        At various points this class specifies that an instance that 
        is "open for reading," "open for writing," or "open for reading 
        and writing" is required. A channel obtained via the getChannel 
        method of a FileInputStream instance will be open for reading. 
        A channel obtained via the getChannel method of a FileOutputStream 
        instance will be open for writing. Finally, a channel obtained via 
        the getChannel method of a RandomAccessFile instance will be open 
        for reading if the instance was created with mode "r" and will be 
        open for reading and writing if the instance was created with mode "rw". 

        A file channel that is open for writing may be in append mode, 
        for example if it was obtained from a file-output stream that 
        was created by invoking the FileOutputStream(File,boolean) 
        constructor and passing true for the second parameter. In this mode 
        each invocation of a relative write operation first advances 
        the position to the end of the file and then writes the requested 
        data. Whether the advancement of the position and the writing of 
        the data are done in a single atomic operation is system-dependent 
        and therefore unspecified.
        */
        
        //create obj to move the file from disk to computer-main-memory
        FileChannel kanal = raf.getChannel();
        /*
        Returns the unique FileChannel object associated with this file.
        
        The position of the returned channel will always be equal to this 
        object's file-pointer offset as returned by the getFilePointer method. 
        Changing this object's file-pointer offset, whether explicitly or by 
        reading or writing bytes, will change the position of the channel, 
        and vice versa. Changing the file's length via this object will 
        change the length seen via the file channel, and vice versa.
        */
        
        
        //moving the file from disk to a buffer which is in the computer-main-memory
        /*
        A direct byte buffer whose content is a memory-mapped region of a file.
        
        Mapped byte buffers are created via the FileChannel.map method. 
        This class extends the ByteBuffer class with operations that are 
        specific to memory-mapped file regions.
        
        A mapped byte buffer and the file mapping that it represents remain 
        valid until the buffer itself is garbage-collected.
        
        The content of a mapped byte buffer can change at any time, for 
        example if the content of the corresponding region of the mapped 
        file is changed by this program or another. Whether or not such 
        changes occur, and when they occur, is operating-system dependent 
        and therefore unspecified. 
        
        All or part of a mapped byte buffer may become inaccessible at 
        any time, for example if the mapped file is truncated. An attempt 
        to access an inaccessible region of a mapped byte buffer will not 
        change the buffer's content and will cause an unspecified exception 
        to be thrown either at the time of the access or at some later time. 
        It is therefore strongly recommended that appropriate precautions be 
        taken to avoid the manipulation of a mapped file by this program, or 
        by a concurrently running program, except to read or write the 
        file's content.
        
        Mapped byte buffers otherwise behave no differently than ordinary 
        direct byte buffers.
        */
            MappedByteBuffer buff = kanal.map(FileChannel.MapMode.READ_ONLY, 0, (int)kanal.size());
        /*
        Maps a region of this channel's file directly into memory.
        
        A region of a file may be mapped into memory in one of three modes:
        
        (*) Read-only: Any attempt to modify the resulting buffer will 
            cause a ReadOnlyBufferException to be thrown. 
            (MapMode.READ_ONLY)
        (*) Read/write: Changes made to the resulting buffer will eventually 
            be propagated to the file; they may or may not be made visible to 
            other programs that have mapped the same file. 
            (MapMode.READ_WRITE)
        (*) Private: Changes made to the resulting buffer will not be 
            propagated to the file and will not be visible to other 
            programs that have mapped the same file; instead, they will 
            cause private copies of the modified portions of the buffer 
            to be created. 
            (MapMode.PRIVATE)
                    
        For a read-only mapping, this channel must have been opened for 
        reading; for a read/write or private mapping, this channel must 
        have been opened for both reading and writing.
                    
        The mapped byte buffer returned by this method will have a position 
        of zero and a limit and capacity of size; its mark will be undefined. 
        The buffer and the mapping that it represents will remain valid until 
        the buffer itself is garbage-collected.
                    
        A mapping, once established, is not dependent upon the file channel 
        that was used to create it. Closing the channel, in particular, 
        has no effect upon the validity of the mapping.
                    
        Many of the details of memory-mapped files are inherently dependent 
        upon the underlying operating system and are therefore unspecified. 
        The behavior of this method when the requested region is not 
        completely contained within this channel's file is unspecified. 
        Whether changes made to the content or size of the underlying file, 
        by this program or another, are propagated to the buffer is unspecified. 
        The rate at which changes to the buffer are propagated to the file is 
        unspecified.
                    
        For most operating systems, mapping a file into memory is more 
        expensive than reading or writing a few tens of kilobytes of data 
        via the usual read and write methods. From the standpoint of 
        performance it is generally only worth mapping relatively large 
        files into memory.
        */
                    
           
        // reading file
        int number = 0;
            System.out.println("Reading file from main-memory....");
            for (int i = 0; i < arrayLength; i++) {
                number = buff.getInt();
            }
            
            System.out.println("The last number in the file is: " + number);
            
            long endTime = System.currentTimeMillis();
            
            long readingTime = endTime - startTime;
            
            System.out.println("Reading-time from main-memory is: " + readingTime + " [ms]");
            
            // close
            kanal.close();
            raf.close();
            
    }// end-try
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    
    }// end-ReadFileByMovingItToMainMemory()
    
    //==========================================================================
    
    
}
