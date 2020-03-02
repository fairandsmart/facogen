package com.fairandsmart.generator.workspace;

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

import com.fairandsmart.generator.job.JobManager;
import com.fairandsmart.generator.security.AuthenticationService;
import com.fairandsmart.generator.workspace.entity.FileItem;
import com.fairandsmart.generator.workspace.entity.Workspace;
import com.fairandsmart.generator.workspace.entity.WorkspaceContent;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Singleton
public class WorkspaceManager {

    private static final Logger LOGGER = Logger.getLogger(WorkspaceManager.class.getName());
    public static final String WORKSPACES_HOME = "workspaces";

    @ConfigProperty(name = "fs.generator.home")
    public String home;

    @ConfigProperty(name = "fs.generator.page.size")
    public long limit;

    @Inject
    AuthenticationService auth;

    @Inject
    JobManager jobs;

    private static Path root;

    @PostConstruct
    protected void init() {
        LOGGER.log(Level.INFO, "Initialising Workspace Service");
        if ( home.startsWith("~") ) {
            home = home.replaceFirst("\\~", System.getProperty("user.home"));
        }
        this.root = Paths.get(home, WORKSPACES_HOME);
        LOGGER.log(Level.INFO, "Initializing service with root folder: " + root);
        try {
            Files.createDirectories(root);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "unable to initialize workspace service", e);
        }
    }

    @Transactional
    public Long findIdForConnectedUser() throws WorkspaceManagerException {
        Workspace workspace = Workspace.findByOwner(auth.getConnectedUser());
        if ( workspace == null ) {
            workspace = this.bootstrap(auth.getConnectedUser());
        }
        return workspace.id;
    }

    @Transactional
    public Workspace load(Long id) throws WorkspaceNotFoundException, WorkspaceManagerException, AccessDeniedException {
        LOGGER.log(Level.INFO, "Loading workspace for id: " + id);
        Workspace workspace = Workspace.findById(id);
        if ( workspace == null ) {
            throw new WorkspaceNotFoundException("Unable to find workspace for id: " + id);
        }
        if ( !auth.isSuperUserConnected() && !auth.getConnectedUser().equals(workspace.owner) ) {
            throw new AccessDeniedException("Access Denied for user [" + auth.getConnectedUser() + "] on workspace with id: " + id);
        }
        Path wsroot = Paths.get(root.toString(), workspace.owner);
        try {
            workspace.root = wsroot.toString();
            workspace.size = Files.size(wsroot);
            workspace.content = Files.list(wsroot).map(this::toFileItem).collect(Collectors.toList());
            workspace.activeJobs = jobs.countActiveForOwner(workspace.owner);
            workspace.jobs = jobs.findForOwner(workspace.owner);
            LOGGER.log(Level.INFO, "Workspace: " + workspace);
            return workspace;
        } catch ( IOException e ) {
            throw new WorkspaceManagerException("Error while accessing workspace folder", e);
        }
    }

    public void purge(Long id) throws WorkspaceNotFoundException, WorkspaceManagerException, AccessDeniedException {
        LOGGER.log(Level.INFO, "Purge workspace for id: " + id);
        Workspace workspace = Workspace.findById(id);
        if ( workspace == null ) {
            throw new WorkspaceNotFoundException("Unable to find workspace for id: " + id);
        }
        if ( !auth.isSuperUserConnected() && !auth.getConnectedUser().equals(workspace.owner) ) {
            throw new AccessDeniedException("Access Denied for user [" + auth.getConnectedUser() + "] on workspace with id: " + id);
        }
        Path wsroot = Paths.get(root.toString(), workspace.owner);
        try {
            Files.list(wsroot).forEach(this::deletePath);
        } catch ( IOException | RuntimeException e ) {
            LOGGER.log(Level.SEVERE, "Error while purging workspace", e);
            throw new WorkspaceManagerException("Error while purging workspace folder", e);
        }
    }

    private Workspace bootstrap(String owner) throws WorkspaceManagerException {
        LOGGER.log(Level.INFO, "Bootstraping workspace for owner: " + owner);
        try {
            Path wsroot = Paths.get(root.toString(), owner);
            try {
                Files.createDirectory(wsroot);
            } catch ( FileAlreadyExistsException e ) { //
            }
            Workspace workspace = new Workspace();
            workspace.owner = owner;
            workspace.persist();
            return workspace;
        } catch ( IOException e ) {
            throw new WorkspaceManagerException("Error while creating workspace folder", e);
        }
    }

    private void deletePath(Path p) throws RuntimeException {
        try {
            Files.delete(p);
        } catch ( IOException e ) {
            throw new RuntimeException(e);
        }
    }

    private FileItem toFileItem(Path path) {
        FileItem item = new FileItem();
        item.setName(path.getFileName().toString());
        try {
            BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
            item.setSize(attributes.size());
            item.setCreationDate(new Date(attributes.creationTime().toMillis()));
            item.setModificationDate(new Date(attributes.lastModifiedTime().toMillis()));
            //TODO Use tika to populate mimetype
        } catch ( IOException e ) {
            throw new RuntimeException(e);
        }
        return item;
    }

}
