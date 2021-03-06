/*
 * ProActive Parallel Suite(TM):
 * The Open Source library for parallel and distributed
 * Workflows & Scheduling, Orchestration, Cloud Automation
 * and Big Data Analysis on Enterprise Grids & Clouds.
 *
 * Copyright (c) 2007 - 2017 ActiveEon
 * Contact: contact@activeeon.com
 *
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation: version 3 of
 * the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 */
package org.ow2.proactive_grid_cloud_portal.cli.cmd.rm;

import static org.ow2.proactive_grid_cloud_portal.cli.HttpResponseStatus.OK;

import org.ow2.proactive_grid_cloud_portal.cli.ApplicationContext;
import org.ow2.proactive_grid_cloud_portal.cli.CLIException;
import org.ow2.proactive_grid_cloud_portal.cli.json.NSStateView;
import org.ow2.proactive_grid_cloud_portal.cli.utils.HttpResponseWrapper;
import org.ow2.proactive_grid_cloud_portal.cli.utils.QueryStringBuilder;


public class EditNodeSourceCommand extends NodeSourceCommand {

    private static final String RM_REST_ENDPOINT = "nodesource/edit";

    private String nodesRecoverable;

    public EditNodeSourceCommand(String nodeSourceName, String nodesRecoverable) {
        super(nodeSourceName);
        this.nodesRecoverable = nodesRecoverable;
    }

    @Override
    protected String getResourceUrlEndpoint() {
        return RM_REST_ENDPOINT;
    }

    @Override
    public void execute(ApplicationContext currentContext) throws CLIException {

        QueryStringBuilder queryStringBuilder = getQuery(currentContext);

        queryStringBuilder.add("nodesRecoverable", this.nodesRecoverable);

        HttpResponseWrapper response = executeRequestWithQuery(currentContext, queryStringBuilder, HttpVerb.PUT);

        if (this.statusCode(OK) == this.statusCode(response)) {
            NSStateView nsState = this.readValue(response, NSStateView.class, currentContext);
            boolean success = nsState.isResult();
            this.resultStack(currentContext).push(success);
            if (success) {
                writeLine(currentContext, "Node source successfully edited.");
            } else {
                writeLine(currentContext,
                          "%s %s. %s",
                          "Cannot edit node source",
                          this.nodeSourceName,
                          nsState.getErrorMessage());
                writeLine(currentContext, nsState.getStackTrace().replace("\\n", "%n").replace("\\t", "    "));
            }
        } else {
            this.handleError("An error occurred while editing node source:", response, currentContext);

        }
    }

}
