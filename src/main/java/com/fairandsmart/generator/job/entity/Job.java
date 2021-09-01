package com.fairandsmart.generator.job.entity;

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

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Entity
public class Job extends PanacheEntity implements Delayed {

    private static final Logger LOGGER = Logger.getLogger(Job.class.getName());

    @Version
    public long version;
    public String owner;
    public String type;
    public long creationDate;
    public long dueDate;
    public long startDate;
    public long stopDate;
    @Enumerated(EnumType.STRING)
    public Status status;
    public long progression;
    @Lob
    public String message;
    @ElementCollection
    public Map<String, String> params;

    public Job() {
        this.params = new HashMap<>();
        progression = 0;
    }

    @Override
    @Transient
    public long getDelay(TimeUnit unit) {
        LOGGER.log(Level.FINE, "Getting delay for job: " + this.id);
        long diff = dueDate - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(@Nonnull Delayed obj) {
        LOGGER.log(Level.FINE, "Comparing to another object: " + obj.toString());
        if (!(obj instanceof Job)) {
            throw new IllegalArgumentException("Illegal comparison to non-Job");
        }
        Job other = (Job) obj;
        return (int) (other.dueDate - this.dueDate);
    }

    public static List<Job> findForOwner(String owner) {
        return find("owner", owner).list();
    }


    /*public static long countActiveForOwner(String owner) {
        return find("owner = ?1 and ( status = ?2)", owner, Status.RUNNING).count(); // status = ?2 or Status.PENDING,
    }*/
    public static List<Job> findActiveForOwner(String owner) {
        return find("owner = ?1 and (status = ?2 or status = ?3)", owner, Status.PENDING, Status.RUNNING).list();
    }

    public static long countActiveForOwner(String owner) {
        return find("owner = ?1 and (status = ?2 or status = ?3)", owner, Status.PENDING, Status.RUNNING).count();
    }

    @Override
    public String toString() {
        return "Job{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", owner='" + owner + '\'' +
                ", type='" + type + '\'' +
                ", creationDate=" + creationDate +
                ", dueDate=" + dueDate +
                ", startDate=" + startDate +
                ", stopDate=" + stopDate +
                ", status=" + status +
                ", progression=" + progression +
                ", message='" + message + '\'' +
                ", params=" + params +
                '}';
    }

    public enum Status {
        PENDING,
        RUNNING,
        DONE,
        FAILED
    }

}
