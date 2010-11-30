/*
 * ################################################################
 *
 * ProActive Parallel Suite(TM): The Java(TM) library for
 *    Parallel, Distributed, Multi-Core Computing for
 *    Enterprise Grids & Clouds
 *
 * Copyright (C) 1997-2010 INRIA/University of
 * 				Nice-Sophia Antipolis/ActiveEon
 * Contact: proactive@ow2.org or contact@activeeon.com
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; version 3 of
 * the License.
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
 * If needed, contact us to obtain a release under GPL Version 2
 * or a different license than the GPL.
 *
 *  Initial developer(s):               The ProActive Team
 *                        http://proactive.inria.fr/team_members.htm
 *  Contributor(s): ActiveEon Team - http://www.activeeon.com
 *
 * ################################################################
 * $$ACTIVEEON_CONTRIBUTOR$$
 */
package org.ow2.proactive.resourcemanager.gui.topology;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.ow2.proactive.resourcemanager.gui.data.RMStore;
import org.ow2.proactive.resourcemanager.gui.data.model.Node;
import org.ow2.proactive.resourcemanager.gui.data.model.TreeElementType;
import org.ow2.proactive.resourcemanager.gui.data.model.TreeLeafElement;
import org.ow2.proactive.resourcemanager.gui.data.model.TreeParentElement;
import org.ow2.proactive.resourcemanager.gui.views.ResourcesTopologyView;


public class TopologySelectionListener implements ISelectionChangedListener {

    public void selectionChanged(SelectionChangedEvent event) {
        ArrayList<Node> selectionList = new ArrayList<Node>();
        if (event != null && event.getSelectionProvider() != null) {
            Object selection = event.getSelectionProvider().getSelection();

            if (selection != null) {
                for (Object leaf : ((IStructuredSelection) selection).toList()) {
                    getSubTreeNodesList((TreeLeafElement) leaf, selectionList);
                }

            }
        }
        //normally RM is connected if I can select something...
        if (ResourcesTopologyView.getTopologyViewer() != null) {
            ResourcesTopologyView.getTopologyViewer().setSelection(selectionList);
        }

    }

    private void getSubTreeNodesList(TreeLeafElement leaf, ArrayList<Node> selectList) {
        // Find the source of the selected item for the removing source and add node combo
        RMStore.getInstance().getModel().findSelectedSource(leaf);
        //if the leaf is a pending node, returns
        if (leaf.getType().equals(TreeElementType.PENDING_NODE)) {
            return;
        }
        if (leaf.getType().equals(TreeElementType.NODE)) {
            if (!selectList.contains(leaf.getName()))
                selectList.add((Node) leaf);
        } else if (((TreeParentElement) leaf).hasChildren()) {
            for (TreeLeafElement element : ((TreeParentElement) leaf).getChildren()) {
                getSubTreeNodesList(element, selectList);
            }
        }
    }
}