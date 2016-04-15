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
package org.onosproject.mtuldp.api.link;

import org.onosproject.mtuldp.api.storage.MtuldpLinkStoreService;
import org.onosproject.net.edge.EdgePortService;
import org.onosproject.net.host.HostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by fernando on 13/04/16.
 */
public class SearchEdgelinks implements Runnable{

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private EdgePortService edgePortService;
    private HostService hostService;
    private MtuldpLinkStoreService mtuldpLinkStoreService;

    public SearchEdgelinks(EdgePortService edgePortService, HostService hostService, MtuldpLinkStoreService mtuldpLinkStoreService) {
        this.edgePortService = edgePortService;
        this.hostService = hostService;
        this.mtuldpLinkStoreService = mtuldpLinkStoreService;
    }

    @Override
    public void run() {
        log.debug("/----------------------------/\nFinding new edge link\n/----------------------------------/");
        if (hostService.getHostCount() != 0){
            edgePortService.getEdgePoints().forEach( edgelink -> {
                if (!hostService.getConnectedHosts(edgelink).isEmpty()){
                    hostService.getConnectedHosts(edgelink).forEach(host -> {
                        MtuldpEdgeLink.Builder melb = new MtuldpEdgeLink.Builder()
                                .devicePoint(edgelink)
                                .hostPoint(host)
                                .mtuRate(0);
                        try {
                            mtuldpLinkStoreService.doAddLink(melb.build());
                        }catch (IllegalAccessException e) {
                            log.error("It cannot to add link by reason: {} ", e.getCause());
                        }
                    });
                }
            });

        } else {
            log.info("There are no edge links available");
        }
    }
}
