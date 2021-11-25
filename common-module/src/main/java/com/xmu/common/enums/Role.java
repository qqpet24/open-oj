package com.xmu.common.enums;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Getter
public enum Role {
    ROOT(0L,"ROOT"),
    ADMIN(1L,"ADMIN"),
    USER(2L,"USER");
    private final String roleName;
    private final Long roleId;

    Role(Long roleId,String roleName) {
        this.roleName = roleName;
        this.roleId = roleId;
    }

}
