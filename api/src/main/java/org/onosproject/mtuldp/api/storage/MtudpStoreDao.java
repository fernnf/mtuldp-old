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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.onosproject.mtuldp.api.link.DefaultMtuldpLink;
import org.onosproject.mtuldp.api.link.MtuldpDirectLink;
import org.onosproject.mtuldp.api.link.MtuldpEdgeLink;
import org.onosproject.net.Link;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by fernando on 22/03/16.
 */
public class MtudpStoreDao {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private Map<String, MtuldpDirectLink> directLinkStore;
    private Map<String, MtuldpEdgeLink> edgeLinkStore;


    public MtudpStoreDao() {
        this.directLinkStore = new ConcurrentHashMap<>();
        this.edgeLinkStore = new ConcurrentHashMap<>();
    }

    public boolean createLink(DefaultMtuldpLink link) {

        checkNotNull(link, "The link cannot be null");

        if (link instanceof MtuldpDirectLink) {
            MtuldpDirectLink tmpLink = (MtuldpDirectLink) link;
            if (!directLinkStore.containsValue(link)) {
                directLinkStore.put(tmpLink.getUrnLinkId(), tmpLink);
                return true;
            } else {
                log.warn("the direct link is already exist");
                return false;
            }

        } else if (link instanceof MtuldpEdgeLink) {
            MtuldpEdgeLink tmpLink = (MtuldpEdgeLink) link;
            if (!edgeLinkStore.containsValue(link)) {
                edgeLinkStore.put(tmpLink.getUrnId(), tmpLink);
                return true;
            } else {
                log.warn("the edge link is already exist");
                return false;
            }
        } else {
            log.error("the link unknown");
        }
        return false;
    }

    public boolean updateLink(String urn, DefaultMtuldpLink newlink) {
        checkNotNull(urn, "urn Cannot be null");
        checkNotNull(newlink, "mtudplink cannot be null");

        if (newlink instanceof MtuldpDirectLink) {
            if (directLinkStore.get(urn).equals(newlink)) {
                log.debug("The new link object is equal to old object");
                return false;
            }
            directLinkStore.replace(urn, directLinkStore.get(urn), (MtuldpDirectLink) newlink);
            log.debug("New Direct link inserted");
            return true;
        } else if (newlink instanceof MtuldpEdgeLink) {
            if (edgeLinkStore.get(urn).equals(newlink)) {
                log.debug("The new link object is equal to old object");
                return false;
            }
            edgeLinkStore.replace(urn, edgeLinkStore.get(urn), (MtuldpEdgeLink) newlink);
            log.debug("New Edge link inserted");
            return true;

        } else {
            log.error("the link unknown");
        }
        return false;
    }

    public MtuldpDirectLink getMtuldpDirectLink(String urn) {

        if (getLinkType(urn) instanceof MtuldpDirectLink) {
            return directLinkStore.get(urn);
        }
        return null;
    }

    public MtuldpEdgeLink getMtuldpEdgeLink(String urn) {

        if (getLinkType(urn) instanceof MtuldpEdgeLink) {
            return edgeLinkStore.get(urn);
        }
        return null;

    }

    public Set<MtuldpDirectLink> getMtuldpDirectLinks() {
        return (Set<MtuldpDirectLink>) directLinkStore.values();
    }

    public Set<MtuldpEdgeLink> getMtuldpEdgeLinks() {
        return (Set<MtuldpEdgeLink>) edgeLinkStore.values();
    }

    public int getMtuLinkRate(String urn) {

        DefaultMtuldpLink link = checkNotNull(getLinkType(urn), "The link object unknown");

        if (link instanceof MtuldpDirectLink) {
            return directLinkStore.get(urn).getMtuRate();
        } else if (link instanceof MtuldpEdgeLink) {
            return edgeLinkStore.get(urn).getMtuRate();
        }
        return -1;
    }

    private DefaultMtuldpLink getLinkType(String urn) {
        if (StringUtils.containsIgnoreCase(urn, Link.Type.DIRECT.name())) {
            return directLinkStore.get(urn);
        } else if (StringUtils.containsIgnoreCase(urn, Link.Type.EDGE.name())) {
            return edgeLinkStore.get(urn);
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MtudpStoreDao that = (MtudpStoreDao) o;

        return new EqualsBuilder()
                .append(directLinkStore, that.directLinkStore)
                .append(edgeLinkStore, that.edgeLinkStore)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(directLinkStore)
                .append(edgeLinkStore)
                .toHashCode();
    }
}
