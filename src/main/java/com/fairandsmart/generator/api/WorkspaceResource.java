package com.fairandsmart.generator.api;

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

import com.fairandsmart.generator.job.AlreadyActiveJobException;
import com.fairandsmart.generator.job.JobManager;
import com.fairandsmart.generator.job.UnsupportedJobException;
import com.fairandsmart.generator.workspace.WorkspaceManager;
import com.fairandsmart.generator.workspace.WorkspaceManagerException;
import com.fairandsmart.generator.workspace.WorkspaceNotFoundException;
import com.fairandsmart.generator.workspace.entity.Workspace;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
//import sun.misc.JavaxCryptoSealedObjectAccess;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

/**
 * Actions availables :
 *   - Lunch a new generation job for this workspace
 *      - Choose layouts
 *      - Choose
 *   - List current running job status
 *   - Purge Workspace content
 *   - Download workspace content
 *   - Browse workspace content
 *
 */

@Path("/api/ws")
public class WorkspaceResource {

    @Inject
    WorkspaceManager workspaceManager;

    @Inject
    JobManager jobManager;

    @Inject
    Template workspace;

    @GET
    @RolesAllowed({"silver","gold","platinum"})
    @Produces(MediaType.TEXT_HTML)
    public Response me(@Context UriInfo uriInfo) throws WorkspaceManagerException {
        Long wsid = workspaceManager.findIdForConnectedUser();
        URI wsuri = uriInfo.getRequestUriBuilder().path(wsid.toString()).build();
        return Response.seeOther(wsuri).entity(wsuri.toString()).build();
    }

    @GET
    @Path("/{wsid}")
    @RolesAllowed({"silver","gold","platinum"})
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getWorkspace(@PathParam("wsid") Long wsid) throws WorkspaceNotFoundException, WorkspaceManagerException, AccessDeniedException {
        return workspace.data("workspace", workspaceManager.load(wsid));
    }

    @POST
    @Path("/{wsid}/purge")
    @RolesAllowed({"silver","gold","platinum"})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance purgeWorkspace(@PathParam("wsid") Long wsid, @FormParam("wsname") String wsname) throws WorkspaceNotFoundException, WorkspaceManagerException, AccessDeniedException {
        Workspace ws = workspaceManager.load(wsid);
        if ( ws.owner.equalsIgnoreCase(wsname) ) {
            workspaceManager.purge(wsid);
            return workspace.data("workspace", workspaceManager.load(wsid));
        } else {
            throw new WorkspaceManagerException("bad workspace name confirmation");
        }
    }

    @GET
    @Path("/{wsid}/content/{filename}")
    @RolesAllowed({"silver","gold","platinum"})
    public Response getWorkspaceContent(@PathParam("wsid") Long wsid, @PathParam("filename") String filename, @QueryParam("download") boolean download) throws WorkspaceNotFoundException, WorkspaceManagerException, IOException {
        Workspace workspace = workspaceManager.load(wsid);
        java.nio.file.Path itemPath = Paths.get(workspace.root, filename);
        return Response.ok(itemPath.toFile())
                .header("Content-Type", Files.probeContentType(itemPath))
                .header("Content-Length", Files.size(itemPath))
                .header("Content-Disposition", ((download) ? "attachment; " : "") + "filename=" + filename).build();

    }

    @POST
    @Path("/{wsid}/jobs")
    @RolesAllowed({"silver","gold","platinum"})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response submitJob(@Context UriInfo uriInfo, @PathParam("wsid") Long wsid, @FormParam("qty") String qty) throws UnsupportedJobException, WorkspaceNotFoundException, WorkspaceManagerException, AccessDeniedException, AlreadyActiveJobException {
        Workspace ws = workspaceManager.load(wsid);
        jobManager.submit(ws, "invoice.generate", Collections.singletonMap("qty", qty));
        URI created = uriInfo.getBaseUriBuilder().path(WorkspaceResource.class).path(wsid.toString()).build();
        return Response.seeOther(created).build();
    }

}
