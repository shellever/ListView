package com.shellever.contacts;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity {

    private static boolean DEBUG = false;

    private ListView mContactsLv;
    private IndexSideBar mIndexSideBar;
    private ContactAdapter mContactAdapter;
    private SearchEditText mSearchEt;
    private List<ContactInfo> mContactInfoList;
    private TextView mLetterDialogTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        mSearchEt = (SearchEditText) findViewById(R.id.et_contacts_search);
        mLetterDialogTv = (TextView) findViewById(R.id.tv_letter_dialog);
        mIndexSideBar = (IndexSideBar) findViewById(R.id.sb_index_letter);
        mContactsLv = (ListView) findViewById(R.id.lv_contacts);

        initEvents();
        initContactAdapter();
    }

    private void initEvents() {
        mContactsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactInfo contactInfo = (ContactInfo) mContactAdapter.getItem(position);
                Toast.makeText(MainActivity.this, contactInfo.getRawName(), Toast.LENGTH_SHORT).show();
            }
        });

        mIndexSideBar.setOnTouchLetterListener(new IndexSideBar.OnTouchLetterListener() {
            @Override
            public void onTouchingLetterListener(String letter) {
                mLetterDialogTv.setText(letter);
                mLetterDialogTv.setVisibility(View.VISIBLE);

                int position = mContactAdapter.getPositionForSection(letter.charAt(0));
                if (position != -1) {
                    mContactsLv.setSelection(position);     // jump to the specified position
                }
            }

            @Override
            public void onTouchedLetterListener() {
                mLetterDialogTv.setVisibility(View.GONE);
            }
        });

        mSearchEt.addTextChangedListener(new SearchEditText.MiddleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contactsFilter(s.toString());
                if (DEBUG) {
                    Toast.makeText(MainActivity.this, s.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void contactsFilter(String filterStr) {
        List<ContactInfo> mFilterList = new ArrayList<>();
        if (TextUtils.isEmpty(filterStr)) {
            mFilterList = mContactInfoList;
        } else {
            mFilterList.clear();
            for (ContactInfo contactInfo : mContactInfoList) {
                String upperFilterStr = filterStr.toUpperCase();
                String rawName = contactInfo.getRawName();
                String pinyinName = contactInfo.getPinyinName();
                if (rawName.toUpperCase().contains(upperFilterStr) || pinyinName.startsWith(upperFilterStr)) {
                    mFilterList.add(contactInfo);
                }
            }
        }

        Collections.sort(mFilterList);
        mContactAdapter.updateContactInfoList(mFilterList);     // update
    }

    private void initContactAdapter() {
        mContactInfoList = loadContactInfoList();
        Collections.sort(mContactInfoList);
        mContactAdapter = new ContactAdapter(this, mContactInfoList);
        mContactsLv.setAdapter(mContactAdapter);
    }


    private List<ContactInfo> loadContactInfoList() {
        String[] contacts = getResources().getStringArray(R.array.contacts);
        List<ContactInfo> results = new ArrayList<>();
        List<String> mLetterIndexList = new ArrayList<>();
        boolean found = false;
        for (String contact : contacts) {
            ContactInfo contactInfo = new ContactInfo();
            contactInfo.setRawName(contact);        // rawName

            String pinyinName = PinyinUtils.toPinyinString(contact, PinyinUtils.CASE_UPPERCASE); // 只会对中文转成的汉字拼音进行大写处理
            pinyinName = pinyinName.toUpperCase();  // 如果包含英文字母的话需要额外再进行大写处理
            contactInfo.setPinyinName(pinyinName);  // pinyinName

            String sortLetters = setupSortLetters(contact);
            contactInfo.setSortLetters(sortLetters);

            String firstLetter = sortLetters.substring(0, 1);
            if (!mLetterIndexList.contains(firstLetter) && !"#".equals(firstLetter)) {
                mLetterIndexList.add(firstLetter);
            }
            if (!found && "#".equals(firstLetter)) {    // 只要找到#字符就不再进行判断
                found = true;
            }
            results.add(contactInfo);
        }

        Collections.sort(mLetterIndexList);     // sort
        if (found) {
            mLetterIndexList.add("#");          // append the special to the last
        }
        mIndexSideBar.setLetterIndexList(mLetterIndexList, false);
        return results;
    }

    // 创建用于排序的字母串：默认返回的字符串全部为大写字母，或者#
    // 规则定义：汉字开头时，只取第一个开头的汉字拼音；英文开头时，只截取从开头到非字母字符之前的字母
    private String setupSortLetters(String contact) {
        String firstChar = String.valueOf(contact.charAt(0));
        String pinyin = PinyinUtils.toPinyinString(firstChar, PinyinUtils.CASE_UPPERCASE | PinyinUtils.TRIM_NON_CHAR);
        if (!TextUtils.isEmpty(pinyin)) {       // 首个字符是汉字 (简单的爱)
            return pinyin;                      // JIAN
        } else {
            String words = contact.split("[^a-zA-Z]")[0]; // 或者以英文串开头 (q$100w)
            if (!TextUtils.isEmpty(words)) {
                return words.toUpperCase();                   // Q
            } else {                // 其他的字符归类到#中 ($$Mr.Dj)
                return "#";             // #
            }
        }
    }
}
