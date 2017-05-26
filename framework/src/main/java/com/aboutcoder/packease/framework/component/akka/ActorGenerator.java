package com.aboutcoder.packease.framework.component.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * <Description>
 * Copyright © 2006-2017 AboutCoder.COM. All rights reserved.<br />
 *
 * @author chenjinlong<br />
 * @CreateDate 03/03/2017 11:49 AM<br />
 * @description <br />
 */
@Component
public class ActorGenerator {

    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private SpringAkkaExtension springAkkaExtension;

    /**
     * Actor creator wrapper.
     *
     * @param actorBeanName
     * @param actorName
     * @param args
     * @return
     */
    public ActorRef createUniqueActor(String actorBeanName, String actorName, Object... args) {
        String uniqueActorName = buildUniqueActorName(actorName);
        ActorRef actorRef = actorSystem.actorOf(springAkkaExtension.props(actorBeanName, args), uniqueActorName);
        actorSystem.log().debug("Create a new actor named:" + uniqueActorName);
        return actorRef;
    }

    /**
     * Actor creator wrapper.
     *
     * @param actorBeanName
     * @param args
     * @return
     */
    public ActorRef createUniqueActor(String actorBeanName, Object... args) {
        String uniqueActorName = buildUniqueActorName(actorBeanName);
        ActorRef actorRef = actorSystem.actorOf(springAkkaExtension.props(actorBeanName, args), uniqueActorName);
        actorSystem.log().debug("Create a new actor named:" + uniqueActorName);
        return actorRef;
    }

    /**
     * Actor creator wrapper.
     *
     * @param actorBeanName
     * @param actorName
     * @param args
     * @return
     */
    public ActorRef createDefinedActor(String actorBeanName, String actorName, Object... args) {
        ActorRef actorRef = actorSystem.actorOf(springAkkaExtension.props(actorBeanName, args), actorName);
        actorSystem.log().debug("Create a new actor named:" + actorName);
        return actorRef;
    }

    /**
     * Actor creator wrapper.
     *
     * @param actorBeanName
     * @param args
     * @return
     */
    public ActorRef createDefinedActor(String actorBeanName, Object... args) {
        ActorRef actorRef = actorSystem.actorOf(springAkkaExtension.props(actorBeanName, args), actorBeanName);
        actorSystem.log().debug("Create a new actor named:" + actorBeanName);
        return actorRef;
    }

    /**
     * Inbox creator wrapper.
     *
     * @return
     */
    public Inbox createInbox() {
        return Inbox.create(actorSystem);
    }

    /**
     * Inbox creator wrapper.
     *
     * @param actorRef
     * @return
     */
    public Inbox createInboxAndWatch(ActorRef actorRef) {
        final Inbox inbox = Inbox.create(actorSystem);
        inbox.watch(actorRef);
        return inbox;
    }

    /**
     * Generate an unique actor name for creating.
     *
     * @param actorNamePrefix
     * @return
     */
    private String buildUniqueActorName(String actorNamePrefix) {
        String uuid = UUID.randomUUID().toString();
        String uuidStr = uuid.replace("-", "");
        StringBuffer stringBuffer = new StringBuffer(actorNamePrefix).append("-").append(uuidStr);
        return stringBuffer.toString();
    }
}
