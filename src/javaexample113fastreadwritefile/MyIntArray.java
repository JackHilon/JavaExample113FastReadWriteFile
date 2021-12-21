
package javaexample113fastreadwritefile;


public class MyIntArray {
    public static int arrayLen = 1000001;
    
    public static int[] CreateMyIntArray() {
        int[] array = new int[arrayLen];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }
        return array;
    }
}
