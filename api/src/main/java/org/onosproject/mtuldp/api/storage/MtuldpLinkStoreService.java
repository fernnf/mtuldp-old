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
import org.onosproject.net.ConnectPoint;

import java.util.Set;

/**
 * Created by fernando on 28/03/16.
 */

public interface MtuldpLinkStoreService {

    public void doAddLink(DefaultMtuldpLink link) ;

    public void doDelLink(ConnectPoint src, ConnectPoint dst);

    public void doUpdLink(ConnectPoint src, ConnectPoint dst, DefaultMtuldpLink newLink);

    public Set<MtuldpDirectLink> getDirectLinks();

    public Set<MtuldpEdgeLink> getEdgeLinks();

}
