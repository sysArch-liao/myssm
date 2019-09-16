package com.atguigu.crud.test;


import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * @author : Albert
 * @date : 2019/9/15 19:49
 *
 *  Java生成8位随机邀请码,不重复
 *  参考的博客： https://www.cnblogs.com/mr-wuxiansheng/p/8922585.html
 *
 */
public class PromotionCode {


    public static void main(String[] args) {
        System.out.println("数组长度：" + chars.length);
        Set<String> set = new HashSet<>();
        for (int i = 1; i <= 100; i++) {
            System.out.println(generateShortUuid());
        }
//        System.out.println(set.size());
    }

    // 数组长度为62
    private static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    // 方法一
    // 8位时1千万都没发现重复的，5位时10万有几个重复的
    private static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        System.out.println("uuid去掉-符号时的长度：" + uuid.length());
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            // 由于UUID都为十六进制
            int x = Integer.parseInt(str, 16);
            // 0x3E是16进制的62
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }

    // 方法二：
    private static String genRandomNum(){
        int  maxNum = 36;
        int i;
        int count = 0;
        char[] str = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while(count < 8){
            i = Math.abs(r.nextInt(maxNum));
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count ++;
            }
        }
        return pwd.toString();
    }

}
