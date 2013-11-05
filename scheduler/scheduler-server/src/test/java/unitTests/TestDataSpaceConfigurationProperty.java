/*
 *  *
 * ProActive Parallel Suite(TM): The Java(TM) library for
 *    Parallel, Distributed, Multi-Core Computing for
 *    Enterprise Grids & Clouds
 *
 * Copyright (C) 1997-2013 INRIA/University of
 *                 Nice-Sophia Antipolis/ActiveEon
 * Contact: proactive@ow2.org or contact@activeeon.com
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; version 3 of
 * the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 *
 *  Initial developer(s):               The ProActive Team
 *                        http://proactive.inria.fr/team_members.htm
 *  Contributor(s):
 *
 *  * $$ACTIVEEON_INITIAL_DEV$$
 */
package unitTests;

import org.junit.Assert;
import org.junit.Test;
import org.ow2.proactive.scheduler.core.DataSpaceServiceStarter;


/**
 * TestDataSpaceConfigurationProperty
 *
 * @author The ProActive Team
 **/
public class TestDataSpaceConfigurationProperty {

    @Test
    public void testPropertyParsing() throws Throwable {
        Assert.assertArrayEquals(new String[0], DataSpaceServiceStarter.dsConfigPropertyToUrls("  \"\"  "));
        Assert.assertArrayEquals(new String[0], DataSpaceServiceStarter.dsConfigPropertyToUrls("  "));
        Assert.assertArrayEquals(new String[] { "a" }, DataSpaceServiceStarter.dsConfigPropertyToUrls(" \"a\"  "));
        Assert.assertArrayEquals(new String[] { "a" }, DataSpaceServiceStarter.dsConfigPropertyToUrls("a"));
        Assert.assertArrayEquals(new String[] { "a" }, DataSpaceServiceStarter.dsConfigPropertyToUrls(" a  "));
        Assert.assertArrayEquals(new String[] { "a b" }, DataSpaceServiceStarter.dsConfigPropertyToUrls(" \"a b\"  "));
        Assert.assertArrayEquals(new String[] { "a", "b" }, DataSpaceServiceStarter.dsConfigPropertyToUrls(" a b  "));
        Assert.assertArrayEquals(new String[] { "a b c" }, DataSpaceServiceStarter.dsConfigPropertyToUrls(" \"a b c\"  "));
        Assert.assertArrayEquals(new String[] { "a", "b", "c" }, DataSpaceServiceStarter.dsConfigPropertyToUrls("  a b c  "));
        Assert.assertArrayEquals(new String[] { "a b c", "d e f" }, DataSpaceServiceStarter.dsConfigPropertyToUrls(" \"a b c\"    \"d e f\"   "));
        Assert.assertArrayEquals(new String[] { "a b c d e f" }, DataSpaceServiceStarter.dsConfigPropertyToUrls("   \"a b c d e f\"   "));
        Assert.assertArrayEquals(new String[] { "a", "b", "c", "d", "e", "f" }, DataSpaceServiceStarter.dsConfigPropertyToUrls("   a b c   d e    f "));
    }
}
