package com.nutricampus.app.entities;

import android.app.Activity;
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

/**
 * @author Paulo Mateus <paulomatew@gmail.com> on 29/05/17.
 */

public class ActionBarManager {
    private Activity _act;
    private Toolbar toolbar;

    public Drawer mActionBar;

    public ActionBarManager(Activity _act, Toolbar toolbar) {
        this._act = _act;
        this.toolbar = toolbar;

        initActionBar();
    }

    private void initActionBar() {
        new DrawerBuilder().withActivity(_act).build();


        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(_act)
                .withHeaderBackground(R.drawable.header)
                .withTranslucentStatusBar(false)
                .addProfiles(
                        new ProfileDrawerItem().withName("João da Silva").withEmail("jsilva@email.com")
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
                .withActivity(_act)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new SecondaryDrawerItem().withIdentifier(0).withName("Início").withIcon(FontAwesome.Icon.faw_inbox),
                        /*separator*/new SectionDrawerItem().withName("Controle"),
                        new SecondaryDrawerItem().withIdentifier(1).withName("Propriedades").withIcon(FontAwesome.Icon.faw_home),
                        new SecondaryDrawerItem().withIdentifier(2).withName("Grupos").withIcon(FontAwesome.Icon.faw_object_group),
                        new SecondaryDrawerItem().withIdentifier(3).withName("Animais").withIcon(FontAwesome.Icon.faw_paw),
                        new SecondaryDrawerItem().withIdentifier(4).withName("Compostos Alimentares").withIcon(FontAwesome.Icon.faw_list),
                        /*separator*/new SectionDrawerItem().withName("Relatórios"),
                        new SecondaryDrawerItem().withIdentifier(5).withName("Estatísticas").withIcon(FontAwesome.Icon.faw_bar_chart),
                        new SecondaryDrawerItem().withIdentifier(6).withName("Cálculo de Dieta").withIcon(FontAwesome.Icon.faw_calculator),
                        /*separator*/new SectionDrawerItem().withName("Licença"),
                        new SecondaryDrawerItem().withIdentifier(7).withName("Assinatura").withIcon(FontAwesome.Icon.faw_shopping_cart),
                        /*separator*/ new DividerDrawerItem(),
                        new SecondaryDrawerItem().withIdentifier(8).withSelectable(false).withName("Compartilhe").withIcon(FontAwesome.Icon.faw_share_alt),
                        new SecondaryDrawerItem().withIdentifier(9).withSelectable(false).withName("Dúvidas").withIcon(FontAwesome.Icon.faw_question),
                        new SecondaryDrawerItem().withIdentifier(10).withName("Sobre").withIcon(FontAwesome.Icon.faw_info_circle)

                )
                .addStickyDrawerItems(new SecondaryDrawerItem().withSelectable(false).withIdentifier(11).withName("Configurações").withIcon(FontAwesome.Icon.faw_cog))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        return false;
                    }
                })
                .build();
    }
}
