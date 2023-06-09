package com.mediscreen.ui.view;

import com.mediscreen.ui.view.component.Header;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("")
@PageTitle("Mediscreen | Main")
public class MainView extends AppLayout {

    public MainView() {
        createHeader();
    }

    private void createHeader() {
        addToNavbar(new Header());
    }


}
