package org.evilsoft.pathfinder.reference;

import java.io.IOException;

import org.acra.ErrorReporter;
import org.evilsoft.pathfinder.reference.db.DbWrangler;
import org.evilsoft.pathfinder.reference.preference.FilterPreferenceManager;
import org.evilsoft.pathfinder.reference.utils.LimitedSpaceException;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class StartActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Give the FilterPreferenceManager a context so it can access
		// default shared preferences
		FilterPreferenceManager.setContext(this);
		
		// The default values must be set here to bypass a bug in Android
		// See http://stackoverflow.com/questions/3907830/android-checkboxpreference-default-value
		PreferenceManager.setDefaultValues(this, R.xml.source_filter, false);
		
		boolean cont = true;
		try {
			DbWrangler dbw = new DbWrangler(this.getApplicationContext());
			dbw.checkDatabases();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (LimitedSpaceException e) {
			cont = false;
			DbWrangler.showLowSpaceError(this, e, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					ErrorReporter e = ErrorReporter.getInstance();
					e.putCustomData("LastClick", "StartActivity.onCreate.onClick: Ok");
					StartActivity.this.finish();
				}
			});
		}
		if(cont) {
			Intent showContent = new Intent(this.getApplicationContext(), PathfinderOpenReferenceActivity.class);
			startActivity(showContent);
		}
	}
}
