/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import dataaccess.RoleDB;
import java.util.List;
import models.Role;

/**
 *
 * @author Steven
 */
public class RoleService {
     public List<Role> getAllRoles() throws Exception {
        RoleDB roleDB = new RoleDB();
        List<Role> roles = roleDB.getAllRoles();
        return roles;
    }

    public int getRoleID(Role role) {
        String name = role.getRoleName();
        if (name.equals("system admin")) {
            return 1;
        } else {
            return 2;
        }
    }
}
