package com.shellever.contacts.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Author: Shellever
 * Date:   11/7/2016
 * Email:  shellever@163.com
 */
public class PinyinUtils {

    // group 0
    public static final int NONE = 0x0000;                // lowercase, no separator
    // group 1
    public static final int CASE_CAPITALIZE = 0x0001;     // capitalize
    public static final int CASE_UPPERCASE = 0x0002;      // uppercase
    // group 2
    public static final int LETTER_FIRST = 0x0004;        // first letter of pinyin, left to right
    public static final int LETTER_FIRST_INV = 0x0008;    // first letter of pinyin, right to left
    public static final int LETTER_LAST = 0x0010;         // last letter of pinyin, left to right
    public static final int LETTER_LAST_INV = 0x0020;     // last letter of pinyin, right to left
    // group 3
    public static final int TRIM_NON_CHAR = 0x0040;       // trim non-char
    // group 4 (extensible)
    public static final int SEPARATOR_BLANK = 0x0080;     // separator: blank
    public static final int SEPARATOR_POINT = 0x0100;     // separator: point
    public static final int SEPARATOR_HYPHEN = 0x0200;    // separator: hyphen


    // default: lowercase, no separator
    public static String toPinyinString(String hanzi) {
        return toPinyinString(hanzi, NONE);
    }

    public static String toPinyinString(String hanzi, int mode) {
        StringBuilder builder = new StringBuilder();
        if (hanzi != null) {
            int length = hanzi.length();                // length
            String tmp;
            for (int i = 0; i < length; i++) {
                char hanziChar = hanzi.charAt(i);       // get the specified index char
                if (checkHanziChar(hanziChar)) {
                    tmp = toPinyinChar(hanziChar);      // lowercase
                    if ((mode & CASE_CAPITALIZE) != NONE) {
                        tmp = capitalize(tmp);          // capitalize (high priority)
                    } else if ((mode & CASE_UPPERCASE) != NONE) {
                        tmp = tmp.toUpperCase();        // uppercase
                    }

                    if ((mode & LETTER_FIRST) != NONE || (mode & LETTER_FIRST_INV) != NONE) {
                        tmp = tmp.substring(0, 1);              // first letter (high priority)
                    } else if ((mode & LETTER_LAST) != NONE || (mode & LETTER_LAST_INV) != NONE) {
                        tmp = tmp.substring(tmp.length() - 1);  // last letter
                    }
                } else {
                    tmp = Character.toString(hanziChar);
                    if ((mode & TRIM_NON_CHAR) != NONE) {
                        tmp = "";           // trim the non-char
                    }
                }

                String separator = "";      // don't join separator by default
                if ((mode & SEPARATOR_BLANK) != NONE) {
                    separator = " ";        // join blank separator (high priority)
                } else if ((mode & SEPARATOR_POINT) != NONE) {
                    separator = ".";        // join point separator
                } else if ((mode & SEPARATOR_HYPHEN) != NONE) {
                    separator = "-";        // joint hyphen separator
                }

                if (i >= length - 1) {      // skip the last item's separator
                    separator = "";
                }

                // add the string to builder now
                if ((mode & LETTER_FIRST_INV) != NONE || (mode & LETTER_LAST_INV) != NONE) { // RTL
                    builder.insert(0, tmp);         // 1. insert tmp to head
                    builder.insert(0, separator);   // 2. insert separator to head
                } else {    // LTR - LeftToRight
                    builder.append(tmp);            // 1. append tmp to tail
                    builder.append(separator);      // 2. append separator to tail
                }
            }   // for (int i = 0; i < length; i++)
        }   // if (hanzi != null)
        return builder.toString();
    }

    public static String toPinyinChar(char hanziChar) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);      // lowercase
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);   // without tone
        format.setVCharType(HanyuPinyinVCharType.WITH_V);       // with v
        return toPinyinChar(hanziChar, format);
    }

    public static String toPinyinChar(char hanziChar, HanyuPinyinOutputFormat format) {
        String[] result = null;
        try {
            // null if non-hanziChar
            result = PinyinHelper.toHanyuPinyinStringArray(hanziChar, format);
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return result != null ? result[0] : "";
    }

    // check hanziChar whether it matches the unicode region for hanzi
    public static boolean checkHanziChar(char hanziChar) {
        return Character.toString(hanziChar).matches("[\\u4E00-\\u9FA5]+");
    }

    public static String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

}


/*
// examples
//
// 和道一文字 => hedaoyiwenzi        // NONE
// 和道一文字 => HDYWZ               // CASE_UPPERCASE | LETTER_FIRST    or CASE_CAPITALIZE | LETTER_FIRST
// 和道一文字 => ZWYDH               // CASE_UPPERCASE | LETTER_FIRST_INV or CASE_CAPITALIZE | LETTER_FIRST_INV
// 和道一文字 => HeDaoYiWenZi        // CASE_CAPITALIZE
// 和道一文字 => He Dao Yi Wen Zi    // CASE_CAPITALIZE | SEPARATOR_BLANK
*/
