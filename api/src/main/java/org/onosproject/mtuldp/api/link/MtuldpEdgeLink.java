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

import org.onosproject.net.ConnectPoint;
import org.onosproject.net.Link;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by fernando on 17/03/16.
 */
public class MtuldpEdgeLink extends DefaultMtuldpLink {

    private final ConnectPoint device;
    private final ConnectPoint host;

    private final String urnId;

    private final int mtuRate;

    private MtuldpEdgeLink(ConnectPoint device, ConnectPoint host, String urnId, int mtuRate) {
        super(Link.Type.EDGE);
        this.device = device;
        this.host = host;
        this.urnId = getUrnModule() + ":"+urnId;
        this.mtuRate = mtuRate;
    }

    public ConnectPoint getDevice() {
        return device;
    }

    public ConnectPoint getHost() {
        return host;
    }

    public int getMtuRate() {
        return mtuRate;
    }

    public String getUrnId() {
        return urnId;
    }

    public static final class Builder {

        private ConnectPoint device;
        private ConnectPoint host;

        private int mtu;

        public Builder devicePoint(ConnectPoint device){
            this.device = device;
            return this;
        }

        public Builder hostPoint(ConnectPoint host){
            this.host = host;
            return this;
        }

        public Builder mtuRate(int rate) {
            this.mtu = rate;
            return this;
        }

        private String buildLinkEdgeURN(){
            return device.deviceId().toString()
                    + ":port:"
                    + device.port().toString()
                    + host.hostId().toString()
                    + ":vlan:"
                    + host.hostId().vlanId()
                    + ":mtu:"
                    + mtu;
        }

        public MtuldpEdgeLink build(){

            checkNotNull(device,"The device cannot be null");
            checkNotNull(host, "The host cannot be null");
            checkNotNull(host.hostId(),"The host description cannot be null");
            checkNotNull(host.ipElementId(), "The host ip description cannot be null");
            checkNotNull(mtu, "The MTU rate cannot be null");

            return new MtuldpEdgeLink(device,host,buildLinkEdgeURN(),mtu);
        }
    }
}

