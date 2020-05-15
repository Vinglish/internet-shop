package mate.academy.internetshop.model;

public class Role {
    private Long id;
    private RoleName roleName;

    public Role(RoleName roleName) {
        this.roleName = roleName;
    }

    private Role(Long id, RoleName roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public static Role of(Long id, String roleName) {
        return new Role(id, RoleName.valueOf(roleName));
    }

    public static Role of(String roleName) {
        return new Role(RoleName.valueOf(roleName));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    public enum RoleName {
        USER, ADMIN;
    }
}
