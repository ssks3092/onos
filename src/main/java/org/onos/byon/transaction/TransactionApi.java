package org.onos.byon.transaction;


public interface TransactionApi {

    void addProfileToMap(Profile profile, ProfileInfo info);

    boolean updateProfile(Profile profile, ProfileInfo profileInfo);

}
