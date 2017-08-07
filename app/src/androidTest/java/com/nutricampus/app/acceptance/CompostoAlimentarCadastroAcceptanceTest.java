package com.nutricampus.app.acceptance;


import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.test.suitebuilder.annotation.LargeTest;

import com.nutricampus.app.R;
import com.nutricampus.app.activities.LoginActivity;
import com.nutricampus.app.activities.MainActivity;
import com.nutricampus.app.database.RepositorioCompostosAlimentares;
import com.nutricampus.app.entities.CompostosAlimentares;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;
import java.util.List;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CompostoAlimentarCadastroAcceptanceTest {
    public Activity currentActivity = null;

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    public Activity getActivityInstance() {
        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
                if (resumedActivities.iterator().hasNext()) {
                    currentActivity = (Activity) resumedActivities.iterator().next();
                }
            }
        });

        return currentActivity;
    }

    @Test
    //TA-01: Cadastrar novos compostos alimentares sem informar seus nutrientes;
    public void cadastrarCompostoSemNutrientes_TA1() {
        String id1 = "identificador123";
        String id2 = "identificador456";

        RepositorioCompostosAlimentares repositorioCompostosAlimentares = new RepositorioCompostosAlimentares(InstrumentationRegistry.getTargetContext());

        if (getActivityInstance() instanceof MainActivity) {
            //dummy if
            try {
                Thread.sleep(4500);//toast de boas vindas
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {

            ViewInteraction appCompatEditText = onView(
                    allOf(withId(R.id.input_usuario), isDisplayed()));
            appCompatEditText.perform(click());
            ViewInteraction appCompatEditText2 = onView(
                    allOf(withId(R.id.input_usuario), isDisplayed()));
            appCompatEditText2.perform(replaceText("admin"), closeSoftKeyboard());

            ViewInteraction appCompatEditText3 = onView(
                    allOf(withId(R.id.input_senha), isDisplayed()));
            appCompatEditText3.perform(replaceText("admin"), closeSoftKeyboard());

            ViewInteraction appCompatButton = onView(
                    allOf(withId(R.id.btn_login), withText("Entrar"), isDisplayed()));
            appCompatButton.perform(click());

        }
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Abrir"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(allOf(withId(R.id.material_drawer_slider_layout),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(6, click()));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.btn_add_composto_alimentar), isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.input_composto_identificador), isDisplayed()));
        appCompatEditText4.perform(click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.input_composto_identificador), isDisplayed()));
        appCompatEditText5.perform(replaceText(id1), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btn_salvar_cadastro), withText("Salvar"),
                        withParent(allOf(withId(R.id.tela_cadastrarcompostosalimentares),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton2.perform(click());
        try {
            Thread.sleep(500);
            new ToastMatcher().isToastMessageDisplayedWithText("Preencha todos os campos");
            Thread.sleep(3500);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<CompostosAlimentares> arr = repositorioCompostosAlimentares.buscarTodosCompostos("identificador");
        for (CompostosAlimentares in : arr) {
            repositorioCompostosAlimentares.removerCompostoAlimentar(in);
        }
    }

    @Test
    //TA-02: Cadastrar novos compostos alimentares informando todos os seus nutrientes;
    public void cadastrarCompostoComNutrientes_TA2() {
        String id1 = "identificador123";
        String id2 = "identificador456";

        RepositorioCompostosAlimentares repositorioCompostosAlimentares = new RepositorioCompostosAlimentares(InstrumentationRegistry.getTargetContext());

        if (getActivityInstance() instanceof MainActivity) {
            //dummy if
            try {
                Thread.sleep(4500);//toast de boas vindas
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {

            ViewInteraction appCompatEditText = onView(
                    allOf(withId(R.id.input_usuario), isDisplayed()));
            appCompatEditText.perform(click());
            ViewInteraction appCompatEditText2 = onView(
                    allOf(withId(R.id.input_usuario), isDisplayed()));
            appCompatEditText2.perform(replaceText("admin"), closeSoftKeyboard());

            ViewInteraction appCompatEditText3 = onView(
                    allOf(withId(R.id.input_senha), isDisplayed()));
            appCompatEditText3.perform(replaceText("admin"), closeSoftKeyboard());

            ViewInteraction appCompatButton = onView(
                    allOf(withId(R.id.btn_login), withText("Entrar"), isDisplayed()));
            appCompatButton.perform(click());

        }
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Abrir"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(allOf(withId(R.id.material_drawer_slider_layout),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(6, click()));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.btn_add_composto_alimentar), isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText01 = onView(
                allOf(withId(R.id.input_composto_identificador), isDisplayed()));
        appCompatEditText01.perform(click());

        ViewInteraction appCompatEditText02 = onView(
                allOf(withId(R.id.input_composto_identificador), isDisplayed()));
        appCompatEditText02.perform(replaceText(id1), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.input_composto_tipo), isDisplayed()));
        appCompatEditText6.perform(replaceText("22"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.input_composto_ms), isDisplayed()));
        appCompatEditText7.perform(replaceText("11"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.input_composto_fdn), isDisplayed()));
        appCompatEditText8.perform(replaceText("22"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.input_composto_ee), isDisplayed()));
        appCompatEditText9.perform(replaceText("33"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.input_composto_mm), isDisplayed()));
        appCompatEditText10.perform(replaceText("44"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.input_composto_cnf), isDisplayed()));
        appCompatEditText11.perform(replaceText("55"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.input_composto_pb), isDisplayed()));
        appCompatEditText12.perform(replaceText("55"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.input_composto_ndt), isDisplayed()));
        appCompatEditText14.perform(replaceText("66"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText15 = onView(
                allOf(withId(R.id.input_composto_fda), isDisplayed()));
        appCompatEditText15.perform(replaceText("77"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText16 = onView(
                allOf(withId(R.id.input_composto_descricao), isDisplayed()));
        appCompatEditText16.perform(replaceText("descrição"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatButton01 = onView(
                allOf(withId(R.id.btn_salvar_cadastro), withText("Salvar"),
                        withParent(allOf(withId(R.id.tela_cadastrarcompostosalimentares),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton01.perform(click());
        try {
            Thread.sleep(500);
            new ToastMatcher().isToastMessageDisplayedWithText("Cadastro realizado com sucesso");
            Thread.sleep(3500);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<CompostosAlimentares> arr = repositorioCompostosAlimentares.buscarTodosCompostos("identificador");
        for (CompostosAlimentares in : arr) {
            repositorioCompostosAlimentares.removerCompostoAlimentar(in);
        }
    }

    @Test
    //TA-03: Cadastrar novos compostos alimentares que já estejam na base de dados;
    public void cadastrarCompostoComIdentificadorJaExistente_TA3() {
        String id1 = "identificador123";
        String id2 = "identificador456";

        RepositorioCompostosAlimentares repositorioCompostosAlimentares = new RepositorioCompostosAlimentares(InstrumentationRegistry.getTargetContext());

        if (getActivityInstance() instanceof MainActivity) {
            //dummy if
            try {
                Thread.sleep(4500);//toast de boas vindas
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {

            ViewInteraction appCompatEditText = onView(
                    allOf(withId(R.id.input_usuario), isDisplayed()));
            appCompatEditText.perform(click());
            ViewInteraction appCompatEditText2 = onView(
                    allOf(withId(R.id.input_usuario), isDisplayed()));
            appCompatEditText2.perform(replaceText("admin"), closeSoftKeyboard());

            ViewInteraction appCompatEditText3 = onView(
                    allOf(withId(R.id.input_senha), isDisplayed()));
            appCompatEditText3.perform(replaceText("admin"), closeSoftKeyboard());

            ViewInteraction appCompatButton = onView(
                    allOf(withId(R.id.btn_login), withText("Entrar"), isDisplayed()));
            appCompatButton.perform(click());

        }
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Abrir"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(allOf(withId(R.id.material_drawer_slider_layout),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(6, click()));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Adicionando primeiro composto
        ViewInteraction floatingActionButton1 = onView(
                allOf(withId(R.id.btn_add_composto_alimentar), isDisplayed()));
        floatingActionButton1.perform(click());

        ViewInteraction appCompatEditText55 = onView(
                allOf(withId(R.id.input_composto_identificador), isDisplayed()));
        appCompatEditText55.perform(replaceText(id1), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText66 = onView(
                allOf(withId(R.id.input_composto_tipo), isDisplayed()));
        appCompatEditText66.perform(replaceText("22"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText77 = onView(
                allOf(withId(R.id.input_composto_ms), isDisplayed()));
        appCompatEditText77.perform(replaceText("11"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText88 = onView(
                allOf(withId(R.id.input_composto_fdn), isDisplayed()));
        appCompatEditText88.perform(replaceText("22"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText99 = onView(
                allOf(withId(R.id.input_composto_ee), isDisplayed()));
        appCompatEditText99.perform(replaceText("33"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText100 = onView(
                allOf(withId(R.id.input_composto_mm), isDisplayed()));
        appCompatEditText100.perform(replaceText("44"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText111 = onView(
                allOf(withId(R.id.input_composto_cnf), isDisplayed()));
        appCompatEditText111.perform(replaceText("55"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText122 = onView(
                allOf(withId(R.id.input_composto_pb), isDisplayed()));
        appCompatEditText122.perform(replaceText("55"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText133 = onView(
                allOf(withId(R.id.input_composto_pb), withText("55"), isDisplayed()));
        appCompatEditText133.perform(pressImeActionButton());
        closeKeyboard();

        ViewInteraction appCompatEditText144 = onView(
                allOf(withId(R.id.input_composto_ndt), isDisplayed()));
        appCompatEditText144.perform(replaceText("66"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText155 = onView(
                allOf(withId(R.id.input_composto_fda), isDisplayed()));
        appCompatEditText155.perform(replaceText("77"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText166 = onView(
                allOf(withId(R.id.input_composto_descricao), isDisplayed()));
        appCompatEditText166.perform(replaceText("descrição"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatButton33 = onView(
                allOf(withId(R.id.btn_salvar_cadastro), withText("Salvar"),
                        withParent(allOf(withId(R.id.tela_cadastrarcompostosalimentares),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton33.perform(click());
        try {
            Thread.sleep(500);
            new ToastMatcher().isToastMessageDisplayedWithText("Cadastro realizado com sucesso");
            Thread.sleep(3500);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Adicionando segundo composto (com mesmo identificador)
        ViewInteraction floatingActionButton11 = onView(
                allOf(withId(R.id.btn_add_composto_alimentar), isDisplayed()));
        floatingActionButton11.perform(click());

        ViewInteraction appCompatEditText555 = onView(
                allOf(withId(R.id.input_composto_identificador), isDisplayed()));
        appCompatEditText555.perform(replaceText(id1), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText666 = onView(
                allOf(withId(R.id.input_composto_tipo), isDisplayed()));
        appCompatEditText666.perform(replaceText("22"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText777 = onView(
                allOf(withId(R.id.input_composto_ms), isDisplayed()));
        appCompatEditText777.perform(replaceText("11"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText888 = onView(
                allOf(withId(R.id.input_composto_fdn), isDisplayed()));
        appCompatEditText888.perform(replaceText("22"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText999 = onView(
                allOf(withId(R.id.input_composto_ee), isDisplayed()));
        appCompatEditText999.perform(replaceText("33"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText1000 = onView(
                allOf(withId(R.id.input_composto_mm), isDisplayed()));
        appCompatEditText1000.perform(replaceText("44"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText1111 = onView(
                allOf(withId(R.id.input_composto_cnf), isDisplayed()));
        appCompatEditText1111.perform(replaceText("55"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText1222 = onView(
                allOf(withId(R.id.input_composto_pb), isDisplayed()));
        appCompatEditText1222.perform(replaceText("55"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText1333 = onView(
                allOf(withId(R.id.input_composto_pb), withText("55"), isDisplayed()));
        appCompatEditText1333.perform(pressImeActionButton());
        closeKeyboard();

        ViewInteraction appCompatEditText1444 = onView(
                allOf(withId(R.id.input_composto_ndt), isDisplayed()));
        appCompatEditText1444.perform(replaceText("66"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText1555 = onView(
                allOf(withId(R.id.input_composto_fda), isDisplayed()));
        appCompatEditText1555.perform(replaceText("77"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText1666 = onView(
                allOf(withId(R.id.input_composto_descricao), isDisplayed()));
        appCompatEditText1666.perform(replaceText("descrição"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatButton333 = onView(
                allOf(withId(R.id.btn_salvar_cadastro), withText("Salvar"),
                        withParent(allOf(withId(R.id.tela_cadastrarcompostosalimentares),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton333.perform(click());
        try {
            Thread.sleep(500);
            new ToastMatcher().isToastMessageDisplayedWithText("Composto já cadastrado!");
            Thread.sleep(3500);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<CompostosAlimentares> arr = repositorioCompostosAlimentares.buscarTodosCompostos("identificador");
        for (CompostosAlimentares in : arr) {
            repositorioCompostosAlimentares.removerCompostoAlimentar(in);
        }
    }

    @Test
    //TA-04: Cadastrar novos compostos alimentares que não estejam na base de dados;
    public void cadastrarCompostoPreenchendoTodosOsCampos_TA4() {
        String id1 = "identificador123";
        String id2 = "identificador456";

        RepositorioCompostosAlimentares repositorioCompostosAlimentares = new RepositorioCompostosAlimentares(InstrumentationRegistry.getTargetContext());

        if (getActivityInstance() instanceof MainActivity) {
            //dummy if
            try {
                Thread.sleep(4500);//toast de boas vindas
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {

            ViewInteraction appCompatEditText = onView(
                    allOf(withId(R.id.input_usuario), isDisplayed()));
            appCompatEditText.perform(click());
            ViewInteraction appCompatEditText2 = onView(
                    allOf(withId(R.id.input_usuario), isDisplayed()));
            appCompatEditText2.perform(replaceText("admin"), closeSoftKeyboard());

            ViewInteraction appCompatEditText3 = onView(
                    allOf(withId(R.id.input_senha), isDisplayed()));
            appCompatEditText3.perform(replaceText("admin"), closeSoftKeyboard());

            ViewInteraction appCompatButton = onView(
                    allOf(withId(R.id.btn_login), withText("Entrar"), isDisplayed()));
            appCompatButton.perform(click());

        }
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Abrir"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(allOf(withId(R.id.material_drawer_slider_layout),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(6, click()));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.btn_add_composto_alimentar), isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText01 = onView(
                allOf(withId(R.id.input_composto_identificador), isDisplayed()));
        appCompatEditText01.perform(click());

        ViewInteraction appCompatEditText02 = onView(
                allOf(withId(R.id.input_composto_identificador), isDisplayed()));
        appCompatEditText02.perform(replaceText(id1), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.input_composto_tipo), isDisplayed()));
        appCompatEditText6.perform(replaceText("22"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.input_composto_ms), isDisplayed()));
        appCompatEditText7.perform(replaceText("11"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.input_composto_fdn), isDisplayed()));
        appCompatEditText8.perform(replaceText("22"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.input_composto_ee), isDisplayed()));
        appCompatEditText9.perform(replaceText("33"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.input_composto_mm), isDisplayed()));
        appCompatEditText10.perform(replaceText("44"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.input_composto_cnf), isDisplayed()));
        appCompatEditText11.perform(replaceText("55"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.input_composto_pb), isDisplayed()));
        appCompatEditText12.perform(replaceText("55"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.input_composto_ndt), isDisplayed()));
        appCompatEditText14.perform(replaceText("66"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText15 = onView(
                allOf(withId(R.id.input_composto_fda), isDisplayed()));
        appCompatEditText15.perform(replaceText("77"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatEditText16 = onView(
                allOf(withId(R.id.input_composto_descricao), isDisplayed()));
        appCompatEditText16.perform(replaceText("descrição"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatButton01 = onView(
                allOf(withId(R.id.btn_salvar_cadastro), withText("Salvar"),
                        withParent(allOf(withId(R.id.tela_cadastrarcompostosalimentares),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton01.perform(click());
        try {
            Thread.sleep(500);
            new ToastMatcher().isToastMessageDisplayedWithText("Cadastro realizado com sucesso");
            Thread.sleep(3500);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<CompostosAlimentares> arr = repositorioCompostosAlimentares.buscarTodosCompostos("identificador");
        for (CompostosAlimentares in : arr) {
            repositorioCompostosAlimentares.removerCompostoAlimentar(in);
        }
    }

    public void closeKeyboard() {
        try {
            Espresso.closeSoftKeyboard();
            Thread.sleep(500);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
