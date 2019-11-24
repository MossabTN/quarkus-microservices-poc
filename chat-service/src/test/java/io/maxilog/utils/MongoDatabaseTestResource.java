package io.maxilog.utils;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class MongoDatabaseTestResource implements QuarkusTestResourceLifecycleManager {

    private static final Logger LOGGER = Logger.getLogger(MongoDatabaseTestResource.class);
    private static MongodExecutable MONGO;


    @Override
    public Map<String, String> start() {
        Version.Main version = Version.Main.V3_4;
        int port = 27018;
        LOGGER.infof("Starting Mongo %s on port %s", version, port);
        IMongodConfig config = null;
        try {
            config = new MongodConfigBuilder()
                    .version(version)
                    .net(new Net(port, Network.localhostIsIPv6()))
                    .build();
            MONGO = MongodStarter.getDefaultInstance().prepare(config);
            MONGO.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyMap();
    }

    @Override
    public void stop() {
        if (MONGO != null) {
            MONGO.stop();
        }
    }
}
