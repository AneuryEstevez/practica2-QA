package com.example.application.views.profile;

import com.example.application.data.User;
import com.example.application.security.AuthenticatedUser;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import jakarta.annotation.security.PermitAll;

import java.util.Optional;

@PageTitle("Profile")
@Route(value = "profile-view", layout = MainLayout.class)
@PermitAll
public class ProfileView extends Composite<VerticalLayout> {

    public ProfileView(AuthenticatedUser authenticatedUser) {
        VerticalLayout layoutColumn2 = new VerticalLayout();
        VerticalLayout layout3 = new VerticalLayout();
        H3 h3 = new H3();
        H5 h5 = new H5();
        HorizontalLayout layoutRow = new HorizontalLayout();
        Avatar avatar = new Avatar();
        avatar.setId("avatar");
        FormLayout formLayout2Col = new FormLayout();
        Paragraph textField = new Paragraph();
        textField.setId("name");
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        h3.setText("Personal Information");
        h3.setWidth("100%");
        h5.setText("Nombre");
        h5.setWidth("100%");
        layoutRow.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        layoutRow.setAlignItems(Alignment.CENTER);
        layoutRow.setJustifyContentMode(JustifyContentMode.CENTER);

        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            avatar.setName(user.getName());
            textField.setText(user.getName());
        }

        avatar.setWidth("200px");
        avatar.setHeight("200px");
        formLayout2Col.setWidth("100%");
        textField.setWidth("100%");
        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(layoutRow);
        layoutRow.add(avatar);
        layoutColumn2.add(formLayout2Col);
        layout3.add(h5);
        layout3.add(textField);
        formLayout2Col.add(layout3);
    }
}
