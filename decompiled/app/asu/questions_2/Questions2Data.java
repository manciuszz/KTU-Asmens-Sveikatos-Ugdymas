package app.asu.questions_2;

import android.view.View;
import android.widget.TextView;
import app.asu.R;
import com.loopj.android.http.RequestParams;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class Questions2Data {
    private static final String KEY_CASUAL_ARRAY = "casualArray";
    private static final String KEY_COMPETITIVE_ARRAY = "competitiveArray";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_FAKULTETAS = "fakultetas";
    private static final String KEY_LOGIN_VARDAS = "loginVardas";
    private static final String KEY_PAVARDE = "pavarde";
    private static final String KEY_SPORTO_LAIKAS = "sportoLaikas";
    private static final String KEY_SPORTO_PASIEKIMAI = "sportoPasiekimai";
    private static final String KEY_SPORTO_PAVADINIMAS = "sportoPavadinimas";
    private static final String KEY_VARDAS = "vardas";
    private CheckBoxListAdapter casualListAdapter;
    private CheckBoxListAdapter competitiveListAdapter;
    private TextView emailTextView;
    private TextView fakultetasTextView;
    private TextView loginVardasTextView;
    private TextView pavardeTextView;
    private TextView sportoLaikasTextView;
    private TextView sportoPasiekimaiTextView;
    private TextView sportoPavadinimasTextView;
    private TextView vardasTextView;

    public class Data {
        private List<Boolean> casualList = new ArrayList();
        private List<Boolean> competitiveList = new ArrayList();
        private String email;
        private String fakultetas;
        private String loginVardas;
        private String pavarde;
        private String sportoLaikas;
        private String sportoPasiekimai;
        private String sportoPavadinimas;
        private String vardas;

        public String toString() {
            return "Login vardas: " + this.loginVardas + "Vardas: " + this.vardas + "Pavarde: " + this.pavarde + "Fakultetas: " + this.fakultetas + "Email: " + this.email + "Sporto pavadinimas: " + this.sportoPavadinimas + "Sporto laikas: " + this.sportoLaikas + "Sporto pasiekimai:" + this.sportoPasiekimai + "\nCasual list: " + this.casualList.toString() + "\nCompetitive list: " + this.competitiveList.toString();
        }

        public RequestParams toRequestParams() {
            RequestParams params = new RequestParams();
            params.put(Questions2Data.KEY_LOGIN_VARDAS, this.loginVardas);
            params.put(Questions2Data.KEY_VARDAS, this.vardas);
            params.put(Questions2Data.KEY_PAVARDE, this.pavarde);
            params.put(Questions2Data.KEY_FAKULTETAS, this.fakultetas);
            params.put("email", this.email);
            params.put(Questions2Data.KEY_SPORTO_PAVADINIMAS, this.sportoPavadinimas);
            params.put(Questions2Data.KEY_SPORTO_LAIKAS, this.sportoLaikas);
            params.put(Questions2Data.KEY_SPORTO_PASIEKIMAI, this.sportoPasiekimai);
            params.put(Questions2Data.KEY_CASUAL_ARRAY, this.casualList.toString());
            params.put(Questions2Data.KEY_COMPETITIVE_ARRAY, this.competitiveList.toString());
            return params;
        }

        public JSONObject toJSON() {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(Questions2Data.KEY_LOGIN_VARDAS, this.loginVardas);
                jsonObject.put(Questions2Data.KEY_VARDAS, this.vardas);
                jsonObject.put(Questions2Data.KEY_PAVARDE, this.pavarde);
                jsonObject.put(Questions2Data.KEY_FAKULTETAS, this.fakultetas);
                jsonObject.put("email", this.email);
                jsonObject.put(Questions2Data.KEY_SPORTO_PAVADINIMAS, this.sportoPavadinimas);
                jsonObject.put(Questions2Data.KEY_SPORTO_LAIKAS, this.sportoLaikas);
                jsonObject.put(Questions2Data.KEY_SPORTO_PASIEKIMAI, this.sportoPasiekimai);
                jsonObject.put(Questions2Data.KEY_CASUAL_ARRAY, this.casualList.toString());
                jsonObject.put(Questions2Data.KEY_COMPETITIVE_ARRAY, this.competitiveList.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject;
        }

        public boolean isFullyFilled() {
            if (this.loginVardas.equals("") || this.vardas.equals("") || this.pavarde.equals("") || this.fakultetas.equals("")) {
                return false;
            }
            return true;
        }

        public String getLoginVardas() {
            return this.loginVardas;
        }

        public void setLoginVardas(String loginVardas) {
            this.loginVardas = loginVardas;
        }

        public String getVardas() {
            return this.vardas;
        }

        public void setVardas(String vardas) {
            this.vardas = vardas;
        }

        public String getPavarde() {
            return this.pavarde;
        }

        public void setPavarde(String pavarde) {
            this.pavarde = pavarde;
        }

        public String getFakultetas() {
            return this.fakultetas;
        }

        public void setFakultetas(String fakultetas) {
            this.fakultetas = fakultetas;
        }

        public String getEmail() {
            return this.email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSportoPavadinimas() {
            return this.sportoPavadinimas;
        }

        public void setSportoPavadinimas(String sportoPavadinimas) {
            this.sportoPavadinimas = sportoPavadinimas;
        }

        public String getSportoLaikas() {
            return this.sportoLaikas;
        }

        public void setSportoLaikas(String sportoLaikas) {
            this.sportoLaikas = sportoLaikas;
        }

        public String getSportoPasiekimai() {
            return this.sportoPasiekimai;
        }

        public void setSportoPasiekimai(String sportoPasiekimai) {
            this.sportoPasiekimai = sportoPasiekimai;
        }

        public List<Boolean> getCompetitiveList() {
            return this.competitiveList;
        }

        public void setCompetitiveList(List<Boolean> competitiveList) {
            this.competitiveList = competitiveList;
        }

        public List<Boolean> getCasualList() {
            return this.casualList;
        }

        public void setCasualList(List<Boolean> casualList) {
            this.casualList = casualList;
        }
    }

    public Questions2Data(View view, CheckBoxListAdapter casualListAdapter, CheckBoxListAdapter competitiveListAdapter) {
        this.loginVardasTextView = (TextView) view.findViewById(R.id.ktuLoginInput);
        this.vardasTextView = (TextView) view.findViewById(R.id.nameInput);
        this.pavardeTextView = (TextView) view.findViewById(R.id.lastNameInput);
        this.fakultetasTextView = (TextView) view.findViewById(R.id.fakultetasInput);
        this.emailTextView = (TextView) view.findViewById(R.id.emailInput);
        this.sportoPavadinimasTextView = (TextView) view.findViewById(R.id.sportNameInput);
        this.sportoLaikasTextView = (TextView) view.findViewById(R.id.sportTimeInput);
        this.sportoPasiekimaiTextView = (TextView) view.findViewById(R.id.sportAchievementsInput);
        this.competitiveListAdapter = competitiveListAdapter;
        this.casualListAdapter = casualListAdapter;
    }

    public Data getData() {
        Data data = new Data();
        data.setLoginVardas(getString(this.loginVardasTextView));
        data.setVardas(getString(this.vardasTextView));
        data.setPavarde(getString(this.pavardeTextView));
        data.setFakultetas(getString(this.fakultetasTextView));
        data.setEmail(getString(this.emailTextView));
        data.setSportoPavadinimas(getString(this.sportoPavadinimasTextView));
        data.setSportoLaikas(getString(this.sportoLaikasTextView));
        data.setSportoPasiekimai(getString(this.sportoPasiekimaiTextView));
        data.setCasualList(this.casualListAdapter.getCheckedList());
        data.setCompetitiveList(this.competitiveListAdapter.getCheckedList());
        return data;
    }

    public String getString(TextView textView) {
        return textView.getText().toString();
    }
}
