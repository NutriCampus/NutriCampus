package com.nutricampus.app.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nutricampus.app.R;
import com.nutricampus.app.entities.ActionBarManager;

/**
 * @author Paulo Mateus <paulomatew@gmail.com> on 29/05/17.
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ActionBarManager actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = new ActionBarManager(this, toolbar);

        /*new DrawerBuilder().withActivity(this).build();

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
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


        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new SecondaryDrawerItem().withIdentifier(0).withName("Início").withIcon(FontAwesome.Icon.faw_home),
                        new SectionDrawerItem().withName("Controle"),
                        new SecondaryDrawerItem().withIdentifier(0).withName("Propriedades").withIcon(FontAwesome.Icon.faw_home),
                        new SecondaryDrawerItem().withIdentifier(0).withName("Grupos").withIcon(FontAwesome.Icon.faw_object_group),
                        new SecondaryDrawerItem().withIdentifier(0).withName("Animais").withIcon(FontAwesome.Icon.faw_paw),
                        new SectionDrawerItem().withName("Relatórios"),
                        new SecondaryDrawerItem().withIdentifier(0).withName("Estatísticas").withIcon(FontAwesome.Icon.faw_home),
                        new SectionDrawerItem().withName("Licença"),
                        new SecondaryDrawerItem().withIdentifier(0).withName("Compras").withIcon(FontAwesome.Icon.faw_shopping_cart),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withIdentifier(4).withSelectable(false).withName("Compartilhe").withIcon(FontAwesome.Icon.faw_share_alt),
                        new SecondaryDrawerItem().withIdentifier(6).withSelectable(false).withName("Dúvidas").withIcon(FontAwesome.Icon.faw_question),
                        new SecondaryDrawerItem().withIdentifier(7).withName("Sobre").withIcon(FontAwesome.Icon.faw_bullhorn)

                )
                .addStickyDrawerItems(new SecondaryDrawerItem().withSelectable(false).withIdentifier(8).withName("Configurações").withIcon(FontAwesome.Icon.faw_cog))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        return false;
                    }
                })
                .build();*/

        //actionBar = new ActionBarManager(MainActivity.this, toolbar);
        ///actionBar.setTitleActionBar(getResources().getString(R.string.app_name));
        //actionBar.setTitleActionBar("AAAAAAAAAAAA");
        //actionBar.mActionBar.openDrawer();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/
    }

    @Override
    public void onBackPressed() {
        if (actionBar.mActionBar.isDrawerOpen()) {
            actionBar.mActionBar.closeDrawer();
        } else {
            super.onBackPressed();
        }
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
