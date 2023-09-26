package enums;

import enums.Role;

public enum ActionPermission {
    ADD(new Role[]{ Role.ADMIN}),
    EDIT(new Role[]{ Role.ADMIN}),
    REMOVE(new Role[]{ Role.ADMIN}),
    LIST(new Role[]{ Role.USER, Role.ADMIN}),
    FILTER_BY_TITLE(new Role[]{ Role.USER, Role.ADMIN}),
    FILTER_BY_AUTHOR(new Role[]{ Role.USER, Role.ADMIN}),
    SELECT(new Role[]{ Role.USER, Role.ADMIN}),
    FILTER_BY_ALL(new Role[]{ Role.USER, Role.ADMIN});
    
    private final Role[] values;
    
    public Role[] getValues() {
        return values;
    }
    
    ActionPermission(Role[] values) {
        this.values = values;
    }
}
