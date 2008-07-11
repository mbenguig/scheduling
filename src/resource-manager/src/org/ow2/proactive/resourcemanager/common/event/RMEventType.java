/*
 * ################################################################
 *
 * ProActive: The Java(TM) library for Parallel, Distributed,
 *            Concurrent computing with Security and Mobility
 *
 * Copyright (C) 1997-2007 INRIA/University of Nice-Sophia Antipolis
 * Contact: proactive@objectweb.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version
 * 2 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 *  Initial developer(s):               The ProActive Team
 *                        http://proactive.inria.fr/team_members.htm
 *  Contributor(s):
 *
 * ################################################################
 */
package org.ow2.proactive.resourcemanager.common.event;

import org.objectweb.proactive.annotation.PublicAPI;


/**
 * Enumeration of all events generated by the Resource Manager,
 * and received by Monitors, which permit to probe RM activity.
 *
 * @see org.ow2.proactive.resourcemanager.frontend.RMMonitoring
 *
 * @author The ProActive Team
 * @version 3.9
 * @since ProActive 3.9
 *
 */
@PublicAPI
public enum RMEventType {
    /**
     * Resource manager has been shutdown
     */
    SHUTDOWN("rmShutDownEvent"),
    /**
     * Resource manager cessation as been asked, so Resource manager performs 
     * shutting down operations.  
     */
    SHUTTING_DOWN("rmShuttingDownEvent"),
    /**
     * Resource manager has been launched. 
     */
    STARTED("rmStartedEvent"),
    /**
     * A new node source has been created in Resource manager.
     */
    NODESOURCE_CREATED("nodeSourceAddedEvent"),
    /**
     * A node source has been removed removed from resource manager.
     */
    NODESOURCE_REMOVED("nodeSourceRemovedEvent"),
    /**
     * A new node has been added to Resource manager, and is ready to
     * be provided to a RM user.
     */
    NODE_ADDED("nodeAddedEvent"),
    /**
     * A node is becoming free.
     */
    NODE_FREE("nodeFreeEvent"),
    /**
     * A node has been provided to a RM user, so it becomes busy. 
     */
    NODE_BUSY("nodeBusyEvent"),
    /**
    * A busy node has to be removed from Resource manager after an RM user
    * give back it, so the node is put to 'to release' state.
    */
    NODE_TO_RELEASE("nodeToReleaseEvent"),
    /**
     * A node becomes unreachable, because he is probably fallen,
     * so it becomes down. 
     */
    NODE_DOWN("nodeDownEvent"),
    /**
     * A node has been removed from Resource manager
     */
    NODE_REMOVED("nodeRemovedEvent");
    private String methodName;

    /**
     * Default constructor.
     * @param method method to call as a string.
     */
    RMEventType(String method) {
        methodName = method;
    }

    /**
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return methodName;
    }
}
