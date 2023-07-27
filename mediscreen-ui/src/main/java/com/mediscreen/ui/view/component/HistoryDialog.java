package com.mediscreen.ui.view.component;

import com.mediscreen.ui.domain.Patient;
import com.mediscreen.ui.service.DoctorDBService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;

public class HistoryDialog extends Dialog {
    private final DoctorDBService ddbService;
    private final Patient patient;
    Grid<String> grid = new Grid<>();

    public HistoryDialog(DoctorDBService ddbService,Patient p) {
        this.ddbService = ddbService;
        this.patient = p;
        setWidth("90%");
        setHeight("90%");

        Button closeButton = new Button("Close", e -> close());
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidthFull();
        verticalLayout.setHeightFull();
        verticalLayout.add(getGrid(), getForm());

        setHeaderTitle("Patient history");
        add(verticalLayout);
        getFooter().add(closeButton);
    }

    public Grid<String> getGrid() {
        grid.setWidthFull();
        grid.setHeight("90%");


        updateGrid();
        grid.addColumn(s->s).setHeader("Notes");
        grid.setSelectionMode(Grid.SelectionMode.NONE);

        return grid;
    }

    public HorizontalLayout getForm() {
        TextArea textArea = new TextArea();
        Button send = new Button("Send");
        send.addClickListener(event -> {
            if(!textArea.isEmpty()) {
                patient.getHistory().add(textArea.getValue());
                grid.setItems(ddbService.addHistory(patient));
                textArea.clear();
            }
        });
        HorizontalLayout layout = new HorizontalLayout(textArea, send);
        layout.setFlexGrow(3, textArea);
        layout.setWidthFull();
        return layout;
    }

    private void updateGrid() {
        grid.setItems(patient.getHistory());
    }
}
