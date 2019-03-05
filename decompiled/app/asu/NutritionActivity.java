package app.asu;

import android.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class NutritionActivity extends TabsActivity {
    public List<Fragment> setupTabs() {
        List<Fragment> f = new ArrayList();
        f.add(new NutritionMonitoringFragment());
        f.add(new FoodProductsFragment());
        f.add(new EAdditivesFragment());
        return f;
    }
}
