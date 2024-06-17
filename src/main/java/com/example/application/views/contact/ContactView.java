package com.example.application.views.contact;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import jakarta.annotation.security.PermitAll;

@PageTitle("Contact")
@Route(value = "", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class ContactView extends Composite<VerticalLayout> {

    private final TextField nameField;
    private final EmailField emailField;
    private final TextArea messageField;

    public ContactView() {
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3();
        HorizontalLayout layoutRow = new HorizontalLayout();
        nameField = new TextField();
        nameField.setId("nameInput");
        nameField.setRequired(true);
        nameField.setErrorMessage("Name is required");

        HorizontalLayout layoutRow2 = new HorizontalLayout();
        emailField = new EmailField();
        emailField.setId("emailInput");
        emailField.setRequired(true);
        emailField.setErrorMessage("Email is required");

        messageField = new TextArea();
        messageField.setId("messageInput");
        messageField.setRequired(true);
        messageField.setErrorMessage("Message is required");
        HorizontalLayout layoutRow3 = new HorizontalLayout();
        Button sendButton = new Button();

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        h3.setText("Contact Us");
        h3.setWidth("100%");
        layoutRow.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        nameField.setLabel("Name");
        nameField.setWidth("100%");
        layoutRow2.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        emailField.setLabel("Email");
        emailField.getStyle().set("flex-grow", "1");
        messageField.setLabel("Message");
        messageField.setWidth("100%");
        layoutRow3.addClassName(Gap.MEDIUM);
        layoutRow3.setWidth("100%");
        layoutRow3.getStyle().set("flex-grow", "1");
        sendButton.setText("Send");
        sendButton.setId("sendButton");
        sendButton.setWidth("min-content");
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sendButton.addClickListener(event -> handleSubmit());

        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(layoutRow);
        layoutRow.add(nameField);
        layoutColumn2.add(layoutRow2);
        layoutRow2.add(emailField);
        layoutColumn2.add(messageField);
        layoutColumn2.add(layoutRow3);
        layoutRow3.add(sendButton);

    }

    private void handleSubmit() {
        boolean isValid = true;

        if (nameField.isEmpty()) {
            nameField.setInvalid(true);
            isValid = false;
        } else {
            nameField.setInvalid(false);
        }

        if (emailField.isEmpty() || !emailField.getValue().contains("@")) {
            emailField.setInvalid(true);
            isValid = false;
        } else {
            emailField.setInvalid(false);
        }

        if (messageField.isEmpty()) {
            messageField.setInvalid(true);
            isValid = false;
        } else {
            messageField.setInvalid(false);
        }

        if (isValid) {
            // Process the form submission
            String name = nameField.getValue();
            String email = emailField.getValue();
            String message = messageField.getValue();

            System.out.println("Name: " + name);
            System.out.println("Email: " + email);
            System.out.println("Message: " + message);

            nameField.clear();
            emailField.clear();
            messageField.clear();

            nameField.setInvalid(false);
            emailField.setInvalid(false);
            messageField.setInvalid(false);

            // Show a success notification
            Notification.show("Message sent successfully!", 30000, Notification.Position.TOP_CENTER);
        }
    }
}
