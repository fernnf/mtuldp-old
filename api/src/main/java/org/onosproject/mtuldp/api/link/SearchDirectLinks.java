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
import org.onosproject.net.link.LinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by fernando on 13/04/16.
 */
public class SearchDirectLinks implements Runnable {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private LinkService linkService;
    private MtuldpLinkStoreService mtuldpLinkStoreService;

    public SearchDirectLinks(LinkService linkService, MtuldpLinkStoreService mlss) {
        this.linkService = linkService;
        this.mtuldpLinkStoreService = mlss;
    }

    @Override
    public void run() {
        log.debug("/----------------------------/\nFinding new infrastructure link\n/----------------------------------/");
        if (linkService.getLinkCount() != 0){
            linkService.getLinks().forEach(link -> {
                MtuldpDirectLink.Builder mdlb = new MtuldpDirectLink.Builder()
                        .deviceEgress(link.dst())
                        .deviceIngress(link.src())
                        .mtuRate(0);
                try {
                    mtuldpLinkStoreService.doAddLink(mdlb.build());
                } catch (IllegalAccessException e){
                    log.error("It cannot to add link by reason: {} ", e.getCause());
                }
            });
        } else {
            log.info("There are no direct links available");
        }
    }
}
