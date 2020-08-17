package com.fairandsmart.generator.workspace.entity;

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

import com.fairandsmart.generator.job.entity.Job;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.persistence.Version;
import java.util.List;

@Entity
public class Workspace extends PanacheEntity {

    @Version
    public long version;
    public String owner;
    @Transient
    public String root;
    @Transient
    public long size;
    @Transient
    public List<FileItem> content;
    @Transient
    public long activeJobs;
    @Transient
    public List<Job> jobs;

    public static Workspace findByOwner(String owner){
        return find("owner", owner).firstResult();
    }

    @Override
    public String toString() {
        return "Workspace{" +
                "id=" + id +
                ", version=" + version +
                ", owner='" + owner + '\'' +
                ", root='" + root + '\'' +
                ", size=" + size +
                ", content=" + content.size() + " items" +
                ", jobs=" + jobs.size() + " jobs" +
                '}';
    }
}
