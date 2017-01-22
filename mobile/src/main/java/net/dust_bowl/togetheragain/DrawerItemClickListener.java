package net.dust_bowl.togetheragain;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.Locale;

import static android.R.attr.fragment;

/**
 * Created by quaz4 on 22/01/2017.
 */

class DrawerItemClickListener extends FragmentActivity implements ListView.OnItemClickListener
{
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

    }

    private void selectItem(int position)
    {
        //Create a new fragment
        Fragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        //Insert fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                       .replace(R.id.content_navigation, fragment)
                       .commit();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }
}

/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public class GalleryFragment extends Fragment {
    public static final String ARG_PLANET_NUMBER = "planet_number";

    public GalleryFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_planet, container, false);
        int i = getArguments().getInt(ARG_PLANET_NUMBER);
        String planet = getResources().getStringArray(R.array.planets_array)[i];

        int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                "drawable", getActivity().getPackageName());
        ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
        getActivity().setTitle(planet);
        return rootView;
    }
}
}
