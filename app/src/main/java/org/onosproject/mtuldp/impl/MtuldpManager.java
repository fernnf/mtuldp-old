/*
 * Copyright (c) 2016,  Gercom. Lab.
 * Licensed under the Apache License,Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onosproject.mtuldp.impl;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.onosproject.mtuldp.api.link.MtuldpDirectLink;
import org.onosproject.mtuldp.api.storage.MtuldpLinkStoreService;
import org.onosproject.net.ConnectPoint;
import org.onosproject.net.Host;
import org.onosproject.net.HostId;
import org.onosproject.net.Link;
import org.onosproject.net.edge.EdgePortService;
import org.onosproject.net.host.HostService;
import org.onosproject.net.link.LinkService;
import org.onosproject.net.topology.Topology;
import org.onosproject.net.topology.TopologyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by fernando on 29/03/16.
 */
@Component(immediate = true)
public class MtuldpManager {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected MtuldpLinkStoreService mtuldpLinkStoreService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected TopologyService topologyService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected LinkService linkService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected EdgePortService edgePortService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected HostService hostService;


    @Activate
    public void start() {



        for (ConnectPoint connectPoint : edgePortService.getEdgePoints() ){
            System.out.println(connectPoint.toString());
            ///System.out.println(hostService.getConnectedHosts(connectPoint));

            for  (Host host : hostService.getConnectedHosts(connectPoint) ){
                System.out.println("Host: " + host.toString());
            }

        }


        log.info("Started the mtuldp protocol");
        for (Link link : linkService.getLinks()){
            System.out.println(link.toString());
        }

    }
    public void stop() {
        log.info("Stopped the mtuldp protocol");
    }


    private class InnerSearchDirectLink implements Runnable {

        LinkService links;

        public InnerSearchDirectLink(LinkService links) {
            this.links = links;
        }

        @Override
        public void run() {
            log.debug("/----------------------------/\nFinding new infrastructure link\n/----------------------------------/");
            for (Link link : linkService.getLinks()) {
                MtuldpDirectLink.Builder dlink = new MtuldpDirectLink.Builder().deviceEgress(link.dst()).deviceIngress(link.src()).mtuRate(1500);
                try {
                    mtuldpLinkStoreService.doAddLink(dlink.build());
                } catch (IllegalAccessException e){
                    log.error("It cannot to add link by reason: {} ", e.getCause());
                }
            }
        }
    }

    private void doSearchLink(Topology topo) {

        if (topo.linkCount() != 0) {

        }

    }

}
