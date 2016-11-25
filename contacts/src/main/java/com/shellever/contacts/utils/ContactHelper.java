package com.shellever.contacts.utils;

import android.text.TextUtils;

import com.shellever.contacts.bean.ContactInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: Shellever
 * Date:   11/23/2016
 * Email:  shellever@163.com
 */

public class ContactHelper {

    public static List<ContactInfo> contactsFilter(String filterStr, List<ContactInfo> contactInfoList) {
        List<ContactInfo> mFilterList = new ArrayList<>();
        if (TextUtils.isEmpty(filterStr)) {     // 若过滤字符为空，则直接返回所有列表信息
            mFilterList = contactInfoList;
        } else {
            mFilterList.clear();
            for (ContactInfo contactInfo : contactInfoList) {
                String upperFilterStr = filterStr.toUpperCase();
                String rawName = contactInfo.getRawName();
                String pinyinName = contactInfo.getPinyinName();
                // 联系人姓名中是否包含此搜索的过滤字符串
                // 或者联系人姓名的拼音字符串是否以搜索的过滤字符串为开头
                if (rawName.toUpperCase().contains(upperFilterStr)
                        || pinyinName.startsWith(upperFilterStr)) {
                    mFilterList.add(contactInfo);
                }
            }
        }
        Collections.sort(mFilterList);      // 排序
        return mFilterList;
    }

    public static List<String> setupLetterIndexList(List<ContactInfo> contactInfoList) {
        List<String> mLetterIndexList = new ArrayList<>();
        boolean found = false;
        for (ContactInfo contactInfo : contactInfoList) {
            String firstLetter = contactInfo.getSortLetters().substring(0, 1);
            if (!mLetterIndexList.contains(firstLetter) && !"#".equals(firstLetter)) {
                mLetterIndexList.add(firstLetter);
            }
            if (!found && "#".equals(firstLetter)) {    // 只要找到#字符就不再进行判断
                found = true;
            }
        }
        Collections.sort(mLetterIndexList);     // 排序
        if (found) {                            // 若发现有"#"
            mLetterIndexList.add("#");          // 则只在列表最后添加"#"
        }
        return mLetterIndexList;
    }

    public static List<ContactInfo> setupContactInfoList(String[] contacts) {
        List<ContactInfo> results = new ArrayList<>();
        for (String contact : contacts) {
            ContactInfo contactInfo = new ContactInfo();
            contactInfo.setRawName(contact);        // rawName

            // 只会对中文转成的汉字拼音进行大写处理
            String pinyinName = PinyinUtils.toPinyinString(contact, PinyinUtils.CASE_UPPERCASE);
            pinyinName = pinyinName.toUpperCase();  // 若包含英文字母则额外再进行大写处理
            contactInfo.setPinyinName(pinyinName);  // pinyinName

            String sortLetters = setupSortLetters(contact);
            contactInfo.setSortLetters(sortLetters); // sortLetters

            results.add(contactInfo);
        }
        Collections.sort(results);  // 排序
        return results;
    }

    // 创建用于排序的字母串：默认返回的字符串全部为大写字母或者#
    // 规则定义：
    // 汉字开头时，只取第一个开头的汉字拼音；
    // 英文开头时，只截取从开头到非字母字符之前的字母
    private static String setupSortLetters(String contact) {
        String firstChar = String.valueOf(contact.charAt(0));
        int mode = PinyinUtils.CASE_UPPERCASE | PinyinUtils.TRIM_NON_CHAR;
        String pinyin = PinyinUtils.toPinyinString(firstChar, mode);
        if (!TextUtils.isEmpty(pinyin)) {       // 首个字符是汉字 (简单的爱)
            return pinyin;                      // JIAN
        } else {
            String words = contact.split("[^a-zA-Z]")[0]; // 或者以英文串开头 (q$100w)
            if (!TextUtils.isEmpty(words)) {
                return words.toUpperCase();               // Q
            } else {                // 其他的字符归类到#中 ($$Mr.Dj)
                return "#";         // #
            }
        }
    }
}
