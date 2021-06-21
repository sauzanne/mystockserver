package fr.mystocks.mystockserver.data.security.constant;

import java.util.ArrayList;
import java.util.List;

public enum RoleEnum {
	SUPERADMIN(4, RoleConst.SUPERADMIN), ADMIN(3, RoleConst.ADMIN), USER(2, RoleConst.USER), READONLY_USER(1,
			RoleConst.READONLY_USER);

	private final int authorizationLevel;

	private final String roleName;

	/**
	 * @author sauzanne @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 *
	 * @param authorizationLevel
	 */
	private RoleEnum(int authorizationLevel, String name) {
		this.authorizationLevel = authorizationLevel;
		this.roleName = name;
	}

	/**
	 * @author sauzanne @return the authorizationLevel
	 */
	public int getAuthorizationLevel() {
		return authorizationLevel;
	}

	/**
	 * Récupère les roles autorisés à partir du plus faible autorisé
	 * 
	 * @author sauzanne
	 * 
	 * @param lowestRole
	 *            role le plus faible autorisé
	 * @return la iste des roles
	 */
	public static List<RoleEnum> getRoleFromLevel(RoleEnum lowestRole) {
		List<RoleEnum> allowedRoles = new ArrayList<>();
		for (RoleEnum role : RoleEnum.values()) {
			if (role.getAuthorizationLevel() >= lowestRole.authorizationLevel) {
				allowedRoles.add(role);
			}
		}
		return allowedRoles;
	}

}
