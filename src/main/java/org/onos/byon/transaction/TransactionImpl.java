package org.onos.byon.transaction;



import org.apache.felix.scr.annotations.*;
import org.onlab.util.KryoNamespace;
import org.onosproject.store.serializers.KryoNamespaces;
import org.onosproject.store.service.ConsistentMap;
import org.onosproject.store.service.StorageService;
import org.onosproject.store.service.TransactionContext;
import org.onosproject.store.service.TransactionalMap;
import org.onosproject.store.service.CommitStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component(immediate = true)
@Service
public class TransactionImpl implements TransactionApi{
    private static final Logger log = LoggerFactory.getLogger(TransactionImpl.class);

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected StorageService storageService;

    private ConsistentMap<Profile, ProfileInfo> usualProfile;

    private  org.onosproject.store.service.Serializer USUALPROFILESERIALIZER =
            org.onosproject.store.service.Serializer.using(KryoNamespace.newBuilder().register(KryoNamespaces.API)
                    .register(Profile.class, ProfileInfo.class).build());

    @Activate
    protected void activate() {
        usualProfile = storageService.<Profile, ProfileInfo>consistentMapBuilder().withName(
                "user_profile").withSerializer(USUALPROFILESERIALIZER).build();

        log.info("Started");
    }

    @Deactivate
    protected void deactivate() {
        usualProfile.destroy();
        log.info("Stopped");
    }

    @Override
    public void addProfileToMap(Profile profile, ProfileInfo profileInfo) {
        log.debug("New Data to be added for phb profiles:{}", profile);
        int retryCount = 3;
        while (retryCount > 1) {
            TransactionContext transactionContext = storageService.transactionContextBuilder().build();
            transactionContext.begin();
            TransactionalMap<Profile, ProfileInfo> transactionalMap = transactionContext.getTransactionalMap(
                    usualProfile.name(), USUALPROFILESERIALIZER);
                transactionalMap.put(profile, profileInfo);
            CompletableFuture<CommitStatus> completableFutureStatus = transactionContext.commit();
            try {
                CommitStatus status = completableFutureStatus.get();
                if (!CommitStatus.SUCCESS.equals(status)) {
                    log.error("Failed to add PHB Profile data in data store, retrying.");
                    retryCount--;
                } else {
                    return;
                }
            } catch (InterruptedException | ExecutionException e) {
                log.error("Thread interrupted in addNewPhbProfiles", e);
                retryCount--;
            }
        }
    }

    @Override
    public void updateProfile(Profile profile, ProfileInfo profileInfo) {

    }
}
