package ru.iteco.fmh.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.model.user.Role;

import java.util.List;

@Service("roleMatchesService")
public class RoleMatchesService {
    public boolean findMatchesByRoleList(List<Role> roleList, Authentication authentication) {
        List<String> authorities =
                authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        if (authorities.contains("ROLE_ADMINISTRATOR")) {
            return true;
        }
        if (!roleList.isEmpty()) {
            return roleList.stream().map(Role::getName).anyMatch(authorities::contains);
        }
        return true;
    }
}
