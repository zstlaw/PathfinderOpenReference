package org.evilsoft.pathfinder.reference;

import org.acra.ErrorReporter;
import org.evilsoft.pathfinder.reference.list.BaseListItem;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

public class DetailsListFragment extends AbstractViewListFragment {
	private static final String TAG = "DetailsListFragment";

	public DetailsListFragment() {
		super();
		super.thin = true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		StringBuffer sb = new StringBuffer();
		sb.append("DetailsListFragment.onItemClick: position:");
		sb.append(position);
		sb.append(", id:");
		sb.append(id);
		ErrorReporter e = ErrorReporter.getInstance();
		e.putCustomData("LastClick", sb.toString());
		if (empty) {
			return;
		}
		String uri = getNextUrl(position);
		Log.d(TAG, uri);
		if (PathfinderOpenReferenceActivity.isTabletLayout(getActivity())) {
			DetailsViewFragment viewer = (DetailsViewFragment) this
					.getActivity().getSupportFragmentManager()
					.findFragmentById(R.id.details_view_fragment);
			viewer.updateUrl(uri, currentUrl);
		} else {
			Intent showContent = new Intent(this.getActivity()
					.getApplicationContext(), DetailsActivity.class);
			showContent.setData(Uri.parse(uri));
			startActivity(showContent);
		}
	}

	private String getNextUrl(Integer position) {
		BaseListItem item = (BaseListItem) currentListAdapter.getItem(position);
		return item.getUrl();
	}
}
