package com.doug.entity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

public class ContactLoader extends BaseFunctionsLoader{
	private final static int REQUEST_CONTACT_LOADER = 21001;
	
	public void dealWithActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (!inited) return;
	    if(requestCode==REQUEST_CONTACT_LOADER)
	    {
	    	String username = "";
			String usernumber = "";
			  if (resultCode == Activity.RESULT_OK) {
		            try {
						ContentResolver reContentResolverol = getActivity().getContentResolver();
						 Uri contactData = data.getData();
						 @SuppressWarnings("deprecation")
						Cursor cursor = getActivity().managedQuery(contactData, null, null, null, null);
						 cursor.moveToFirst();
						 username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
						String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
						Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
						         null, 
						         ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, 
						         null, 
						         null);
						 while (phone.moveToNext()) {
						     usernumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						     onContactSelectListener(username, usernumber);
						 }
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

		         }
	    }
	}
	
	private void onContactSelectListener(String username, String tel) {
		getWebView().loadUrl("javascript:onContactSelected('" + username + "','" + tel + "')");
	}


	@Override
	public void functionsStart(String... params) {
		getActivity().startActivityForResult(new Intent(
                Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI), REQUEST_CONTACT_LOADER);
		
	}

}
