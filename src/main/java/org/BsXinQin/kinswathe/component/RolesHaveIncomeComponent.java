package org.BsXinQin.kinswathe.component;

import dev.doctor4t.wathe.api.Role;
import dev.doctor4t.wathe.api.WatheRoles;
import org.BsXinQin.kinswathe.KinsWathe;
import org.agmas.noellesroles.Noellesroles;

import java.util.ArrayList;
import java.util.List;

public class RolesHaveIncomeComponent {

    public static List<Role> RolesHaveIncome() {
        List<Role> roles = new ArrayList<>();
        //添加基础身份
        roles.add(WatheRoles.KILLER);
        roles.add(KinsWathe.BELLRINGER);
        roles.add(KinsWathe.COOK);
        roles.add(KinsWathe.DETECTIVE);
        roles.add(KinsWathe.JUDGE);
        roles.add(KinsWathe.CLEANER);
        roles.add(KinsWathe.DRUGMAKER);
        roles.add(KinsWathe.LICENSED_VILLAIN);
        //添加NoellesRoles身份
        if (KinsWathe.NOELLESROLES_LOADED) {
            roles.add(Noellesroles.MIMIC);
            roles.add(Noellesroles.JESTER);
            roles.add(Noellesroles.PHANTOM);
            roles.add(Noellesroles.SWAPPER);
            roles.add(Noellesroles.TRAPPER);
            roles.add(Noellesroles.RECALLER);
            roles.add(Noellesroles.BARTENDER);
            roles.add(Noellesroles.MORPHLING);
            roles.add(Noellesroles.NOISEMAKER);
            roles.add(Noellesroles.EXECUTIONER);
            roles.add(Noellesroles.THE_INSANE_DAMNED_PARANOID_KILLER_OF_DOOM_DEATH_DESTRUCTION_AND_WAFFLES);
        }
        return List.copyOf(roles);
    }
}