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
import org.onosproject.net.Link;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by fernando on 17/03/16.
 */
public class MtuldpDirectLink extends DefaultMtuldpLink {

    private final ConnectPoint src;
    private final ConnectPoint dst;

    private final String urnId;

    private final int mtuRate;

    private MtuldpDirectLink(ConnectPoint src, ConnectPoint dst, String urnId, int mtuRate) {
        super(Link.Type.DIRECT);
        this.src = src;
        this.dst = dst;
        this.urnId = getUrnModule()+":"+urnId;
        this.mtuRate = mtuRate;
    }

    public ConnectPoint getPointIngress(){
        return src;
    }

    public ConnectPoint getPointEgress() {
        return dst;
    }

    public int getMtuRate() {
        return mtuRate;
    }

    public String getUrnLinkId() {
        return urnId;
    }

    public static final class Builder {

        private ConnectPoint ingress;
        private ConnectPoint egress;

        private int mtu;

        public Builder deviceIngress(ConnectPoint src){
            this.ingress = src;
            return this;
        }
        public Builder deviceEgress(ConnectPoint dst){
            this.egress = dst;
            return this;
        }

        public Builder mtuRate(int rate){
            this.mtu = rate;
            return this;
        }

        private String buildLinkDirectURN(){
            return "src:"
                    +ingress.deviceId().toString()
                    + ":port:"
                    + ingress.port().toString()
                    + ":dst:"
                    + egress.deviceId()
                    + ":port:"
                    + egress.port().toString()
                    + ":mtu:"
                    + mtu;
        }

        public MtuldpDirectLink build(){
            checkNotNull(ingress, "The ingress device cannot be null");
            checkNotNull(egress, "The egress device cannot be null");
            checkNotNull(mtu, "The MTU rate cannot be null");

            return new MtuldpDirectLink(ingress,egress,buildLinkDirectURN(),mtu);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MtuldpDirectLink that = (MtuldpDirectLink) o;

        return new EqualsBuilder()
                .append(mtuRate, that.mtuRate)
                .append(src, that.src)
                .append(dst, that.dst)
                .append(urnId, that.urnId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(src)
                .append(dst)
                .append(urnId)
                .append(mtuRate)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "\nMtuldpDirectLink{" +
                "\nsrc=" + src +
                ", \ndst=" + dst +
                ", \nmtuRate=" + mtuRate +
                ", \nurnId='" + urnId + '\'' +
                '}';
    }
}
