package com.fairandsmart.generator.security;

/*-
 * #%L
 * FacoGen / A tool for annotated GEDI based invoice generation.
 * 
 * Authors:
 * 
 * Xavier Lefevre <xavier.lefevre@fairandsmart.com> / FairAndSmart
 * Nicolas Rueff <nicolas.rueff@fairandsmart.com> / FairAndSmart
 * Alan Balbo <alan.balbo@fairandsmart.com> / FairAndSmart
 * Frederic Pierre <frederic.pierre@fairansmart.com> / FairAndSmart
 * Victor Guillaume <victor.guillaume@fairandsmart.com> / FairAndSmart
 * Jérôme Blanchard <jerome.blanchard@fairandsmart.com> / FairAndSmart
 * Aurore Hubert <aurore.hubert@fairandsmart.com> / FairAndSmart
 * Kevin Meszczynski <kevin.meszczynski@fairandsmart.com> / FairAndSmart
 * %%
 * Copyright (C) 2019 - 2020 Fair And Smart
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import io.quarkus.security.identity.SecurityIdentity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.security.Principal;

@RequestScoped
public class AuthenticationService {

    public static final String UNAUTHENTIFIED_IDENTIFIER = "anonymous";
    public static final String SUPERUSER_ROLE = "sudoers";

    @Inject
    SecurityIdentity securityIdentity;

    public String getConnectedUser() {
        Principal user = securityIdentity.getPrincipal();
        String name = user != null ? user.getName() : UNAUTHENTIFIED_IDENTIFIER;
        return name;
    }

    public boolean isConnectedUserInRole(String role) {
        return securityIdentity.hasRole(role);
    }

    public boolean isSuperUserConnected() {
        Principal user = securityIdentity.getPrincipal();
        return user != null && securityIdentity.hasRole(SUPERUSER_ROLE);
    }

}
