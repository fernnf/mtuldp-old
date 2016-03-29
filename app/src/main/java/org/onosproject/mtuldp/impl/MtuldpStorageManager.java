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

import com.google.common.collect.ImmutableSet;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Service;
import org.onosproject.mtuldp.api.link.DefaultMtuldpLink;
import org.onosproject.mtuldp.api.link.MtuldpDirectLink;
import org.onosproject.mtuldp.api.link.MtuldpEdgeLink;
import org.onosproject.mtuldp.api.storage.MtuldpDataStorage;
import org.onosproject.mtuldp.api.storage.MtuldpLinkStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Created by fernando on 29/03/16.
 */
@Component(immediate = true)
@Service
public class MtuldpStorageManager implements MtuldpLinkStoreService{


    protected final Logger log = LoggerFactory.getLogger(getClass());

    private MtuldpDataStorage data;

    @Activate
    public void start(){
        log.info("Mtuldp Storage Started");
        data = new MtuldpDataStorage();
    }

    @Deactivate
    public void stop(){
        log.info("Mtuldp Storage Stoped");
        data = null;

    }

    @Override
    public void doAddLink(DefaultMtuldpLink link)  {

        if (data.createLink(link)){
            log.info("New Link added");
            log.debug("New Link added: {}", link.toString());
        } else {
            throw new IllegalStateException("It is not possible to add link");
        }
    }

    @Override
    public void doDelLink(String urn) {
        if (data.removeLink(urn)){
            log.info("The Link was added");
            log.debug("The Link was added: ID({})", urn);
        } else {
            throw new IllegalStateException("It is not possible to remove link");
        }
    }

    @Override
    public void doUpdLink(String urn, DefaultMtuldpLink newLink) {
        if (data.updateLink(urn,newLink)){
            log.info("The Link was added: ID ({})", urn);
            log.debug("The Link was added: ID ({})", urn);
        } else {
            throw new IllegalStateException("It is not possible to update link");
        }
    }

    @Override
    public Set<MtuldpDirectLink> getDirectLinks() {
        return ImmutableSet.copyOf(data.getMtuldpDirectLinks());
    }

    @Override
    public Set<MtuldpEdgeLink> getEdgeLinks() {
        return ImmutableSet.copyOf(data.getMtuldpEdgeLinks());
    }
}
