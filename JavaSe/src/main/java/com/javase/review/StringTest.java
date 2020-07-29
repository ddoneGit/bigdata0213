package com.javase.review;


import java.util.Arrays;

/**
 * @author ddone
 * @date 2020/7/26-07:36
 */
public class StringTest {
    public static void main(String[] args) {
        String str ="B";
        String str1 ="AB";
        System.out.println(str1.hashCode());//31*65+66=2081
        //特别散 31*h+value[i]
        System.out.println(str.hashCode()); //65 //31*0+66

        //底层是一个char类型的value[]
//        public String(char value[]) {
//            this.value = Arrays.copyOf(value, value.length);
//        }//构造器 Arrays.copyOf
        /*
          public String(char value[], int offset, int count) {
        if (offset < 0) {
            throw new StringIndexOutOfBoundsException(offset);
        }
        if (count <= 0) {
            if (count < 0) {
                throw new StringIndexOutOfBoundsException(count);
            }
            if (offset <= value.length) {
                this.value = "".value;
                return;
            }
        }
                // Note: offset or count might be near -1>>>1.
        if (offset > value.length - count) {
            throw new StringIndexOutOfBoundsException(offset + count);
        }
        this.value = Arrays.copyOfRange(value, offset, offset+count);
    }
    指定字符集,读取数据时,可以指定字符集

        public String(byte bytes[], int offset, int length, String charsetName)
            throws UnsupportedEncodingException {
        if (charsetName == null)
            throw new NullPointerException("charsetName");
        checkBounds(bytes, offset, length);
        this.value = StringCoding.decode(charsetName, bytes, offset, length);
    }


   public boolean equalsIgnoreCase(String anotherString) {
        return (this == anotherString) ? true
                : (anotherString != null)
                && (anotherString.value.length == value.length)
                && regionMatches(true, 0, anotherString, 0, value.length);
    }
         */
//        new String(new byte[],charset)
    }
}
