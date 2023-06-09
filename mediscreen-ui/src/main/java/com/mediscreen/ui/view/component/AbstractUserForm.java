package com.mediscreen.ui.view.component;

import com.mediscreen.ui.domain.Patient;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import lombok.Getter;

@Getter
public abstract class AbstractUserForm extends FormLayout {

    protected final TextField firstname = new TextField("First Name");
    protected final TextField lastname = new TextField("Last Name");
    protected final TextField family = new TextField("Family");
    protected final TextField sex = new TextField("Sex");
    protected final DatePicker dob = new DatePicker("Birthdate");

    protected final Binder<Patient> binder = new BeanValidationBinder<>(Patient.class);

    protected Patient patient;

    protected AbstractUserForm() {
        binder.bindInstanceFields(this);
    }

    /**
     * Need to be invoked in derived constructor if form struct not require change
     */
    protected void createDefaultLayout() {
        VerticalLayout formVerticalLayout = new VerticalLayout(firstname, lastname, family, sex, dob, createButtonsLayout());
        formVerticalLayout.addClassName("user-form-vertical-layout");
        formVerticalLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        add(formVerticalLayout);
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        binder.readBean(patient);
    }

    public void setFormReadOnly(boolean enabled) {
        getFirstname().setReadOnly(enabled);
        getLastname().setReadOnly(enabled);
        getDob().setReadOnly(enabled);
        getFamily().setReadOnly(enabled);
        getSex().setReadOnly(enabled);
    }

    public void setFieldFullWidth() {
        dob.setWidthFull();
        sex.setWidthFull();
        firstname.setWidthFull();
        lastname.setWidthFull();
        family.setWidthFull();
    }

    protected void validateAndSave() {
        try {
            binder.writeBean(patient);
            fireEvent(createSaveEvent());
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    protected abstract HorizontalLayout createButtonsLayout();

    protected abstract AbstractUserFormEvent createSaveEvent();


    @Getter
    public static abstract class AbstractUserFormEvent extends ComponentEvent<AbstractUserForm> {

        private final Patient patient;

        public AbstractUserFormEvent(AbstractUserForm source, Patient patient) {
            super(source, false);
            this.patient = patient;
        }
    }
}