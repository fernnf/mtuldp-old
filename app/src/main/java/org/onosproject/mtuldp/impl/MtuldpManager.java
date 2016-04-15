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
import org.onosproject.mtuldp.api.link.SearchDirectLinks;
import org.onosproject.mtuldp.api.link.SearchEdgelinks;
import org.onosproject.mtuldp.api.storage.MtuldpLinkStoreService;
import org.onosproject.net.Link;
import org.onosproject.net.edge.EdgePortService;
import org.onosproject.net.host.HostService;
import org.onosproject.net.link.LinkEvent;
import org.onosproject.net.link.LinkListener;
import org.onosproject.net.link.LinkService;
import org.onosproject.net.topology.TopologyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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


    private final ExecutorService searchService = Executors.newFixedThreadPool(2);

    @Activate
    public void start() {

        searchService.execute(new SearchDirectLinks(linkService, mtuldpLinkStoreService));
        searchService.execute(new SearchEdgelinks(edgePortService,hostService,mtuldpLinkStoreService));

        try {
            Thread.sleep(3000);
        } catch (Exception e){

        }


        System.out.println("Total Direct Links (" + mtuldpLinkStoreService.getDirectLinks().toString()+ ") / Total Edge Links (" + mtuldpLinkStoreService.getEdgeLinks().size()+ ")");

    }
    public void stop() {
        log.info("Stopped the mtuldp protocol");
        searchService.shutdown();
    }

    private class InnerDirectLinkListerner implements LinkListener{

        @Override
        public void event(LinkEvent event) {

            switch (event.type()){
                case LINK_ADDED:
                    Link link = event.subject();
                    MtuldpDirectLink.Builder mdlb = new MtuldpDirectLink.Builder().deviceEgress(link.dst()).deviceIngress(link.src()).mtuRate(0);
                    try {
                        mtuldpLinkStoreService.doAddLink(mdlb.build());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    break;
                case LINK_REMOVED:
                    break;
                case LINK_UPDATED:
                    break;
                default:

            }


        }
    }

}
