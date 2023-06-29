package com.mediscreen.ui.view;

import com.mediscreen.ui.domain.Patient;
import com.mediscreen.ui.service.DoctorDBService;
import com.mediscreen.ui.service.PatientDBService;
import com.mediscreen.ui.view.component.PatientForm;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Mediscreen | Patients")
@Route(value = "patients", layout = MainView.class)
public class PatientListView extends VerticalLayout {
    private final Grid<Patient> grid = new Grid<>();
    private PatientForm form;

    private final Button createUserButton = new Button();

    private final PatientDBService patientService;
    private final DoctorDBService doctorDBService;

    @Autowired
    public PatientListView(PatientDBService patientService, DoctorDBService doctorDBService) {
        this.patientService = patientService;
        this.doctorDBService = doctorDBService;

        Button getAllBtn = new Button("Get all patients");
        getAllBtn.addClickListener(this::onAllPatientClick);

        add(getToolbar(), getContent(), getAllBtn);
    }

    private HorizontalLayout getToolbar() {

        HorizontalLayout formControlLayout = getFormControlPanel();

        HorizontalLayout toolbar = new HorizontalLayout(formControlLayout);
        toolbar.setFlexGrow(1, formControlLayout);
        toolbar.setWidthFull();

        return toolbar;
    }

    private HorizontalLayout getFormControlPanel() {
        createUserButton.addClickListener((buttonClickEvent) -> {
            if(!form.isVisible())
                addContact();
            else
                closeEditor();
        });

        HorizontalLayout controlPanel = new HorizontalLayout(createUserButton);
        controlPanel.setJustifyContentMode(JustifyContentMode.END);
        return controlPanel;
    }


    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(getGrid(), getPatientForm());
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    public Grid<Patient> getGrid() {
        updateGrid();
        grid.addColumn(Patient::getId).setHeader("ID");
        grid.addColumn(Patient::getFamily).setHeader("Family");
        grid.addColumn(Patient::getSex).setHeader("Sex");
        grid.addColumn(Patient::getDob).setHeader("DOB");
        grid.addColumn(Patient::getLastname).setHeader("Lastname");
        grid.addColumn(Patient::getFirstname).setHeader("Firstname");

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.asSingleSelect().addValueChangeListener(event -> editContact(event.getValue()));

        return grid;
    }

    public void onAllPatientClick(ClickEvent<Button> event) {
        patientService.getPatientById(1);
    }

    private void updateGrid() {
        grid.setItems(patientService.getAllPatient());
    }

    public void editContact(Patient patient) {

        if (patient == null) {
            closeEditor();
        } else {
            form.setPatient(patient);
            form.setVisible(true);
            updateCreateUserButton();
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setPatient(null);
        form.setVisible(false);
        updateCreateUserButton();
        removeClassName("editing");
    }

    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Patient());
    }

    private void updateCreateUserButton() {
        createUserButton.setText(form.isVisible()? "Hide Form" : "Create user");
    }

    private PatientForm getPatientForm() {
        form = new PatientForm(doctorDBService);
        form.setMaxWidth("25em");
        form.setFieldFullWidth();
        form.setVisible(false);
        updateCreateUserButton();

        form.addListener(PatientForm.SaveEvent.class,
                saveEvent -> {
                    Patient p = saveEvent.getSource().getPatient();
                    patientService.savePatient(p);
                    updateGrid();
                }
        );

        form.addListener(PatientForm.DeleteEvent.class,
                deleteEvent -> {
                    patientService.deleteUser(deleteEvent.getSource().getPatient());
                    updateGrid();
                }
        );

        form.addListener(PatientForm.CloseEvent.class,
                closeEvent -> closeEditor()
        );

        return form;
    }

}
