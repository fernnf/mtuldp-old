
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

import org.onosproject.net.Link;


public abstract class DefaultMtuldpLink {

    private final static String DEFAULT_URN_MODULE = "urn:onos:mtuldp:link";

    private final String obj_id;

    DefaultMtuldpLink(Link.Type type) {

        this.obj_id = DEFAULT_URN_MODULE + ":" + type.name();
    }

    public String getUrnModule() {
        return obj_id;
    }

    public String getDefaultUrnModule() {
        return DEFAULT_URN_MODULE;
    }
}