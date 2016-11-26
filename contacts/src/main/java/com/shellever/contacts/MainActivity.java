package com.shellever.contacts;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shellever.contacts.bean.ContactInfo;
import com.shellever.contacts.utils.ContactHelper;
import com.shellever.contacts.view.IndexSideBar;
import com.shellever.contacts.view.SearchEditText;

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
        initEvents();
        initContactAdapter();
    }

    private void initViews() {
        mSearchEt = (SearchEditText) findViewById(R.id.et_contacts_search);
        mLetterDialogTv = (TextView) findViewById(R.id.tv_letter_dialog);
        mIndexSideBar = (IndexSideBar) findViewById(R.id.sb_index_letter);
        mContactsLv = (ListView) findViewById(R.id.lv_contacts);
    }

    private void initEvents() {
        // 设置联系人列表的点击事件监听
        mContactsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactInfo contactInfo = (ContactInfo) mContactAdapter.getItem(position);
                Toast.makeText(MainActivity.this, contactInfo.getRawName(), Toast.LENGTH_SHORT).show();
            }
        });

        // 设置侧边栏的触摸事件监听
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

        // 设置搜索框的文本内容改变事件监听
        mSearchEt.addTextChangedListener(new SearchEditText.MiddleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<ContactInfo> mFilterList = ContactHelper.contactsFilter(s.toString(), mContactInfoList);
                mContactAdapter.updateContactInfoList(mFilterList); // update
                if (DEBUG) {
                    Toast.makeText(MainActivity.this, s.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initContactAdapter() {
        String[] contacts = getResources().getStringArray(R.array.contacts);
        mContactInfoList = ContactHelper.setupContactInfoList(contacts);

        // 设置侧边栏中的字母索引
        List<String> mLetterIndexList = ContactHelper.setupLetterIndexList(mContactInfoList);
        mIndexSideBar.setLetterIndexList(mLetterIndexList, false);

        // 设置联系人列表的信息
        mContactAdapter = new ContactAdapter(this, mContactInfoList);
        mContactsLv.setAdapter(mContactAdapter);
    }

}
