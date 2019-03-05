package app.asu;

import android.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class SportsActivity extends TabsActivity {
    public List<Fragment> setupTabs() {
        List<Fragment> f = new ArrayList();
        f.add(new SportsFragment());
        f.add(new RecreationZonesFragment());
        return f;
    }
}
