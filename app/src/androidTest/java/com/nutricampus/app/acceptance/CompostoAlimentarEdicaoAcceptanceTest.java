package com.nutricampus.app.acceptance;


import android.app.Activity;
import android.support.test.InstrumentationRegistry;
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
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
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
public class CompostoAlimentarEdicaoAcceptanceTest {
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
    //TA-01: Atualizar o composto alimentar por um nome já cadastrado;
    public void atualizarCompostoPorNomeJaExistente_TA1() {
        String id1 = "Identificador1";
        String id2 = "Identificador2";
        String tipo = "tipo1234";
        double ms = 999, fdn = 888, ee = 777;
        double mm = 666, cnf = 555, pb = 444;
        double ndt = 333, fda = 222;
        String descricao = "descricao1";

        RepositorioCompostosAlimentares repositorioCompostosAlimentares = new RepositorioCompostosAlimentares(InstrumentationRegistry.getTargetContext());
        CompostosAlimentares ca1 = new CompostosAlimentares(20, tipo, id1, ms, fdn, ee, mm, cnf, pb, ndt, fda, descricao);
        CompostosAlimentares ca2 = new CompostosAlimentares(21, tipo, id2, ms, fdn, ee, mm, cnf, pb, ndt, fda, descricao);

        repositorioCompostosAlimentares.inserirCompostoAlimentar(ca1);
        repositorioCompostosAlimentares.inserirCompostoAlimentar(ca2);

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

        onView(withText(id1))
                .perform(longClick());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
        appCompatTextView.perform(click());


        ViewInteraction appCompatEditText1 = onView(
                allOf(withId(R.id.input_composto_identificador), isDisplayed()));
        appCompatEditText1.perform(replaceText(id2), closeSoftKeyboard());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.btn_salvar_cadastro), withText("Atualizar"),
                        withParent(allOf(withId(R.id.tela_cadastrarcompostosalimentares),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton5.perform(click());
        try {
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
    //TA-02: Atualizar o composto alimentar por um nome ainda não cadastrado;
    public void atualizarCompostoNormalmente_TA2() {
        String id1 = "Identificador1";
        String id2 = "Identificador2";
        String tipo = "tipo1234";
        double ms = 999, fdn = 888, ee = 777;
        double mm = 666, cnf = 555, pb = 444;
        double ndt = 333, fda = 222;
        String descricao = "descricao1";

        RepositorioCompostosAlimentares repositorioCompostosAlimentares = new RepositorioCompostosAlimentares(InstrumentationRegistry.getTargetContext());
        CompostosAlimentares ca1 = new CompostosAlimentares(20, tipo, id1, ms, fdn, ee, mm, cnf, pb, ndt, fda, descricao);
        CompostosAlimentares ca2 = new CompostosAlimentares(21, tipo, id2, ms, fdn, ee, mm, cnf, pb, ndt, fda, descricao);

        repositorioCompostosAlimentares.inserirCompostoAlimentar(ca1);
        repositorioCompostosAlimentares.inserirCompostoAlimentar(ca2);

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

        onView(withText(id1))
                .perform(longClick());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.input_composto_identificador), isDisplayed()));
        appCompatEditText2.perform(replaceText(""), closeSoftKeyboard());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.input_composto_ms), isDisplayed()));
        appCompatEditText3.perform(replaceText(""), closeSoftKeyboard());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        appCompatEditText2.perform(replaceText("identificador999"), closeSoftKeyboard());
        appCompatEditText3.perform(replaceText("999999"), closeSoftKeyboard());
        try {
            Thread.sleep(500);
            new ToastMatcher().isToastMessageDisplayedWithText("Registro atualizado com sucesso");
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
    //TA-03: Atualizar o composto alimentar por um nome vazio;
    public void atualizarCompostoPorNomeVazio_TA3() {
        String id1 = "Identificador1";
        String id2 = "Identificador2";
        String tipo = "tipo1234";
        double ms = 999, fdn = 888, ee = 777;
        double mm = 666, cnf = 555, pb = 444;
        double ndt = 333, fda = 222;
        String descricao = "descricao1";

        RepositorioCompostosAlimentares repositorioCompostosAlimentares = new RepositorioCompostosAlimentares(InstrumentationRegistry.getTargetContext());
        CompostosAlimentares ca1 = new CompostosAlimentares(20, tipo, id1, ms, fdn, ee, mm, cnf, pb, ndt, fda, descricao);
        CompostosAlimentares ca2 = new CompostosAlimentares(21, tipo, id2, ms, fdn, ee, mm, cnf, pb, ndt, fda, descricao);

        repositorioCompostosAlimentares.inserirCompostoAlimentar(ca1);
        repositorioCompostosAlimentares.inserirCompostoAlimentar(ca2);

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

        onView(withText(id1))
                .perform(longClick());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.input_composto_identificador), isDisplayed()));
        appCompatEditText2.perform(replaceText(""), closeSoftKeyboard());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.btn_salvar_cadastro), withText("Atualizar"),
                        withParent(allOf(withId(R.id.tela_cadastrarcompostosalimentares),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton6.perform(click());
        try {
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
    //TA-04: Atualizar o composto alimentar deixando um nutriente vazio;
    public void atualizarCompostoPorNomeJaExistente_TA4() {
        String id1 = "Identificador1";
        String id2 = "Identificador2";
        String tipo = "tipo1234";
        double ms = 999, fdn = 888, ee = 777;
        double mm = 666, cnf = 555, pb = 444;
        double ndt = 333, fda = 222;
        String descricao = "descricao1";

        RepositorioCompostosAlimentares repositorioCompostosAlimentares = new RepositorioCompostosAlimentares(InstrumentationRegistry.getTargetContext());
        CompostosAlimentares ca1 = new CompostosAlimentares(20, tipo, id1, ms, fdn, ee, mm, cnf, pb, ndt, fda, descricao);
        CompostosAlimentares ca2 = new CompostosAlimentares(21, tipo, id2, ms, fdn, ee, mm, cnf, pb, ndt, fda, descricao);

        repositorioCompostosAlimentares.inserirCompostoAlimentar(ca1);
        repositorioCompostosAlimentares.inserirCompostoAlimentar(ca2);

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

        onView(withText(id1))
                .perform(longClick());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.input_composto_ms), isDisplayed()));
        appCompatEditText3.perform(replaceText(""), closeSoftKeyboard());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.btn_salvar_cadastro), withText("Atualizar"),
                        withParent(allOf(withId(R.id.tela_cadastrarcompostosalimentares),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton7.perform(click());
        try {
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

    /*@Test
    public void compostoAlimentarEdicao_TA1_TA2_TA3_TA4() {
        String id1 = "Identificador1";
        String id2 = "Identificador2";
        String tipo = "tipo1234";
        double ms = 999, fdn = 888, ee = 777;
        double mm = 666, cnf = 555, pb = 444;
        double ndt = 333, fda = 222;
        String descricao = "descricao1";

        RepositorioCompostosAlimentares repositorioCompostosAlimentares = new RepositorioCompostosAlimentares(InstrumentationRegistry.getTargetContext());
        CompostosAlimentares ca1 = new CompostosAlimentares(20, tipo, id1, ms, fdn, ee, mm, cnf, pb, ndt, fda, descricao);
        CompostosAlimentares ca2 = new CompostosAlimentares(21, tipo, id2, ms, fdn, ee, mm, cnf, pb, ndt, fda, descricao);

        repositorioCompostosAlimentares.inserirCompostoAlimentar(ca1);
        repositorioCompostosAlimentares.inserirCompostoAlimentar(ca2);

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

        onView(withText(id1))
                .perform(longClick());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
        appCompatTextView.perform(click());

        //TA-01: Atualizar o composto alimentar por um nome já cadastrado;
        ViewInteraction appCompatEditText1 = onView(
                allOf(withId(R.id.input_composto_identificador), isDisplayed()));
        appCompatEditText1.perform(replaceText(id2), closeSoftKeyboard());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.btn_salvar_cadastro), withText("Atualizar"),
                        withParent(allOf(withId(R.id.tela_cadastrarcompostosalimentares),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton5.perform(click());
        try {
            new ToastMatcher().isToastMessageDisplayedWithText("Composto já cadastrado!");
            Thread.sleep(3500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //TA-03: Atualizar o composto alimentar por um nome vazio;
        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.input_composto_identificador), isDisplayed()));
        appCompatEditText2.perform(replaceText(""), closeSoftKeyboard());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.btn_salvar_cadastro), withText("Atualizar"),
                        withParent(allOf(withId(R.id.tela_cadastrarcompostosalimentares),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton6.perform(click());
        try {
            new ToastMatcher().isToastMessageDisplayedWithText("Preencha todos os campos");
            Thread.sleep(3500);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //TA-04: Atualizar o composto alimentar deixando um nutriente vazio;
        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.input_composto_ms), isDisplayed()));
        appCompatEditText3.perform(replaceText(""), closeSoftKeyboard());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.btn_salvar_cadastro), withText("Atualizar"),
                        withParent(allOf(withId(R.id.tela_cadastrarcompostosalimentares),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton7.perform(click());
        try {
            new ToastMatcher().isToastMessageDisplayedWithText("Preencha todos os campos");
            Thread.sleep(3500);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //TA-02: Atualizar o composto alimentar por um nome ainda não cadastrado;
        appCompatEditText2.perform(replaceText("identificador999"), closeSoftKeyboard());
        appCompatEditText3.perform(replaceText("999999"), closeSoftKeyboard());
        try {
            Thread.sleep(500);
            new ToastMatcher().isToastMessageDisplayedWithText("Registro atualizado com sucesso");
            Thread.sleep(3500);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //repositorioCompostosAlimentares.removerCompostoAlimentar(ca2);
        List<CompostosAlimentares> arr = repositorioCompostosAlimentares.buscarTodosCompostos("identificador");
        for (CompostosAlimentares in : arr) {
            repositorioCompostosAlimentares.removerCompostoAlimentar(in);
        }
    }*/

}
