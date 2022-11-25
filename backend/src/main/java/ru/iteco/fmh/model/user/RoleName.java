package ru.iteco.fmh.model.user;

public enum RoleName {
    ROLE_ADMINISTRATOR("Администратор"),
    ROLE_MEDICAL_WORKER("Медицинский работник");

    private final String roleName;

    RoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
