package com.mediscreen.ui.view.component;

import com.mediscreen.ui.view.PatientListView;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;

public class Header extends HorizontalLayout {

    public Header() {
        createRouterLinks();
    }

    private void createRouterLinks() {
        RouterLink listLink = new RouterLink("List", PatientListView.class);
        add(listLink);
    }
}
