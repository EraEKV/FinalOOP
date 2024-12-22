package Users;

import System.Organization;

public interface ManageOrganization {

    void joinOrganization(Organization org);
    void createOrganization(String name);
    void leaveOrganization(Organization org);

}
