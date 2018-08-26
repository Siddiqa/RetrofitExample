package com.app.retrofitdemo.fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.retrofitdemo.R;
import com.app.retrofitdemo.adapter.ContactAdapter;
import com.app.retrofitdemo.utils.CustomLog;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;

/**
 * Created by admin on 11/19/2016.
 */

public class ContactFragment extends BaseFragment {

    private View view;
    private RecyclerView recylerview_contatc;
    ArrayList<String> name_list = new ArrayList<>();
    ArrayList<String> number_list = new ArrayList<>();
    private int READCONTATCS_PERMISSION_CODE = 10;
    private ContactAdapter ca;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contact, container, false);
        initComponents();
        setListner();
        prepairView();
        return view;
    }

    @Override
    void initComponents() {
        recylerview_contatc = (RecyclerView) view.findViewById(R.id.recyclerview_contact);
        recylerview_contatc.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    void setListner() {

    }

    @Override
    void prepairView() {

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion > android.os.Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {

                // We have access. Life is good.
                getContact();
                ca = new ContactAdapter(getContext(), name_list, number_list, this);
                recylerview_contatc.setAdapter(ca);
                final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(ca);
                recylerview_contatc.addItemDecoration(headersDecor);
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_CONTACTS)) {

                // We've been denied once before. Explain why we need the permission, then ask again.
                //  getActivity().showDialog(DIALOG_PERMISSION_REASON);
            } else {

                // We've never asked. Just do it.
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, READCONTATCS_PERMISSION_CODE);
            }
        }




    }

    public void filter(String text) {
        ca.filter(text);
    }

    //We are calling this method to check the permission status

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.e(CustomLog.getTAG(ContactFragment.this),"OnRequestPermissionMethod");
        //Checking the request code of our request
        if (requestCode == READCONTATCS_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e(CustomLog.getTAG(ContactFragment.this),"OnRequestPermissionMethod Inside if");
                //Displaying a toast

                getContact();
                ca = new ContactAdapter(getContext(), name_list, number_list, this);
                recylerview_contatc.setAdapter(ca);
                final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(ca);
                recylerview_contatc.addItemDecoration(headersDecor);
            } else {
                Log.e(CustomLog.getTAG(ContactFragment.this),"OnRequestPermissionMethod Inside else");
            }
        }
    }



    public void getContact() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        Cursor people = getContext().getContentResolver().query(uri, projection, null, null, "UPPER(" + ContactsContract.Contacts.DISPLAY_NAME + ") ASC");

        int indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

        if (people.moveToFirst()) {
            do {
                String name = people.getString(indexName);
                String number = people.getString(indexNumber);
                name_list.add(name);
                number_list.add(number);

            } while (people.moveToNext());
        }
    }

    public void fetchContacts() {

        String phoneNumber = null;
        String email = null;
        int count = 1;
        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

        Uri EmailCONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
        String DATA = ContactsContract.CommonDataKinds.Email.DATA;

        String[] projection = new String[]{"DISTINCT " + ContactsContract.Contacts.DISPLAY_NAME
        };
        ContentResolver contentResolver = getContext().getContentResolver();

        // Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null,
                ContactsContract.Contacts.HAS_PHONE_NUMBER + " = 1",
                null,
                "UPPER(" + ContactsContract.Contacts.DISPLAY_NAME + ") ASC");


        // Loop for every contact in the phone
        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                Log.e(CustomLog.getTAG(ContactFragment.this), "While count" + count + "");
                String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                name_list.add(name);
                Log.e(CustomLog.getTAG(ContactFragment.this), contact_id + "----" + name);
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));

                if (hasPhoneNumber > 0) {


                    // Query and loop for every phone number of the contact
                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);

                    while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        number_list.add(phoneNumber);
                        //  Log.e(CustomLog.getTAG(ContactFragment.this),name);

                    }

                    phoneCursor.close();


                }
                count++;

            }


        }
    }
}
