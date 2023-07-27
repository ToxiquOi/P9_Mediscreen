package com.mediscreen.ui.view.component;

import com.mediscreen.ui.view.PatientListView;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;

public class Header extends HorizontalLayout {

    public Header() {
        addClassNames("py-0", "px-m");
        setDefaultVerticalComponentAlignment(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);
        setWidth("100%");
        setHeight(14, Unit.MM);
        setMaxHeight(20, Unit.MM);

        getStyle().set("background-color", "white")
                        .set("border-bottom", "solig black 1px");

        createRouterLinks();
    }

    private void createRouterLinks() {
        RouterLink listLink = new RouterLink("Patients", PatientListView.class);
        Button listBtn = new Button(listLink);
        listBtn.getStyle().set("margin-left", "2mm")
                .set("color", "lightgray");
        listBtn.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        add(listBtn);
    }
}
