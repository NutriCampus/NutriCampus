package com.nutricampus.app.entities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.nutricampus.app.R;
import com.nutricampus.app.activities.ListaAnimaisActivity;
import com.nutricampus.app.activities.ListaPropriedadesActivity;
import com.nutricampus.app.database.SharedPreferencesManager;

/**
 * Created by Mateus on 29/05/2017.
 * For project NutriCampus.
 * Contact: <paulomatew@gmail.com>
 */

public class ActionBarManager {
    private Activity activity;
    private Toolbar toolbar;
    private Drawer mActionBar;

    SharedPreferencesManager session;

    public ActionBarManager(Activity activity, Toolbar toolbar) {
        this.activity = activity;
        this.toolbar = toolbar;

        initActionBar();
    }

    private void initActionBar() {
        session = new SharedPreferencesManager(activity);
        new DrawerBuilder().withActivity(activity).build();


        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.header)
                .withTranslucentStatusBar(false)
                .addProfiles(
                        new ProfileDrawerItem().withName(session.getUsuarioNC()).withEmail(session.getEmailNC())
                        //.withIcon(getResources().getDrawable(R.drawable.profile))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        mActionBar = new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new SecondaryDrawerItem().withIdentifier(0).withName("Início").withIcon(FontAwesome.Icon.faw_inbox),
                        new SectionDrawerItem().withName("Controle"),
                        new SecondaryDrawerItem().withIdentifier(1).withSelectable(false).withName("Propriedades").withIcon(FontAwesome.Icon.faw_home),
                        new SecondaryDrawerItem().withIdentifier(2).withSelectable(false).withName("Grupos").withIcon(FontAwesome.Icon.faw_object_group),
                        new SecondaryDrawerItem().withIdentifier(3).withSelectable(false).withName("Animais").withIcon(FontAwesome.Icon.faw_paw),
                        new SecondaryDrawerItem().withIdentifier(4).withSelectable(false).withName("Compostos Alimentares").withIcon(FontAwesome.Icon.faw_list),
                        new SectionDrawerItem().withName("Relatórios"),
                        new SecondaryDrawerItem().withIdentifier(5).withSelectable(false).withName("Estatísticas").withIcon(FontAwesome.Icon.faw_bar_chart),
                        new SecondaryDrawerItem().withIdentifier(6).withSelectable(false).withName("Cálculo de Dieta").withIcon(FontAwesome.Icon.faw_calculator),
                        new SectionDrawerItem().withName("Licença"),
                        new SecondaryDrawerItem().withIdentifier(7).withSelectable(false).withName("Assinatura").withIcon(FontAwesome.Icon.faw_shopping_cart),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withIdentifier(8).withSelectable(false).withName("Compartilhe").withIcon(FontAwesome.Icon.faw_share_alt),
                        new SecondaryDrawerItem().withIdentifier(9).withSelectable(false).withName("Dúvidas").withIcon(FontAwesome.Icon.faw_question),
                        new SecondaryDrawerItem().withIdentifier(10).withName("Sobre").withIcon(FontAwesome.Icon.faw_info_circle)
                )
                .addStickyDrawerItems(new SecondaryDrawerItem().withSelectable(false).withIdentifier(11).withName("Configurações").withIcon(FontAwesome.Icon.faw_cog),
                        new SecondaryDrawerItem().withSelectable(false).withIdentifier(12).withName("Sair").withIcon(FontAwesome.Icon.faw_sign_out))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Intent intent = null;
                        switch ((int) drawerItem.getIdentifier()){
                            case 1:
                                intent = new Intent(activity, ListaPropriedadesActivity.class);
                                break;
                            case 3:
                                intent = new Intent(activity, ListaAnimaisActivity.class);
                                break;
                            case 12: // Sair
                                session.logoutUser();
                                activity.finish();
                                break;
                            default:
                                break;

                        }

                        if (intent != null)
                            activity.startActivity(intent);

                        return false;
                    }
                })
                .build();
    }

    public Drawer getmActionBar() {
        return mActionBar;
    }

    public void setmActionBar(Drawer mActionBar) {
        this.mActionBar = mActionBar;
    }
}
