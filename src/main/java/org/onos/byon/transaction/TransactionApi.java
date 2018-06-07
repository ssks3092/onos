package org.onos.byon.transaction;


public interface TransactionApi {

    void addProfileToMap(Profile profile, ProfileInfo info);

    void updateProfile(Profile profile, ProfileInfo profileInfo);

}
