package org.onosproject.net.intent;
import java.util.Collections;
import java.util.List;

import org.onosproject.core.ApplicationId;
import org.onosproject.net.ConnectPoint;
import org.onosproject.net.flow.TrafficSelector;
import org.onosproject.net.flow.TrafficTreatment;

import com.google.common.base.MoreObjects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class SolIntent extends ConnectivityIntent {
	
	private final ConnectPoint ingressPoint;
	private final ConnectPoint egressPoint;
	
	public static SolIntent.Builder builder() {
        return new Builder();
    }
	
	public static final class Builder extends ConnectivityIntent.Builder {
        ConnectPoint ingressPoint;
        ConnectPoint egressPoint;

        private Builder() {
            // Hide constructor
        }

        @Override
        public Builder appId(ApplicationId appId) {
            return (Builder) super.appId(appId);
        }

        @Override
        public Builder key(Key key) {
            return (Builder) super.key(key);
        }

        @Override
        public Builder selector(TrafficSelector selector) {
            return (Builder) super.selector(selector);
        }

        @Override
        public Builder treatment(TrafficTreatment treatment) {
            return (Builder) super.treatment(treatment);
        }

        @Override
        public Builder constraints(List<Constraint> constraints) {
            return (Builder) super.constraints(constraints);
        }

        @Override
        public Builder priority(int priority) {
            return (Builder) super.priority(priority);
        }

        /**
         * Sets the ingress point of SOL intent that will be built.
         *
         * @param ingressPoint ingress connect point
         * @return this builder
         */
        public Builder ingressPoint(ConnectPoint ingressPoint) {
            this.ingressPoint = ingressPoint;
            return this;
        }

        /**
         * Sets the egress point of the SOL intent that will be built.
         *
         * @param egressPoint egress connect point
         * @return this builder
         */
        public Builder egressPoint(ConnectPoint egressPoint) {
            this.egressPoint = egressPoint;
            return this;
        }

        /**
         * Builds a SOL intent from the accumulated parameters.
         *
         * @return point to point intent
         */
        public SolIntent build() {

            return new SolIntent(
                    appId,
                    key,
                    selector,
                    treatment,
                    ingressPoint,
                    egressPoint,
                    constraints,
                    priority
            );
        }
    }



    /**
     * Creates a new SOL intent with the supplied ingress/egress
     * ports and constraints.
     *
     * @param appId        application identifier
     * @param key          key of the intent
     * @param selector     traffic selector
     * @param treatment    treatment
     * @param ingressPoint ingress port
     * @param egressPoint  egress port
     * @param constraints  optional list of constraints
     * @param priority     priority to use for flows generated by this intent
     * @throws NullPointerException if {@code ingressPoint} or
     *        {@code egressPoints} or {@code appId} is null.
     */
    private SolIntent(ApplicationId appId,
                              Key key,
                              TrafficSelector selector,
                              TrafficTreatment treatment,
                              ConnectPoint ingressPoint,
                              ConnectPoint egressPoint,
                              List<Constraint> constraints,
                              int priority) {
        super(appId, key, Collections.emptyList(), selector, treatment, constraints,
                priority);

        checkArgument(!ingressPoint.equals(egressPoint),
                "ingress and egress should be different (ingress: %s, egress: %s)", ingressPoint, egressPoint);

        this.ingressPoint = checkNotNull(ingressPoint);
        this.egressPoint = checkNotNull(egressPoint);
    }

    /**
     * Constructor for serializer.
     */
    protected SolIntent() {
        super();
        this.ingressPoint = null;
        this.egressPoint = null;
    }

    /**
     * Returns the port on which the ingress traffic should be connected to
     * the egress.
     *
     * @return ingress port
     */
    public ConnectPoint ingressPoint() {
        return ingressPoint;
    }

    /**
     * Returns the port on which the traffic should egress.
     *
     * @return egress port
     */
    public ConnectPoint egressPoint() {
        return egressPoint;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("id", id())
                .add("key", key())
                .add("appId", appId())
                .add("priority", priority())
                .add("resources", resources())
                .add("selector", selector())
                .add("treatment", treatment())
                .add("ingress", ingressPoint)
                .add("egress", egressPoint)
                .add("constraints", constraints())
                .toString();
    }

}