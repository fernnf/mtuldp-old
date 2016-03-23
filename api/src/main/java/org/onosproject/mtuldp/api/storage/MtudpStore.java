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
package org.onosproject.mtuldp.api.storage;

import org.onosproject.mtuldp.api.link.DefaultMtuldpLink;
import org.onosproject.mtuldp.api.link.MtuldpDirectLink;
import org.onosproject.mtuldp.api.link.MtuldpEdgeLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by fernando on 22/03/16.
 */
public class MtudpStore {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private Map<String, MtuldpDirectLink> directLinkStore;
    private Map<String, MtuldpEdgeLink> edgeLinkStore;


    public MtudpStore(){
        this.directLinkStore = new ConcurrentHashMap<String, MtuldpDirectLink>();
        this.edgeLinkStore = new ConcurrentHashMap<String, MtuldpEdgeLink>();
    }

    public void createLink(DefaultMtuldpLink link){

        checkNotNull(link, "The link cannot be null");

        if (link instanceof  MtuldpDirectLink){
            MtuldpDirectLink tmpLink = (MtuldpDirectLink) link;
            if (!directLinkStore.containsValue(link)){
                directLinkStore.put(tmpLink.getUrnLinkId(), tmpLink);
            } else {
                log.warn("the direct link is already exist");
            }

        } else if (link instanceof MtuldpEdgeLink){
            MtuldpEdgeLink tmpLink = (MtuldpEdgeLink) link;
            if (!edgeLinkStore.containsValue(link)){
                edgeLinkStore.put(tmpLink.getUrnId(),tmpLink);
            } else {
                log.warn("the edge link is already exist");
            }
        } else {
            log.error("the link unknown");
        }
    }

    public updateLink(String urn, DefaultMtuldpLink newlink){
        checkNotNull(urn, "urn Cannot be null");
        checkNotNull(newlink, "mtudplink cannot be null");

        if (newlink instanceof MtuldpDirectLink) {
            directLinkStore.replace(urn, directLinkStore.get(urn), (MtuldpDirectLink) newlink);
        } else if (newlink instanceof MtuldpEdgeLink){

        }


    }

}
