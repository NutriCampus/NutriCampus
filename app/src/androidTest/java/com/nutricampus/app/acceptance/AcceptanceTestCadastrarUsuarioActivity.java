package com.nutricampus.app.acceptance;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.nutricampus.app.R;
import com.nutricampus.app.activities.CadastrarUsuarioActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by Mateus on 29/06/2017.
 * For project NutriCampus.
 * Contact: <paulomatew@gmail.com>
 */

//https://github.com/sebaslogen/espresso-cucumber
//@RunWith(AndroidJUnit4.class)
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AcceptanceTestCadastrarUsuarioActivity/* extends ActivityTestRule<CadastrarUsuarioActivity> */ {
    /*private static final String TAG = "CucumberCadasUsuctivity";
    private final Application.ActivityLifecycleCallbacks mActivityLifecycleCallback;
    private CountDownLatch mDoneSignal;
    private Application mApplication;*/

    /*public AcceptanceTestCadastrarUsuarioActivity() {
        super(CadastrarUsuarioActivity.class);

        mActivityLifecycleCallback = new Application.ActivityLifecycleCallbacks() {

            @Override
            public void onActivityDestroyed(final Activity activity) {
                Log.d(TAG, "onActivityDestroyed()");
                if (mDoneSignal != null) {
                    mDoneSignal.countDown();
                }
                if (mApplication != null) {
                    mApplication.unregisterActivityLifecycleCallbacks(this);
                }
            }

            @Override
            public void onActivityStopped(final Activity activity) {
            }

            @Override
            public void onActivityStarted(final Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(final Activity activity, final Bundle outState) {
            }

            @Override
            public void onActivityResumed(final Activity activity) {
            }

            @Override
            public void onActivityPaused(final Activity activity) {
            }

            @Override
            public void onActivityCreated(final Activity activity, final Bundle savedInstanceState) {
            }
        };
    }*/
    @Rule
    public ActivityTestRule<CadastrarUsuarioActivity> mActivityRule = new ActivityTestRule<>(
            CadastrarUsuarioActivity.class);

    @Test
    public void attempToCreatAccountSuccessfully() throws Exception {
        onView(withId(R.id.edtNome))
                .perform(typeText("Vinicius attempToCreatAccountSuccessfully"));
        closeSoftKeyboard();
        onView(withId(R.id.edtCpf))
                .perform(typeText("44502396605"));
        closeSoftKeyboard();
        onView(withId(R.id.edtRegistro))
                .perform(typeText("44502396605"));
        closeSoftKeyboard();
        onView(withId(R.id.edtEmail))
                .perform(typeText("vini_attempToCreatAccountSuccessfully@email.com"));
        closeSoftKeyboard();
        onView(withId(R.id.edtSenha))
                .perform(typeText("12345"));
        closeSoftKeyboard();

        onView(withId(R.id.btn_salvar_cadastro)).perform(click());

        //Check por dialog https://stackoverflow.com/questions/21045509/check-if-a-dialog-is-displayed-with-espresso
        onView(withText("Cadastro")).check(matches(isDisplayed()));

        Thread.sleep(3000);
    }


    /*//@Given("^I press \"(.+)\"$")
    @Given("^Eu preencho campo de texto do nome")
    public void cadastrarUsuario_preencherNome() throws Exception {
        onView(withId(R.id.edtNome))
                .perform(typeText("Vinicius attempToCreatAccountSuccessfully"));
        closeSoftKeyboard();
    }

    @Given("^Eu preencho campo de texto do cpf")
    public void cadastrarUsuario_preencherCpf() throws Exception {
        onView(withId(R.id.edtCpf))
                .perform(typeText("44502396605"));
        closeSoftKeyboard();
    }

    @Given("^Eu preencho campo de texto do registro")
    public void cadastrarUsuario_preencherRegistro() throws Exception {
        onView(withId(R.id.edtRegistro))
                .perform(typeText("44502396605"));
        closeSoftKeyboard();
    }

    @Given("^Eu preencho campo de texto do email")
    public void cadastrarUsuario_preencherEmail() throws Exception {
        onView(withId(R.id.edtEmail))
                .perform(typeText("vini_attempToCreatAccountSuccessfully@email.com"));
        closeSoftKeyboard();
    }

    @Given("^Eu preencho campo de texto do cpf")
    public void cadastrarUsuario_preencherSenha() throws Exception {
        onView(withId(R.id.edtSenha))
                .perform(typeText("44502396605"));
        closeSoftKeyboard();
    }

    @Given("^Eu clico no botão de salvar$")
    public void cadastrarUsuario_clicarBotaoSalvar() throws Exception {
        onView(withId(R.id.btn_salvar_cadastro)).perform(click());
    }

    @Given("^Vejo dialog mostrando o cadastro realizado$")
    public void cadastrarUsuario_dialogCadastroSucesso() throws Exception {
        onView(withText("Cadastro")).check(matches(isDisplayed()));
    }*/
}
