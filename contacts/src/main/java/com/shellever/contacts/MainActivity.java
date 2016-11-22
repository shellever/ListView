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
                mContactAdapter.updateContactInfoList(ContactHelper.contactsFilter(s.toString(), mContactInfoList));     // update
                if (DEBUG) {
                    Toast.makeText(MainActivity.this, s.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initContactAdapter() {
        String[] contacts = getResources().getStringArray(R.array.contacts);
        mContactInfoList = ContactHelper.setupContactInfoList(contacts);

        List<String> mLetterIndexList = ContactHelper.setupLetterIndexList(mContactInfoList);
        mIndexSideBar.setLetterIndexList(mLetterIndexList, false);

        mContactAdapter = new ContactAdapter(this, mContactInfoList);
        mContactsLv.setAdapter(mContactAdapter);
    }

}
