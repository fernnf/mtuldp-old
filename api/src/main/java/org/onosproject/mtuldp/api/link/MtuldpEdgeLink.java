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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.onosproject.net.ConnectPoint;
import org.onosproject.net.Host;
import org.onosproject.net.Link;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by fernando on 17/03/16.
 */
public class MtuldpEdgeLink extends DefaultMtuldpLink {

    private final ConnectPoint device;
    private final Host host;

    private final String urnId;

    private final int mtuRate;

    private MtuldpEdgeLink(ConnectPoint device, Host host, String urnId, int mtuRate) {
        super(Link.Type.DIRECT);
        this.device = device;
        this.host = host;
        this.urnId = getUrnModule() + ":"+urnId;
        this.mtuRate = mtuRate;
    }

    public ConnectPoint getDevice() {
        return device;
    }

    public Host getHost() {
        return host;
    }

    public int getMtuRate() {
        return mtuRate;
    }

    public String getUrnLinkId() {
        return urnId;
    }

    public static final class Builder {

        private ConnectPoint device;
        private Host host;

        private int mtu;

        public Builder devicePoint(ConnectPoint device){
            this.device = device;
            return this;
        }

        public Builder hostPoint(Host host){
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
                    + host.mac().toString()
                    + ":vlan:"
                    + host.vlan()
                    + ":mtu:"
                    + mtu;
        }

        public MtuldpEdgeLink build(){

            checkNotNull(device,"The device cannot be null");
            checkNotNull(host, "The host cannot be null");
            checkNotNull(mtu, "The MTU rate cannot be null");

            return new MtuldpEdgeLink(device,host,buildLinkEdgeURN(),mtu);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MtuldpEdgeLink that = (MtuldpEdgeLink) o;

        return new EqualsBuilder()
                .append(mtuRate, that.mtuRate)
                .append(device, that.device)
                .append(host, that.host)
                .append(urnId, that.urnId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(device)
                .append(host)
                .append(urnId)
                .append(mtuRate)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "MtuldpEdgeLink{" +
                "device=" + device +
                ", host=" + host +
                ", urnId='" + urnId + '\'' +
                ", mtuRate=" + mtuRate +
                '}';
    }
}

