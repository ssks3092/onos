/*
 * Copyright 2015 Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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

package org.onos.byon.cli;

import org.apache.karaf.shell.commands.Command;
import org.onos.byon.NetworkService;
import org.onos.byon.transaction.Profile;
import org.onos.byon.transaction.ProfileInfo;
import org.onos.byon.transaction.TransactionApi;
import org.onosproject.cli.AbstractShellCommand;
import org.onosproject.net.HostId;

/**
 * CLI to list networks.
 */
@Command(scope="byon", name="list-networks", description = "Lists all the networks")
public class ListNetworkCommand extends AbstractShellCommand {

    @Override
    protected void execute() {
        NetworkService networkService = get(NetworkService.class);
        TransactionApi api = get(TransactionApi.class);
        Profile newProfile = new Profile();
        ProfileInfo profileInfo = new ProfileInfo();
        ProfileInfo profileInfo1 = new ProfileInfo();
        newProfile.setId("ID1");
        newProfile.setName("Profile1");
        newProfile.setProfileInfo(profileInfo);

        api.addProfileToMap(newProfile, profileInfo);
        System.out.println("Profile added...");
/*        for (String net : networkService.getNetworks()) {
            print("%s", net);
            for (HostId hostId : networkService.getHosts(net)) {
                print("\t%s", hostId);
            }
        }*/
        System.out.println("Updating Profile ...");
        boolean isUpdated = api.updateProfile(newProfile, profileInfo1);
        System.out.println("Update status : "+ isUpdated);

    }
}
