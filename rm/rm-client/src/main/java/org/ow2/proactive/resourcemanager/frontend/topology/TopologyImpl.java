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
package org.ow2.proactive.resourcemanager.frontend.topology;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.objectweb.proactive.core.node.Node;
import org.ow2.proactive.resourcemanager.frontend.topology.clustering.Cluster;
import org.ow2.proactive.resourcemanager.frontend.topology.clustering.HAC;
import org.ow2.proactive.topology.descriptor.DistanceFunction;


/**
 *
 * Class represents hosts topology handled by resource manager.
 *
 */
@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class TopologyImpl implements Topology, Cloneable {

    /**
     * Host -&gt; Hosts -&gt; Distance
     * Store here only half of matrix - each host have distances to hosts added before.
     * Assume that they are symmetrical.
     */
    private HashMap<String, HashMap<String, Long>> distances = new HashMap<>();

    /**
     * This map is needed to store the dependency between host name and address.
     * All listeners of the RM receive only host name in events, so for them it's simpler to
     * operate with strings.
     */
    private HashMap<String, InetAddress> hosts = new HashMap<>();

    private Long long0 = new Long(0);

    private Long longMax = new Long(Long.MAX_VALUE);

    /**
     * {@inheritDoc}
     */
    public Long getDistance(Node node, Node node2) {
        String host = node.getVMInformation().getHostName();
        String host2 = node2.getVMInformation().getHostName();
        return getDistance(host, host2);
    }

    /**
     * {@inheritDoc}
     */
    public Long getDistance(String host, String host2) {
        if (host.equals(host2)) {
            return long0;
        } else {
            HashMap<String, Long> dhost = distances.get(host);
            HashMap<String, Long> dhost2 = distances.get(host2);
            Long dHostToHost2;
            Long dHost2ToHost;
            if (dhost != null && (dHostToHost2 = dhost.get(host2)) != null) {
                return dHostToHost2;
            } else if (dhost2 != null && (dHost2ToHost = dhost2.get(host)) != null) {
                return dHost2ToHost;
            }
        }
        return longMax;
    }

    /**
     * {@inheritDoc}
     */
    public boolean onSameHost(Node node, Node node2) {
        return getDistance(node, node2) == 0;
    }

    /**
     * {@inheritDoc}
     */
    public Set<String> getHosts() {
        return distances.keySet();
    }

    @Override
    public InetAddress getInetAddress(String hostName) {
        return hosts.get(hostName);
    }

    public HashMap<String, InetAddress> getHostsMap() {
        return hosts;
    }

    /**
     * Adds host information to the topology.
     * Pass both name and address of host avoiding to do a conversion inside.
     *
     * @param hostName the name of the host
     * @param hostAddress the address of the host
     * @param hostTopology distances to other hosts
     */
    public void addHostTopology(String hostName, InetAddress hostAddress, HashMap<String, Long> hostTopology) {
        distances.put(hostName, hostTopology);
        hosts.put(hostName, hostAddress);
    }

    /**
     * Removes all information about host from the topology.
     * As it stores internally names and addresses we do not try to convert one into
     * another (as it may lead to network call) but rather pass them as parameters.
     *
     * @param hostName name of host to be removed
     */
    public void removeHostTopology(String hostName) {
        distances.remove(hostName);
        hosts.remove(hostName);
        // removing links to "host"
        for (String h : distances.keySet()) {
            if (distances.get(h).containsKey(hostName)) {
                distances.get(h).remove(hostName);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public HashMap<String, Long> getHostTopology(String hostName) {
        return distances.get(hostName);
    }

    /**
     * {@inheritDoc}
     */
    public boolean knownHost(String hostName) {
        return distances.containsKey(hostName);
    }

    /**
     * Clones the topology object, which is uses for synchronization purposes.
     */
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public List<Cluster<String>> clusterize(int numberOfClusters, DistanceFunction distanceFunction) {
        HAC hac = new HAC(this, new LinkedList<Node>(), distanceFunction, Long.MAX_VALUE);
        return hac.clusterize(numberOfClusters, hosts.keySet());
    }

    public HashMap<String, HashMap<String, Long>> getDistances() {
        return distances;
    }
}
