package com.mediscreen.ui.view.component;

import com.mediscreen.ui.domain.Patient;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;


import lombok.Getter;

@Getter
public class PatientForm extends AbstractUserForm {

    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");
    private final Button close = new Button("Cancel");

    public PatientForm() {
        super();
        createDefaultLayout();
    }

    @Override
    protected HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, patient)));

        close.addClickListener(event -> fireEvent(new CloseEvent(this)));
        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    @Override
    protected AbstractUserFormEvent createSaveEvent() {
        return new SaveEvent(this, patient);
    }

    public static class SaveEvent extends AbstractUserForm.AbstractUserFormEvent {
        SaveEvent(PatientForm source, Patient contact) {
            super(source, contact);
        }
    }

    public static class DeleteEvent extends AbstractUserForm.AbstractUserFormEvent {
        DeleteEvent(PatientForm source, Patient contact) {
            super(source, contact);
        }
    }

    public static class CloseEvent extends AbstractUserForm.AbstractUserFormEvent {
        CloseEvent(PatientForm source) {
            super(source, null);
        }
    }
}

